package com.example.library.entity;

import com.example.library.entity.template.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileUpload extends AbstractEntity {

    private String name;
    private String originalName;
    @JsonIgnore
    private Long size;
    @JsonIgnore
    private String contentType;

}
