package com.example.library.repository;

import com.example.library.entity.Managment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagmentRepository extends JpaRepository<Managment, Long> {
}
