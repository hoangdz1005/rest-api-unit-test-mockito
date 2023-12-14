package com.project.restfulapi.service;
import com.project.restfulapi.entity.Project;
import com.project.restfulapi.entity.Task;
import com.project.restfulapi.mapper.TaskMapper;
import com.project.restfulapi.model.MessageResponse;
import com.project.restfulapi.model.TaskReq;
import com.project.restfulapi.model.TaskRes;
import com.project.restfulapi.repository.ProjectRepository;
import com.project.restfulapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;

    public TaskRes addTaskToProject (Long projectId, TaskReq taskReq) {
        Task task = taskMapper.mapTaskFromTaskReq(taskReq);
        taskRepository.save(task);
        Project project = projectRepository.findById(projectId).get();
        task.setProject(project);
        project.getTasks().add(task);
        projectRepository.save(project);
        taskRepository.save(task);
        return taskMapper.mapTaskResFromTask(task);
    }
    public TaskRes getTask(Long taskId) {
        return taskMapper.mapTaskResFromTask(taskRepository.findById(taskId).get());
    }
    public List<TaskRes> getTasksFromProject(Long projectId) {
        List<Task> tasks = projectRepository.findById(projectId).get().getTasks();
        return taskMapper.mapListTaskResFromListTask(tasks);
    }
    public TaskRes updateTask(Long id, TaskReq taskReq) {
        Task task = taskRepository.findById(id).get();
        task.setName(taskReq.getName());
        task.setEndDate(taskReq.getEndDate());
        taskRepository.save(task);
        return taskMapper.mapTaskResFromTask(task);
    }
    public MessageResponse deleteTask(Long id) {
        taskRepository.deleteById(id);
        return new MessageResponse("Task has been deleted!");
    }
}
