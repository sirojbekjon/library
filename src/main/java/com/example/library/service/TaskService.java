package com.example.library.service;

import com.example.library.entity.FileUpload;
import com.example.library.entity.Task;
import com.example.library.entity.User;
import com.example.library.payload.TaskDto;
import com.example.library.repository.FileUploadRepository;
import com.example.library.repository.TaskRepository;
import com.example.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FileUploadRepository fileUploadRepository;

    public HttpEntity<?> addTask(TaskDto taskDto) {

        Optional<User> optionalUser = userRepository.findById(taskDto.getUserId());
        Optional<FileUpload> optionalFileUpload = fileUploadRepository.findById(taskDto.getFileUploadId());

        if (optionalUser.isPresent() || optionalFileUpload.isPresent()) {
            User user = optionalUser.get();
            FileUpload fileUpload = optionalFileUpload.get();
            Task task = new Task(taskDto.getName(),
                    taskDto.getDate(),
                    taskDto.getLastDate(),
                    user,
                    taskDto.isStatus(),
                    taskDto.isResult(),
                    fileUpload);
            Task savedTask = taskRepository.save(task);
            return ResponseEntity.status(202).body(savedTask);
        }
        return ResponseEntity.status(409).body("Saqlanmadi");
    }

    public HttpEntity<?> findTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()){
            Task task = taskOptional.get();
            return ResponseEntity.status(202).body(task);
        }
        return ResponseEntity.status(409).body("Topilmadi");
    }


    public HttpEntity<?> findTasks(User user, int page) {
        try {
            if (user.getRole().getName().equals("SUPERADMIN")) {
                Page<Task> tasks = taskRepository.findAllTask(PageRequest.of(page, 10));
                return ResponseEntity.status(202).body(tasks);
            }
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not allowed");
        }
        catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
        }
}
