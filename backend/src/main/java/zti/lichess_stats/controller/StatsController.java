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
import chariot.model.StatsPerf.StatsPerfGame;
import chariot.model.StatsPerfType;

import zti.lichess_stats.model.Stats;
import zti.lichess_stats.service.MetadataService;
import zti.lichess_stats.service.PlayerService;
import zti.lichess_stats.service.StatsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3_600)
@RestController
@RequestMapping("/chess/stats")
public class StatsController
{
    Client lichessClient = Client.basic();
    private final StatsService statsService;
    private final PlayerService playerService;
    private final MetadataService metadataService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for StatsController.
     * @param statsService Service used to interact with the Stats repository.
     * @param playerService Service used to interact with the Player repository.
     * @param metadataService Service used to interact with the Metadata repository.
     */
    @Autowired
    public StatsController(StatsService statsService,
        PlayerService playerService,
        MetadataService metadataService)
    {
        this.statsService = statsService;
        this.playerService = playerService;
        this.metadataService = metadataService;
    }

    /**
     * Returns all statistics for a given player (either from database cache or from Lichess API).
     * @param username Unique player ID from Lichess account.
     * @return Statistics of given player.
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getStatsByPlayerId(@PathVariable String username)
    {
        log.info("/CHESS/STATS/" + username.toUpperCase());
        if(!playerService.ensurePlayerExists(username))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player '" + username + "' does not exist");
        }

        if(statsService.doesStatsExistForPlayerId(username))
        {
            if(!metadataService.areStatsOutdated(username))
            {
                log.info("Returning from SQL database...");
                return ResponseEntity.status(HttpStatus.OK)
                    .body(statsService.getStatsByPlayerId(username));
            }
            else
            {
                log.info("Stats cache is too old, deleting existing entry...");
                statsService.deleteStatsByPlayerId(username);
            }
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

        log.info("Returning from Lichess API...");
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

        return ResponseEntity.status(HttpStatus.OK).body(newStats);
    }
}
