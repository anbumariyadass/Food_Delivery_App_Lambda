package org.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.entity.Dish;

import jakarta.transaction.Transactional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
	@Transactional
    void deleteByRestaurantId(Long restaurantId);
}

