package com.example.library.entity.template;


import com.example.library.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@Data
public abstract class AbstractEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @JsonIgnore
    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
    @JsonIgnore
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @JsonIgnore
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;


}
