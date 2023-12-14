package com.project.restfulapi.mapper;

import com.project.restfulapi.entity.Project;
import com.project.restfulapi.model.ProjectReq;
import com.project.restfulapi.model.ProjectRes;
import com.project.restfulapi.model.TaskRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMapper {

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    public ProjectRes mapProjectResFromProject(Project from) {
        ProjectRes projectRes = new ProjectRes();
        projectRes.setId(from.getId());
        projectRes.setName(from.getName());
        projectRes.setStartDate(from.getStartDate());
        projectRes.setEndDate(from.getEndDate());
        projectRes.setManager(userMapper.mapUserResFromUser(from.getManager()));
        List<TaskRes> resList= new ArrayList<>();
        from.getTasks().forEach(t -> resList.add(taskMapper.mapTaskResFromTask(t)));
        projectRes.setTasks(resList);
        return projectRes;
    }
    public Project mapProjectFromProjectReq (ProjectReq from) {
        Project to = new Project();
        to.setName(from.getName());
        to.setStartDate(Instant.now());
        to.setEndDate(from.getEndDate());
        return to;
    }
    public List<ProjectRes> mapListProjectResFromListProject(List<Project> from) {
        List<ProjectRes> to = new ArrayList<>();
        from.forEach(p -> to.add(mapProjectResFromProject(p)));
        return to;
    }
}
