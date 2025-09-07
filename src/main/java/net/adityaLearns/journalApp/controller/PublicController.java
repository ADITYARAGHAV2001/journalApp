package net.adityaLearns.journalApp.controller;

import net.adityaLearns.journalApp.entity.UserEntity;
import net.adityaLearns.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    UserService userService;

    @GetMapping("healthcheck")
    public String healthCheck(){
        return "ok";
    }



    @PostMapping("/create-user")
    public void CreateUser(@RequestBody UserEntity user){
        userService.saveNewUser(user);
    }
}
