/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PhuongNT
 */
public class DBUtilities implements Serializable {

    private DBUtilities() {
    }

    private static EntityManagerFactory emf;
    private static Object lock = new Object();

    public static EntityManager getEntityManager() {
        if (emf == null) {
            synchronized (lock) {
                try {
                    emf = Persistence.createEntityManagerFactory("TPGamingGearPU");
                } catch (Exception e) {
                    Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        return emf.createEntityManager();
    }
}
