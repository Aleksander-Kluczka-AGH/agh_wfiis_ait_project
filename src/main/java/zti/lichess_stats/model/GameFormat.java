package zti.lichess_stats.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_format")
public class GameFormat
{
    @Id
    private String name;

    public GameFormat(String name) { this.name = name; }

    // Default constructor (required for some frameworks)
    public GameFormat() { }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
