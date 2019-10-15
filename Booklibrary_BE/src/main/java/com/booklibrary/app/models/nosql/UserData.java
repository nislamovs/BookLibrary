package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.embedded.Address;
import com.booklibrary.app.models.nosql.embedded.Bankdata;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Document(collection = "Users")
public class UserData {

    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String phone;
    private String gender;
    private String email;
    private List<Interests> interests;
    private Category category;
    private Address livingAddress;
    private Address deliveryAddress;
    private List<Debt> debts;
    private List<Payment> payments;
    private Bankdata bankData;

    enum Interests { MANAGEMENT, SOFTWARE_DEVELOPMENT, PSYCHOLOGY, SCIENCE, ENGINEERING, MEDICINE, PHARMACOLOGY, KIDS, SPORT }

    enum Category { STUDENT, ENGINEER, RETIRED, PREGNANT, UNEMPLOYEED, OTHER }

}
