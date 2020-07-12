package com.demo.avla.integration;

import com.demo.avla.config.DataSourceConfig;
import com.demo.avla.controller.TaskController;
import com.demo.avla.model.request.TaskRequest;
import com.demo.avla.model.response.TaskResponse;
import com.demo.avla.type.ProgressType;
import com.demo.avla.type.StatusType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TaskController.class, includeFilters = { @ComponentScan.Filter(Service.class), @ComponentScan.Filter(Repository.class) })
@AutoConfigureDataJpa
@Sql(scripts = { "classpath:sql/structure.sql", "classpath:sql/data.sql" } , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Import(DataSourceConfig.class)
public class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldExecuteSaveTask() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TaskRequest request = new TaskRequest();
        request.setName("TASK THREE");
        request.setDescription("SIMPLE TASK THREE DESCRIPTION");
        request.setProgress(ProgressType.PENDING.getCode());
        request.setIdEmployee(1L);
        request.setStatus(StatusType.ACTIVE.getCode());

        this.mockMvc.perform(post("/task/save")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldExecuteUpdateTask() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TaskRequest request = new TaskRequest();
        request.setIdTask(1L);
        request.setName("TASK ONE UPDATED");
        request.setDescription("SIMPLE TASK ONE DESCRIPTION UPDATED");
        request.setProgress(ProgressType.FINISHED.getCode());
        request.setIdEmployee(2L);
        request.setStatus(StatusType.ACTIVE.getCode());

        this.mockMvc.perform(put("/task/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldExecuteSearchAllTasks() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/task/search")).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<TaskResponse> listTasks = objectMapper.readValue(response, new TypeReference<List<TaskResponse>>(){});

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(listTasks).isNotNull();
            assertions.assertThat(listTasks).isNotEmpty();
            assertions.assertThat(listTasks.size()).isEqualTo(2);
            assertions.assertThat(listTasks.get(0)).isNotNull();
            assertions.assertThat(listTasks.get(0).getId()).isEqualTo(1);
            assertions.assertThat(listTasks.get(0).getName()).isEqualTo("TASK ONE");
            assertions.assertThat(listTasks.get(1)).isNotNull();
            assertions.assertThat(listTasks.get(1).getId()).isEqualTo(2);
            assertions.assertThat(listTasks.get(1).getName()).isEqualTo("TASK TWO");
        });
    }

    @Test
    public void shouldExecuteCustomSearchTasks() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("TASK");
        taskRequest.setIdEmployee(null);

        MvcResult result = this.mockMvc.perform(post("/task/customSearch")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();


        List<TaskResponse> listTasks = objectMapper.readValue(response, new TypeReference<List<TaskResponse>>(){});

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(listTasks).isNotNull();
            assertions.assertThat(listTasks).isNotEmpty();
            assertions.assertThat(listTasks.size()).isEqualTo(2);
            assertions.assertThat(listTasks.get(0)).isNotNull();
            assertions.assertThat(listTasks.get(0).getId()).isEqualTo(1);
            assertions.assertThat(listTasks.get(0).getName()).isEqualTo("TASK ONE");
            assertions.assertThat(listTasks.get(1)).isNotNull();
            assertions.assertThat(listTasks.get(1).getId()).isEqualTo(2);
            assertions.assertThat(listTasks.get(1).getName()).isEqualTo("TASK TWO");
        });
    }

    @Test
    public void shouldExecuteSearchTask() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/task/search/1")).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        TaskResponse taskResponse = objectMapper.readValue(response, new TypeReference<TaskResponse>(){});

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(taskResponse).isNotNull();
            assertions.assertThat(taskResponse.getId()).isEqualTo(1);
            assertions.assertThat(taskResponse.getName()).isEqualTo("TASK ONE");
            assertions.assertThat(taskResponse.getDescription()).isEqualTo("SIMPLE TASK ONE DESCRIPTION");
            assertions.assertThat(taskResponse.getEmployee()).isEqualTo("ANTHONY OSWALDO FLORES CARRASCO");
            assertions.assertThat(taskResponse.getDescriptionStatus()).isEqualTo(StatusType.ACTIVE.name());
            assertions.assertThat(taskResponse.getDescriptionProgress()).isEqualTo(ProgressType.PENDING.name());
        });
    }

    @Test
    public void shouldExecuteDeleteTask() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TaskRequest request = new TaskRequest();
        request.setIdTask(1L);
        request.setStatus(StatusType.INACTIVE.getCode());

        this.mockMvc.perform(put("/task/delete")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldExecuteAssignTask() throws Exception {
        this.mockMvc.perform(put("/task/assign/{idTask}/employee/{idEmployee}", 1L, 2L))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldExecuteCompleteTaskBatch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TaskRequest taskRequestOne = new TaskRequest();
        taskRequestOne.setIdTask(1L);
        taskRequestOne.setProgress(ProgressType.FINISHED.getCode());
        TaskRequest taskRequestTwo = new TaskRequest();
        taskRequestTwo.setIdTask(2L);
        taskRequestTwo.setProgress(ProgressType.FINISHED.getCode());
        List<TaskRequest> listTasksRequest = Arrays.asList(taskRequestOne, taskRequestTwo);

        this.mockMvc.perform(put("/task/complete")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(listTasksRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrownExceptionInSaveTaskWhenSomeFieldIsNull() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TaskRequest request = new TaskRequest();
        request.setName(null);
        request.setDescription("SIMPLE TASK THREE DESCRIPTION");
        request.setProgress(ProgressType.PENDING.getCode());
        request.setIdEmployee(1L);
        request.setStatus(StatusType.ACTIVE.getCode());

        this.mockMvc.perform(post("/task/save")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().json("{'type':'GENERIC_ERROR'}"))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void shouldThrownExceptionInUpdateTaskWhenIdTaskIsNotValid() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        TaskRequest request = new TaskRequest();
        request.setIdTask(100L);
        request.setName("TASK ONE UPDATED");
        request.setDescription("SIMPLE TASK ONE DESCRIPTION UPDATED");
        request.setProgress(ProgressType.FINISHED.getCode());
        request.setIdEmployee(2L);
        request.setStatus(StatusType.ACTIVE.getCode());

        this.mockMvc.perform(put("/task/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().json("{\"type\": \"API_ERROR\",\"message\": \"The task searched doesn't exists.\"}"))
                .andExpect(status().isNotFound());

    }

}
