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
        Optional<Player> optionalUser = playerRepository.findById(playerId.toLowerCase());
        return optionalUser.orElse(null);
    }

    public Player createPlayer(Player player) { return playerRepository.save(player); }

    public boolean doesPlayerExist(String playerId)
    {
        return playerRepository.existsById(playerId.toLowerCase());
    }

    public boolean ensurePlayerExists(String username)
    {
        if(this.doesPlayerExist(username))
        {
            System.out.println("ensurePlayerExists: Player exists, returning true.");
            return true;
        }

        System.out.println("ensurePlayerExists: Retrieving player from Lichess API: " + username);
        var lichessUser = lichessClient.users().byId(username).get();
        if(lichessUser == null)
        {
            System.out.println("ensurePlayerExists: Lichess player '" + username
                               + "' does not exist, returning false.");
            return false;
        }


        String playTime;
        if(lichessUser.playTimeTotal() != null)
        {
            playTime = lichessUser.playTimeTotal().toString();
        }
        else
        {
            playTime = new String("0");
        }
        Player player = new Player(lichessUser.id(),
            lichessUser.name(),
            lichessUser.url().toString(),
            lichessUser.createdAt(),
            lichessUser.seenAt(),
            playTime);

        System.out.println(
            "ensurePlayerExists: Inserting player '" + username + "' into database...");
        this.createPlayer(player);

        System.out.println(
            "ensurePlayerExists: Player '" + username + "' inserted, returning true.");
        return true;
    }
}
