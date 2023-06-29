package zti.lichess_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import zti.lichess_stats.model.Metadata;

import org.hibernate.exception.ConstraintViolationException;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    /**
     * Saves a metadata to the database. This method does not require to obtain Metadata object.
     * @param playerId Unique player ID from Lichess account.
     * @throws ConstraintViolationException Thrown when given player does not exist in the database.
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO metadata (player_id) VALUES (?1)", nativeQuery = true)
    void saveNative(String playerId) throws ConstraintViolationException;

    /**
     * Checks with the database if player cache is outdated.
     * @param playerId Unique player ID from Lichess account.
     * @return True if player cache is outdated, false otherwise.
     */
    @Query(value = "SELECT check_player_cache_outdated(?1)", nativeQuery = true)
    boolean checkPlayerCacheOutdated(String playerId);

    /**
     * Checks with the database if stats cache is outdated.
     * @param playerId Unique player ID from Lichess account.
     * @return True if stats cache is outdated, false otherwise.
     */
    @Query(value = "SELECT check_stats_cache_outdated(?1)", nativeQuery = true)
    boolean checkStatsCacheOutdated(String playerId);

    /**
     * Checks with the database if game results cache is outdated.
     * @param playerId Unique player ID from Lichess account.
     * @return True if game results cache is outdated, false otherwise.
     */
    @Query(value = "SELECT check_game_results_cache_outdated(?1)", nativeQuery = true)
    boolean checkGameResultsCacheOutdated(String playerId);

    Metadata findByPlayerId(String playerId);
}
