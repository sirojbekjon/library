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
public class Managment extends AbstractEntity {

    private String name;

    public Managment(String name) {
        this.name = name;
    }
}







