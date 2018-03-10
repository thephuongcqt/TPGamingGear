/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.Serializable;
import static java.rmi.server.LogStream.log;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PhuongNT
 */
public class DBUtilities implements Serializable {

    private static EntityManagerFactory emf;
    
    public static EntityManager getEntityManager() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("TPGamingGearPU");
            } catch (Exception e) {
                log(DBUtilities.class.getName() + " Exception: " + e.getMessage());
            }
        }
        
        return emf.createEntityManager();
    }
}
