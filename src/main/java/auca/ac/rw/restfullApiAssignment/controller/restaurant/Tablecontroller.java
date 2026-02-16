package auca.ac.rw.restfullApiAssignment.controller.restaurant;

import auca.ac.rw.restfullApiAssignment.modal.restaurant.Table;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private List<Table> tables = new ArrayList<>();
    private Long nextId = 6L;

    // Constructor - Initialize with 5 sample tables
    public TableController() {
        tables.add(new Table(1L, "T-01", 2, "INDOOR", true));
        tables.add(new Table(2L, "T-02", 4, "INDOOR", true));
        tables.add(new Table(3L, "T-03", 6, "INDOOR", false));
        tables.add(new Table(4L, "P-01", 4, "PATIO", true));
        tables.add(new Table(5L, "PR-01", 8, "PRIVATE_ROOM", true));
    }

    /**
     * GET /api/tables
     * Return all tables
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tables);
    }

    /**
     * GET /api/tables/{id}
     * Return a specific table by ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable Long id) {
        Optional<Table> table = tables.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();

        if (table.isPresent()) {
            return ResponseEntity.ok(table.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/tables/available
     * Get all available tables
     * Status: 200 OK
     */
    @GetMapping("/available")
    public ResponseEntity<List<Table>> getAvailableTables() {
        List<Table> availableTables = tables.stream()
                .filter(Table::getAvailable)
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableTables);
    }

    /**
     * GET /api/tables/location/{location}
     * Get tables by location
     * Status: 200 OK
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Table>> getTablesByLocation(@PathVariable String location) {
        List<Table> locationTables = tables.stream()
                .filter(t -> t.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());

        return ResponseEntity.ok(locationTables);
    }

    /**
     * GET /api/tables/capacity/{capacity}
     * Get tables by minimum capacity
     * Status: 200 OK
     */
    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<Table>> getTablesByCapacity(@PathVariable Integer capacity) {
        List<Table> capacityTables = tables.stream()
                .filter(t -> t.getCapacity() >= capacity)
                .filter(Table::getAvailable)
                .collect(Collectors.toList());

        return ResponseEntity.ok(capacityTables);
    }

    /**
     * GET /api/tables/search?number={number}
     * Search table by number
     * Status: 200 OK
     */
    @GetMapping("/search")
    public ResponseEntity<Table> searchTableByNumber(@RequestParam String number) {
        Optional<Table> table = tables.stream()
                .filter(t -> t.getTableNumber().equalsIgnoreCase(number))
                .findFirst();

        if (table.isPresent()) {
            return ResponseEntity.ok(table.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * POST /api/tables
     * Add a new table
     * Status: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Table> addTable(@RequestBody Table table) {
        table.setId(nextId++);
        if (table.getAvailable() == null) {
            table.setAvailable(true);
        }
        tables.add(table);
        return ResponseEntity.status(HttpStatus.CREATED).body(table);
    }

    /**
     * PUT /api/tables/{id}
     * Update an existing table
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Table> updateTable(@PathVariable Long id, @RequestBody Table updatedTable) {
        Optional<Table> existingTable = tables.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();

        if (existingTable.isPresent()) {
            Table table = existingTable.get();
            table.setTableNumber(updatedTable.getTableNumber());
            table.setCapacity(updatedTable.getCapacity());
            table.setLocation(updatedTable.getLocation());
            table.setAvailable(updatedTable.getAvailable());
            return ResponseEntity.ok(table);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/tables/{id}/availability
     * Toggle table availability
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Table> toggleAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        Optional<Table> existingTable = tables.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();

        if (existingTable.isPresent()) {
            Table table = existingTable.get();
            table.setAvailable(available);
            return ResponseEntity.ok(table);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/tables/{id}
     * Delete a table by ID
     * Status: 204 NO CONTENT if deleted, 404 NOT FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        boolean removed = tables.removeIf(t -> t.getId().equals(id));

        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/tables/count
     * Get total count of tables
     * Status: 200 OK
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTableCount() {
        return ResponseEntity.ok((long) tables.size());
    }

    /**
     * GET /api/tables/count/available
     * Get count of available tables
     * Status: 200 OK
     */
    @GetMapping("/count/available")
    public ResponseEntity<Long> getAvailableTableCount() {
        long count = tables.stream()
                .filter(Table::getAvailable)
                .count();

        return ResponseEntity.ok(count);
    }

    /**
     * GET /api/tables/capacity/total
     * Get total seating capacity
     * Status: 200 OK
     */
    @GetMapping("/capacity/total")
    public ResponseEntity<Integer> getTotalCapacity() {
        int total = tables.stream()
                .mapToInt(Table::getCapacity)
                .sum();

        return ResponseEntity.ok(total);
    }
}