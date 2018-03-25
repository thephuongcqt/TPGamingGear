/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PhuongNT
 */
public class DBUtilities {

    private DBUtilities() {
    }

    private static EntityManagerFactory emf;
    private static final Object LOCK = new Object();

    public static EntityManager getEntityManager() {
        synchronized (LOCK) {
            if (emf == null) {
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
