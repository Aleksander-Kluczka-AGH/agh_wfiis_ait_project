package zti.lichess_stats.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "game_result")
public class Metadata
{
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "player_id")
    @JsonBackReference
    private Player player;
    @Column
    private LocalDate playerCacheDatetime;
    @Column
    private LocalDate statsCacheDatetime;
    @Column
    private LocalDate gameResultsCacheDatetime;
    @Column
    private boolean areStatsPopulated;
    @Column
    private boolean areGameResultsPopulated;

    public Metadata(Long id,
        Player player,
        LocalDate playerCacheDatetime,
        LocalDate statsCacheDatetime,
        LocalDate gameResultsCacheDatetime,
        boolean areStatsPopulated,
        boolean areGameResultsPopulated)
    {
        this.id = id;
        this.player = player;
        this.playerCacheDatetime = playerCacheDatetime;
        this.statsCacheDatetime = statsCacheDatetime;
        this.gameResultsCacheDatetime = gameResultsCacheDatetime;
        this.areStatsPopulated = areStatsPopulated;
        this.areGameResultsPopulated = areGameResultsPopulated;
    }

    public Metadata() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @JsonProperty("player_id")
    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(@JsonProperty("player_id") Player player) { this.player = player; }

    public LocalDate getPlayerCacheDatetime() { return playerCacheDatetime; }

    public void setPlayerCacheDatetime(LocalDate playerCacheDatetime)
    {
        this.playerCacheDatetime = playerCacheDatetime;
    }

    public LocalDate getStatsCacheDatetime() { return statsCacheDatetime; }

    public void setStatsCacheDatetime(LocalDate statsCacheDatetime)
    {
        this.statsCacheDatetime = statsCacheDatetime;
    }

    public LocalDate getGameResultsCacheDatetime() { return gameResultsCacheDatetime; }

    public void setGameResultsCacheDatetime(LocalDate gameResultsCacheDatetime)
    {
        this.gameResultsCacheDatetime = gameResultsCacheDatetime;
    }

    public boolean isAreStatsPopulated() { return areStatsPopulated; }

    public void setAreStatsPopulated(boolean areStatsPopulated)
    {
        this.areStatsPopulated = areStatsPopulated;
    }

    public boolean isAreGameResultsPopulated() { return areGameResultsPopulated; }

    public void setAreGameResultsPopulated(boolean areGameResultsPopulated)
    {
        this.areGameResultsPopulated = areGameResultsPopulated;
    }
}
