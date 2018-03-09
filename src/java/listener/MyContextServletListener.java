/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import constant.AppConstant;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import utilities.AzaudioCrawler;
import utilities.H2Crawler;
import utilities.MybossCrawler;
import utilities.Utils;

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
        Runnable azThread = new Runnable() {
            @Override
            public void run() {
                AzaudioCrawler crawler = new AzaudioCrawler(context);
                Map<String, String> categories = crawler.getCategoriesForAzAudio(azAudioStoredFilePath, AppConstant.urlAzAudio);
                crawler = null;
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    final Map.Entry<String, String> currentEntry = entry;
                    Runnable crawlingThread = new Runnable() {
                        @Override
                        public void run() {
                            AzaudioCrawler categoryCrawler = new AzaudioCrawler(context);
                            categoryCrawler.crawlHtmlFromCategoryAzaudio(currentEntry.getKey(), currentEntry.getValue());
                        }
                    };
                    crawlingThread.run();
                }
            }
        };
        
        Runnable h2Thread = new Runnable() {
            @Override
            public void run() {
                H2Crawler crawler = new H2Crawler(context);
                Map<String, String> categories = crawler.getCategoriesForAzAudio(AppConstant.urlH2Gaming);
                                    categories = new HashMap<String, String>();
//                categories.put("http://sg.h2gaming.vn/CHUỘT-GAMING", "CHUỘT GAMING");
//                categories.put("http://sg.h2gaming.vn/BÀN-PHÍM-GAMING", "BÀN PHÍM GAMING");
//                categories.put("http://sg.h2gaming.vn/TAI-NGHE-GAMING", "TAI NGHE GAMING");
//                categories.put("http://sg.h2gaming.vn/MOUSEPAD-GAMING", "MOUSEPAD GAMING");
//                categories.put("http://sg.h2gaming.vn/GHẾ-GAMING", "GHẾ GAMING");
//                categories.put("http://sg.h2gaming.vn/PHỤ-KIỆN-GAMING", "PHỤ KIỆN GAMING");
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    final Map.Entry<String, String> currentEntry = entry;
                    Runnable crawlingThread = new Runnable() {
                        @Override
                        public void run() {
                            H2Crawler categoryCrawler = new H2Crawler(context);
                            categoryCrawler.crawlHtmlFromCategoryAzaudio(currentEntry.getKey(), currentEntry.getValue());
                        }
                    };
                    crawlingThread.run();
                }                
            }
        };
        
        Runnable mybossThread = new Runnable() {
            @Override
            public void run() {
                MybossCrawler crawler = new MybossCrawler(context);
                Map<String, String> categories = new HashMap<String, String>();
                categories.put("http://www.myboss.vn/ghe-choi-game-c15", "Ghế Chơi game");
//                categories.put("http://www.myboss.vn/tay-cam-choi-game-c14", "Tay cầm chơi game");
//                categories.put("http://www.myboss.vn/tai-nghe-choi-game-c13", "Tai nghe chơi game");
//                categories.put("http://www.myboss.vn/ban-di-chuot-choi-game-c11", "Bàn di chuột chơi game");
//                categories.put("http://www.myboss.vn/chuot-choi-game-c10", "Chuột chơi game");
//                categories.put("http://www.myboss.vn/phu-kien-gaming-c6", "Phụ kiện chơi game");
//                categories.put("http://www.myboss.vn/ban-phim-choi-game-c12", "Bàn phím chơi game");
//                categories.put("http://www.myboss.vn/loa-gaming-c44", "Loa gaming");
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    
                    crawler.crawlHtmlFromCategoryAzaudio(key, value);
                }
            }
        };
        
        mybossThread.run();
//        scheduler.scheduleAtFixedRate(azThread, 0, 7, TimeUnit.DAYS);
//        scheduler.scheduleAtFixedRate(h2Thread, 0, 7, TimeUnit.DAYS);

        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroy");
        scheduler.shutdownNow();
    }
}
