package com.taskmanager.teamtaskmanager.repository;

import com.taskmanager.teamtaskmanager.entity.Task;
import com.taskmanager.teamtaskmanager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndIsDeletedFalse(Long id);
    List<Task> findByIsDeletedFalse();
    List<Task> findByProjectIdAndIsDeletedFalse(Long projectId);
    List<Task> findByAssignedToIdAndIsDeletedFalse(Long userId);

    List<Task> findByStatusAndIsDeletedFalse(TaskStatus status);
    List<Task> findByDueDateBeforeAndStatusNotAndIsDeletedFalse(LocalDate date, TaskStatus status);
}
