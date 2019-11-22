package com.booklibrary.app.configuration.changelogs.V2.actions;

import com.booklibrary.app.models.nosql.Debt;
import com.booklibrary.app.models.nosql.Payment;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.booklibrary.app.configuration.changelogs.V2.actions.PresetPaymentCollection.presetPaymentCollection;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V2/resources/debt/DEBTS.yml")
//@ConfigurationProperties
//@Configuration
@Slf4j
public class PresetDebtCollection {

//    # DEBTS
//#    private String debtId;            ok     //generate [same interval as paymentId]
//#    private String bookNumber;        ok     //get from payments
//#    private String visitorId;         ok     //generate [same interval as paymentId]
//#    private int overdueDays;          ok     //1-15 random generate

//#    private Money actualPenalty;      ok     //get from payments by debt id
//#    private List<Payment> payments;   ok     //get payment by debt id

    private static List<Debt> debts = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();

    public void setDebts(List<Debt> debts) {
        PresetDebtCollection.debts = debts;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public static List<Debt> presetDebtsCollection() {

        payments = presetPaymentCollection();

        Iterator<Debt> debtIterator = debts.iterator();
        Iterator<Payment> paymentIterator = payments.iterator();

        while (debtIterator.hasNext() && paymentIterator.hasNext()) {
            var debt = debtIterator.next();
            var payment = paymentIterator.next();
            debt.setPayments(ImmutableList.of(payment));
            debt.setActualPenalty(payment.getAmount());
        }

        return debts;
    }
}
