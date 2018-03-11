/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblCategory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utilities.DBUtilities;

/**
 *
 * @author PhuongNT
 */
public class CategoryDao {
    public static long addCategory(TblCategory category){
        EntityManager em = DBUtilities.getEntityManager();
        try{
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(category);
            transaction.commit();
            em.flush();
            
        } catch (Exception e){
            System.out.println(e);
        } finally{
            if(em != null){
                em.close();
            }
        }
        return 1;
    }
    
    public static boolean checkExistedCategoryName(String categoryName){
        EntityManager em = DBUtilities.getEntityManager();
        try{            
            String sql = "Select * from Tbl_Category";
            Query query = em.createQuery(sql);
//            query.setParameter("paramName", categoryName);
            List<TblCategory> result = (List<TblCategory>) query.getResultList();
            if(result != null){
                System.out.println(" ok : " + result.size());
                return result.size() > 0 ? true : false;
            }
        } catch (Exception e){
            System.out.println(e);
        } finally{
            if(em != null){
                em.close();
            }
        }
      return true;  
    };
}
