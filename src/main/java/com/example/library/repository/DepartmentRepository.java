package com.example.library.repository;

import com.example.library.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findDepartmentByManagment_Id(Long managment_id);
}
