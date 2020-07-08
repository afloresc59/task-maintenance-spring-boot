package com.demo.avla.service;

import com.demo.avla.entity.Task;
import com.demo.avla.request.TaskRequest;
import com.demo.avla.repository.TaskRepository;
import com.demo.avla.service.base.TaskBase;
import com.demo.avla.utils.EmployeeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements TaskBase {

    Logger logger = LogManager.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void save(TaskRequest request) {
        try {
            Task task = buildTask(request);
            task.setUserRegistration(EmployeeUtils.DEFAULT_EMPLOYEE);
            task.setDateRegistration(new Date());
            this.taskRepository.save(task);
        } catch (Exception ex) {
            this.logger.error("There was an error in the method save() - TaskService." + ex);
        }

    }

    @Override
    public void update(TaskRequest request) {
        try {
            Optional<Task> optOriginalTask = this.taskRepository.findById(request.getIdTask());
            if(optOriginalTask.isPresent()) {
                Task task = buildTask(request);
                task.setId(optOriginalTask.get().getId());
                task.setUserRegistration(optOriginalTask.get().getUserRegistration());
                task.setDateRegistration(optOriginalTask.get().getDateRegistration());
                task.setUserUpdate(EmployeeUtils.DEFAULT_EMPLOYEE);
                task.setDateUpdate(new Date());
                this.taskRepository.save(task);
            }
        } catch (Exception ex) {
            this.logger.error("There was an error in the method update() - TaskService." + ex);
        }
    }

    @Override
    public List<Task> searchAllTasks() {
        return (List<Task>) this.taskRepository.findAll();
    }

    @Override
    public Task searchTask(Long id) {
        return this.taskRepository.findById(id).get();
    }

    private Task buildTask(TaskRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setIdEmployee(request.getIdEmployee());
        task.setEnabled(request.getEnabled());
        return task;
    }
}
