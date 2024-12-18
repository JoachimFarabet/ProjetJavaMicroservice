package com.renfobackend.student.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "students")
public class Student {

    @Id
    private String id;
    private String name;
    private String email;

    private Long schoolId;
    private String schoolName; 
    
}
