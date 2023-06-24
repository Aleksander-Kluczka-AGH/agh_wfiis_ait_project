package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import zti.lichess_stats.model.Metadata;
import zti.lichess_stats.repository.MetadataRepository;

@Service
public class MetadataService
{
    @Autowired
    private MetadataRepository metadataRepository;

    public void createMetadata(Metadata metadata) { metadataRepository.save(metadata); }

    public void createMetadataNative(String playerId)
    {
        metadataRepository.saveNative(playerId);
        System.out.println("Created metadata for player: '" + playerId + "'");
    }

    public Metadata getMetadataByPlayerId(String playerId)
    {
        return metadataRepository.findByPlayerId(playerId);
    }

    public void refreshPlayerCacheDatetime(String playerId)
    {
        metadataRepository.refreshPlayerCacheDatetime(playerId);
    }

    public boolean refreshPlayerCacheDatetime(String playerId, LocalDate date)
    {
        Metadata currentMetadata = this.getMetadataByPlayerId(playerId);
        if(currentMetadata == null)
        {
            return false;
        }

        if(currentMetadata.getPlayerCacheDatetime().isAfter(date.minusDays(1)))
        {
            return false;
        }

        this.refreshPlayerCacheDatetime(playerId);
        return true;
    }

    public void refreshStatsCacheDatetime(String playerId)
    {
        metadataRepository.refreshStatsCacheDatetime(playerId);
    }

    public boolean refreshStatsDatetime(String playerId, LocalDate date)
    {
        Metadata currentMetadata = this.getMetadataByPlayerId(playerId);
        if(currentMetadata == null)
        {
            return false;
        }

        if(currentMetadata.getStatsCacheDatetime().isAfter(date.minusDays(1)))
        {
            return false;
        }

        this.refreshStatsCacheDatetime(playerId);
        return true;
    }

    public void refreshGameResultsCacheDatetime(String playerId)
    {
        metadataRepository.refreshGameResultsCacheDatetime(playerId);
    }

    public boolean refreshGameResultsCacheDatetime(String playerId, LocalDate date)
    {
        Metadata currentMetadata = this.getMetadataByPlayerId(playerId);
        if(currentMetadata == null)
        {
            return false;
        }

        if(currentMetadata.getGameResultsCacheDatetime().isAfter(date.minusDays(1)))
        {
            return false;
        }

        this.refreshGameResultsCacheDatetime(playerId);
        return true;
    }

    public void refreshAllCacheDatetimes(String playerId)
    {
        metadataRepository.refreshAllCacheDatetimes(playerId);
    }

    public void setStatsPopulated(String playerId)
    {
        metadataRepository.setStatsPopulated(playerId);
    }

    public void setGameResultsPopulated(String playerId)
    {
        metadataRepository.setGameResultsPopulated(playerId);
    }

    public void setAllPopulated(String playerId) { metadataRepository.setAllPopulated(playerId); }
}
