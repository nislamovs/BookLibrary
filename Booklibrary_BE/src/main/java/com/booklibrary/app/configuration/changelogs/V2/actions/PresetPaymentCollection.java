package com.booklibrary.app.configuration.changelogs.V2.actions;

import com.booklibrary.app.models.nosql.Payment;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

//@PropertySource(factory = YamlPropertySourceFactory.class, value = "file:./src/main/java/com/booklibrary/app/configuration/changelogs/V2/resources/payment/PAYMENTS.yml")
//@ConfigurationProperties
//@Configuration
@Slf4j
public class PresetPaymentCollection {

    //TODO add random [payment receipt photo]                   ok
    // and [bookId by bookNumber from bookRepository]           nok

    private static List<Payment> payments = new ArrayList<>();

    public void setPayments(List<Payment> payments) {
        PresetPaymentCollection.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public static List<Payment> presetPaymentCollection() {
        for (var payment : payments) {
            String paymentReceiptPhoto = "";
            String filenamePostfix = String.valueOf(new Random().nextInt(5) + 1);
            try {
                paymentReceiptPhoto = "src/main/java/com/booklibrary/app/configuration/changelogs/V2/resources/payment/payment_receipts/"
                    + "payment_receipt_example" + filenamePostfix + ".jpg";
                payment.setPaymentReceiptPhoto(Base64.getEncoder().encodeToString(IOUtils.toByteArray(new FileInputStream(paymentReceiptPhoto))));
            } catch (Exception e) {
                log.info("Filename '{}' not found!", paymentReceiptPhoto);
            }
            System.out.println(">>>>>>>>    "+payment);
        }
        return payments;
    }
}
