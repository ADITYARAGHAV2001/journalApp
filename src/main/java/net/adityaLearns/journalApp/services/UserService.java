package net.adityaLearns.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.adityaLearns.journalApp.entity.UserEntity;
import net.adityaLearns.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService  {

    @Autowired
    UserRepository userRepository;

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public void saveNewUser(UserEntity userEntity){
        log.debug("inside save new user");
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        userRepository.save(userEntity);
    }

    public void saveAdminUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(userEntity);
    }

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getJournalById(ObjectId id){
        Optional<UserEntity> oldEntry = userRepository.findById(id);
        return oldEntry;
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public UserEntity findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void deleteUserByUserName(String username){
         userRepository.deleteByUserName(username);
    }
}
