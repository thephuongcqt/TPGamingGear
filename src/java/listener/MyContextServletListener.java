/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import utilities.AzaudioThread;
import utilities.DBUtilities;
import utilities.MybossThread;

/**
 * Web application lifecycle listener.
 *
 * @author PhuongNT
 */
@WebListener()
public class MyContextServletListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;
    private static String realPath = "";
    private static Thread azThread;
    private static Thread mybossThread;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Deploying...");
        realPath = sce.getServletContext().getRealPath("/");

        scheduler = Executors.newSingleThreadScheduledExecutor();
        final ServletContext context = sce.getServletContext();
        azThread = new AzaudioThread(context);
        mybossThread = new MybossThread(context);

//        scheduler.scheduleAtFixedRate(mybossThread, 0, 7, TimeUnit.DAYS); 
//        scheduler.scheduleAtFixedRate(azThread, 0, 7, TimeUnit.DAYS);

        azThread.start();
        mybossThread.start();
        System.out.println("----------------End contextInitialized----------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroy");
        EntityManager em = DBUtilities.getEntityManager();
        if(em != null){
            em.close();
        }
        scheduler.shutdownNow();
    }

    public static String getRealPath() {        
        return realPath;
    }
    
    public static void pendingCrawl(){
        if(azThread != null && mybossThread != null){
            if(azThread.isAlive()){
                azThread.interrupt();
            }
            if(mybossThread.isAlive()){
                mybossThread.interrupt();
            }
            System.out.println("====pending===========");
            System.out.println("Az is alive: " + azThread.isAlive());
            System.out.println("Myboss is alive: " + mybossThread.isAlive());
        }
        
    }

    //        Runnable h2Thread = new Runnable() {
//            @Override
//            public void run() {
//                H2Crawler crawler = new H2Crawler(context);
//                Map<String, String> categories = crawler.getCategoriesForAzAudio(AppConstant.urlH2Gaming);
//                                    categories = new HashMap<String, String>();
//                categories.put("http://sg.h2gaming.vn/CHUỘT-GAMING", "CHUỘT GAMING");
//                categories.put("http://sg.h2gaming.vn/BÀN-PHÍM-GAMING", "BÀN PHÍM GAMING");
//                categories.put("http://sg.h2gaming.vn/TAI-NGHE-GAMING", "TAI NGHE GAMING");
//                categories.put("http://sg.h2gaming.vn/MOUSEPAD-GAMING", "MOUSEPAD GAMING");
//                categories.put("http://sg.h2gaming.vn/GHẾ-GAMING", "GHẾ GAMING");
//                categories.put("http://sg.h2gaming.vn/PHỤ-KIỆN-GAMING", "PHỤ KIỆN GAMING");
//                for (Map.Entry<String, String> entry : categories.entrySet()) {
//                    final Map.Entry<String, String> currentEntry = entry;
//                    Runnable crawlingThread = new Runnable() {
//                        @Override
//                        public void run() {
//                            H2Crawler categoryCrawler = new H2Crawler(context);
//                            categoryCrawler.crawlHtmlFromCategoryAzaudio(currentEntry.getKey(), currentEntry.getValue());
//                        }
//                    };
//                    crawlingThread.run();
//                }                
//            }
//        };
    //        Runnable azThread = new Runnable() {
//            @Override
//            public void run() {
//                AzaudioCrawler crawler = new AzaudioCrawler(context);
//                Map<String, String> categories = crawler.getCategoriesForAzAudio(AppConstant.urlAzAudio);
//                crawler = null;
//                for (Map.Entry<String, String> entry : categories.entrySet()) {
//                    final Map.Entry<String, String> currentEntry = entry;
//                    Runnable crawlingThread = new Runnable() {
//                        @Override
//                        public void run() {
//                            AzaudioCrawler categoryCrawler = new AzaudioCrawler(context);
//                            categoryCrawler.crawlHtmlFromCategoryAzaudio(currentEntry.getKey(), currentEntry.getValue());
//                        }
//                    };
//                    crawlingThread.run();
//                }
//            }
//        };
//
//        Runnable mybossThread = new Runnable() {
//            @Override
//            public void run() {
//                MybossCrawler crawler = new MybossCrawler(context);
//                Map<String, String> categories = new HashMap<String, String>();
//                categories.put("http://www.myboss.vn/chuot-choi-game-c10", Name.CHUỘT_CHƠI_GAME.value());
//                categories.put("http://www.myboss.vn/ban-phim-choi-game-c12", Name.BÀN_PHÍM_CHƠI_GAME.value());
//                categories.put("http://www.myboss.vn/ghe-choi-game-c15", Name.GHẾ_CHƠI_GAME.value());
//                categories.put("http://www.myboss.vn/tay-cam-choi-game-c14", Name.TAY_CẦM_CHƠI_GAME.value());
//                categories.put("http://www.myboss.vn/ban-di-chuot-choi-game-c11", Name.BÀN_DI_CHUỘT_CHƠI_GAME.value());
//                categories.put("http://www.myboss.vn/tai-nghe-choi-game-c13", Name.TAI_NGHE_CHƠI_GAME.value());
//                categories.put("http://www.myboss.vn/phu-kien-gaming-c6", Name.PHỤ_KIỆN_GEAR_GAMING.value());
//                categories.put("http://www.myboss.vn/loa-gaming-c44", Name.LOA_GAMING.value());
//
//                for (Map.Entry<String, String> entry : categories.entrySet()) {
//                    final String key = entry.getKey();
//                    final String value = entry.getValue();
//                    Runnable crawlingThread = new Runnable() {
//                        @Override
//                        public void run() {
//                            MybossCrawler categoryCrawler = new MybossCrawler(context);
//                            categoryCrawler.crawlHtmlFromCategoryMyboss(key, value);
//                        }
//                    };
//                    crawlingThread.run();
//                }
//            }
//        };
}
