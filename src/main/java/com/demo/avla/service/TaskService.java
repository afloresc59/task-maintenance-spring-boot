package com.demo.avla.service;

import com.demo.avla.entity.Task;
import com.demo.avla.exception.ServiceException;
import com.demo.avla.mapper.TaskMapper;
import com.demo.avla.model.request.TaskRequest;
import com.demo.avla.model.response.TaskResponse;
import com.demo.avla.repository.TaskRepository;
import com.demo.avla.service.base.TaskBase;
import com.demo.avla.utils.EmployeeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw  ex;
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
            } else {
                throw new ServiceException("The task searched doesn't exists.");
            }
        } catch (Exception ex) {
            this.logger.error("There was an error in the method update() - TaskService." + ex);
            throw  ex;
        }
    }

    @Override
    public List<TaskResponse> searchAllTasks() {
        List<Task> listTasks = (List<Task>) this.taskRepository.findAll();
        return listTasks.stream().map(task -> TaskMapper.from(task)).collect(Collectors.toList());
    }

    @Override
    public TaskResponse searchTask(Long id) {
        Optional<Task> optTask = this.taskRepository.findById(id);
        if(!optTask.isPresent()) {
            throw new ServiceException("The task searched doesn't exists.");
        }
        return TaskMapper.from(this.taskRepository.findById(id).get());
    }

    private Task buildTask(TaskRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setProgress(request.getProgress());
        task.setIdEmployee(request.getIdEmployee());
        task.setStatus(request.getStatus());
        return task;
    }
}
