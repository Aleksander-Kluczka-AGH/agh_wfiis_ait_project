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

import java.util.ArrayList;
import java.util.List;

import zti.lichess_stats.model.GameResult;
import zti.lichess_stats.service.GameResultService;
import zti.lichess_stats.service.MetadataService;
import zti.lichess_stats.service.PlayerService;

@CrossOrigin(origins = {"http://localhost:5173/", "https://vis4rd.github.io/ait_project_2023/"},
    maxAge = 3_600)
@RestController
@RequestMapping("/chess/game")
public class ChessGameResultController
{
    Client lichessClient = Client.basic();
    private final GameResultService gameResultService;
    private final PlayerService playerService;
    private final MetadataService metadataService;

    @Autowired
    public ChessGameResultController(GameResultService gameResultService,
        PlayerService playerService,
        MetadataService metadataService)
    {
        this.gameResultService = gameResultService;
        this.playerService = playerService;
        this.metadataService = metadataService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getGameResultsByPlayerId(@PathVariable String username)
    {
        System.out.println("/CHESS/GAME/" + username.toUpperCase());

        if(!playerService.ensurePlayerExists(username))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player '" + username + "' does not exist");
        }

        // if(metadataService.areStatsOutdated()) { }
        // TODO: check if last cache date is older than 1 day


        List<GameResult> resultsFromDb = gameResultService.getGameResultsByPlayerId(username);
        if(!resultsFromDb.isEmpty())
        {
            System.out.println("Returning from SQL database...");
            return ResponseEntity.status(HttpStatus.OK).body(resultsFromDb);
        }

        Many<RatingHistory> ratingHistories = lichessClient.users().ratingHistoryById(username);
        for(var ratingHistory : ratingHistories.stream().toList())
        {
            var formatName = ratingHistory.name().toLowerCase().replaceAll(" ", "");
            System.out.println("Format = " + formatName);
            switch(formatName)
            {
                case "puzzles": System.out.println("Skipping puzzles format..."); continue;
                case "three-check": System.out.println("Skipping three-check format..."); continue;

                default: break;
            }

            for(var gameResult : ratingHistory.results())
            {
                if(resultsFromDb.stream().anyMatch(result
                       -> result.getDate().equals(gameResult.date())
                              && result.getPoints().equals(gameResult.points().longValue())))
                {
                    System.out.println("Skipping date = " + gameResult.date().toString()
                                       + ", points = " + gameResult.points().longValue()
                                       + " because it already exists in the database.");
                    continue;
                }
                System.out.print("points = " + gameResult.points() + ", ");
                System.out.println("date = " + gameResult.date().toString());
                gameResultService.createGameResultNative(gameResult.points().longValue(),
                    gameResult.date(),
                    username.toLowerCase(),
                    formatName);
            }
        }

        System.out.println("Returning from Lichess API...");
        return ResponseEntity.status(HttpStatus.OK)
            .body(gameResultService.getGameResultsByPlayerId(username));
    }
}
