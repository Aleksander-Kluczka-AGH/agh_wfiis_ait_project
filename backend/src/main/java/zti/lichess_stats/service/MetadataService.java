package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zti.lichess_stats.model.Metadata;
import zti.lichess_stats.repository.MetadataRepository;

@Service
public class MetadataService
{
    @Autowired
    private MetadataRepository metadataRepository;

    public Metadata getMetadataByPlayerId(String playerId)
    {
        return metadataRepository.findByPlayerId(playerId);
    }

    public boolean isPlayerOutdated(String playerId)
    {
        return metadataRepository.checkPlayerCacheOutdated(playerId);
    }

    public boolean areStatsOutdated(String playerId)
    {
        return metadataRepository.checkStatsCacheOutdated(playerId);
    }

    public boolean areGameResultsOutdated(String playerId)
    {
        return metadataRepository.checkGameResultsCacheOutdated(playerId);
    }
}
