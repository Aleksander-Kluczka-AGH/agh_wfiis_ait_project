package zti.lichess_stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chariot.Client;
import chariot.model.Many;
import chariot.model.RatingHistory;

import java.util.List;

import zti.lichess_stats.model.GameResult;
import zti.lichess_stats.service.GameResultService;
import zti.lichess_stats.service.MetadataService;
import zti.lichess_stats.service.PlayerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3_600)
@RestController
@RequestMapping("/chess/game")
public class ChessGameResultController
{
    Client lichessClient = Client.basic();
    private final GameResultService gameResultService;
    private final PlayerService playerService;
    private final MetadataService metadataService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for ChessGameResultController.
     * @param gameResultService Service used to interact with the GameResult repository.
     * @param playerService Service used to interact with the Player repository.
     * @param metadataService Service used to interact with the Metadata repository.
     */
    @Autowired
    public ChessGameResultController(GameResultService gameResultService,
        PlayerService playerService,
        MetadataService metadataService)
    {
        this.gameResultService = gameResultService;
        this.playerService = playerService;
        this.metadataService = metadataService;
    }

    /**
     * Returns all game results for a given player.
     * @param username Unique player ID from Lichess account.
     * @return List of GameResult objects.
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getGameResultsByPlayerId(@PathVariable String username)
    {
        log.info("/CHESS/GAME/" + username.toUpperCase());

        if(!playerService.ensurePlayerExists(username))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player '" + username + "' does not exist");
        }

        List<GameResult> resultsFromDb = gameResultService.getGameResultsByPlayerId(username);

        if(!metadataService.areGameResultsOutdated(username))
        {
            log.info("Returning from SQL database...");
            return ResponseEntity.status(HttpStatus.OK).body(resultsFromDb);
        }

        Many<RatingHistory> ratingHistories = lichessClient.users().ratingHistoryById(username);
        for(var ratingHistory : ratingHistories.stream().toList())
        {
            var formatName = ratingHistory.name().toLowerCase().replaceAll(" ", "");
            log.info("Format = " + formatName);
            switch(formatName)
            {
                case "puzzles": log.info("Skipping puzzles format..."); continue;
                case "three-check": log.info("Skipping three-check format..."); continue;

                default: break;
            }

            for(var gameResult : ratingHistory.results())
            {
                if(resultsFromDb.stream().anyMatch(result
                       -> result.getDate().equals(gameResult.date())
                              && result.getPoints().equals(gameResult.points().longValue())))
                {
                    log.info("Skipping date = " + gameResult.date().toString()
                             + ", points = " + gameResult.points().longValue()
                             + " because it already exists in the database.");
                    continue;
                }
                log.info(
                    "points = " + gameResult.points() + ", date = " + gameResult.date().toString());
                gameResultService.createGameResultNative(gameResult.points().longValue(),
                    gameResult.date(),
                    username.toLowerCase(),
                    formatName);
            }
        }

        log.info("Returning from Lichess API...");
        return ResponseEntity.status(HttpStatus.OK)
            .body(gameResultService.getGameResultsByPlayerId(username));
    }
}
