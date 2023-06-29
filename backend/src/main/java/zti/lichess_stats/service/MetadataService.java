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

    /**
     * Returns all metadata from the database for a given player ID.
     * @param playerId Unique player ID from Lichess account.
     * @return Metadata object.
     */
    public Metadata getMetadataByPlayerId(String playerId)
    {
        return metadataRepository.findByPlayerId(playerId);
    }

    /**
     * Checks whether player's cache is outdated.
     * @param playerId Unique player ID from Lichess account.
     * @return True if player's cache is outdated, false otherwise.
     */
    public boolean isPlayerOutdated(String playerId)
    {
        return metadataRepository.checkPlayerCacheOutdated(playerId);
    }

    /**
     * Checks whether player's stats cache is outdated.
     * @param playerId Unique player ID from Lichess account.
     * @return True if player's stats cache is outdated, false otherwise.
     */
    public boolean areStatsOutdated(String playerId)
    {
        return metadataRepository.checkStatsCacheOutdated(playerId);
    }

    /**
     * Checks whether player's game results cache is outdated.
     * @param playerId Unique player ID from Lichess account.
     * @return True if player's game results cache is outdated, false otherwise.
     */
    public boolean areGameResultsOutdated(String playerId)
    {
        return metadataRepository.checkGameResultsCacheOutdated(playerId);
    }
}
