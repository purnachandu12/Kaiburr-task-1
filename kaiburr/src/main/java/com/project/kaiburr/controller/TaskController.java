package com.project.kaiburr.controller;

import com.project.kaiburr.Model.Task;
import com.project.kaiburr.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Tasks")
public class TaskController {

    TaskService taskService;

    @Autowired
    TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @PostMapping("/add")
    public Task save(@RequestBody Task task){
        return taskService.save(task);
    }

    @GetMapping
    public List<Task> get(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable String id){
        return taskService.getById(id);
    }

    @GetMapping("/Search/{name}")
    public ResponseEntity<List<Task>> getByName(@PathVariable String name){
        List<Task> tasks=taskService.getByName(name);
        if(tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(tasks);
        }
    }

    @DeleteMapping("/Delete/{id}")
    public Task DeleteById(@PathVariable String id){
        return taskService.DeleteById(id);
    }

    @PutMapping("/Execute/{id}")
    public Task ExecutedById(@PathVariable String id){
        return taskService.ExecuteById(id);
    }

}
