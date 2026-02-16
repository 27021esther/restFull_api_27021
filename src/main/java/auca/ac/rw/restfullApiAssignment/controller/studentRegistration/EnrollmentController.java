package auca.ac.rw.restfullApiAssignment.controller.studentRegistration;

import auca.ac.rw.restfullApiAssignment.modal.studentRegistration.Enrollment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private List<Enrollment> enrollments = new ArrayList<>();
    private Long nextId = 4L;

    // Constructor - Initialize with 3 sample enrollments
    public EnrollmentController() {
        enrollments.add(new Enrollment(1L, 1L, "John Doe", 1L, "CS101", 
                                       "Introduction to Programming", LocalDate.now(), 
                                       "ENROLLED", null));
        enrollments.add(new Enrollment(2L, 1L, "John Doe", 2L, "CS201", 
                                       "Data Structures and Algorithms", LocalDate.now(), 
                                       "ENROLLED", null));
        enrollments.add(new Enrollment(3L, 2L, "Jane Smith", 1L, "CS101", 
                                       "Introduction to Programming", LocalDate.now(), 
                                       "ENROLLED", null));
    }

    /**
     * GET /api/enrollments
     * Return all enrollments
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollments);
    }

    /**
     * GET /api/enrollments/{id}
     * Return a specific enrollment by ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        Optional<Enrollment> enrollment = enrollments.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (enrollment.isPresent()) {
            return ResponseEntity.ok(enrollment.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/enrollments/student/{studentId}
     * Get all enrollments for a specific student
     * Status: 200 OK
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        List<Enrollment> studentEnrollments = enrollments.stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentEnrollments);
    }

    /**
     * GET /api/enrollments/course/{courseId}
     * Get all enrollments for a specific course
     * Status: 200 OK
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        List<Enrollment> courseEnrollments = enrollments.stream()
                .filter(e -> e.getCourseId().equals(courseId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(courseEnrollments);
    }

    /**
     * GET /api/enrollments/status/{status}
     * Get enrollments by status
     * Status: 200 OK
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStatus(@PathVariable String status) {
        List<Enrollment> statusEnrollments = enrollments.stream()
                .filter(e -> e.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        return ResponseEntity.ok(statusEnrollments);
    }

    /**
     * GET /api/enrollments/student/{studentId}/course/{courseId}
     * Check if student is enrolled in specific course
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Enrollment> getEnrollmentByStudentAndCourse(
            @PathVariable Long studentId, 
            @PathVariable Long courseId) {
        Optional<Enrollment> enrollment = enrollments.stream()
                .filter(e -> e.getStudentId().equals(studentId) && e.getCourseId().equals(courseId))
                .findFirst();

        if (enrollment.isPresent()) {
            return ResponseEntity.ok(enrollment.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * POST /api/enrollments
     * Create a new enrollment
     * Status: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        enrollment.setId(nextId++);
        enrollment.setEnrollmentDate(LocalDate.now());
        if (enrollment.getStatus() == null || enrollment.getStatus().isEmpty()) {
            enrollment.setStatus("ENROLLED");
        }
        enrollments.add(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
    }

    /**
     * PUT /api/enrollments/{id}
     * Update an existing enrollment
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, 
                                                       @RequestBody Enrollment updatedEnrollment) {
        Optional<Enrollment> existingEnrollment = enrollments.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (existingEnrollment.isPresent()) {
            Enrollment enrollment = existingEnrollment.get();
            enrollment.setStatus(updatedEnrollment.getStatus());
            enrollment.setGrade(updatedEnrollment.getGrade());
            return ResponseEntity.ok(enrollment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/enrollments/{id}/status
     * Update enrollment status
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(@PathVariable Long id, 
                                                             @RequestParam String status) {
        Optional<Enrollment> existingEnrollment = enrollments.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (existingEnrollment.isPresent()) {
            Enrollment enrollment = existingEnrollment.get();
            enrollment.setStatus(status.toUpperCase());
            return ResponseEntity.ok(enrollment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/enrollments/{id}/grade
     * Update enrollment grade
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/grade")
    public ResponseEntity<Enrollment> updateEnrollmentGrade(@PathVariable Long id, 
                                                            @RequestParam Double grade) {
        Optional<Enrollment> existingEnrollment = enrollments.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();

        if (existingEnrollment.isPresent()) {
            Enrollment enrollment = existingEnrollment.get();
            enrollment.setGrade(grade);
            if (grade >= 50.0) {
                enrollment.setStatus("COMPLETED");
            } else {
                enrollment.setStatus("FAILED");
            }
            return ResponseEntity.ok(enrollment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/enrollments/{id}
     * Delete an enrollment (drop course)
     * Status: 204 NO CONTENT if deleted, 404 NOT FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        boolean removed = enrollments.removeIf(e -> e.getId().equals(id));

        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/enrollments/count
     * Get total count of enrollments
     * Status: 200 OK
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getEnrollmentCount() {
        return ResponseEntity.ok((long) enrollments.size());
    }

    /**
     * GET /api/enrollments/count/student/{studentId}
     * Get enrollment count for a specific student
     * Status: 200 OK
     */
    @GetMapping("/count/student/{studentId}")
    public ResponseEntity<Long> getEnrollmentCountByStudent(@PathVariable Long studentId) {
        long count = enrollments.stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .filter(e -> e.getStatus().equals("ENROLLED"))
                .count();

        return ResponseEntity.ok(count);
    }

    /**
     * GET /api/enrollments/count/course/{courseId}
     * Get enrollment count for a specific course
     * Status: 200 OK
     */
    @GetMapping("/count/course/{courseId}")
    public ResponseEntity<Long> getEnrollmentCountByCourse(@PathVariable Long courseId) {
        long count = enrollments.stream()
                .filter(e -> e.getCourseId().equals(courseId))
                .filter(e -> e.getStatus().equals("ENROLLED"))
                .count();

        return ResponseEntity.ok(count);
    }

    /**
     * GET /api/enrollments/student/{studentId}/gpa
     * Calculate GPA for a student
     * Status: 200 OK
     */
    @GetMapping("/student/{studentId}/gpa")
    public ResponseEntity<Double> calculateStudentGPA(@PathVariable Long studentId) {
        List<Enrollment> completedCourses = enrollments.stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .filter(e -> e.getStatus().equals("COMPLETED"))
                .filter(e -> e.getGrade() != null)
                .collect(Collectors.toList());

        if (completedCourses.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }

        double average = completedCourses.stream()
                .mapToDouble(Enrollment::getGrade)
                .average()
                .orElse(0.0);

        return ResponseEntity.ok(Math.round(average * 100.0) / 100.0);
    }
}