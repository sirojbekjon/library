package com.example.library.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {

    private String name;
    private String date;
    private String lastDate;
    private Long userId;
    private boolean status;
    private boolean result;
    private Long fileUploadId;

}
