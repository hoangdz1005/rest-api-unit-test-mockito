package com.project.restfulapi.service;

import com.project.restfulapi.entity.Project;
import com.project.restfulapi.entity.Task;
import com.project.restfulapi.entity.User;
import com.project.restfulapi.mapper.ProjectMapper;
import com.project.restfulapi.model.ProjectReq;
import com.project.restfulapi.model.ProjectRes;
import com.project.restfulapi.model.TaskRes;
import com.project.restfulapi.model.UserRes;
import com.project.restfulapi.repository.ProjectRepository;
import com.project.restfulapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    ProjectService projectService;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProjectMapper projectMapper;
    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;

    private ProjectRes projectRes;
    private ProjectReq projectReq;
    private Project projectEntity;
    private User managerEntity;
    private UserRes managerRes;
    private Long projectId;
    private Long managerId;
    private List<Project> projectList = new ArrayList<>();
    private List<ProjectRes> projectResList= new ArrayList<>();
    private List<Task> taskList;
    private List<TaskRes> taskResList;
    private ProjectRes projectUpdateRes;
    private ProjectReq projectUpdateReq;

    @BeforeEach
    void setup() {
        // GIVEN
        managerId = 1L;
        managerEntity = new User(1L, "devfullstack", "12345678", "devfullstack@gmail.com");
        managerRes = new UserRes(1L, "devfullstack", "devfullstack@gmail.com");
        projectReq = new ProjectReq("Microservice With Kafka In Spring Boot 3", Instant.parse("2024-12-12T12:00:00Z"));
        projectUpdateReq = new ProjectReq("Microservice With Kafka In Spring Boot 4x", Instant.parse("2025-01-12T12:00:00Z"));
        projectUpdateRes = new ProjectRes(1L, "Microservice With Kafka In Spring Boot 4x",Instant.parse("2024-01-12T12:00:00Z"), Instant.parse("2025-01-12T12:00:00Z"), managerRes, taskResList);
        projectEntity = new Project(1L, "Microservice With Kafka In Spring Boot 3",Instant.parse("2024-01-12T12:00:00Z"), Instant.parse("2024-12-12T12:00:00Z"), managerEntity,taskList);
        projectRes = new ProjectRes(1L, "Microservice With Kafka In Spring Boot 3",Instant.parse("2024-01-12T12:00:00Z"), Instant.parse("2024-12-12T12:00:00Z"),managerRes, taskResList);
        projectList.add(projectEntity);
        projectResList.add(projectRes);
    }

    @Test
    void addProject() {
        // WHEN
        when(userRepository.findById(managerId)).thenReturn(Optional.of(managerEntity));
        when(projectMapper.mapProjectFromProjectReq(projectReq)).thenReturn(projectEntity);
        when(projectMapper.mapProjectResFromProject(projectEntity)).thenReturn(projectRes);
        // THEN
        ProjectRes result = projectService.addProject(managerId, projectReq);
        assertThat(result).isEqualTo(projectRes);
        verify(projectRepository, times(1)).save(projectEntity);
    }

    @Test
    void getProject() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(projectMapper.mapProjectResFromProject(projectEntity)).thenReturn(projectRes);
        ProjectRes result = projectService.getProject(projectId);
        assertThat(result).isEqualTo(projectRes);
    }

    @Test
    void getProjects() {
        when(projectRepository.findAll()).thenReturn(projectList);
        when(projectMapper.mapListProjectResFromListProject(projectList)).thenReturn(projectResList);
        List<ProjectRes> result = projectService.getProjects();
        assertThat(result).isEqualTo(projectResList);
    }

    @Test
    void updateProject() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(projectMapper.mapProjectResFromProject(projectEntity)).thenReturn(projectUpdateRes);
        ProjectRes result = projectService.updateProject(projectId, projectReq);
        assertThat(result).isEqualTo(projectUpdateRes);

    }

    @Test
    void updateDeadline() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(projectMapper.mapProjectResFromProject(projectEntity)).thenReturn(projectUpdateRes);
        ProjectRes result = projectService.updateDeadline(projectId,Instant.parse("2025-01-12T12:00:00Z"));
        assertThat(result).isEqualTo(projectUpdateRes);
        verify(projectRepository, times(1)).save(projectEntity);
    }

    @Test
    void replaceManager() {
        when(userRepository.findById(managerId)).thenReturn(Optional.of(managerEntity));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(projectMapper.mapProjectResFromProject(projectEntity)).thenReturn(projectRes);
        ProjectRes result = projectService.replaceManager(projectId, managerId);
        assertThat(result).isEqualTo(projectRes);
        verify(projectRepository,times(1)).save(projectEntity);
    }
}