/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.TblUser;
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
public class UserDao extends BaseDao<TblUser, String> {

    private UserDao() {
    }

    private static UserDao instance;
    private final static Object LOCK = new Object();

    public static UserDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new UserDao();
            }
        }
        return instance;
    }
    
    public TblUser login(String username, String password) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            
            List<TblUser> listUsers = em.createNamedQuery("TblUser.checkLogin", TblUser.class)
                    .setParameter("email", username)
                    .setParameter("password", password)
                    .setParameter("isActive", true)
                    .getResultList();            
            transaction.commit();
            if(listUsers.size() == 1){
                return listUsers.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
