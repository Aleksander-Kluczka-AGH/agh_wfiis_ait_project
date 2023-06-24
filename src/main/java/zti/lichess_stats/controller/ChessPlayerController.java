package zti.lichess_stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chariot.Client;

import zti.lichess_stats.model.Player;
import zti.lichess_stats.service.PlayerService;

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

        Player playerFromDb = playerService.getPlayerById(playerId);
        if(playerFromDb != null)
        {
            System.out.println("Returning from SQL database...");

            return playerFromDb;
        }

        var lichessUser = lichessClient.users().byId(playerId).get();
        Player player = new Player(lichessUser.id(),
            lichessUser.name(),
            lichessUser.url().toString(),
            lichessUser.createdAt(),
            lichessUser.seenAt(),
            lichessUser.playTimeTotal().toString());
        playerService.createPlayer(player);

        System.out.println("Returning from Lichess API...");
        return player;
    }

    @PostMapping("/")
    public Player createPlayer(@RequestBody Player player)
    {
        System.out.println("POST:: /CHESS/PLAYER/");
        return playerService.createPlayer(player);
    }
}
