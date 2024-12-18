package com.renfobackend.school.controller;

import com.renfobackend.school.dto.SchoolDto;
import com.renfobackend.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public ResponseEntity<List<SchoolDto>> findAll() {
        List<SchoolDto> schools = schoolService.findAll();
        return ResponseEntity.ok(schools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> findById(@PathVariable Long id) {
        SchoolDto school = schoolService.findById(id);
        return ResponseEntity.ok(school);
    }

    @PostMapping
    public ResponseEntity<SchoolDto> save(@RequestBody SchoolDto schoolDto) {
        SchoolDto savedSchool = schoolService.save(schoolDto);
        return ResponseEntity.ok(savedSchool);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> update(@PathVariable Long id, @RequestBody SchoolDto schoolDto) {
        SchoolDto updatedSchool = schoolService.update(id, schoolDto);
        return ResponseEntity.ok(updatedSchool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        schoolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
