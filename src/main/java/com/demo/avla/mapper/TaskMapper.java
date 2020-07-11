package com.demo.avla.mapper;

import com.demo.avla.entity.Task;
import com.demo.avla.exception.ServiceException;
import com.demo.avla.model.response.TaskResponse;
import com.demo.avla.type.StatusType;
import com.demo.avla.type.ProgressType;
import com.demo.avla.utils.EmployeeUtils;

import java.util.stream.Stream;

public final class TaskMapper {

    public static TaskResponse from(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setIdEmployee(task.getIdEmployee());
        taskResponse.setEmployee(EmployeeUtils.getFullEmployeeName(task.getIdEmployee()));
        taskResponse.setProgress(task.getProgress());
        taskResponse.setDescriptionProgress(searchDescriptionProgress(task.getProgress()));
        taskResponse.setStatus(task.getStatus());
        taskResponse.setDescriptionStatus(searchDescriptionStatus(task.getStatus()));
        return taskResponse;
    }

    private static String searchDescriptionProgress(String progress) {
        return Stream.of(ProgressType.values())
                .filter(progressType -> progressType.getCode().equals(progress))
                .findFirst()
                .orElseThrow(() -> new ServiceException("The type of progress was not found.")).getDescription();
    }

    private static String searchDescriptionStatus(String status) {
        return Stream.of(StatusType.values())
                .filter(statusType -> statusType.getCode().equals(status))
                .findFirst()
                .orElseThrow(() -> new ServiceException("The type of status was not found.")).name();
    }

}
