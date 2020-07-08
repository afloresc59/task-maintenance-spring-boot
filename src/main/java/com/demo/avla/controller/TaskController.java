package com.demo.avla.controller;

import com.demo.avla.entity.Task;
import com.demo.avla.request.TaskRequest;
import com.demo.avla.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/save")
    public void save(@RequestBody TaskRequest request) {
        this.taskService.save(request);
    }

    @PutMapping("/update")
    public void update(@RequestBody TaskRequest request) {
        this.taskService.update(request);
    }

    @GetMapping("/search")
    public List<Task> searchAllTasks() {
        return this.taskService.searchAllTasks();
    }

    @GetMapping("/search/{id}")
    public Task searchTask(@PathVariable("id") Long id) {
        return this.taskService.searchTask(id);
    }

}
