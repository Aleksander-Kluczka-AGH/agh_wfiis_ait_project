package zti.lichess_stats.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zti.lichess_stats.model.GameFormat;
import zti.lichess_stats.service.GameFormatService;

@RestController
@RequestMapping("/chess/format")
public class ChessGameFormatController
{
    private final GameFormatService gameFormatService;

    @Autowired
    public ChessGameFormatController(GameFormatService gameFormatService)
    {
        this.gameFormatService = gameFormatService;
    }

    @GetMapping("/{formatName}")
    public GameFormat getGameFormatByName(@PathVariable String formatName)
    {
        System.out.println("/CHESS/FORMAT/" + formatName.toUpperCase());

        GameFormat formatFromDb = gameFormatService.getGameFormatByName(formatName);
        System.out.println("Returning from SQL database...");
        return formatFromDb;
    }
}
