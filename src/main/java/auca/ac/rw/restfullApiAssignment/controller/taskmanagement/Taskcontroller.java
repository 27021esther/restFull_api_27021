package com.restapi.task.controller;

import com.restapi.task.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Task Management API
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // In-memory storage for tasks
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with sample data
    public TaskController() {
        tasks.add(new Task(nextId++, "Complete Spring Boot Assignment", 
                "Finish all 5 questions for the REST API assignment", 
                false, "HIGH", "2024-02-20"));
        
        tasks.add(new Task(nextId++, "Review Java Collections", 
                "Study ArrayList, HashMap, and LinkedList", 
                true, "MEDIUM", "2024-02-15"));
        
        tasks.add(new Task(nextId++, "Prepare for Database Exam", 
                "Review SQL queries and normalization", 
                false, "HIGH", "2024-02-25"));
        
        tasks.add(new Task(nextId++, "Read Spring Documentation", 
                "Go through Spring Boot official documentation", 
                false, "LOW", "2024-03-01"));
        
        tasks.add(new Task(nextId++, "Practice REST API Testing", 
                "Learn Postman advanced features", 
                true, "MEDIUM", "2024-02-18"));
        
        tasks.add(new Task(nextId++, "Update Resume", 
                "Add recent projects and skills", 
                false, "LOW", "2024-03-05"));
    }

    /**
     * GET /api/tasks - Get all tasks
     * @return List of all tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/{taskId} - Get task by ID
     * @param taskId The ID of the task
     * @return Task object or 404 if not found
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElse(null);
        
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/tasks/status?completed={true/false} - Get tasks by completion status
     * @param completed Boolean value for completion status
     * @return List of filtered tasks
     */
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam boolean completed) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.isCompleted() == completed)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(filteredTasks);
    }

    /**
     * GET /api/tasks/priority/{priority} - Get tasks by priority
     * @param priority Priority level (LOW, MEDIUM, HIGH)
     * @return List of tasks with specified priority
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
        
        if (filteredTasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.ok(filteredTasks);
    }

    /**
     * POST /api/tasks - Create new task
     * @param task Task object from request body
     * @return Created task with 201 status
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setTaskId(nextId++);
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    /**
     * PUT /api/tasks/{taskId} - Update task
     * @param taskId ID of task to update
     * @param updatedTask Updated task data
     * @return Updated task or 404 if not found
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, 
                                          @RequestBody Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTaskId().equals(taskId)) {
                updatedTask.setTaskId(taskId);
                tasks.set(i, updatedTask);
                return ResponseEntity.ok(updatedTask);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * PATCH /api/tasks/{taskId}/complete - Mark task as completed
     * @param taskId ID of task to mark as complete
     * @return Updated task or 404 if not found
     */
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsComplete(@PathVariable Long taskId) {
        Task task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElse(null);
        
        if (task != null) {
            task.setCompleted(true);
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/tasks/{taskId} - Delete task
     * @param taskId ID of task to delete
     * @return 204 No Content if successful, 404 if not found
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(task -> task.getTaskId().equals(taskId));
        
        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
```

## 3. Testing the API

### Sample Requests in Postman:

#### 1. GET All Tasks
```
GET http://localhost:8080/api/tasks