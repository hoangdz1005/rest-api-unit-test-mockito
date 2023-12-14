package com.project.restfulapi.controller;

import com.project.restfulapi.model.ProjectReq;
import com.project.restfulapi.model.ProjectRes;
import com.project.restfulapi.service.ProjectService;
import com.project.restfulapi.validator.ProjectValidator;
import com.project.restfulapi.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserValidator userValidator;
    private final ProjectValidator projectValidator;

    @PostMapping("/projects")
    public ResponseEntity<ProjectRes> addProject(@RequestParam(name = "managerId") Long managerId, @RequestBody ProjectReq projectReq) {
        userValidator.validateUserExist(managerId);
        projectValidator.validateEndDate(projectReq.getEndDate());
        return new ResponseEntity<>(projectService.addProject(managerId, projectReq), HttpStatus.OK);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectRes>> getProjects() {
        return new ResponseEntity<>(projectService.getProjects(), HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectRes> getProject(@PathVariable Long id) {
        projectValidator.validateProjectExist(id);
        return new ResponseEntity<>(projectService.getProject(id), HttpStatus.OK);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectRes> updateProject(@PathVariable Long id, @RequestBody ProjectReq projectReq) {
        projectValidator.validateProjectExist(id);
        projectValidator.validateEndDate(projectReq.getEndDate());
        return new ResponseEntity<>(projectService.updateProject(id, projectReq), HttpStatus.OK);
    }

    @PatchMapping("/projects/{projectId}/{managerId}")
    public ResponseEntity<ProjectRes> replaceManager(@PathVariable(name = "projectId") Long projectId, @PathVariable(name = "managerId") Long managerId ) {
        userValidator.validateUserExist(managerId);
        projectValidator.validateProjectExist(projectId);
        return new ResponseEntity<>(projectService.replaceManager(projectId, managerId), HttpStatus.OK);
    }
    @PatchMapping("/projects/{id}")
    public ResponseEntity<ProjectRes> updateDeadline(@PathVariable Long id, @RequestParam(name = "endDate") Instant endDate) {
        projectValidator.validateEndDate(endDate);
        return new ResponseEntity<>(projectService.updateDeadline(id, endDate), HttpStatus.OK);
    }
}
