package auca.ac.rw.restfullApiAssignment.controller.restaurant;

import auca.ac.rw.restfullApiAssignment.modal.restaurant.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private Long nextId = 4L;

    // Constructor - Initialize with 3 sample reservations
    public ReservationController() {
        reservations.add(new Reservation(1L, "John Smith", "+250788123456", "john@example.com",
                4, LocalDateTime.of(2024, 2, 20, 19, 0), "CONFIRMED", "Window seat preferred"));
        reservations.add(new Reservation(2L, "Emma Johnson", "+250788234567", "emma@example.com",
                2, LocalDateTime.of(2024, 2, 21, 18, 30), "PENDING", "Anniversary dinner"));
        reservations.add(new Reservation(3L, "Michael Brown", "+250788345678", "michael@example.com",
                6, LocalDateTime.of(2024, 2, 22, 20, 0), "CONFIRMED", "Birthday celebration"));
    }

    /**
     * GET /api/reservations
     * Return all reservations
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservations);
    }

    /**
     * GET /api/reservations/{id}
     * Return a specific reservation by ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/reservations/customer?phone={phone}
     * Get reservations by customer phone
     * Status: 200 OK
     */
    @GetMapping("/customer")
    public ResponseEntity<List<Reservation>> getReservationsByPhone(@RequestParam String phone) {
        List<Reservation> customerReservations = reservations.stream()
                .filter(r -> r.getCustomerPhone().equals(phone))
                .collect(Collectors.toList());

        return ResponseEntity.ok(customerReservations);
    }

    /**
     * GET /api/reservations/status/{status}
     * Get reservations by status
     * Status: 200 OK
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Reservation>> getReservationsByStatus(@PathVariable String status) {
        List<Reservation> statusReservations = reservations.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        return ResponseEntity.ok(statusReservations);
    }

    /**
     * GET /api/reservations/date/{date}
     * Get reservations by date (format: YYYY-MM-DD)
     * Status: 200 OK
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationsByDate(@PathVariable String date) {
        LocalDate searchDate = LocalDate.parse(date);
        List<Reservation> dateReservations = reservations.stream()
                .filter(r -> r.getReservationDateTime().toLocalDate().equals(searchDate))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dateReservations);
    }

    /**
     * GET /api/reservations/today
     * Get today's reservations
     * Status: 200 OK
     */
    @GetMapping("/today")
    public ResponseEntity<List<Reservation>> getTodayReservations() {
        LocalDate today = LocalDate.now();
        List<Reservation> todayReservations = reservations.stream()
                .filter(r -> r.getReservationDateTime().toLocalDate().equals(today))
                .collect(Collectors.toList());

        return ResponseEntity.ok(todayReservations);
    }

    /**
     * GET /api/reservations/upcoming
     * Get upcoming reservations (future dates)
     * Status: 200 OK
     */
    @GetMapping("/upcoming")
    public ResponseEntity<List<Reservation>> getUpcomingReservations() {
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> upcomingReservations = reservations.stream()
                .filter(r -> r.getReservationDateTime().isAfter(now))
                .filter(r -> !r.getStatus().equals("CANCELLED"))
                .sorted((r1, r2) -> r1.getReservationDateTime().compareTo(r2.getReservationDateTime()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(upcomingReservations);
    }

    /**
     * POST /api/reservations
     * Create a new reservation
     * Status: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        reservation.setId(nextId++);
        if (reservation.getStatus() == null || reservation.getStatus().isEmpty()) {
            reservation.setStatus("PENDING");
        }
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    /**
     * PUT /api/reservations/{id}
     * Update an existing reservation
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, 
                                                         @RequestBody Reservation updatedReservation) {
        Optional<Reservation> existingReservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            reservation.setCustomerName(updatedReservation.getCustomerName());
            reservation.setCustomerPhone(updatedReservation.getCustomerPhone());
            reservation.setCustomerEmail(updatedReservation.getCustomerEmail());
            reservation.setNumberOfGuests(updatedReservation.getNumberOfGuests());
            reservation.setReservationDateTime(updatedReservation.getReservationDateTime());
            reservation.setSpecialRequests(updatedReservation.getSpecialRequests());
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/reservations/{id}/status
     * Update reservation status
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Optional<Reservation> existingReservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            reservation.setStatus(status.toUpperCase());
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/reservations/{id}
     * Cancel/Delete a reservation
     * Status: 204 NO CONTENT if deleted, 404 NOT FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(r -> r.getId().equals(id));

        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/reservations/count
     * Get total count of reservations
     * Status: 200 OK
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getReservationCount() {
        return ResponseEntity.ok((long) reservations.size());
    }

    /**
     * GET /api/reservations/count/status/{status}
     * Get count of reservations by status
     * Status: 200 OK
     */
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getReservationCountByStatus(@PathVariable String status) {
        long count = reservations.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase(status))
                .count();

        return ResponseEntity.ok(count);
    }

    /**
     * GET /api/reservations/guests/total
     * Get total number of guests for all reservations
     * Status: 200 OK
     */
    @GetMapping("/guests/total")
    public ResponseEntity<Integer> getTotalGuests() {
        int total = reservations.stream()
                .filter(r -> !r.getStatus().equals("CANCELLED"))
                .mapToInt(Reservation::getNumberOfGuests)
                .sum();

        return ResponseEntity.ok(total);
    }
}