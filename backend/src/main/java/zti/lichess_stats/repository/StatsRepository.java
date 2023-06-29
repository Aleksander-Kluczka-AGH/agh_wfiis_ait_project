package zti.lichess_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import zti.lichess_stats.model.Stats;

@Repository
public interface StatsRepository extends JpaRepository<Stats, String> {
    /**
     * Deletes stats by player ID.
     * @param player_id Unique player ID from Lichess account.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM stats WHERE player_id = ?1", nativeQuery = true)
    void deleteByPlayerId(String player_id);

    /**
     * Saves stats to the database. This method does not require to obtain Stats object in order to
     * insert it into the database. The main obstacle here is Player foreign key, which would
     * require Player persistence object.
     * @param player_id Unique player ID from Lichess account.
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
     * @throws Exception Thrown when given player ID does not exist in the database.
     */
    @Transactional
    @Modifying
    @Query(
        value =
            "INSERT INTO stats (player_id, \"all\", rated, ai, drawn, lost, won, imported, puzzle_count, puzzle_rating, rapid_count, rapid_rating, blitz_count, blitz_rating, bullet_count, bullet_rating, classical_count, classical_rating, correspondence_count, correspondence_rating) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20)",
        nativeQuery = true)
    void saveNative(String player_id,
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
        Long correspondence_rating) throws Exception;

    /**
     * Finds stats by player ID.
     * @param player_id Unique player ID from Lichess account.
     * @return Stats object if found, empty optional otherwise.
     */
    Optional<Stats> findByPlayerId(String player_id);

    /**
     * Checks with the database if stats exist for given player ID.
     * @param player_id Unique player ID from Lichess account.
     * @return True if stats exist for given player ID, false otherwise.
     */
    boolean existsByPlayerId(String player_id);
}
