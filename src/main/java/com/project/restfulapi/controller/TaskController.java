package com.project.restfulapi.controller;

import com.project.restfulapi.model.MessageResponse;
import com.project.restfulapi.model.TaskReq;
import com.project.restfulapi.model.TaskRes;
import com.project.restfulapi.service.TaskService;
import com.project.restfulapi.validator.ProjectValidator;
import com.project.restfulapi.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskValidator taskValidator;
    private final ProjectValidator projectValidator;

    @PostMapping("/tasks/project/{id}")
    public ResponseEntity<TaskRes> addTask(@PathVariable Long id, @RequestBody TaskReq taskReq) {
        projectValidator.validateProjectExist(id);
        taskValidator.validateEndDate(taskReq.getEndDate());
        return new ResponseEntity<>(taskService.addTaskToProject(id, taskReq), HttpStatus.CREATED);
    }
    @GetMapping("/tasks/project/{id}")
    public ResponseEntity<List<TaskRes>> getTasksFromProject(@PathVariable Long id) {
        taskValidator.validateTaskExist(id);
        return new ResponseEntity<>(taskService.getTasksFromProject(id), HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskRes> getTask(@PathVariable Long id) {
        taskValidator.validateTaskExist(id);
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskRes> updateTask(@PathVariable Long id, @RequestBody TaskReq taskReq) {
        taskValidator.validateTaskExist(id);
        taskValidator.validateEndDate(taskReq.getEndDate());
        return new ResponseEntity<>(taskService.updateTask(id, taskReq), HttpStatus.OK);
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<MessageResponse> deleteTask(@PathVariable Long id) {
        taskValidator.validateTaskExist(id);
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }
}
