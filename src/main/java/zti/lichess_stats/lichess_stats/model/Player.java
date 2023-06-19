package zti.lichess_stats.lichess_stats.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "player")
public class Player
{
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String link;
    @Column
    private ZonedDateTime created;
    @Column
    private ZonedDateTime seen;
    @Column
    private String time_played;

    public Player(String id,
        String name,
        String link,
        ZonedDateTime created,
        ZonedDateTime seen,
        String time_played)
    {
        this.id = id;
        this.name = name;
        this.link = link;
        this.created = created;
        this.seen = seen;
        this.time_played = time_played;
    }

    // Default constructor (required for some frameworks)
    public Player() { }

    // Getters and Setters
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public ZonedDateTime getCreated() { return created; }

    public void setCreated(ZonedDateTime created) { this.created = created; }

    public ZonedDateTime getSeen() { return seen; }

    public void setSeen(ZonedDateTime seen) { this.seen = seen; }

    public String getTime_played() { return time_played; }

    public void setTime_played(String time_played) { this.time_played = time_played; }
}
