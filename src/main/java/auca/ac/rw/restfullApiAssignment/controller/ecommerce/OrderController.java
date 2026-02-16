package auca.ac.rw.restfullApiAssignment.controller.ecommerce;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Order;
import auca.ac.rw.restfullApiAssignment.modal.ecommerce.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private List<Order> orders = new ArrayList<>();
    private Long nextId = 3L;

    // Constructor - Initialize with 2 sample orders
    public OrderController() {
        // Sample Order 1
        List<OrderItem> items1 = Arrays.asList(
                new OrderItem(1L, "Laptop", 1, 1299.99),
                new OrderItem(3L, "Headphones", 2, 349.99)
        );
        orders.add(new Order(1L, "John Doe", "john@example.com", items1, 1999.97, "CONFIRMED"));

        // Sample Order 2
        List<OrderItem> items2 = Arrays.asList(
                new OrderItem(2L, "Smartphone", 1, 899.99)
        );
        orders.add(new Order(2L, "Jane Smith", "jane@example.com", items2, 899.99, "PENDING"));
    }

    /**
     * GET /api/orders
     * Return all orders
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orders);
    }

    /**
     * GET /api/orders/{id}
     * Return a specific order by ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();

        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/orders/customer?email={email}
     * Get orders by customer email
     * Status: 200 OK
     */
    @GetMapping("/customer")
    public ResponseEntity<List<Order>> getOrdersByCustomerEmail(@RequestParam String email) {
        List<Order> customerOrders = orders.stream()
                .filter(o -> o.getCustomerEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());

        return ResponseEntity.ok(customerOrders);
    }

    /**
     * GET /api/orders/status/{status}
     * Get orders by status
     * Status: 200 OK
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> statusOrders = orders.stream()
                .filter(o -> o.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        return ResponseEntity.ok(statusOrders);
    }

    /**
     * POST /api/orders
     * Create a new order
     * Status: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        order.setId(nextId++);
        order.setOrderDate(LocalDateTime.now());
        
        // Calculate total amount from items
        double total = order.getItems().stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
        order.setTotalAmount(total);
        
        orders.add(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    /**
     * PUT /api/orders/{id}
     * Update an existing order
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Optional<Order> existingOrder = orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();

        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setCustomerName(updatedOrder.getCustomerName());
            order.setCustomerEmail(updatedOrder.getCustomerEmail());
            order.setItems(updatedOrder.getItems());
            
            // Recalculate total
            double total = updatedOrder.getItems().stream()
                    .mapToDouble(OrderItem::getSubtotal)
                    .sum();
            order.setTotalAmount(total);
            
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/orders/{id}/status
     * Update order status
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Optional<Order> existingOrder = orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();

        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setStatus(status.toUpperCase());
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/orders/{id}
     * Cancel/Delete an order
     * Status: 204 NO CONTENT if deleted, 404 NOT FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean removed = orders.removeIf(o -> o.getId().equals(id));

        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/orders/count
     * Get total count of orders
     * Status: 200 OK
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getOrderCount() {
        return ResponseEntity.ok((long) orders.size());
    }

    /**
     * GET /api/orders/total-revenue
     * Get total revenue from all orders
     * Status: 200 OK
     */
    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        double revenue = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
        return ResponseEntity.ok(revenue);
    }
}