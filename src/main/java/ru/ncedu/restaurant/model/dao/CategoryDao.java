package ru.ncedu.restaurant.model.dao;

import ru.ncedu.restaurant.model.entity.Category;

import java.util.List;

public interface CategoryDao extends GenericDao<Category, Long> {
    public List<Category> findByName(String name) throws PersistException;
}
