package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.example.entity.Restaurant;
import org.example.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public Restaurant saveRestaurant(Restaurant restaurant) {
        // Ensure dishes are correctly associated with the restaurant
        if (restaurant.getDishes() != null) {
            restaurant.getDishes().forEach(dish -> dish.setRestaurant(restaurant));
        }
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}

