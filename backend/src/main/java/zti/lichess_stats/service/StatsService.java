package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import zti.lichess_stats.model.Stats;
import zti.lichess_stats.repository.StatsRepository;

import org.hibernate.exception.ConstraintViolationException;

@Service
public class StatsService
{
    @Autowired
    private StatsRepository statsRepository;

    public Stats getStatsByPlayerId(String playerId)
    {
        System.out.println("getStatsByPlayerId");
        Optional<Stats> optionalStats = statsRepository.findByPlayerId(playerId.toLowerCase());
        return optionalStats.orElse(null);
    }

    public boolean doesStatsExistForPlayerId(String playerId)
    {
        System.out.println("doesStatsExistForPlayerId");
        return statsRepository.existsByPlayerId(playerId.toLowerCase());
    }

    public Stats createStats(Stats stats)
    {
        System.out.println("createStats");
        return statsRepository.save(stats);
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
        System.out.println("createStatsNative");
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
