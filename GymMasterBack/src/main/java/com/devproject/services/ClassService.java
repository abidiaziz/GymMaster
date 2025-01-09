package com.devproject.services;

import com.devproject.dto.ClassDTO;
import com.devproject.dto.CreateClassDTO;
import com.devproject.dto.UpdateClassDTO;
import com.devproject.entities.Class;
import com.devproject.entities.Role;
import com.devproject.entities.User;
import com.devproject.repositories.ClassRepository;
import com.devproject.repositories.UserRepository;
import com.devproject.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClassService(ClassRepository classRepository, UserRepository userRepository) {
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ClassDTO createClass(CreateClassDTO createClassDTO) {
        Class classEntity = new Class();
        BeanUtils.copyProperties(createClassDTO, classEntity);

        User coach = userRepository.findById(createClassDTO.getCoachId())
                .orElseThrow(() -> new RuntimeException("Coach not found"));
        classEntity.setCoach(coach);

        Class savedClass = classRepository.save(classEntity);
        return convertToDTO(savedClass);
    }

    @Transactional
    public ClassDTO updateClass(Long id, UpdateClassDTO updateClassDTO) {
        Class existingClass = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        BeanUtils.copyProperties(updateClassDTO, existingClass, "customers");

        User coach = userRepository.findById(updateClassDTO.getCoachId())
                .orElseThrow(() -> new RuntimeException("Coach not found"));
        existingClass.setCoach(coach);

        Class updatedClass = classRepository.save(existingClass);
        return convertToDTO(updatedClass);
    }

    @Transactional
    public void enrollCustomer(Long classId) {
        Class classObj = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        String currentUserEmail = SecurityUtils.getCurrentUserEmail();
        User customer = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can enroll in classes");
        }

        if (classObj.getCustomers().size() >= classObj.getMaxCapacity()) {
            throw new RuntimeException("Class is at full capacity");
        }

        classObj.getCustomers().add(customer);
        classRepository.save(classObj);
    }

    public ClassDTO getClassById(Long id) {
        Class classObj = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        return convertToDTO(classObj);
    }

    public List<ClassDTO> getClasses() {
        List<Class> classes = classRepository.findAll();
        return classes.stream().map(this::convertToDTO).toList();
    }

    private ClassDTO convertToDTO(Class classEntity) {
        ClassDTO dto = new ClassDTO();
        BeanUtils.copyProperties(classEntity, dto);
        dto.setCoachId(classEntity.getCoach().getId());
        if (!(classEntity.getCustomers() == null)) {
            dto.setCustomerIds(classEntity.getCustomers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }
}

