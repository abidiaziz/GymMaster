package com.devproject.controllers;

import com.devproject.dto.ClassDTO;
import com.devproject.dto.CreateClassDTO;
import com.devproject.dto.UpdateClassDTO;
import com.devproject.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassDTO> createClass(@RequestBody CreateClassDTO createClassDTO) {
        ClassDTO createdClass = classService.createClass(createClassDTO);
        return ResponseEntity.ok(createdClass);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable Long id, @RequestBody UpdateClassDTO updateClassDTO) {
        ClassDTO updatedClass = classService.updateClass(id, updateClassDTO);
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
    }

    @PostMapping("/{classId}/enroll")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> enrollMe(@PathVariable Long classId) {
        classService.enrollCustomer(classId);
        return ResponseEntity.ok("You have been enrolled successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'COACH')")
    public ResponseEntity<ClassDTO> getClass(@PathVariable Long id) {
        ClassDTO classDTO = classService.getClassById(id);
        return ResponseEntity.ok(classDTO);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'COACH')")
    public ResponseEntity<List<ClassDTO>> getClasses() {
        List<ClassDTO> classes = classService.getClasses();
        return ResponseEntity.ok(classes);
    }
}

