package com.demo.avla.model.response;

public class EmployeeResponse {

    public EmployeeResponse(Long id, String names, String surnames) {
        this.id = id;
        this.names = names;
        this.surnames = surnames;
    }

    private Long id;

    private String names;

    private String surnames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }
}
