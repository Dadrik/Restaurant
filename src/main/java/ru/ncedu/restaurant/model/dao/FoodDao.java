package ru.ncedu.restaurant.model.dao;

import ru.ncedu.restaurant.model.entity.Food;

import java.util.List;

public interface FoodDao extends GenericDao<Food, Long> {
    List<Food> findByNameOrCategory(String substring) throws PersistException;
}
