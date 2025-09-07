package net.adityaLearns.journalApp.controller;


import net.adityaLearns.journalApp.entity.UserEntity;
import net.adityaLearns.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        UserEntity oldUserInDB = userService.findByUserName(username);
        oldUserInDB.setUserName(user.getUserName());
        oldUserInDB.setPassword(user.getPassword());
        userService.saveNewUser(oldUserInDB);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByUserName(authentication.getName());
        return new ResponseEntity<> (HttpStatus.NO_CONTENT );
    }


}
