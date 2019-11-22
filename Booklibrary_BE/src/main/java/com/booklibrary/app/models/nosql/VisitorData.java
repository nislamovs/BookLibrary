package com.booklibrary.app.models.nosql;

import com.booklibrary.app.models.nosql.audit.AbstractDocument;
import com.booklibrary.app.models.nosql.embedded.Address;
import com.booklibrary.app.models.nosql.embedded.Bankdata;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "visitors")
public class VisitorData extends AbstractDocument {

    private String visitorId;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String phone;
    private Gender gender;
    private String email;
    private List<Interests> interests;
    private Category category;
    private Address livingAddress;
    private Address deliveryAddress;
    private List<Debt> debts;
    private List<Payment> payments;
    private Bankdata bankData;

    public static enum Interests { BIOLOGY, CHEMISTRY, COMPUTER_SCIENCE, ECONOMICS,
        ENGINEERING, MANAGEMENT, MATERIAL_SCIENCE, MATHEMATICS, MEDICINE, MICROBIOLOGY,
        PHARMACOLOGY, SOCIAL_SCIENCE, TOXICOLOGY, VETERINARY}

    public static enum Category { STUDENT, ENGINEER, RETIRED, WANTED, UNEMPLOYED, OTHER }

    public static enum Gender { MALE, FEMALE }

}
