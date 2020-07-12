package com.demo.avla.repository;

import com.demo.avla.config.DataSourceConfig;
import com.demo.avla.entity.Task;
import com.demo.avla.type.ProgressType;
import com.demo.avla.type.StatusType;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts = { "classpath:sql/structure.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Import(DataSourceConfig.class)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private static final String DEFAULT_USER = "ADMIN";

    @Test
    public void shouldSaveTask() {
        this.taskRepository.save(buildTask());
    }

    @Test
    public void shouldUpdateTask() {
        this.taskRepository.save(buildTask());

        Task task = this.taskRepository.findById(1L).get();
        task.setName("TASK TWO");
        task.setDescription("NEW TASK DESCRIPTION");
        task.setUserUpdate(DEFAULT_USER);
        task.setDateUpdate(new Date());

        this.taskRepository.save(task);
        Task updatedTask = this.taskRepository.findById(1L).get();

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(updatedTask.getId()).isEqualTo(task.getId());
            assertions.assertThat(updatedTask.getName()).isEqualTo("TASK TWO");
            assertions.assertThat(updatedTask.getDescription()).isEqualTo("NEW TASK DESCRIPTION");
            assertions.assertThat(updatedTask.getUserUpdate()).isNotNull();
            assertions.assertThat(updatedTask.getDateUpdate()).isNotNull();
        });
    }

    @Test
    public void shouldFindTask() {
        this.taskRepository.save(buildTask());

        Task task = this.taskRepository.findById(1L).get();

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(task.getId()).isEqualTo(1);
            assertions.assertThat(task.getName()).isNotNull();
            assertions.assertThat(task.getName()).isEqualTo("TASK");
            assertions.assertThat(task.getDescription()).isNotNull();
            assertions.assertThat(task.getDescription()).isEqualTo("SIMPLE TASK DESCRIPTION");
            assertions.assertThat(task.getStatus()).isNotNull();
            assertions.assertThat(task.getStatus()).isEqualTo(StatusType.ACTIVE.getCode());
            assertions.assertThat(task.getProgress()).isNotNull();
            assertions.assertThat(task.getProgress()).isEqualTo(ProgressType.PENDING.getCode());
        });
    }

    @Test
    public void shouldFindAllTask() {
        this.taskRepository.save(buildTask());
        this.taskRepository.save(buildTask());

        List<Task> tasks = (List<Task>) this.taskRepository.findAll();

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(tasks).isNotNull();
            assertions.assertThat(tasks).isNotEmpty();
            assertions.assertThat(tasks.size()).isEqualTo(2);
            assertions.assertThat(tasks.get(0).getId()).isEqualTo(1);
            assertions.assertThat(tasks.get(1).getId()).isEqualTo(2);
        });

    }

    @Test
    public void shouldDeleteTask() {
        this.taskRepository.save(buildTask());

        Task task = this.taskRepository.findById(1L).get();
        task.setStatus(StatusType.INACTIVE.getCode());
        task.setUserUpdate(DEFAULT_USER);
        task.setDateUpdate(new Date());

        this.taskRepository.save(task);
        Task updatedTask = this.taskRepository.findById(1L).get();

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(updatedTask.getId()).isEqualTo(task.getId());
            assertions.assertThat(updatedTask.getName()).isEqualTo("TASK");
            assertions.assertThat(updatedTask.getDescription()).isEqualTo("SIMPLE TASK DESCRIPTION");
            assertions.assertThat(updatedTask.getStatus()).isEqualTo(StatusType.INACTIVE.getCode());
            assertions.assertThat(updatedTask.getUserUpdate()).isNotNull();
            assertions.assertThat(updatedTask.getDateUpdate()).isNotNull();
        });
    }

    @Test
    public void shouldAssignEmployee() {
        this.taskRepository.save(buildTask());

        Task task = this.taskRepository.findById(1L).get();
        task.setIdEmployee(1L);
        task.setUserUpdate(DEFAULT_USER);
        task.setDateUpdate(new Date());

        this.taskRepository.save(task);
        Task updatedTask = this.taskRepository.findById(1L).get();

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(updatedTask.getId()).isEqualTo(task.getId());
            assertions.assertThat(updatedTask.getIdEmployee()).isNotNull();
            assertions.assertThat(updatedTask.getIdEmployee()).isEqualTo(1L);
            assertions.assertThat(updatedTask.getUserUpdate()).isNotNull();
            assertions.assertThat(updatedTask.getDateUpdate()).isNotNull();
        });
    }

    @Test
    public void shouldCompleteTaskBatch() {
        this.taskRepository.save(buildTask());
        this.taskRepository.save(buildTask());

        Task taskOne = this.taskRepository.findById(1L).get();
        taskOne.setProgress(ProgressType.FINISHED.getCode());

        Task taskTwo = this.taskRepository.findById(2L).get();
        taskTwo.setProgress(ProgressType.FINISHED.getCode());

        List<Task> listTasks = Arrays.asList(taskOne, taskTwo);

        this.taskRepository.saveAll(listTasks);

        Task updatedTaskOne = this.taskRepository.findById(1L).get();
        Task updatedTaskTwo = this.taskRepository.findById(2L).get();

        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(updatedTaskOne).isNotNull();
            assertions.assertThat(updatedTaskOne.getProgress()).isEqualTo(ProgressType.FINISHED.getCode());
            assertions.assertThat(updatedTaskTwo).isNotNull();
            assertions.assertThat(updatedTaskTwo.getProgress()).isEqualTo(ProgressType.FINISHED.getCode());
        });

    }

    private Task buildTask() {
        Task task = new Task();
        task.setName("TASK");
        task.setDescription("SIMPLE TASK DESCRIPTION");
        task.setStatus(StatusType.ACTIVE.getCode());
        task.setProgress(ProgressType.PENDING.getCode());
        task.setDateRegistration(new Date());
        task.setUserRegistration(DEFAULT_USER);
        return task;
    }

}
