package zti.lichess_stats.lichess_stats.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chariot.Client;
import chariot.model.Many;
import chariot.model.RatingHistory;

@RestController
@RequestMapping("/chess")
public class LichessTestController
{
    Client client = Client.basic();

    @GetMapping("/rating/{username}")
    public Many<RatingHistory> hello(@PathVariable String username)
    {
        System.out.println("LOG FROM ME");
        return client.users().ratingHistoryById(username);
    }
}
