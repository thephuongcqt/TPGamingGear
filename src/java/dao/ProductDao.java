/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblProduct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utilities.DBUtilities;

/**
 *
 * @author PhuongNT
 */
public class ProductDao {
    public static int addProduct(TblProduct product){
        EntityManager em = DBUtilities.getEntityManager();
        try{
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(product);
            transaction.commit();
            em.flush();
            
        } catch (Exception e){
            System.out.println(e);
        } finally{
            if(em != null){
                em.close();
            }
        }
        return product.getId();
    }
}
