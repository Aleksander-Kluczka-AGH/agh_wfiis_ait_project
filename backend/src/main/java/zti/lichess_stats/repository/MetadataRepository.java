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
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO metadata (player_id) VALUES (?1)", nativeQuery = true)
    void saveNative(String playerId) throws ConstraintViolationException;

    @Query(value = "SELECT check_player_cache_outdated(?1)", nativeQuery = true)
    boolean checkPlayerCacheOutdated(String playerId);

    @Query(value = "SELECT check_stats_cache_outdated(?1)", nativeQuery = true)
    boolean checkStatsCacheOutdated(String playerId);

    @Query(value = "SELECT check_game_results_cache_outdated(?1)", nativeQuery = true)
    boolean checkGameResultsCacheOutdated(String playerId);

    Metadata findByPlayerId(String playerId);
}
