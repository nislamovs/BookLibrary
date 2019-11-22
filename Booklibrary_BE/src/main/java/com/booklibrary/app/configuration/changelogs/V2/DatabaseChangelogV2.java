package com.booklibrary.app.configuration.changelogs.V2;

import com.booklibrary.app.configuration.changelogs.V2.actions.PresetHistoryCollection;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

@Slf4j
@ChangeLog(order = "001")
public class DatabaseChangelogV2 {

//    @ChangeSet(order = "001", id = "id01", author = "Nizami Islamovs", runAlways = true)
//    public void paymentCollection(MongoTemplate repo) {
//        PresetPaymentCollection.presetPaymentCollection().forEach(repo::save);
//    }
//
//    @ChangeSet(order = "002", id = "id02", author = "Nizami Islamovs", runAlways = true)
//    public void debtCollection(MongoTemplate repo) {
//        PresetDebtCollection.presetDebtsCollection().forEach(repo::save);
//    }

    @ChangeSet(order = "001", id = "id01", author = "Nizami Islamovs", runAlways = false)
    public void historyCollection(MongoTemplate repo) {
        System.out.println(">>>>>>>>>>>>>>     STARTING");
        PresetHistoryCollection.presetHistoryCollection().forEach(repo::save);
        System.out.println(">>>>>>>>>>>>>>     END");
    }
}
