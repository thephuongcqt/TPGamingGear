/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblCategory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import utilities.DBUtilities;

/**
 *
 * @author PhuongNT
 */
public class CategoryDao extends BaseDao<TblCategory, String>{        

    private CategoryDao() {
    }
    
    private static CategoryDao instance;
    private final static Object LOCK = new Object();
    public static CategoryDao getInstance(){
        if(instance == null){
            synchronized (LOCK) {
                instance = new CategoryDao();
            }
        }
        return instance;
    }
    
    public synchronized TblCategory getFirstCategoryByName(String categoryName){
        EntityManager em = DBUtilities.getEntityManager();
        try{                        
            List<TblCategory> result = em.createNamedQuery("TblCategory.findByCategoryName", TblCategory.class)
                    .setParameter("categoryName", categoryName)
                    .getResultList();
            
            if(result != null && !result.isEmpty()){                
                return result.get(0);
            }
        } catch (Exception ex){
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(em != null){
                em.close();
            }
        }
        return null;  
    };
}
