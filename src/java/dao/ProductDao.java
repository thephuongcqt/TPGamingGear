/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class ProductDao {

    public static long addProduct(TblProduct product) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(product);
            transaction.commit();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return product.getProductID();
    }

    public static TblProduct getProductBy(String productName, String categoryId) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<TblProduct> result = em.createNamedQuery("TblProduct.findByNameAndCategoryId", TblProduct.class)
                    .setParameter("productName", productName)
                    .setParameter("categoryID", categoryId)
                    .getResultList();

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
    
    public static boolean updateProduct(TblProduct product){
        EntityManager em = DBUtilities.getEntityManager();
        try {
            TblProduct result = em.find(TblProduct.class, product.getProductID());

            if(result != null){
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                
                result.setProductName(product.getProductName());
                result.setPrice(product.getPrice());
                result.setThumbnail(product.getProductName());
                result.setCategoryID(product.getCategoryID());
                result.setIsActive(product.getIsActive());
                
                em.merge(result);
                transaction.commit();
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return false;
    }
    
    public static void saveProductWhenCrawling(TblProduct product){
        TblProduct existedProduct = getProductBy(product.getProductName(), product.getCategoryID());
        if(existedProduct == null){
            //create new one
            long id = addProduct(product);
            System.out.println("Add id: " + id);
        } else{
            //update data
            boolean result = updateProduct(product);
            System.out.println("Update: " + result);
        }
    }          
}
