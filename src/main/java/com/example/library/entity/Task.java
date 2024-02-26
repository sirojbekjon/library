package com.example.library.entity;
import com.example.library.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task extends AbstractEntity {
    private String name;
    private String date;
    private String lastdate;

    @ManyToOne
    private User user;

    private Boolean status;
    private Boolean result;
    @ManyToOne
    private FileUpload fileUpload;


}



