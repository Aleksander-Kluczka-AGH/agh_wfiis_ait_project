package zti.lichess_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zti.lichess_stats.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    /**
     * Checks with the database if player exists. This method does not require to obtain Player
     * object in order to perform this query.
     * @param id Unique player ID from Lichess account.
     * @return True if player exists in the database, false otherwise.
     */
    boolean existsById(String id);
}
