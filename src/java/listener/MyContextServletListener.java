/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import constant.AppConstant;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import utilities.Crawler;

/**
 * Web application lifecycle listener.
 *
 * @author PhuongNT
 */
@WebListener()
public class MyContextServletListener implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    private String realPath = "";
    private String azAudioStoredFilePath = "";
    private String h2GamingStoredFilePath = "";
    
    @Override 
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Deploying...");
        realPath = sce.getServletContext().getRealPath("/");
        azAudioStoredFilePath = realPath + AppConstant.azAudioFilePath;
        h2GamingStoredFilePath = realPath + AppConstant.h2GamingFilePath;
        
        scheduler = Executors.newSingleThreadScheduledExecutor();
        final ServletContext context = sce.getServletContext();
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                Crawler crawler = new Crawler(context);
                Map<String, String> categories = crawler.getCategoriesForAzAudio(azAudioStoredFilePath, AppConstant.urlAzAudio);
                crawler = null;
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    final Map.Entry<String, String> currentEntry = entry;
                    Runnable crawlingThread = new Runnable() {
                        @Override
                        public void run() {
                            Crawler categoryCrawler = new Crawler(context);
                            categoryCrawler.crawlHtmlFromCategoryAzaudio(currentEntry.getKey(), currentEntry.getValue());
                        }
                    };
                    crawlingThread.run();
                }
            }
        };
        
        scheduler.scheduleAtFixedRate(thread, 0, 7, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroy");
        scheduler.shutdownNow();
    }
}
