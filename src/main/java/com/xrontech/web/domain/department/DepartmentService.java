package com.xrontech.web.domain.department;

import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public ApplicationResponseDTO createDepartment(DepartmentDTO departmentDTO) {
        if(departmentRepository.findByName(departmentDTO.getName()).isPresent()){
            throw  new ApplicationCustomException(HttpStatus.BAD_REQUEST,"DEPARTMENT_NAME_EXIST","Department Name Already Exit");
        }
        departmentRepository.save(Department.builder()
                .name(departmentDTO.getName())
                .build());
        return new ApplicationResponseDTO(HttpStatus.CREATED, "NEW_DEPARTMENT_CREATED_SUCCESSFULLY", "New Department Created Successfully!");
    }

    public List<Department> getDepartments() {
        return  departmentRepository.findAllByNameNot("Admin");
    }

    public Department getDepartment(Long id) {
        return  departmentRepository.findById(id).orElseThrow(()-> new ApplicationCustomException(HttpStatus.NOT_FOUND,"INVALID_DEPARTMENT","Invalid Department"));
    }

    public Department getDepartment(String name) {
        return  departmentRepository.findByName(name).orElseThrow(()-> new ApplicationCustomException(HttpStatus.NOT_FOUND,"INVALID_DEPARTMENT","Invalid Department"));
    }

    public ApplicationResponseDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {

        Department department = departmentRepository.findById(id).orElseThrow(()-> new ApplicationCustomException(HttpStatus.NOT_FOUND,"INVALID_DEPARTMENT", "Invalid Department"));

        departmentRepository.findByName(departmentDTO.getName()).ifPresent(existingUser -> {
            if (!department.getName().equals(departmentDTO.getName())) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "MOBILE_ALREADY_EXISTS", "Mobile Already Exists");
            }
        });

        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "New_DEPARTMENT_UPDATE_DEPARTMENT", "New Department Updated Successfully!");
    }

}
