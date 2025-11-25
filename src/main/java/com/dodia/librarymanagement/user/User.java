package com.dodia.librarymanagement.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long uid;

    @Column(name = "firstname", columnDefinition = "varchar(100)")
    private String firstName;

    @Column(name = "lastname", columnDefinition = "varchar(100)")
    private String lastName;

    //    @Column(name="email", columnDefinition = "VARCHAR(80)", unique=true)
    // @Column(name="email", columnDefinition = "VARCHAR(80)")
    @JsonProperty(value = "email")
    private String emailId;

    //@Size(max = 10, min = 10, message = "Enter mobile number of 10 digits")
    @Column(name = "mobile", columnDefinition = "VARCHAR(10)")
    private long mobile;
//    private Date dob;

    @Column(name = "type")
    private String type; //s for student L for librarian
    private int active;

}
