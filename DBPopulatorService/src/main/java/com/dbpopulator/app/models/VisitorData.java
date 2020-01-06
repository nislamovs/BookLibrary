package com.dbpopulator.app.models;

import com.dbpopulator.app.models.audit.AbstractDocument;
import com.dbpopulator.app.models.embedded.Address;
import com.dbpopulator.app.models.embedded.Bankdata;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.kaiso.relmongo.annotation.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "visitors")
public class VisitorData extends AbstractDocument {

    @Field(name="visitorId")
    private String visitorId;

    @Field(name="firstname")
    private String firstname;

    @Field(name="lastname")
    private String lastname;

    @Field(name="birthdate")
    private Date birthdate;

    @Field(name="phone")
    private String phone;

    @Field(name="gender")
    private Gender gender;

    @Field(name="email")
    private String email;

    @Field(name="interests")
    private List<Interests> interests;

    @Field(name="category")
    private Category category;

    @Field(name="livingAddress")
    private Address livingAddress;

    @Field(name="deliveryAddress")
    private Address deliveryAddress;

    @Field(name="history")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinProperty(name="history")
    private List<History> history;

    @Field(name="bankData")
    private Bankdata bankData;

    public static enum Interests { BIOLOGY, CHEMISTRY, COMPUTER_SCIENCE, ECONOMICS,
        ENGINEERING, MANAGEMENT, MATERIAL_SCIENCE, MATHEMATICS, MEDICINE, MICROBIOLOGY,
        PHARMACOLOGY, SOCIAL_SCIENCE, TOXICOLOGY, VETERINARY}

    public static enum Category { STUDENT, ENGINEER, RETIRED, WANTED, UNEMPLOYED, OTHER }

    public static enum Gender { @JsonProperty("Male") MALE, @JsonProperty("Female") FEMALE }

}
