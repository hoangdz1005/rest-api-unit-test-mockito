package com.project.restfulapi.service;
import com.project.restfulapi.entity.Project;
import com.project.restfulapi.entity.User;
import com.project.restfulapi.mapper.ProjectMapper;
import com.project.restfulapi.model.ProjectReq;
import com.project.restfulapi.model.ProjectRes;
import com.project.restfulapi.repository.ProjectRepository;
import com.project.restfulapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    public ProjectRes addProject(Long managerId, ProjectReq projectReq) {
        Project project = projectMapper.mapProjectFromProjectReq(projectReq);
        User manager = userRepository.findById(managerId).get();
        project.setManager(manager);
        projectRepository.save(project);
        return projectMapper.mapProjectResFromProject(project);
    }

    public ProjectRes getProject (Long id) {
        Project project = projectRepository.findById(id).get();
        return projectMapper.mapProjectResFromProject(project);
    }
    public List<ProjectRes> getProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.mapListProjectResFromListProject(projects);
    }
    public ProjectRes updateProject(Long id, @RequestBody ProjectReq projectReq) {
        Project project = projectRepository.findById(id).get();
        project.setName(projectReq.getName());
        project.setEndDate(projectReq.getEndDate());
        projectRepository.save(project);
        return projectMapper.mapProjectResFromProject(project);
    }
    public ProjectRes updateDeadline (Long id, Instant endDate) {
        Project project = projectRepository.findById(id).get();
        project.setEndDate(endDate);
        projectRepository.save(project);
        return projectMapper.mapProjectResFromProject(project);
    }
    public ProjectRes replaceManager(Long projectId, Long managerId) {
        Project project = projectRepository.findById(projectId).get();
        User manager = userRepository.findById(managerId).get();
        project.setManager(manager);
        projectRepository.save(project);
        return projectMapper.mapProjectResFromProject(project);
    }
}
