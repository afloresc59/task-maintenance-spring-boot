package com.demo.avla.utils;

import com.demo.avla.exception.ServiceException;
import com.demo.avla.model.response.EmployeeResponse;

import java.util.Arrays;
import java.util.List;

public final class EmployeeUtils {

    private EmployeeUtils() {
        throw new UnsupportedOperationException("Do not instantiate this class, use statically.");
    }

    public static final String DEFAULT_EMPLOYEE = "ADMIN";

    public static List<EmployeeResponse> buildMockEmployees() {
        EmployeeResponse employeeOne = new EmployeeResponse(1L, "ANTHONY OSWALDO", "FLORES CARRASCO");
        EmployeeResponse employeeTwo = new EmployeeResponse(2L, "JOSE", "GARCIA CASTILLO");
        EmployeeResponse employeeThree = new EmployeeResponse(3L, "ROCKY", "FLORES CARRASCO");
        return Arrays.asList(employeeOne, employeeTwo, employeeThree);
    }

    public static String getFullEmployeeName(Long id) {
        EmployeeResponse employeeResponse = buildMockEmployees().stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ServiceException("The employee is not registered."));

        return employeeResponse.getNames() + " " + employeeResponse.getSurnames();
    }

}
