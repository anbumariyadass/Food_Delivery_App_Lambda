package org.example.controller;

import java.util.List;

import org.example.dto.ApiResponse;
import org.example.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.entity.Restaurant;
import org.example.service.RestaurantService;

@RestController
@CrossOrigin(origins = "*")  // Allows requests from any origin
@RequestMapping("/restaurant")
public class RestaurantController {

	 @Autowired
	private RestaurantService restaurantService;

	@GetMapping("/ping")
	public ResponseEntity<String> ping(){
		return ResponseEntity.ok("hello from poc");
	}

	@GetMapping("/allRestaurants")
	public ResponseEntity<ApiResponse> getAllRestaurants() {
		List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();
		return ResponseEntity.ok(new ApiResponse("All restaurants with dishes", allRestaurants));
	}

	@PostMapping("/addRestaurant")
	public ResponseEntity<ApiResponse> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant addedRestaurant = restaurantService.saveRestaurant(restaurant);
		return ResponseEntity.ok(new ApiResponse("Restaurant added successfully", addedRestaurant));
	}

	@PostMapping("/{restaurantId}/dishes")
	public ResponseEntity<ApiResponse> addDishToRestaurant(@PathVariable Long restaurantId, @RequestBody Dish dish) {
		Dish addedDish = restaurantService.addDishToRestaurant(restaurantId, dish);
		return ResponseEntity.ok(new ApiResponse("Dish added successfully", addedDish));
	}

	@PutMapping("/{restaurantId}")
	public ResponseEntity<ApiResponse> updateRestaurant(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		Restaurant updatedRestaurant =restaurantService.updateRestaurant(restaurantId, restaurant);
		return ResponseEntity.ok(new ApiResponse("Restaurant updated successfully", updatedRestaurant));
	}

	// Retrieve all dishes for a specific restaurant
	@GetMapping("/{restaurantId}/dishes")
	public ResponseEntity<ApiResponse> getDishesByRestaurant(@PathVariable Long restaurantId) {
		List<Dish> dishes = restaurantService.getDishesByRestaurant(restaurantId);
		return ResponseEntity.ok(new ApiResponse("Dishes retrieved successfully", dishes));
	}

	// Update dishes for a specific restaurant
	@PutMapping("/{restaurantId}/dishes")
	public ResponseEntity<ApiResponse> updateDishesForRestaurant(@PathVariable Long restaurantId, @RequestBody List<Dish> dishes) {
		List<Dish> updatedDishes =  restaurantService.updateDishesForRestaurant(restaurantId, dishes);
		return ResponseEntity.ok(new ApiResponse("Dishes updated successfully", updatedDishes));
	}

	// Delete a restaurant by ID (also deletes all associated dishes)
	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<ApiResponse> deleteRestaurantById(@PathVariable Long restaurantId) {
		restaurantService.deleteRestaurantById(restaurantId);
		String response = "Restaurant with ID " + restaurantId + " deleted successfully!";
		return ResponseEntity.ok(new ApiResponse(response, null));

	}

	// Delete a dish by ID (keeps the restaurant intact)
	@DeleteMapping("/dishes/{dishId}")
	public ResponseEntity<ApiResponse> deleteDishById(@PathVariable Long dishId) {
		restaurantService.deleteDishById(dishId);
		String response = "Dish with ID " + dishId + " deleted successfully!";
		return ResponseEntity.ok(new ApiResponse(response, null));
	}

	// Delete all dishes for a specific restaurant
	@DeleteMapping("/{restaurantId}/dishes")
	public ResponseEntity<ApiResponse> deleteAllDishesForRestaurant(@PathVariable Long restaurantId) {
		//String
		restaurantService.deleteAllDishesForRestaurant(restaurantId);
		String response = "All dishes for Restaurant ID " + restaurantId + " have been deleted successfully!";
		return ResponseEntity.ok(new ApiResponse(response, null));
	}
}
