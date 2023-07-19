package com.example.library.repository;

import com.example.library.entity.Library;
import com.example.library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LibraryRepository extends JpaRepository<Library,Long> {


    boolean existsByName(String name);
}
