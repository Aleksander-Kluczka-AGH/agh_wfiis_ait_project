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

    // TODO: make function checking below functions
    // ( use `SELECT *function()*;` )
    // check_player_cache_outdated(text)
    // check_stats_cache_outdated(text)
    // check_game_results_cache_outdated(text)
}
