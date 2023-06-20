package zti.lichess_stats.lichess_stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zti.lichess_stats.lichess_stats.model.GameFormat;

@Repository
public interface GameFormatRepository extends JpaRepository<GameFormat, String> { }
