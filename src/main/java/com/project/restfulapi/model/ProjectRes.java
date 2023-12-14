package com.project.restfulapi.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ProjectRes {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private UserRes manager;
    private List<TaskRes> tasks;

    public ProjectRes() {
    }

    public ProjectRes(Long id, String name,Instant startDate, Instant endDate, UserRes manager, List<TaskRes> tasks) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.tasks = tasks;
    }
}
