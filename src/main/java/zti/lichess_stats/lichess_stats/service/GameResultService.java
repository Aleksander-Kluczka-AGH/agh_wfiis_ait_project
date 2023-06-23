package zti.lichess_stats.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import zti.lichess_stats.lichess_stats.model.GameResult;
import zti.lichess_stats.lichess_stats.repository.GameResultRepository;

@Service
public class GameResultService
{
    @Autowired
    private GameResultRepository gameResultRepository;

    public List<GameResult> getAllGameFormats() { return gameResultRepository.findAll(); }

    public GameResult getGameResultById(Long id)
    {
        Optional<GameResult> optionalUser = gameResultRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<GameResult> getGameResultsByPlayerId(String playerId)
    {
        return gameResultRepository.findAllByPlayerId(playerId);
    }

    public void createGameResult(GameResult gameResult) { gameResultRepository.save(gameResult); }

    public void createGameResultNative(Long points, LocalDate date, String playerId, String format)
    {
        gameResultRepository.saveNative(points, date, playerId, format);
    }
}
