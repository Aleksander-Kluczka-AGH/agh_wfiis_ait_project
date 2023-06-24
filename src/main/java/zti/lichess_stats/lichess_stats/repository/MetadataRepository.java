package zti.lichess_stats.lichess_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import zti.lichess_stats.lichess_stats.model.Metadata;

import org.hibernate.exception.ConstraintViolationException;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO metadata (player_id) VALUES (?1)", nativeQuery = true)
    void saveNative(String playerId) throws ConstraintViolationException;

    @Transactional
    @Modifying
    @Query(value = "UPDATE metadata SET player_cache_datetime = NOW() WHERE player_id = ?1",
        nativeQuery = true)
    void refreshPlayerCacheDatetime(String playerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE metadata SET stats_cache_datetime = NOW() WHERE player_id = ?1",
        nativeQuery = true)
    void refreshStatsCacheDatetime(String playerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE metadata SET game_results_cache_datetime = NOW() WHERE player_id = ?1",
        nativeQuery = true)
    void refreshGameResultsCacheDatetime(String playerId);

    @Transactional
    @Modifying
    @Query(
        value =
            "UPDATE metadata SET player_cache_datetime = NOW(), stats_cache_datetime = NOW(), game_results_cache_datetime = NOW() WHERE player_id = ?1",
        nativeQuery = true)
    void refreshAllCacheDatetimes(String playerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE metadata SET are_stats_populated = True WHERE player_id = ?1",
        nativeQuery = true)
    void setStatsPopulated(String playerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE metadata SET are_game_results_populated = True WHERE player_id = ?1",
        nativeQuery = true)
    void setGameResultsPopulated(String playerId);

    @Transactional
    @Modifying
    @Query(
        value =
            "UPDATE metadata SET are_stats_populated = True, are_game_results_populated = True WHERE player_id = ?1",
        nativeQuery = true)
    void setAllPopulated(String playerId);

    Metadata findByPlayerId(String playerId);
}
