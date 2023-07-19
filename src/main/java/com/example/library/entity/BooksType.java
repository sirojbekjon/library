package com.example.library.entity;


import com.example.library.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books_type")
public class BooksType extends AbstractEntity {

    @Column(nullable = false)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    private Library library;

}
