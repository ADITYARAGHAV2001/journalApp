package net.adityaLearns.journalApp.controller;

import net.adityaLearns.journalApp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journalv1")
public class JournalController {
    public Map<ObjectId, JournalEntity> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntity> getAllEntries(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntity myEntity){
        journalEntries.put(myEntity.getId(),myEntity);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntity getJournalById(@PathVariable ObjectId myId){
        return journalEntries.get(myId);
    }

    @PutMapping("id/{myId}")
    public boolean updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntity updatedEntity){
        journalEntries.put(myId, updatedEntity);
        return true;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalById(@PathVariable ObjectId myId){
        journalEntries.remove(myId);
        return true;
    }
}
