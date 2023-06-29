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

    public Stats getStatsByPlayerId(String playerId)
    {
        Optional<Stats> optionalStats = statsRepository.findByPlayerId(playerId.toLowerCase());
        return optionalStats.orElse(null);
    }

    public boolean doesStatsExistForPlayerId(String playerId)
    {
        return statsRepository.existsByPlayerId(playerId.toLowerCase());
    }

    public void deleteStatsByPlayerId(String playerId)
    {
        statsRepository.deleteByPlayerId(playerId.toLowerCase());
    }

    public Stats createStats(Stats stats) { return statsRepository.save(stats); }

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
