package com.dodia.librarymanagement.assignment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Assignment {

//assignmentId;
    //sid
    //issue_lid;
    //return_lid;
    //bid;
//    renueCount
    //issueData;
    //dueDate;
    //active;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private int sid;
    private int bid;
    private long issueBy;
    private int submitBy;
    private int active;
    @Transient
    private int availableCopy;
}
