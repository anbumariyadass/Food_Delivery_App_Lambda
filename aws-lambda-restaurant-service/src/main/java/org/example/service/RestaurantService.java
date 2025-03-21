package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.example.entity.Dish;
import org.example.entity.Restaurant;
import org.example.exception.handler.DishNotFoundException;
import org.example.exception.handler.RestaurantNotFoundException;
import org.example.repository.DishRepository;
import org.example.repository.RestaurantRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private DishRepository dishRepository;
    
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
    
    @Transactional
    public Dish addDishToRestaurant(Long restaurantId, Dish dish) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            dish.setRestaurant(restaurant);
            return dishRepository.save(dish);
        } else {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    }
    
    
    
    @Transactional
    public Restaurant updateRestaurant(Long restaurantId, Restaurant updatedRestaurant) {
        Optional<Restaurant> existingRestaurantOpt = restaurantRepository.findById(restaurantId);
        
        if (existingRestaurantOpt.isPresent()) {
            Restaurant existingRestaurant = existingRestaurantOpt.get();

            // Update basic fields
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setAddress(updatedRestaurant.getAddress());
            existingRestaurant.setPhone(updatedRestaurant.getPhone());
            existingRestaurant.setCuisine(updatedRestaurant.getCuisine());
            existingRestaurant.setRating(updatedRestaurant.getRating());
            existingRestaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
            existingRestaurant.setDeliveryOptions(updatedRestaurant.getDeliveryOptions());
            existingRestaurant.setAveragePricePerPerson(updatedRestaurant.getAveragePricePerPerson());

            // Update dishes
            if (updatedRestaurant.getDishes() != null) {
                List<Dish> newDishes = updatedRestaurant.getDishes();

                // Remove dishes that are not in the updated list
                existingRestaurant.getDishes().removeIf(existingDish -> 
                    newDishes.stream().noneMatch(newDish -> newDish.getId() != null &&
                            newDish.getId().equals(existingDish.getId()))
                );

                // Add or update dishes
                for (Dish newDish : newDishes) {
                    if (newDish.getId() == null) {
                        // New dish: Add to the restaurant
                        newDish.setRestaurant(existingRestaurant);
                        existingRestaurant.getDishes().add(newDish);
                    } else {
                        // Existing dish: Update details
                        Optional<Dish> existingDishOpt = dishRepository.findById(newDish.getId());
                        existingDishOpt.ifPresent(existingDish -> {
                            existingDish.setName(newDish.getName());
                            existingDish.setDescription(newDish.getDescription());
                            existingDish.setPrice(newDish.getPrice());
                        });
                    }
                }
            }

            return restaurantRepository.save(existingRestaurant);
        } else {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    }
    
    public List<Dish> getDishesByRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (restaurantOpt.isPresent()) {
            return restaurantOpt.get().getDishes();
        } else {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    }

    @Transactional
    public List<Dish> updateDishesForRestaurant(Long restaurantId, List<Dish> updatedDishes) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);

        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();

            // Remove old dishes that are not in the updated list
            restaurant.getDishes().removeIf(existingDish -> 
                updatedDishes.stream().noneMatch(newDish -> newDish.getId() != null &&
                        newDish.getId().equals(existingDish.getId()))
            );

            // Add or update dishes
            for (Dish newDish : updatedDishes) {
                if (newDish.getId() == null) {
                    // New dish: Add to the restaurant
                    newDish.setRestaurant(restaurant);
                    restaurant.getDishes().add(newDish);
                } else {
                    // Existing dish: Update details
                    Optional<Dish> existingDishOpt = dishRepository.findById(newDish.getId());
                    existingDishOpt.ifPresent(existingDish -> {
                        existingDish.setName(newDish.getName());
                        existingDish.setDescription(newDish.getDescription());
                        existingDish.setPrice(newDish.getPrice());
                    });
                }
            }

            restaurantRepository.save(restaurant);
            return restaurant.getDishes();
        } else {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    }
    
    @Transactional
    public void deleteRestaurantById(Long restaurantId) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (restaurantOpt.isPresent()) {
            restaurantRepository.deleteById(restaurantId);
        } else {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    }

    @Transactional
    public void deleteDishById(Long dishId) {
        Optional<Dish> dishOpt = dishRepository.findById(dishId);
        if (dishOpt.isPresent()) {
            dishRepository.deleteById(dishId);
        } else {
            throw new DishNotFoundException("Dish not found with ID: " + dishId);
        }
    }
    
    @Transactional
    public void deleteAllDishesForRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            
            // Remove all dishes from the restaurant
            restaurant.getDishes().clear();
            
            // Save the updated restaurant without dishes
            restaurantRepository.save(restaurant);

            // Delete all dishes from the database for this restaurant
            dishRepository.deleteByRestaurantId(restaurantId);
        } else {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    }

    public Optional<Restaurant> getUserByUserName(String userName) {
        return restaurantRepository.findByUserName(userName);
    }
    
}

