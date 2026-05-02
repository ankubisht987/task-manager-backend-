package com.task.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.backend.model.Task;
import com.task.backend.repository.TaskRepository;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // ✅ Create Task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // ✅ Get All Tasks
    @GetMapping
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    // ✅ Get Tasks by Project
    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    // ✅ Update Task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task updatedTask) {

        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setAssignedTo(updatedTask.getAssignedTo());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }
}