/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import utilities.AzaudioThread;
import utilities.Crawler;
import utilities.DBUtilities;
import utilities.MybossThread;

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
    
    public static void stopCrawling(){
        azThread.suspendThread();
        mybossThread.suspendThread();
        System.out.println("end stop");
    }
    
    public static void resumCrawling(){
        azThread.resumeThread();
        mybossThread.resumeThread();
        System.out.println("end resume");
    }
}
