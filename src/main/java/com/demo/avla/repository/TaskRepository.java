package com.demo.avla.repository;

import com.demo.avla.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("SELECT t from Task t WHERE (:name is null or t.name like %:name%) and (:idEmployee is null or t.idEmployee = :idEmployee)")
    List<Task> findTasksByNameOrEmployee(@Param("name") String name, @Param("idEmployee") Long idEmployee);

}
