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
    @Transactional
    @Modifying
    @Query(
        value =
            "INSERT INTO stats (player_id, \"all\", rated, ai, drawn, lost, won, imported, puzzle_count, puzzle_rating, rapid_count, rapid_rating, blitz_count, blitz_rating, bullet_count, bullet_rating, classical_count, classical_rating, correspondence_count, correspondence_rating) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20)",
        nativeQuery = true)
    void saveNative(String id,  // foreign key with player id value
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

    Optional<Stats> findByPlayerId(String player_id);
    boolean existsByPlayerId(String player_id);
}
