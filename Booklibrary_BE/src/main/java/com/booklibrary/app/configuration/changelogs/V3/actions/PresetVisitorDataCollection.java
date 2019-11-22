package com.booklibrary.app.configuration.changelogs.V3.actions;

import com.booklibrary.app.models.nosql.Debt;
import com.booklibrary.app.models.nosql.Payment;
import com.booklibrary.app.models.nosql.VisitorData;
import lombok.var;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.booklibrary.app.configuration.changelogs.V2.actions.PresetDebtCollection.presetDebtsCollection;
import static com.booklibrary.app.configuration.changelogs.V2.actions.PresetPaymentCollection.presetPaymentCollection;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V3/resources/visitor/VISITORS.yml")
//@ConfigurationProperties
//@Configuration
//@Slf4j
public class PresetVisitorDataCollection {

    private static List<VisitorData> visitors = new ArrayList<>();
    private static List<Debt> debts = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();

    public void setVisitors(List<VisitorData> penalties) {
        PresetVisitorDataCollection.visitors = visitors;
    }

    public List<VisitorData> getVisitors() {
        return visitors;
    }

    public static List<VisitorData> presetVisitorDataCollection() {

        debts = presetDebtsCollection();
        payments = presetPaymentCollection();

        Iterator<Debt> debtIterator = debts.iterator();
        Iterator<Payment> paymentIterator = payments.iterator();
        Iterator<VisitorData> visitorIterator = visitors.iterator();

        while (debtIterator.hasNext() && paymentIterator.hasNext() && visitorIterator.hasNext()) {
            var visitor = visitorIterator.next();
            visitor.setPayments(Collections.singletonList(paymentIterator.next()));
            visitor.setDebts(Collections.singletonList(debtIterator.next()));
        }


        return visitors;
    }
}
