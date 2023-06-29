package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import zti.lichess_stats.model.GameFormat;
import zti.lichess_stats.repository.GameFormatRepository;

/**
 * This service is kept for eventual future extension, i.e. support of automatic addition of new
 * game formats. As of right now, it is not used.
 */
@Service
public class GameFormatService
{
    @Autowired
    private GameFormatRepository gameFormatRepository;

    /**
     * Returns all game formats from the database.
     * @return List of all game formats.
     */
    public List<GameFormat> getAllGameFormats() { return gameFormatRepository.findAll(); }

    /**
     * Returns a game format by its name.
     * @param name Name of the game format.
     * @return Game format object.
     */
    public GameFormat getGameFormatByName(String name)
    {
        Optional<GameFormat> optionalUser = gameFormatRepository.findById(name);
        return optionalUser.orElse(null);
    }
}
