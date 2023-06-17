package zti.lichess_stats.lichess_stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import zti.lichess_stats.lichess_stats.model.User;
import zti.lichess_stats.lichess_stats.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId)
    {
        return userService.getUserById(userId);
    }

    @GetMapping("/")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }
}
