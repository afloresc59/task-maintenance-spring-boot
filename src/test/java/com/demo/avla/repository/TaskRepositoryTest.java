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

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts = { "classpath:sql/structure.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Import(DataSourceConfig.class)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private static final String DEFAULT_EMPLOYEE = "ADMIN";

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
        task.setUserUpdate(DEFAULT_EMPLOYEE);
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

    private Task buildTask() {
        Task task = new Task();
        task.setName("TASK");
        task.setDescription("SIMPLE TASK DESCRIPTION");
        task.setStatus(StatusType.ACTIVE.getCode());
        task.setIdEmployee(10L);
        task.setProgress(ProgressType.PENDING.getCode());
        task.setDateRegistration(new Date());
        task.setUserRegistration(DEFAULT_EMPLOYEE);
        return task;
    }

}
