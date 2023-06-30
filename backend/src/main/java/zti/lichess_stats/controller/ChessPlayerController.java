package zti.lichess_stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chariot.Client;

import zti.lichess_stats.model.Player;
import zti.lichess_stats.service.MetadataService;
import zti.lichess_stats.service.PlayerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3_600)
@RestController
@RequestMapping("/chess/player")
public class ChessPlayerController
{
    Client lichessClient = Client.basic();
    private final PlayerService playerService;
    private final MetadataService metadataService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for ChessPlayerController.
     * @param playerService Service used to interact with the Player repository.
     * @param metadataService Service used to interact with the Metadata repository.
     */
    @Autowired
    public ChessPlayerController(PlayerService playerService, MetadataService metadataService)
    {
        this.playerService = playerService;
        this.metadataService = metadataService;
    }

    /**
     * Returns a player either from database cache or Lichess API.
     * @param playerId Unique player ID from Lichess account.
     * @return Player object.
     */
    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable String playerId)
    {
        log.info("/CHESS/PLAYER/" + playerId.toUpperCase());
        if(!playerService.ensurePlayerExists(playerId))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player '" + playerId + "' does not exist");
        }

        var player = playerService.getPlayerById(playerId);
        if(!metadataService.isPlayerOutdated(playerId))
        {
            log.info("Returning from SQL database...");
            return ResponseEntity.status(HttpStatus.OK).body(player);
        }

        log.info("Returning from Lichess API...");
        var lichessPlayer = lichessClient.users().byId(playerId).get();

        player.setSeen(lichessPlayer.seenAt());
        player.setTime_played(lichessPlayer.playTimeTotal().toString());
        playerService.updatePlayer(player);

        return ResponseEntity.status(HttpStatus.OK).body(player);
    }

    /**
     * Creates a new player in the database.
     * @param player Player object.
     * @return Response informing about creation result.
     */
    @PostMapping("/")
    public ResponseEntity<?> createPlayer(@RequestBody Player player)
    {
        log.info("POST:: /CHESS/PLAYER/");
        playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body("Player created successfully!");
    }
}
