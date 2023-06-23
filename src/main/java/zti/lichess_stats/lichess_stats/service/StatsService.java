package zti.lichess_stats.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import zti.lichess_stats.lichess_stats.model.Stats;
import zti.lichess_stats.lichess_stats.repository.StatsRepository;

import org.hibernate.exception.ConstraintViolationException;

@Service
public class StatsService
{
    @Autowired
    private StatsRepository statsRepository;

    public Stats getStatsByPlayerId(String playerId)
    {
        Optional<Stats> optionalStats = statsRepository.findById(playerId);
        return optionalStats.orElse(null);
    }

    public Stats createStats(Stats stats)
    {
        statsRepository.save(stats);
        return stats;
    }

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
            statsRepository.saveNative(id,
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
            System.out.println("TABLE CONSTRAINT ERROR: " + e);
        }
        catch(Exception e)
        {
            System.out.println("UNKNOWN ERROR: " + e);
        }
        return this.getStatsByPlayerId(id);
    }
}
