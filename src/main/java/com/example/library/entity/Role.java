package com.example.library.entity;


import com.example.library.entity.enums.Permission;
import com.example.library.entity.template.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Permission> permissions;

}
