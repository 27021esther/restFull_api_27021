package auca.ac.rw.restfullApiAssignment.modal.restaurant;

public class Table {
    private Long id;
    private String tableNumber;
    private Integer capacity;
    private String location; // INDOOR, OUTDOOR, PATIO, PRIVATE_ROOM
    private Boolean available;

    // Default constructor
    public Table() {
        this.available = true;
    }

    // Parameterized constructor
    public Table(Long id, String tableNumber, Integer capacity, String location, Boolean available) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.location = location;
        this.available = available;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", tableNumber='" + tableNumber + '\'' +
                ", capacity=" + capacity +
                ", location='" + location + '\'' +
                ", available=" + available +
                '}';
    }
}