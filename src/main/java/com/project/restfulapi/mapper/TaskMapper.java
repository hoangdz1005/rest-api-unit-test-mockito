package com.project.restfulapi.mapper;

import com.project.restfulapi.entity.Task;
import com.project.restfulapi.model.TaskReq;
import com.project.restfulapi.model.TaskRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskMapper {
    public TaskRes mapTaskResFromTask(Task from) {
        TaskRes to = new TaskRes();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        return to;
    }
    public Task mapTaskFromTaskReq(TaskReq from) {
        Task to = new Task();
        to.setName(from.getName());
        to.setStartDate(Instant.now());
        to.setEndDate(from.getEndDate());
        return to;
    }
    public List<TaskRes> mapListTaskResFromListTask(List<Task> from) {
        List<TaskRes> to = new ArrayList<>();
        from.forEach(t -> to.add(mapTaskResFromTask(t)));
        return to;
    }
}
