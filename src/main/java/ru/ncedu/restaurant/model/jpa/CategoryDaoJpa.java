package ru.ncedu.restaurant.model.jpa;

import ru.ncedu.restaurant.model.dao.CategoryDao;
import ru.ncedu.restaurant.model.dao.PersistException;
import ru.ncedu.restaurant.model.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDaoJpa extends AbstractJPADao<Category, Long> implements CategoryDao {
    public CategoryDaoJpa() {
        super(Category.class);
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT c FROM Category c";
    }

    @Override
    public List<Category> findByName(String name) throws PersistException{
        EntityManager entityManager = jpaUtil.getEntityManagerFactory().createEntityManager();
        List<Category> result;
        try {
            TypedQuery<Category> typedQuery = entityManager.createQuery (
                    "SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(:name_template)",
                    Category.class
            ).setParameter("name_template", "%" + name + "%");
            result = typedQuery.getResultList();
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            entityManager.close();
        }
        return result;
    }
}
