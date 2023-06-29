package zti.lichess_stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LichessStatsApplication
{
    /**
     * Entrypoint of the application.
     * @param args CLI arguments.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(LichessStatsApplication.class, args);
    }
}
