package com.project.restfulapi.validator;

import com.project.restfulapi.exception.BadRequestException;
import com.project.restfulapi.exception.EntityNotFoundException;
import com.project.restfulapi.repository.ProjectRepository;
import com.project.restfulapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TaskValidator {
    private static final String END_DATE_NOT_VALID = "EndDate must be greater than now!";
    private static final String TASK_NOT_EXIST = "Task not exits!";

    private final TaskRepository taskRepository;
    public void validateEndDate(Instant endDate) {
        if(endDate.isAfter(Instant.now())) {return;}
        throw new BadRequestException(END_DATE_NOT_VALID);
    }
    public void validateTaskExist(Long id) {
        if(taskRepository.findById(id).isPresent()) {return;}
        throw new EntityNotFoundException(TASK_NOT_EXIST);
    }
}
