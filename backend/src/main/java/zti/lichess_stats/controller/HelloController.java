package zti.lichess_stats.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController
{
    @GetMapping("/hello")
    public String hello()
    {
        return "Hello, World!";
    }

    @GetMapping("/nou")
    public String nou()
    {
        return "Holy Hell!";
    }
}
