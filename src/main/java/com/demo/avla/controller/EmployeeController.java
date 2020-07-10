package com.demo.avla.controller;

import com.demo.avla.model.response.EmployeeResponse;
import com.demo.avla.utils.EmployeeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/search")
    public List<EmployeeResponse> searchAllEmployees() {
        return EmployeeUtils.buildMockEmployees();
    }

}
