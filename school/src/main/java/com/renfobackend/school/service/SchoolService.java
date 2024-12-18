package com.renfobackend.school.service;

import com.renfobackend.school.dto.SchoolDto;
import com.renfobackend.school.entity.School;
import com.renfobackend.school.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public List<SchoolDto> findAll() {
        return schoolRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SchoolDto findById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School not found with id " + id));
        return toDto(school);
    }

    public SchoolDto save(SchoolDto schoolDto) {
        School school = toEntity(schoolDto);
        School savedSchool = schoolRepository.save(school);
        return toDto(savedSchool);
    }

    public SchoolDto update(Long id, SchoolDto schoolDto) {
        School existingSchool = schoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School not found with id " + id));
        schoolDto.setId(existingSchool.getId());
        School updatedSchool = toEntity(schoolDto);
        updatedSchool = schoolRepository.save(updatedSchool);
        return toDto(updatedSchool);
    }

    public void delete(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new NotFoundException("School not found with id " + id);
        }
        schoolRepository.deleteById(id);
    }

    private SchoolDto toDto(School school) {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setId(school.getId());
        schoolDto.setName(school.getName());
        return schoolDto;
    }

    private School toEntity(SchoolDto schoolDto) {
        School school = new School();
        school.setId(schoolDto.getId());
        school.setName(schoolDto.getName());
        return school;
    }
}
