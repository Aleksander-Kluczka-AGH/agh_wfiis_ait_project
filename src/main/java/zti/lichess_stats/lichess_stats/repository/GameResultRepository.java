package zti.lichess_stats.lichess_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import zti.lichess_stats.lichess_stats.model.GameResult;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {
    List<GameResult> findAllByPlayerId(String playerId);

    @Modifying
    @Query(
        value = "INSERT INTO game_result (points, date, player_id, format) VALUES (?1, ?2, ?3, ?4)",
        nativeQuery = true)
    void saveNative(Long points, LocalDate date, String playerId, String format) throws Exception;
}
