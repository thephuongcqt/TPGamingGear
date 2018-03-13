/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utilities.DBUtilities;

/**
 *
 * @author PhuongNT
 */
public class BaseDao<T, PK extends Serializable> implements IGenericDao<T, PK> {

    protected Class<T> entityClass;

    public BaseDao() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass
                .getActualTypeArguments()[0];
    }

    @Override
    public T create(T t) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    @Override
    public T findByID(PK id) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            T result = em.find(entityClass, id);
            transaction.commit();
            return result;
        } catch (Exception e) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    @Override
    public T update(T t) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            T result = em.merge(t);
            transaction.commit();
            return result;
        } catch (Exception e) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    @Override
    public boolean delete(T t) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            t = em.merge(t);
            em.remove(t);
            transaction.commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return false;
    }

    @Override
    public List<T> getAll(String namedQuery) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<T> result = em.createNamedQuery(namedQuery, entityClass).getResultList();
            transaction.commit();
            return result;
        } catch (Exception e) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    @Override
    public long getNumberRecord(String namedQuery) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            long result = ((Number)em.createNamedQuery(namedQuery, entityClass).getSingleResult()).longValue();
            transaction.commit();
            return result;
        } catch (Exception e) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return 0;
    }
            
}
