package com.demo.avla.model.response;

public class TaskResponse {

    private Long id;

    private String name;

    private String description;

    private String descriptionProgress;

    private String employee;

    private String descriptionStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionProgress() {
        return descriptionProgress;
    }

    public void setDescriptionProgress(String descriptionProgress) {
        this.descriptionProgress = descriptionProgress;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public void setDescriptionStatus(String descriptionStatus) {
        this.descriptionStatus = descriptionStatus;
    }
}
