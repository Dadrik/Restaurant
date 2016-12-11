package ru.ncedu.restaurant.model.jpa;

import ru.ncedu.restaurant.model.dao.FoodDao;
import ru.ncedu.restaurant.model.dao.PersistException;
import ru.ncedu.restaurant.model.entity.Food;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery; // Hibernate dependency!
import java.util.List;

public class FoodDaoJpa extends AbstractJPADao<Food, Long> implements FoodDao {
    public FoodDaoJpa() {
        super(Food.class);
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT f FROM Food f";
    }

    @Override
    public List<Food> findByNameOrCategory(String substring) throws PersistException {
        EntityManager entityManager = jpaUtil.getEntityManagerFactory().createEntityManager();
        List<Food> result;
        try {
            TypedQuery<Food> typedQuery = entityManager.createQuery(
                    "SELECT food FROM Food food " +
                            "WHERE LOWER(food.name) LIKE LOWER(:name_template) " +
                            "OR LOWER(category.name) LIKE LOWER(:name_template)",
                    Food.class
            ).setParameter("name_template", "%" + substring + "%");
            result = typedQuery.getResultList();
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            entityManager.close();
        }
        return result;
    }
}
