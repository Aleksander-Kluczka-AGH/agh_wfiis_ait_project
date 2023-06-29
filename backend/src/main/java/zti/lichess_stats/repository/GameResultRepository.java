package zti.lichess_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import zti.lichess_stats.model.GameResult;

import org.hibernate.exception.ConstraintViolationException;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {
    List<GameResult> findAllByPlayerId(String playerId);

    /**
     * Saves a game result to the database. This method does not require to obtain GameResult object
     * in order to insert it into the database.
     * @param points Rating status after the game has finished.
     * @param date Date when the game was played.
     * @param playerId Unique player ID from Lichess account.
     * @param format Format of the game (e.g. "blitz", "rapid", "bullet").
     * @throws ConstraintViolationException Thrown when given player does not exist in the database.
     */
    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO game_result (points, date, player_id, format) VALUES (?1, ?2, ?3, ?4)",
        nativeQuery = true)
    void saveNative(Long points, LocalDate date, String playerId, String format)
        throws ConstraintViolationException;
}
