package com.project.restfulapi.validator;

import com.project.restfulapi.exception.BadRequestException;
import com.project.restfulapi.exception.EntityNotFoundException;
import com.project.restfulapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ProjectValidator {
    private static final String END_DATE_NOT_VALID = "EndDate must be greater than now!";
    private static final String PROJECT_NOT_EXIST = "Project not exits!";

    private final ProjectRepository projectRepository;
    public void validateEndDate(Instant endDate) {
        if(endDate.isAfter(Instant.now())) {return;}
        throw new BadRequestException(END_DATE_NOT_VALID);
    }
    public void validateProjectExist(Long id) {
        if(projectRepository.findById(id).isPresent()) {return;}
        throw new EntityNotFoundException(PROJECT_NOT_EXIST);
    }
}
