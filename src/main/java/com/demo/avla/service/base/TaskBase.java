package com.demo.avla.service.base;

import com.demo.avla.model.request.TaskRequest;
import com.demo.avla.model.response.TaskResponse;

import java.util.List;

public interface TaskBase {
    void save(TaskRequest request);
    void update(TaskRequest request);
    void delete(TaskRequest request);
    List<TaskResponse> searchAllTasks();
    TaskResponse searchTask(Long id);
    void assignEmployee(Long idTask, Long idEmployee);
    void completeTaskBatch(List<TaskRequest> tasks);
    List<TaskResponse> customSearchTask(TaskRequest taskRequest);
}
