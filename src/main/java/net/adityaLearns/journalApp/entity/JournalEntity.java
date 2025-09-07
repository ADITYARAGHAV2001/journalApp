package net.adityaLearns.journalApp.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Getter
@Setter
public class JournalEntity {

    @Id
    private ObjectId id;
    private String name;
    private String content;
    private LocalDateTime date;
}
