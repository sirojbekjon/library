package com.example.library.controller;

import com.example.library.entity.Managment;
import com.example.library.entity.User;
import com.example.library.log.Loggerr;
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
@RequestMapping("/api/manage")
public class ManagmentController {


    private final ManagmentRepository managmentRepository;

    @PostMapping("/add")
    public HttpEntity<?> addManagment(@RequestBody Managment managment){
        Loggerr.log();
        Managment managment1 = new Managment(managment.getName());
        Managment save = managmentRepository.save(managment1);
        return ResponseEntity.status(202).body("Muvoffaqqiyatli saqlandi");
    }


    @GetMapping("/get/all")
    public HttpEntity<List<Managment>> getAllManagment(){
        Loggerr.log();
        List<Managment> all = managmentRepository.findAll();
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/get/{id}")
    public HttpEntity<?> getManagmentById(@PathVariable Long id){
        Loggerr.log();
        Optional<Managment> managmentRepositoryById = managmentRepository.findById(id);
        if (managmentRepositoryById.isPresent()){
            Managment managments = managmentRepositoryById.get();
            return ResponseEntity.status(200).body(managments);
        }
        return ResponseEntity.status(404).body("Topilmadi");
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> editManagment(@PathVariable Long id, @RequestBody Managment managment){
        Loggerr.log();
        Optional<Managment> managmentRepositoryById = managmentRepository.findById(id);
        if (managmentRepositoryById.isPresent()){
            Managment managment1 = managmentRepositoryById.get();
            managment1.setName(managment.getName());
            managmentRepository.save(managment1);
        }
        return ResponseEntity.status(202).body("Muvaffaqqiyatli o'zgartirildi");
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteManagment(@PathVariable Long id){
        Loggerr.log();
        managmentRepository.deleteById(id);
        return ResponseEntity.status(200).body("Muvaffaqqiyatli o'chirildi");
    }
}

