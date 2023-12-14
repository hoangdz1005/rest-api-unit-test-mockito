package com.project.restfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRes {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant endDate;

}
