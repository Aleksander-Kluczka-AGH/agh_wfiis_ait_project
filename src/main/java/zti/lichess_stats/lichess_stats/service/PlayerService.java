package zti.lichess_stats.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import zti.lichess_stats.lichess_stats.model.Player;
import zti.lichess_stats.lichess_stats.repository.PlayerRepository;

@Service
public class PlayerService
{
    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() { return playerRepository.findAll(); }

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
}
