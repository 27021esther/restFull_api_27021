package auca.ac.rw.restfullApiAssignment.modal.studentRegistration;

public class Course {
    private Long id;
    private String courseCode;
    private String courseName;
    private String description;
    private Integer credits;
    private String instructor;
    private String semester;
    private Integer capacity;
    private Integer enrolled;

    // Default constructor
    public Course() {
        this.enrolled = 0;
    }

    // Parameterized constructor
    public Course(Long id, String courseCode, String courseName, String description,
                  Integer credits, String instructor, String semester, Integer capacity, Integer enrolled) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.instructor = instructor;
        this.semester = semester;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                ", instructor='" + instructor + '\'' +
                ", semester='" + semester + '\'' +
                ", capacity=" + capacity +
                ", enrolled=" + enrolled +
                '}';
    }
}