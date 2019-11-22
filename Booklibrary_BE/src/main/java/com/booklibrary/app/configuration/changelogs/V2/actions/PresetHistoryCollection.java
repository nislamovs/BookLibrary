package com.booklibrary.app.configuration.changelogs.V2.actions;

import com.booklibrary.app.models.nosql.Debt;
import com.booklibrary.app.models.nosql.History;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.booklibrary.app.configuration.changelogs.V2.actions.PresetDebtCollection.presetDebtsCollection;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V2/resources/history/HISTORY.yml")
//@ConfigurationProperties
//@Configuration
@Slf4j
public class PresetHistoryCollection {

    private static List<History> history = new ArrayList<>();
    private static List<Debt> debts = new ArrayList<>();

    public void setHistory(List<History> history) {
        PresetHistoryCollection.history = history;
    }

    public List<History> getHistory() {
        return history;
    }

    public static List<History> presetHistoryCollection() {
        debts = presetDebtsCollection();

        Iterator<Debt> debtIterator = debts.iterator();
        Iterator<History> historyIterator = history.iterator();

        while (debtIterator.hasNext() && historyIterator.hasNext()) {
            historyIterator.next().setDebt(debtIterator.next());
        }

        return history;
    }
}
