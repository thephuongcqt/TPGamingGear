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
import utilities.DBUtilities;

/**
 *
 * @author PhuongNT
 */
public class ProductDao extends BaseDao<TblProduct, Long>{
    
    private ProductDao() {
    }
    
    private static ProductDao instance;
    private static Object lock = new Object();
    public static ProductDao getInstance(){
        if(instance == null){
            synchronized (lock) {
                instance = new ProductDao();
            }
        }
        return instance;
    }
    
    public TblProduct getProductBy(String productName, String categoryId) {
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
    
    public void saveProductWhenCrawling(TblProduct product){
        TblProduct existedProduct = getProductBy(product.getProductName(), product.getCategoryID());
        TblProduct result;
        if(existedProduct == null){
            //create new one
            result = create(product);
//            System.out.println(product == null ? "Add fail" : "Add id: " + result.getProductID());
        } else{
            //update data
            result = update(product);
//            System.out.println(product == null ? "Update fail" : "Update id: " + result.getProductID());
        }
    } 
    
    public Products getTrendingProducts(int quantity){
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<TblProduct> result = em.createNamedQuery("TblProduct.findTrendingProducts", TblProduct.class)
                    .setMaxResults(quantity)
                    .getResultList();

            if (result != null && !result.isEmpty()) {
                Products listProducts = new Products();
                listProducts.getProductType().addAll(result);
//                List<TblProduct> products = listProducts.getProductType();
//                if(products == null){
//                    products = new ArrayList<TblProduct>();
//                }
//                products.addAll(result);
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
}
