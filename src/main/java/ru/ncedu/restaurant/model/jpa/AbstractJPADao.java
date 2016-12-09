package ru.ncedu.restaurant.model.jpa;

import ru.ncedu.restaurant.model.dao.GenericDao;
import ru.ncedu.restaurant.model.dao.Identified;
import ru.ncedu.restaurant.model.dao.PersistException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractJPADao<T extends Identified<PK>, PK extends Long> implements GenericDao<T, PK> {
    private final Class<T> entityClass;

    AbstractJPADao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    JPAUtil jpaUtil;

    public void setJpaUtil(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    protected abstract String getSelectAllQuery();

    @Override
    public T create() throws PersistException {
        try {
            T t = entityClass.newInstance();
            add(t);
            return t;
        } catch (PersistException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void add(T object) throws PersistException {
        EntityManager entityManager = jpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T getById(PK key) throws PersistException {
        EntityManager entityManager = jpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(entityClass, key);
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(T object) throws PersistException {
        EntityManager entityManager = jpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(T object) throws PersistException {
        EntityManager entityManager = jpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            T persistentObject = entityManager.find(entityClass, object.getId());
            entityManager.remove(persistentObject);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<T> getAll() throws PersistException {
        EntityManager entityManager = jpaUtil.getEntityManagerFactory().createEntityManager();
        List<T> result;
        try {
            TypedQuery<T> query = entityManager.createQuery(getSelectAllQuery(), entityClass);
            result = query.getResultList();
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
            entityManager.close();
        }
        return result;
    }
}
