package com.renfobackend.student.dto;
import lombok.Data;

@Data

public class StudentDTO {
    private String id;
    private String name;
    private String email;
    private Long schoolId;
    private String schoolName; 
}
