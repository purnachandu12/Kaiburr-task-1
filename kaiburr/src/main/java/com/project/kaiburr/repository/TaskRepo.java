package com.project.kaiburr.repository;

import com.project.kaiburr.Model.Task;

import java.util.List;

public interface TaskRepo {
    List<Task>  getAllTasks();
    Task findById(String id);
    Task save(Task task);
    List<Task> findTasksByName(String name);
    Task DeleteById(String id);
    Task ExecutedById(String id);
}
