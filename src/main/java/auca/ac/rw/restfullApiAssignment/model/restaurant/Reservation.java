package auca.ac.rw.restfullApiAssignment.modal.restaurant;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Integer numberOfGuests;
    private LocalDateTime reservationDateTime;
    private String status; // PENDING, CONFIRMED, SEATED, COMPLETED, CANCELLED
    private String specialRequests;

    // Default constructor
    public Reservation() {
        this.status = "PENDING";
    }

    // Parameterized constructor
    public Reservation(Long id, String customerName, String customerPhone, String customerEmail,
                      Integer numberOfGuests, LocalDateTime reservationDateTime, 
                      String status, String specialRequests) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.numberOfGuests = numberOfGuests;
        this.reservationDateTime = reservationDateTime;
        this.status = status;
        this.specialRequests = specialRequests;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", numberOfGuests=" + numberOfGuests +
                ", reservationDateTime=" + reservationDateTime +
                ", status='" + status + '\'' +
                ", specialRequests='" + specialRequests + '\'' +
                '}';
    }
}