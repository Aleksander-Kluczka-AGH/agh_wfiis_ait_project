package zti.lichess_stats.lichess_stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chariot.Client;
import chariot.model.StatsPerf.StatsPerfGame;
import chariot.model.StatsPerfType;

import zti.lichess_stats.lichess_stats.model.Stats;
import zti.lichess_stats.lichess_stats.service.StatsService;

@RestController
@RequestMapping("/chess/stats")
public class StatsController
{
    Client lichessClient = Client.basic();
    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService)
    {
        this.statsService = statsService;
    }

    @GetMapping("/{username}")
    public Stats getStatsByPlayerId(@PathVariable String username)
    {
        System.out.println("/CHESS/STATS/" + username.toUpperCase());

        Stats resultsFromDb = statsService.getStatsByPlayerId(username);
        if(resultsFromDb != null)
        {
            // TODO: check if last cache date is older than 1 day
            System.out.println("Returning from SQL database...");
            return resultsFromDb;
        }

        var lichessStats1 = lichessClient.users().byId(username).get().accountStats();
        var lichessStats2 = lichessClient.users().byId(username).get().ratings();
        var puzzleStats = (StatsPerfGame) lichessStats2.get(StatsPerfType.puzzle);
        var rapidStats = (StatsPerfGame) lichessStats2.get(StatsPerfType.rapid);
        var bulletStats = (StatsPerfGame) lichessStats2.get(StatsPerfType.bullet);
        var blitzStats = (StatsPerfGame) lichessStats2.get(StatsPerfType.blitz);
        var classicalStats = (StatsPerfGame) lichessStats2.get(StatsPerfType.classical);
        var correspondenceStats = (StatsPerfGame) lichessStats2.get(StatsPerfType.correspondence);

        var puzzleGames = puzzleStats != null ? Long.valueOf(puzzleStats.games()) : 0L;
        var puzzleRating = puzzleStats != null ? Long.valueOf(puzzleStats.rating()) : 0L;
        var rapidGames = rapidStats != null ? Long.valueOf(rapidStats.games()) : 0L;
        var rapidRating = rapidStats != null ? Long.valueOf(rapidStats.rating()) : 0L;
        var blitzGames = blitzStats != null ? Long.valueOf(blitzStats.games()) : 0L;
        var blitzRating = blitzStats != null ? Long.valueOf(blitzStats.rating()) : 0L;
        var bulletGames = bulletStats != null ? Long.valueOf(bulletStats.games()) : 0L;
        var bulletRating = bulletStats != null ? Long.valueOf(bulletStats.rating()) : 0L;
        var classicalGames = classicalStats != null ? Long.valueOf(classicalStats.games()) : 0L;
        var classicalRating = classicalStats != null ? Long.valueOf(classicalStats.rating()) : 0L;
        var correspondenceGames =
            correspondenceStats != null ? Long.valueOf(correspondenceStats.games()) : 0L;
        var correspondenceRating =
            correspondenceStats != null ? Long.valueOf(correspondenceStats.rating()) : 0L;

        System.out.println("Returning from Lichess API...");
        Stats newStats = statsService.createStatsNative(username,
            Long.valueOf(lichessStats1.all()),
            Long.valueOf(lichessStats1.rated()),
            Long.valueOf(lichessStats1.ai()),
            Long.valueOf(lichessStats1.drawH()),
            Long.valueOf(lichessStats1.lossH()),
            Long.valueOf(lichessStats1.winH()),
            Long.valueOf(lichessStats1.imported()),
            Long.valueOf(puzzleGames),
            Long.valueOf(puzzleRating),
            Long.valueOf(rapidGames),
            Long.valueOf(rapidRating),
            Long.valueOf(blitzGames),
            Long.valueOf(blitzRating),
            Long.valueOf(bulletGames),
            Long.valueOf(bulletRating),
            Long.valueOf(classicalGames),
            Long.valueOf(classicalRating),
            Long.valueOf(correspondenceGames),
            Long.valueOf(correspondenceRating));

        return newStats;
    }
}
