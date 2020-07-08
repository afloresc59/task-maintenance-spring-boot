package com.demo.avla.service.base;

import com.demo.avla.entity.Task;
import com.demo.avla.request.TaskRequest;

import java.util.List;

public interface TaskBase {
    void save(TaskRequest request);
    void update(TaskRequest request);
    List<Task> searchAllTasks();
    Task searchTask(Long id);
}
