package net.adityaLearns.journalApp.controller;

import net.adityaLearns.journalApp.entity.JournalEntity;
import net.adityaLearns.journalApp.entity.UserEntity;
import net.adityaLearns.journalApp.services.JournalService;
import net.adityaLearns.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalControllerV2 {

    @Autowired
    JournalService journalService;

    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllEntries(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntity> entityList = user.getJournalEntries();
        return new ResponseEntity<>(entityList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntity){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalService.saveEntry(myEntity,userName);
            return new ResponseEntity<>(myEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.findByUserName(userName);
        List<JournalEntity> collect = userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
       if(!collect.isEmpty()){
           Optional<JournalEntity> getEntity =  journalService.getJournalById(myId);
           if(getEntity.isPresent()){
               return new ResponseEntity<>(getEntity.get(), HttpStatus.OK);
           }
       }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntity> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntity newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.findByUserName(userName);
        List<JournalEntity> collect = userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntity> getEntity = journalService.getJournalById(myId);
            if (getEntity.isPresent()) {
                getEntity.get().setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : getEntity.get().getContent());
                getEntity.get().setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : getEntity.get().getName());
                journalService.saveEntry(getEntity.get());
                return new ResponseEntity<>(getEntity.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean removed = journalService.deleteById(myId,userName);
            if(removed){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}