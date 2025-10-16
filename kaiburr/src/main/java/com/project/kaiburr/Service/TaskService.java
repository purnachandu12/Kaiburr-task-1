package com.project.kaiburr.Service;

import com.project.kaiburr.Model.Task;
import com.project.kaiburr.repository.TaskRepoimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class TaskService {

    TaskRepoimp taskRepoimp;

    private static final Pattern forbidden = Pattern.compile("[;&|`$<>\\\\*(){}\\[\\]]");

    private static final Set<String> allowed = Set.of("echo", "date", "ls", "pwd");

    @Autowired
    TaskService(TaskRepoimp taskRepoimp){
        this.taskRepoimp=taskRepoimp;
    }

    public Task save(Task task){
        if(!isValid(task.getCommand())){
            return null;
        }
        return taskRepoimp.save(task);
    }

    public List<Task> getAllTasks(){
        return taskRepoimp.getAllTasks();
    }

    public Task getById(String id){
        return taskRepoimp.findById(id);
    }

    public List<Task> getByName(String name){
        return taskRepoimp.findTasksByName(name);
    }

    public Task DeleteById(String id){
        return taskRepoimp.DeleteById(id);
    }

    public Task ExecuteById(String id){
        return taskRepoimp.ExecutedById(id);
    }

    public static boolean isValid(String command) {
        if (command == null || command.trim().isEmpty()) return false;
        command = command.trim();
        if (forbidden.matcher(command).find()) return false;
        if (command.contains("&&") || command.contains("||")) return false;
        if (command.length() > 1000) return false;

        // optional: allowlist check
        String firstWord = command.split("\\s+")[0];
        return allowed.contains(firstWord);
    }

}
