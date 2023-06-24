package zti.lichess_stats.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chariot.Client;
import chariot.model.Many;
import chariot.model.RatingHistory;

@RestController
@RequestMapping("/chess/rating")
public class ChessRatingController
{
    Client client = Client.basic();

    @GetMapping("/{format}/{username}")
    public RatingHistory userRating(@PathVariable String format, @PathVariable String username)
    {
        return this.getUserRating(username)
            .stream()
            .filter(rating -> rating.name().equals(StringUtils.capitalize(format)))
            .findFirst()
            .get();
    }

    private Many<RatingHistory> getUserRating(String username)
    {
        return client.users().ratingHistoryById(username);
    }
}
