package com.project.kaiburr.repository;

import com.project.kaiburr.Model.Task;
import com.project.kaiburr.Model.TaskExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public class TaskRepoimp implements TaskRepo{

    MongoTemplate mongoTemplate;

    @Autowired
    TaskRepoimp(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    @Override
    public List<Task> getAllTasks() {
        return mongoTemplate.findAll(Task.class);
    }

    @Override
    public Task findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Task.class);
    }

    @Override
    public Task save(Task task){
        mongoTemplate.save(task);
        return task;
    }

    @Override
    public List<Task> findTasksByName(String name) {
        List<Task> allTasks = mongoTemplate.findAll(Task.class);
        return allTasks.stream()
                .filter(task -> task.getName() != null && task.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public Task DeleteById(String id) {
        Task task=findById(id);
        mongoTemplate.remove(task);
        return task;
    }

    @Override
    public Task ExecutedById(String id){
        Task task = findById(id);
        if (task == null) {
            return null;
        }

        try {
            Date startTime = new Date();
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", task.getCommand());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            String output = new String(process.getInputStream().readAllBytes());
            process.waitFor();

            Date endTime = new Date();
            TaskExecution execution = new TaskExecution(startTime, endTime, output.trim());

            task.getTaskExecutions().add(execution);
            mongoTemplate.save(task);

            return task;
        } catch (Exception e) {
            throw new RuntimeException("Error executing command: " + e.getMessage());
        }
    }


}
