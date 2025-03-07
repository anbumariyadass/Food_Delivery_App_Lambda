package org.example.controller;

import java.util.List;

import org.example.dto.ApiResponse;
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
		return ResponseEntity.ok("hello from restaurant");
	}

	 @GetMapping("/allRestaurants")
	 public ResponseEntity<ApiResponse> getAllRestaurants() {
		 return ResponseEntity.ok(new ApiResponse("All restaurants with its dishes", restaurantService.getAllRestaurants()));
	 }
	 
	 @PostMapping("/addRestaurant")
     public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.saveRestaurant(restaurant);
     }
}
