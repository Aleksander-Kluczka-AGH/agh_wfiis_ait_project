package zti.lichess_stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chariot.Client;
import chariot.model.Many;
import chariot.model.RatingHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import zti.lichess_stats.model.GameResult;
import zti.lichess_stats.model.Player;
import zti.lichess_stats.service.GameResultService;
import zti.lichess_stats.service.MetadataService;
import zti.lichess_stats.service.PlayerService;

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
    public List<GameResult> getGameResultsByPlayerId(@PathVariable String username)
    {
        System.out.println("/CHESS/GAME/" + username.toUpperCase());

        // if(metadataService.refreshGameResultsCacheDatetime(username, LocalDate.now())) { }

        List<GameResult> resultsFromDb = gameResultService.getGameResultsByPlayerId(username);
        if(!resultsFromDb.isEmpty())
        {
            System.out.println("Returning from SQL database...");
            return resultsFromDb;
        }

        // TODO: make this part a function in PlayerService, reuse in StatsController
        if(!playerService.doesPlayerExist(username))
        {
            System.out.println("Inserting player '" + username + "' into database...");
            var lichessUser = lichessClient.users().byId(username).get();
            if(lichessUser != null)
            {
                System.out.println("Retrieved player from Lichess API: " + lichessUser.name());
                Player player = new Player(lichessUser.id(),
                    lichessUser.name(),
                    lichessUser.url().toString(),
                    lichessUser.createdAt(),
                    lichessUser.seenAt(),
                    lichessUser.playTimeTotal().toString());
                playerService.createPlayer(player);
            }
            else
            {
                System.out.println("Lichess player '" + username + "' does not exist.");
                return new ArrayList<GameResult>();
            }
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
        return gameResultService.getGameResultsByPlayerId(username);
    }
}
