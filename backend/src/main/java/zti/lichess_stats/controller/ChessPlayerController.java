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
import zti.lichess_stats.service.PlayerService;

@CrossOrigin(origins = {"http://localhost:5173/", "https://vis4rd.github.io/ait_project_2023/"},
    maxAge = 3_600)
@RestController
@RequestMapping("/chess/player")
public class ChessPlayerController
{
    Client lichessClient = Client.basic();
    private final PlayerService playerService;

    @Autowired
    public ChessPlayerController(PlayerService playerService)
    {
        this.playerService = playerService;
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable String playerId)
    {
        System.out.println("/CHESS/PLAYER/" + playerId.toUpperCase());
        if(!playerService.ensurePlayerExists(playerId))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player '" + playerId + "' does not exist");
        }

        // TODO: check if last cache date is older than 1 day
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerById(playerId));
    }

    @PostMapping("/")
    public ResponseEntity<?> createPlayer(@RequestBody Player player)
    {
        System.out.println("POST:: /CHESS/PLAYER/");
        playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body("Player created successfully!");
    }
}
