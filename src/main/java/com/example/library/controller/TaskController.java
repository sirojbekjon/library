package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.log.Loggerr;
import com.example.library.payload.TaskDto;
import com.example.library.security.CurrentUser;
import com.example.library.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add")
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto){
        Loggerr.log();
        return taskService.addTask(taskDto);
    }

    @GetMapping("/get/{id}")
    public HttpEntity<?> getTaskById(@PathVariable Long id){
        Loggerr.log();
        return taskService.findTaskById(id);
    }

    @GetMapping("/get/all")
    public HttpEntity<?> getTasksAll(@CurrentUser User user, int page){
        Loggerr.log();
        return taskService.findTasks(user,page);
    }





}
