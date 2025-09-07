package net.adityaLearns.journalApp.controller;

import net.adityaLearns.journalApp.entity.UserEntity;
import net.adityaLearns.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        List<UserEntity> all = userService.getAll();
        if(all!= null || !all.isEmpty()){
            return new ResponseEntity<List<UserEntity>>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody UserEntity user){
        userService.saveAdminUser(user);
    }

}
