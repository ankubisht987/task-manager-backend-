package com.task.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.backend.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByProjectId(String projectId);
}