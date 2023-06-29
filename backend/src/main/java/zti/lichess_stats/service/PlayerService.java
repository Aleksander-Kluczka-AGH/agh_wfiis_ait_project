package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chariot.Client;

import java.util.Optional;

import zti.lichess_stats.model.Player;
import zti.lichess_stats.repository.PlayerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PlayerService
{
    @Autowired
    private PlayerRepository playerRepository;
    Client lichessClient = Client.basic();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Player getPlayerById(String playerId)
    {
        Optional<Player> optionalUser = playerRepository.findById(playerId.toLowerCase());
        return optionalUser.orElse(null);
    }

    public Player createPlayer(Player player) { return playerRepository.save(player); }

    public Player updatePlayer(Player player) { return playerRepository.save(player); }

    public boolean doesPlayerExist(String playerId)
    {
        return playerRepository.existsById(playerId.toLowerCase());
    }

    public boolean ensurePlayerExists(String username)
    {
        if(this.doesPlayerExist(username))
        {
            log.info("ensurePlayerExists: Player exists, returning true.");
            return true;
        }

        log.info("ensurePlayerExists: Retrieving player from Lichess API: " + username);
        var lichessUser = lichessClient.users().byId(username).get();
        if(lichessUser == null)
        {
            log.info("ensurePlayerExists: Lichess player '" + username
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

        log.info("ensurePlayerExists: Inserting player '" + username + "' into database...");
        this.createPlayer(player);

        log.info("ensurePlayerExists: Player '" + username + "' inserted, returning true.");
        return true;
    }
}
