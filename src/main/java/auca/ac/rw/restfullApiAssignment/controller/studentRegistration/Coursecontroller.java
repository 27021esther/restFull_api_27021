package auca.ac.rw.restfullApiAssignment.controller.studentRegistration;

import auca.ac.rw.restfullApiAssignment.modal.studentRegistration.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private List<Course> courses = new ArrayList<>();
    private Long nextId = 5L;

    // Constructor - Initialize with 4 sample courses
    public CourseController() {
        courses.add(new Course(1L, "CS101", "Introduction to Programming", 
                              "Basic programming concepts using Java", 3, "Dr. Smith", 
                              "Fall 2024", 40, 25));
        courses.add(new Course(2L, "CS201", "Data Structures and Algorithms", 
                              "Advanced data structures and algorithmic thinking", 4, "Prof. Johnson", 
                              "Fall 2024", 35, 30));
        courses.add(new Course(3L, "CS301", "Database Management Systems", 
                              "Relational databases and SQL", 3, "Dr. Williams", 
                              "Spring 2024", 30, 20));
        courses.add(new Course(4L, "CS401", "Web Development", 
                              "Modern web development with React and Spring Boot", 4, "Prof. Brown", 
                              "Spring 2024", 25, 25));
    }

    /**
     * GET /api/courses
     * Return all courses
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courses);
    }

    /**
     * GET /api/courses/{id}
     * Return a specific course by ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/courses/code/{code}
     * Get course by course code
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<Course> getCourseByCourseCode(@PathVariable String code) {
        Optional<Course> course = courses.stream()
                .filter(c -> c.getCourseCode().equalsIgnoreCase(code))
                .findFirst();

        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/courses/semester/{semester}
     * Get courses by semester
     * Status: 200 OK
     */
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<Course>> getCoursesBySemester(@PathVariable String semester) {
        List<Course> semesterCourses = courses.stream()
                .filter(c -> c.getSemester().equalsIgnoreCase(semester))
                .collect(Collectors.toList());

        return ResponseEntity.ok(semesterCourses);
    }

    /**
     * GET /api/courses/instructor/{instructor}
     * Get courses by instructor
     * Status: 200 OK
     */
    @GetMapping("/instructor/{instructor}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable String instructor) {
        List<Course> instructorCourses = courses.stream()
                .filter(c -> c.getInstructor().toLowerCase().contains(instructor.toLowerCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(instructorCourses);
    }

    /**
     * GET /api/courses/credits/{credits}
     * Get courses by credits
     * Status: 200 OK
     */
    @GetMapping("/credits/{credits}")
    public ResponseEntity<List<Course>> getCoursesByCredits(@PathVariable Integer credits) {
        List<Course> creditCourses = courses.stream()
                .filter(c -> c.getCredits().equals(credits))
                .collect(Collectors.toList());

        return ResponseEntity.ok(creditCourses);
    }

    /**
     * GET /api/courses/available
     * Get courses with available seats
     * Status: 200 OK
     */
    @GetMapping("/available")
    public ResponseEntity<List<Course>> getAvailableCourses() {
        List<Course> availableCourses = courses.stream()
                .filter(c -> c.getEnrolled() < c.getCapacity())
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableCourses);
    }

    /**
     * GET /api/courses/search?name={name}
     * Search courses by name
     * Status: 200 OK
     */
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String name) {
        List<Course> foundCourses = courses.stream()
                .filter(c -> c.getCourseName().toLowerCase().contains(name.toLowerCase()) ||
                           c.getCourseCode().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(foundCourses);
    }

    /**
     * POST /api/courses
     * Add a new course
     * Status: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        course.setId(nextId++);
        if (course.getEnrolled() == null) {
            course.setEnrolled(0);
        }
        courses.add(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    /**
     * PUT /api/courses/{id}
     * Update an existing course
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        Optional<Course> existingCourse = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setCourseCode(updatedCourse.getCourseCode());
            course.setCourseName(updatedCourse.getCourseName());
            course.setDescription(updatedCourse.getDescription());
            course.setCredits(updatedCourse.getCredits());
            course.setInstructor(updatedCourse.getInstructor());
            course.setSemester(updatedCourse.getSemester());
            course.setCapacity(updatedCourse.getCapacity());
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/courses/{id}/capacity
     * Update course capacity
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/capacity")
    public ResponseEntity<Course> updateCourseCapacity(@PathVariable Long id, @RequestParam Integer capacity) {
        Optional<Course> existingCourse = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setCapacity(capacity);
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/courses/{id}
     * Delete a course by ID
     * Status: 204 NO CONTENT if deleted, 404 NOT FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean removed = courses.removeIf(c -> c.getId().equals(id));

        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/courses/count
     * Get total count of courses
     * Status: 200 OK
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getCourseCount() {
        return ResponseEntity.ok((long) courses.size());
    }

    /**
     * GET /api/courses/count/semester/{semester}
     * Get count of courses by semester
     * Status: 200 OK
     */
    @GetMapping("/count/semester/{semester}")
    public ResponseEntity<Long> getCourseCountBySemester(@PathVariable String semester) {
        long count = courses.stream()
                .filter(c -> c.getSemester().equalsIgnoreCase(semester))
                .count();

        return ResponseEntity.ok(count);
    }

    /**
     * GET /api/courses/{id}/enrollment-status
     * Check if course has available seats
     * Status: 200 OK
     */
    @GetMapping("/{id}/enrollment-status")
    public ResponseEntity<String> getEnrollmentStatus(@PathVariable Long id) {
        Optional<Course> course = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (course.isPresent()) {
            Course c = course.get();
            int available = c.getCapacity() - c.getEnrolled();
            String message = available > 0 ? 
                "Available seats: " + available : 
                "Course is full";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}