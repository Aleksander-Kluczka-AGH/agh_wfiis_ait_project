package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import zti.lichess_stats.model.GameResult;
import zti.lichess_stats.repository.GameResultRepository;

@Service
public class GameResultService
{
    @Autowired
    private GameResultRepository gameResultRepository;

    /**
     * Returns all game results from the database.
     * @return List of all game results.
     */
    public List<GameResult> getAllGameResults() { return gameResultRepository.findAll(); }

    /**
     * Returns a game result by its ID.
     * @param id ID of the game result.
     * @return Game result object.
     */
    public GameResult getGameResultById(Long id)
    {
        Optional<GameResult> optionalUser = gameResultRepository.findById(id);
        return optionalUser.orElse(null);
    }

    /**
     * Returns all game results of a player.
     * @param playerId Unique player ID from Lichess account.
     * @return List of all game results of a player.
     */
    public List<GameResult> getGameResultsByPlayerId(String playerId)
    {
        return gameResultRepository.findAllByPlayerId(playerId);
    }

    /**
     * Creates a new game result in the database.
     * @param gameResult Game result object.
     * @return Inserted game result object.
     */
    public GameResult createGameResult(GameResult gameResult)
    {
        return gameResultRepository.save(gameResult);
    }

    /**
     * Creates a new game result in the database for a given player. This method does not require to
     * obtain Player object before inserting it into the database.
     * @param points Rating status after the game has finished.
     * @param date Date when the game was played.
     * @param playerId Unique player ID from Lichess account.
     * @param format Format of the game (e.g. "blitz", "rapid", "bullet").
     */
    public void createGameResultNative(Long points, LocalDate date, String playerId, String format)
    {
        gameResultRepository.saveNative(points, date, playerId, format);
    }
}
