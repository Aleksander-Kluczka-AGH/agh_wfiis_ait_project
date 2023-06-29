package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import zti.lichess_stats.model.Stats;
import zti.lichess_stats.repository.StatsRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StatsService
{
    @Autowired
    private StatsRepository statsRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Returns stats by player ID.
     * @param playerId Unique player ID from Lichess account.
     * @return Stats object.
     */
    public Stats getStatsByPlayerId(String playerId)
    {
        Optional<Stats> optionalStats = statsRepository.findByPlayerId(playerId.toLowerCase());
        return optionalStats.orElse(null);
    }

    /**
     * Checks whether stats for given player ID exist in the database.
     * @param playerId Unique player ID from Lichess account.
     * @return True if stats exist in the database, false otherwise.
     */
    public boolean doesStatsExistForPlayerId(String playerId)
    {
        return statsRepository.existsByPlayerId(playerId.toLowerCase());
    }

    /**
     * Deletes stats from the database by player ID.
     * @param playerId Unique player ID from Lichess account.
     */
    public void deleteStatsByPlayerId(String playerId)
    {
        statsRepository.deleteByPlayerId(playerId.toLowerCase());
    }

    /**
     * Creates a new stats object in the database.
     * @param stats Stats object.
     * @return Inserted stats object.
     */
    public Stats createStats(Stats stats) { return statsRepository.save(stats); }

    /**
     * Creates a new stats object in the database for a given player. This method does not require
     * to obtain Player object in order to insert stats into the database.
     * @param id Unique player ID from Lichess account.
     * @param all Total number of games played.
     * @param rated Total number of rated games played.
     * @param ai Total number of games played against computer.
     * @param drawn Total number of drawn games.
     * @param lost Total number of lost games.
     * @param won Total number of won games.
     * @param imported Total number of imported games.
     * @param puzzle_count Total number of puzzles solved.
     * @param puzzle_rating Rating of the player in puzzles.
     * @param rapid_count Total number of rapid games played.
     * @param rapid_rating Rating of the player in rapid games.
     * @param blitz_count Total number of blitz games played.
     * @param blitz_rating Rating of the player in blitz games.
     * @param bullet_count Total number of bullet games played.
     * @param bullet_rating Rating of the player in bullet games.
     * @param classical_count Total number of classical games played.
     * @param classical_rating Rating of the player in classical games.
     * @param correspondence_count Total number of correspondence games played.
     * @param correspondence_rating Rating of the player in correspondence games.
     * @return Inserted stats object.
     */
    public Stats createStatsNative(String id,
        Long all,
        Long rated,
        Long ai,
        Long drawn,
        Long lost,
        Long won,
        Long imported,
        Long puzzle_count,
        Long puzzle_rating,
        Long rapid_count,
        Long rapid_rating,
        Long blitz_count,
        Long blitz_rating,
        Long bullet_count,
        Long bullet_rating,
        Long classical_count,
        Long classical_rating,
        Long correspondence_count,
        Long correspondence_rating)
    {
        try
        {
            statsRepository.saveNative(id.toLowerCase(),
                all,
                rated,
                ai,
                drawn,
                lost,
                won,
                imported,
                puzzle_count,
                puzzle_rating,
                rapid_count,
                rapid_rating,
                blitz_count,
                blitz_rating,
                bullet_count,
                bullet_rating,
                classical_count,
                classical_rating,
                correspondence_count,
                correspondence_rating);
        }
        catch(ConstraintViolationException e)
        {
            log.info("TABLE CONSTRAINT ERROR: " + e);
        }
        catch(Exception e)
        {
            log.info("UNKNOWN ERROR: " + e);
        }
        return this.getStatsByPlayerId(id);
    }
}
