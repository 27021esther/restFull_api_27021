package auca.ac.rw.restfullApiAssignment.controller.studentRegistration;

import auca.ac.rw.restfullApiAssignment.modal.studentRegistration.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();
    private Long nextId = 4L;

    // Constructor - Initialize with 3 sample students
    public StudentController() {
        students.add(new Student(1L, "S2024001", "John", "Doe", "john.doe@auca.ac.rw", 
                                "+250788123456", "Computer Science", 3, "ACTIVE"));
        students.add(new Student(2L, "S2024002", "Jane", "Smith", "jane.smith@auca.ac.rw", 
                                "+250788234567", "Software Engineering", 2, "ACTIVE"));
        students.add(new Student(3L, "S2024003", "Bob", "Johnson", "bob.johnson@auca.ac.rw", 
                                "+250788345678", "Information Technology", 1, "ACTIVE"));
    }

    /**
     * GET /api/students
     * Return all students
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    /**
     * GET /api/students/{id}
     * Return a specific student by ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/students/studentId/{studentId}
     * Get student by student ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/studentId/{studentId}")
    public ResponseEntity<Student> getStudentByStudentId(@PathVariable String studentId) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getStudentId().equalsIgnoreCase(studentId))
                .findFirst();

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/students/program/{program}
     * Get students by program
     * Status: 200 OK
     */
    @GetMapping("/program/{program}")
    public ResponseEntity<List<Student>> getStudentsByProgram(@PathVariable String program) {
        List<Student> programStudents = students.stream()
                .filter(s -> s.getProgram().equalsIgnoreCase(program))
                .collect(Collectors.toList());

        return ResponseEntity.ok(programStudents);
    }

    /**
     * GET /api/students/year/{year}
     * Get students by year
     * Status: 200 OK
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Student>> getStudentsByYear(@PathVariable Integer year) {
        List<Student> yearStudents = students.stream()
                .filter(s -> s.getYear().equals(year))
                .collect(Collectors.toList());

        return ResponseEntity.ok(yearStudents);
    }

    /**
     * GET /api/students/status/{status}
     * Get students by status
     * Status: 200 OK
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Student>> getStudentsByStatus(@PathVariable String status) {
        List<Student> statusStudents = students.stream()
                .filter(s -> s.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        return ResponseEntity.ok(statusStudents);
    }

    /**
     * GET /api/students/search?name={name}
     * Search students by first or last name
     * Status: 200 OK
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String name) {
        List<Student> foundStudents = students.stream()
                .filter(s -> s.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                           s.getLastName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(foundStudents);
    }

    /**
     * POST /api/students
     * Add a new student
     * Status: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        student.setId(nextId++);
        if (student.getStatus() == null || student.getStatus().isEmpty()) {
            student.setStatus("ACTIVE");
        }
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    /**
     * PUT /api/students/{id}
     * Update an existing student
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Optional<Student> existingStudent = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();

        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setStudentId(updatedStudent.getStudentId());
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setPhoneNumber(updatedStudent.getPhoneNumber());
            student.setProgram(updatedStudent.getProgram());
            student.setYear(updatedStudent.getYear());
            student.setStatus(updatedStudent.getStatus());
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/students/{id}/status
     * Update student status
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Student> updateStudentStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<Student> existingStudent = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();

        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setStatus(status.toUpperCase());
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/students/{id}
     * Delete a student by ID
     * Status: 204 NO CONTENT if deleted, 404 NOT FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean removed = students.removeIf(s -> s.getId().equals(id));

        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/students/count
     * Get total count of students
     * Status: 200 OK
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getStudentCount() {
        return ResponseEntity.ok((long) students.size());
    }

    /**
     * GET /api/students/count/program/{program}
     * Get count of students by program
     * Status: 200 OK
     */
    @GetMapping("/count/program/{program}")
    public ResponseEntity<Long> getStudentCountByProgram(@PathVariable String program) {
        long count = students.stream()
                .filter(s -> s.getProgram().equalsIgnoreCase(program))
                .count();

        return ResponseEntity.ok(count);
    }
}