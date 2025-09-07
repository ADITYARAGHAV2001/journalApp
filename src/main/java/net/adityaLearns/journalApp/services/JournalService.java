package net.adityaLearns.journalApp.services;

import net.adityaLearns.journalApp.entity.JournalEntity;
import net.adityaLearns.journalApp.entity.UserEntity;
import net.adityaLearns.journalApp.repository.JournalRepository;
import net.adityaLearns.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    UserRepository userRepository;

    public void saveEntry(JournalEntity journalEntity,String userName){
        UserEntity user = userRepository.findByUserName(userName);
        journalEntity.setDate(LocalDateTime.now());
        JournalEntity saved = journalRepository.save(journalEntity);
        user.getJournalEntries().add(saved);
        userRepository.save(user);
    }

    public void saveEntry(JournalEntity journalEntity){
        journalRepository.save(journalEntity);
    }

    public List<JournalEntity> getAll(){
        return journalRepository.findAll();
    }

    public Optional<JournalEntity> getJournalById(ObjectId id){
        Optional<JournalEntity> oldEntry = journalRepository.findById(id);
        return oldEntry;
    }

    @Transactional
    public boolean deleteById(ObjectId id,String userName){
        boolean removed = false;
        try{
            UserEntity user = userRepository.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id) );
            if(removed){
                userRepository.save(user);
                journalRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting journal " + e);
        }
        return removed;
    }

}


// controller --> service --> repository it basically implements mongorepository