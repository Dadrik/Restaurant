package ru.ncedu.restaurant.model.dao;

import ru.ncedu.restaurant.model.entity.Food;

import java.util.List;

public interface FoodDao extends GenericDao<Food, Long> {
    public List<Food> findByName(String name) throws PersistException;
    public List<Food> findByCategory(String categoryName);
}
