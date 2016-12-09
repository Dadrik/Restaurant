package ru.ncedu.restaurant;

import ru.ncedu.restaurant.model.dao.CategoryDao;
import ru.ncedu.restaurant.model.dao.FoodDao;
import ru.ncedu.restaurant.model.dao.PersistException;
import ru.ncedu.restaurant.model.entity.Category;
import ru.ncedu.restaurant.model.entity.Food;
import ru.ncedu.restaurant.model.jpa.CategoryDaoJpa;
import ru.ncedu.restaurant.model.jpa.FoodDaoJpa;
import ru.ncedu.restaurant.model.jpa.JPAUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        JPAUtil jpaUtil = new JPAUtil();
        CategoryDaoJpa categoryDao = new CategoryDaoJpa();
        categoryDao.setJpaUtil(jpaUtil);
        FoodDaoJpa foodDao = new FoodDaoJpa();
        foodDao.setJpaUtil(jpaUtil);
        try {
            Category category = categoryDao.create();
            category.setName("Main dishes");
            categoryDao.update(category);
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }

        Food food1 = new Food("Milk", 30);
        Food food2 = new Food("Burger", 100);
        try {
            foodDao.add(food1);
            foodDao.add(food2);
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }
        food2.setName("Cheeseburger");
        try {
            foodDao.update(food2);
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }

        try {
            List<Food> all = foodDao.getAll();
            for (Food food : all) {
                System.out.println(food.toString());
            }
        } catch (PersistException e) {
            System.err.println(e.getMessage());
        }

    }
}
