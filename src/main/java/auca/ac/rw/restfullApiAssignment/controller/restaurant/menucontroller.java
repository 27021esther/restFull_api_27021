package auca.ac.rw.restfullApiAssignment.controller.restaurant;

import auca.ac.rw.restfullApiAssignment.modal.restaurant.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 6L;

    // Constructor - Initialize with 5 sample menu items
    public MenuController() {
        menuItems.add(new MenuItem(1L, "Margherita Pizza", "Classic pizza with tomato, mozzarella, and basil", 
                                   12.99, "Main Course", true, "margherita.jpg"));
        menuItems.add(new MenuItem(2L, "Caesar Salad", "Fresh romaine lettuce with Caesar dressing and croutons", 
                                   8.99, "Appetizer", true, "caesar.jpg"));
        menuItems.add(new MenuItem(3L, "Grilled Salmon", "Atlantic salmon with lemon butter sauce", 
                                   18.99, "Main Course", true, "salmon.jpg"));
        menuItems.add(new MenuItem(4L, "Chocolate Lava Cake", "Warm chocolate cake with molten center", 
                                   7.99, "Dessert", true, "lava_cake.jpg"));
        menuItems.add(new MenuItem(5L, "Fresh Lemonade", "Homemade lemonade with fresh lemons", 
                                   3.99, "Beverage", true, "lemonade.jpg"));
    }

    /**
     * GET /api/menu
     * Return all menu items
     * Status: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItems);
    }

    /**
     * GET /api/menu/{id}
     * Return a specific menu item by ID
     * Status: 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        Optional<MenuItem> menuItem = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (menuItem.isPresent()) {
            return ResponseEntity.ok(menuItem.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/menu/category/{category}
     * Get menu items by category
     * Status: 200 OK
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItem> categoryItems = menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryItems);
    }

    /**
     * GET /api/menu/search?name={name}
     * Search menu items by name
     * Status: 200 OK
     */
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(@RequestParam String name) {
        List<MenuItem> foundItems = menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(foundItems);
    }

    /**
     * GET /api/menu/available
     * Get only available menu items
     * Status: 200 OK
     */
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems() {
        List<MenuItem> availableItems = menuItems.stream()
                .filter(MenuItem::getAvailable)
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableItems);
    }

    /**
     * GET /api/menu/price-range?min={min}&max={max}
     * Get menu items within price range
     * Status: 200 OK
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<MenuItem>> getMenuItemsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<MenuItem> priceRangeItems = menuItems.stream()
                .filter(item -> item.getPrice() >= min && item.getPrice() <= max)
                .collect(Collectors.toList());

        return ResponseEntity.ok(priceRangeItems);
    }

    /**
     * POST /api/menu
     * Add a new menu item
     * Status: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        menuItem.setId(nextId++);
        if (menuItem.getAvailable() == null) {
            menuItem.setAvailable(true);
        }
        menuItems.add(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
    }

    /**
     * PUT /api/menu/{id}
     * Update an existing menu item
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedItem) {
        Optional<MenuItem> existingItem = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (existingItem.isPresent()) {
            MenuItem item = existingItem.get();
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            item.setPrice(updatedItem.getPrice());
            item.setCategory(updatedItem.getCategory());
            item.setAvailable(updatedItem.getAvailable());
            item.setImageUrl(updatedItem.getImageUrl());
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PATCH /api/menu/{id}/availability
     * Toggle menu item availability
     * Status: 200 OK if updated, 404 NOT FOUND if not found
     */
    @PatchMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        Optional<MenuItem> existingItem = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (existingItem.isPresent()) {
            MenuItem item = existingItem.get();
            item.setAvailable(available);
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/menu/{id}
     * Delete a menu item by ID
     * Status: 204 NO CONTENT if deleted, 404 NOT FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));

        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/menu/count
     * Get total count of menu items
     * Status: 200 OK
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getMenuItemCount() {
        return ResponseEntity.ok((long) menuItems.size());
    }

    /**
     * GET /api/menu/categories
     * Get list of all categories
     * Status: 200 OK
     */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = menuItems.stream()
                .map(MenuItem::getCategory)
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity.ok(categories);
    }
}