package zti.lichess_stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import zti.lichess_stats.model.GameFormat;
import zti.lichess_stats.repository.GameFormatRepository;

@Service
public class GameFormatService
{
    @Autowired
    private GameFormatRepository gameFormatRepository;

    public List<GameFormat> getAllGameFormats() { return gameFormatRepository.findAll(); }

    public GameFormat getGameFormatByName(String name)
    {
        Optional<GameFormat> optionalUser = gameFormatRepository.findById(name);
        return optionalUser.orElse(null);
    }
}
