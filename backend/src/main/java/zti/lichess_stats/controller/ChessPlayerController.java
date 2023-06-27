package zti.lichess_stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "http://localhost:5173/", maxAge = 3_600)
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
    public Player getPlayerById(@PathVariable String playerId)
    {
        System.out.println("/CHESS/PLAYER/" + playerId.toUpperCase());
        if(!playerService.ensurePlayerExists(playerId))
        {
            return null;  // TODO: return response here instead of raw data
        }

        // TODO: check if last cache date is older than 1 day
        return playerService.getPlayerById(
            playerId);  // TODO: return response here instead of raw data
    }

    @PostMapping("/")
    public Player createPlayer(@RequestBody Player player)
    {
        System.out.println("POST:: /CHESS/PLAYER/");
        return playerService.createPlayer(
            player);  // TODO: return response here instead of raw data
    }
}
