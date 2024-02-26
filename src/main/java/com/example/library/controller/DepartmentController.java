package com.example.library.controller;

import com.example.library.entity.Department;
import com.example.library.entity.Managment;
import com.example.library.log.Loggerr;
import com.example.library.payload.DepartmentDto;
import com.example.library.repository.DepartmentRepository;
import com.example.library.repository.ManagmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {

    
    private final DepartmentRepository departmentRepository;
    private final ManagmentRepository managmentRepository;

    @PostMapping("/add")
    public HttpEntity<?> addDepartment(@RequestBody DepartmentDto departmentDto){
        Loggerr.log();
        Optional<Managment> optionalManagment = managmentRepository.findById(departmentDto.getManagment());
        if (optionalManagment.isPresent()){
        Department department1 = new Department(
                departmentDto.getName(),
                optionalManagment.get()
        );
        departmentRepository.save(department1);
            return ResponseEntity.status(202).body(department1);
        }
        return ResponseEntity.status(409).body("Not saved");
    }

    @GetMapping("/get/all")
    public HttpEntity<?> getDepartments(){
        Loggerr.log();
        List<Department> departments = departmentRepository.findAll();
        if (!departments.isEmpty()){
            return ResponseEntity.status(202). body(departments);
        }
        return ResponseEntity.status(404).body("Topilmadi");
    }

    @GetMapping("/get/{id}")
    public HttpEntity<?> getDepartmentById(@PathVariable Long id){
        Loggerr.log();
        Optional<Department> departmentRepositoryById = departmentRepository.findById(id);
        if (departmentRepositoryById.isPresent()){
            return ResponseEntity.status(202). body(departmentRepositoryById.get());
        }
        return ResponseEntity.status(404).body("Topilmadi");
    }

    @GetMapping("/get/bymanage/{id}")
    public HttpEntity<?> getDepartmentByManagId(@PathVariable Long id){
        Loggerr.log();
        List<Department> departmentRepositoryById = departmentRepository.findDepartmentByManagment_Id(id);
        if (!departmentRepositoryById.isEmpty()){
            return ResponseEntity.status(202). body(departmentRepositoryById);
        }
        return ResponseEntity.status(404).body("Topilmadi");
    }


    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteDepartment(@PathVariable Long id){
        Loggerr.log();
        departmentRepository.deleteById(id);
        return ResponseEntity.status(202).body("O'chirildi");
    }


    @PutMapping("/edit/{id}")
    public HttpEntity<?> editDepartment (@PathVariable Long id, @RequestBody DepartmentDto departmentDto){
        Loggerr.log();
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        Optional<Managment> optionalManagment = managmentRepository.findById(departmentDto.getManagment());
        if (optionalDepartment.isPresent() && optionalDepartment.isPresent()){
            Department department1 = optionalDepartment.get();
            department1.setName(departmentDto.getName());
            department1.setManagment(optionalManagment.get());
            departmentRepository.save(department1);
            return ResponseEntity.status(202).body(department1);
        }
        return ResponseEntity.status(404).body("Saqlanmadi");
    }
}
