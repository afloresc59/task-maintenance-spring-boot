package com.demo.avla.type;

public enum StatusType {
    ACTIVE("1"),
    INACTIVE("0");

    StatusType(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
