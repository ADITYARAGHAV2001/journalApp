package net.adityaLearns.journalApp.repository;

import net.adityaLearns.journalApp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<JournalEntity, ObjectId> {

}
