package com.renfobackend.student.service;

import com.renfobackend.student.dto.StudentDTO;
import com.renfobackend.student.entity.Student;
import com.renfobackend.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;


    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setSchoolId(student.getSchoolId());

        if(student.getSchoolId() == null) {
            return dto;
        }
       String schoolServiceUrl = "http://SCHOOL/api/schools/" + student.getSchoolId();
       System.out.println("schoolServiceUrl: " + schoolServiceUrl);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                schoolServiceUrl,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> schoolData = response.getBody();

        if (schoolData != null) {
            String schoolName = (String) schoolData.get("name");
            dto.setSchoolName(schoolName);
        }



        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setSchoolId(dto.getSchoolId());
        return student;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO getStudentById(String id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public StudentDTO updateStudent(String id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(studentDTO.getName());
                    existingStudent.setEmail(studentDTO.getEmail());
                    existingStudent.setSchoolId(studentDTO.getSchoolId());
                    Student updatedStudent = studentRepository.save(existingStudent);
                    return convertToDTO(updatedStudent);
                })
                .orElse(null);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
