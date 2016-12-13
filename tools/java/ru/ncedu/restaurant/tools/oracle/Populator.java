package ru.ncedu.restaurant.tools.oracle;

import ru.ncedu.restaurant.model.entity.Category;
import ru.ncedu.restaurant.model.entity.Food;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Populator {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("restaurant");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Populator.populate(em);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void populate(EntityManager em) {
        Category soups = new Category("Soups");
        em.persist(soups);
        Category burgers = new Category("Burgers");
        em.persist(burgers);
        Category salads = new Category("Salads");
        em.persist(salads);
        Category appetizers = new Category("Appetizers");
        em.persist(appetizers);
        Category entrees = new Category("Entrees");
        em.persist(entrees);
        Category garnish = new Category("Garnish");
        em.persist(garnish);
        Category desserts = new Category("Desserts");
        em.persist(desserts);
        Category cold = new Category("Cold drinks");
        em.persist(cold);
        Category hot = new Category("Hot drinks");
        em.persist(hot);

        // Soups
        Food food = new Food("Chicken Noodle Soup", 289, soups);
        em.persist(food);
        food = new Food("French Onion Soup", 275, soups);
        em.persist(food);
        // Burgers
        food = new Food("Cheddar BBQ Onion Ring Burger", 549, burgers);
        em.persist(food);
        food = new Food("Starlite Cheeseburger", 475, burgers);
        em.persist(food);
        // Salads
        food = new Food("Greek Salad with Grilled Chicken Breast", 548, salads);
        em.persist(food);
        food = new Food("Asian Ahi Tuna Salad", 665, salads);
        em.persist(food);
        food = new Food("Bocconcini Mozzarella & Plum Tomato Salad", 499, salads);
        em.persist(food);
        // Entrees
        food = new Food("Grilled Rib Eye Steak", 1599, entrees);
        em.persist(food);
        // Appetizers
        food = new Food("Sesame Crusted Seared Fresh Ahi Tuna", 745, appetizers);
        em.persist(food);
        food = new Food("Buffalo Chicken Wings (Double)", 879, appetizers);
        em.persist(food);
        food = new Food("Buffalo Chicken Wings (Regular)", 489, appetizers);
        em.persist(food);
        food = new Food("Mozzarella Sticks", 345, appetizers);
        em.persist(food);
        food = new Food("Chili & Cheese Premium Crinkle Cut Fries", 249, appetizers);
        em.persist(food);
        food = new Food("Mini - Cheeseburgers", 429, appetizers);
        em.persist(food);
        // Dessert
        food = new Food("New York Style Tall Cheesecake", 379, desserts);
        em.persist(food);
        food = new Food("Mile high 4 layer Carrot Cake", 359, desserts);
        em.persist(food);
        // Special
        food = new Food("Mini Nachitos", 229);
        em.persist(food);
        food = new Food("Buffalo Shrimp", 499);
        em.persist(food);
    }
}
