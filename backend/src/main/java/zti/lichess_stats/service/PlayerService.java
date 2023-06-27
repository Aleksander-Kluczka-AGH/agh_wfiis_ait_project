package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chariot.Client;

import java.util.Optional;

import zti.lichess_stats.model.Player;
import zti.lichess_stats.repository.PlayerRepository;

@Service
public class PlayerService
{
    @Autowired
    private PlayerRepository playerRepository;

    Client lichessClient = Client.basic();

    public Player getPlayerById(String playerId)
    {
        Optional<Player> optionalUser = playerRepository.findById(playerId);
        return optionalUser.orElse(null);
    }

    public Player createPlayer(Player player) { return playerRepository.save(player); }

    public boolean doesPlayerExist(String playerId)
    {
        return playerRepository.existsById(playerId);
    }

    public boolean ensurePlayerExists(String username)
    {
        if(this.doesPlayerExist(username))
        {
            return true;
        }

        System.out.println("Inserting player '" + username + "' into database...");
        var lichessUser = lichessClient.users().byId(username).get();
        if(lichessUser == null)
        {
            System.out.println("Lichess player '" + username + "' does not exist.");
            return false;
        }

        System.out.println("Retrieved player from Lichess API: " + lichessUser.name());
        Player player = new Player(lichessUser.id(),
            lichessUser.name(),
            lichessUser.url().toString(),
            lichessUser.createdAt(),
            lichessUser.seenAt(),
            lichessUser.playTimeTotal().toString());
        this.createPlayer(player);
        return true;
    }
}
