/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import crawler.AzaudioThread;
import utilities.DBUtilities;
import crawler.MybossThread;

/**
 * Web application lifecycle listener.
 *
 * @author PhuongNT
 */
@WebListener()
public class MyContextServletListener implements ServletContextListener {

    private static String realPath = "";
    private static AzaudioThread azThread;
    private static MybossThread mybossThread;
    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {        
        realPath = sce.getServletContext().getRealPath("/");

        final ServletContext context = sce.getServletContext();
        azThread = new AzaudioThread(context);
        mybossThread = new MybossThread(context);

        azThread.start();
        mybossThread.start();            
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {        
        EntityManager em = DBUtilities.getEntityManager();
        if(em != null){
            em.close();
        }
    }

    public static String getRealPath() {        
        return realPath;
    }
}
