package zti.lichess_stats.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "game_result")
public class GameResult
{
    @Id
    private Long id;
    @Column
    private Long points;
    @Column
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonBackReference
    private Player player;
    @ManyToOne
    @JoinColumn(name = "format")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    private GameFormat format;

    public GameResult(Long id,
        Long points,
        LocalDate date,
        @JsonProperty("player_id") Player player,
        @JsonProperty("format") GameFormat format)
    {
        this.id = id;
        this.points = points;
        this.date = date;
        this.player = player;
        this.format = format;
    }

    public GameResult() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getPoints() { return points; }

    public void setPoints(Long points) { this.points = points; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    @JsonProperty("player_id")
    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(@JsonProperty("player_id") Player player) { this.player = player; }

    @JsonProperty("format")
    public GameFormat getFormat()
    {
        return format;
    }

    public void setFormat(@JsonProperty("format") GameFormat format) { this.format = format; }
}
