package zti.lichess_stats.lichess_stats.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "stats")
public class Stats
{
    @Id
    private String id;
    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    @JsonBackReference
    private Player player;
    @Column
    private Long all;
    @Column
    private Long rated;
    @Column
    private Long ai;
    @Column
    private Long drawn;
    @Column
    private Long lost;
    @Column
    private Long won;
    @Column
    private Long imported;
    @Column
    private Long puzzle_count;
    @Column
    private Long puzzle_rating;
    @Column
    private Long rapid_count;
    @Column
    private Long rapid_rating;
    @Column
    private Long blitz_count;
    @Column
    private Long blitz_rating;
    @Column
    private Long bullet_count;
    @Column
    private Long bullet_rating;
    @Column
    private Long classical_count;
    @Column
    private Long classical_rating;
    @Column
    private Long correspondence_count;
    @Column
    private Long correspondence_rating;

    public Stats(@JsonProperty("id") Player player,
        Long all,
        Long rated,
        Long ai,
        Long drawn,
        Long lost,
        Long won,
        Long imported,
        Long puzzle_count,
        Long puzzle_rating,
        Long rapid_count,
        Long rapid_rating,
        Long blitz_count,
        Long blitz_rating,
        Long bullet_count,
        Long bullet_rating,
        Long classical_count,
        Long classical_rating,
        Long correspondence_count,
        Long correspondence_rating)
    {
        this.player = player;
        this.all = all;
        this.rated = rated;
        this.ai = ai;
        this.drawn = drawn;
        this.lost = lost;
        this.won = won;
        this.imported = imported;
        this.puzzle_count = puzzle_count;
        this.puzzle_rating = puzzle_rating;
        this.rapid_count = rapid_count;
        this.rapid_rating = rapid_rating;
        this.blitz_count = blitz_count;
        this.blitz_rating = blitz_rating;
        this.bullet_count = bullet_count;
        this.bullet_rating = bullet_rating;
        this.classical_count = classical_count;
        this.classical_rating = classical_rating;
        this.correspondence_count = correspondence_count;
        this.correspondence_rating = correspondence_rating;
    }

    public Stats() { }

    @JsonProperty("id")
    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(@JsonProperty("id") Player player) { this.player = player; }

    public Long getAll() { return all; }

    public void setAll(Long all) { this.all = all; }

    public Long getRated() { return rated; }

    public void setRated(Long rated) { this.rated = rated; }

    public Long getAi() { return ai; }

    public void setAi(Long ai) { this.ai = ai; }

    public Long getDrawn() { return drawn; }

    public void setDrawn(Long drawn) { this.drawn = drawn; }

    public Long getLost() { return lost; }

    public void setLost(Long lost) { this.lost = lost; }

    public Long getWon() { return won; }

    public void setWon(Long won) { this.won = won; }

    public Long getImported() { return imported; }

    public void setImported(Long imported) { this.imported = imported; }

    public Long getPuzzle_count() { return puzzle_count; }

    public void setPuzzle_count(Long puzzle_count) { this.puzzle_count = puzzle_count; }

    public Long getPuzzle_rating() { return puzzle_rating; }

    public void setPuzzle_rating(Long puzzle_rating) { this.puzzle_rating = puzzle_rating; }

    public Long getRapid_count() { return rapid_count; }

    public void setRapid_count(Long rapid_count) { this.rapid_count = rapid_count; }

    public Long getRapid_rating() { return rapid_rating; }

    public void setRapid_rating(Long rapid_rating) { this.rapid_rating = rapid_rating; }

    public Long getBlitz_count() { return blitz_count; }

    public void setBlitz_count(Long blitz_count) { this.blitz_count = blitz_count; }

    public Long getBlitz_rating() { return blitz_rating; }

    public void setBlitz_rating(Long blitz_rating) { this.blitz_rating = blitz_rating; }

    public Long getBullet_count() { return bullet_count; }

    public void setBullet_count(Long bullet_count) { this.bullet_count = bullet_count; }

    public Long getBullet_rating() { return bullet_rating; }

    public void setBullet_rating(Long bullet_rating) { this.bullet_rating = bullet_rating; }

    public Long getClassical_count() { return classical_count; }

    public void setClassical_count(Long classical_count) { this.classical_count = classical_count; }

    public Long getClassical_rating() { return classical_rating; }

    public void setClassical_rating(Long classical_rating)
    {
        this.classical_rating = classical_rating;
    }

    public Long getCorrespondence_count() { return correspondence_count; }

    public void setCorrespondence_count(Long correspondence_count)
    {
        this.correspondence_count = correspondence_count;
    }

    public Long getCorrespondence_rating() { return correspondence_rating; }

    public void setCorrespondence_rating(Long correspondence_rating)
    {
        this.correspondence_rating = correspondence_rating;
    }
}
