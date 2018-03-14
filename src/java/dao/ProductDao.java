/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Products;
import entities.TblProduct;
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
public class ProductDao extends BaseDao<TblProduct, Long> {

    private ProductDao() {
    }

    private static ProductDao instance;
    private final static Object LOCK = new Object();

    public static ProductDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ProductDao();
            }
        }
        return instance;
    }

    public TblProduct getProductBy(String productName, String categoryId) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<TblProduct> result = em.createNamedQuery("TblProduct.findByNameAndCategoryId", TblProduct.class)
                    .setParameter("productName", productName)
                    .setParameter("categoryID", categoryId)
                    .getResultList();
            transaction.commit();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public void saveProductWhenCrawling(TblProduct product) {
        TblProduct existedProduct = getProductBy(product.getProductName(), product.getCategoryID());
        TblProduct result;
        if (existedProduct == null) {
            result = create(product);
            System.out.println(product == null ? "Add fail" : "Add id: " + result.getProductID());
        } else {
            result = update(product);
            System.out.println(product == null ? "Update fail" : "Update id: " + result.getProductID());
        }
    }

    public Products getTrendingProducts(int quantity) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<TblProduct> result = em.createNamedQuery("TblProduct.findTrendingProducts", TblProduct.class)
                    .setMaxResults(quantity)
                    .getResultList();
            transaction.commit();
            if (result != null && !result.isEmpty()) {
                Products listProducts = new Products();
                listProducts.getProductType().addAll(result);
                return listProducts;
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public Products getProductsInCategory(String categoryID, int offset, int limit) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<TblProduct> result = em.createNamedQuery("TblProduct.findByCategoryID", TblProduct.class)
                    .setParameter("categoryID", categoryID)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
            transaction.commit();

            if (result != null && !result.isEmpty()) {
                Products listProducts = new Products();
                listProducts.getProductType().addAll(result);
                return listProducts;
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public long countRecordsInCagegory(String categoryId) {

        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            long result = ((Number) em.createNamedQuery("TblProduct.countAllRecordsInCategory")
                       .setParameter("categoryID", categoryId)
                       .getSingleResult()).longValue();
            transaction.commit();
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return 0;
    }
}
