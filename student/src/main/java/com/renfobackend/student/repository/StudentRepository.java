package com.renfobackend.student.repository;

import com.renfobackend.student.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
}
