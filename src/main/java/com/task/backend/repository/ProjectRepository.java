package com.task.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.backend.model.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {
}