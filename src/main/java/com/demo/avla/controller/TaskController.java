package com.demo.avla.controller;

import com.demo.avla.model.request.TaskRequest;
import com.demo.avla.model.response.TaskResponse;
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
    public List<TaskResponse> searchAllTasks() {
        return this.taskService.searchAllTasks();
    }

    @GetMapping("/search/{id}")
    public TaskResponse searchTask(@PathVariable("id") Long id) {
        return this.taskService.searchTask(id);
    }

    @PutMapping("/delete")
    public void delete(@RequestBody TaskRequest request) {
        this.taskService.delete(request);
    }

    @PutMapping("/assign/{idTask}/employee/{idEmployee}")
    public void assignEmployee(@PathVariable("idTask") Long idTask, @PathVariable("idEmployee") Long idEmployee) {
        this.taskService.assignEmployee(idTask, idEmployee);
    }

}
