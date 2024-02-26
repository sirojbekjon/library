package com.example.library.entity;

import com.example.library.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Department extends AbstractEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "managment_id")
    private Managment managment;


    public Department(String name, Managment managment) {
        this.name = name;
        this.managment = managment;
    }
}
