/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import constant.AppConstant;
import static java.lang.Thread.sleep;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author PhuongNT
 */
public class AzaudioThread extends BaseThread implements Runnable{

    private ServletContext context;

    public AzaudioThread(ServletContext context) {
        this.context = context;
    }   

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("begin az thrread");
                AzaudioCategoriesCrawler categoriesCrawler = new AzaudioCategoriesCrawler(context);
                Map<String, String> categories = categoriesCrawler.getCategories(AppConstant.urlAzAudio);
                categoriesCrawler = null;
                
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    final Map.Entry<String, String> currentEntry = entry;
                    Thread crawlingThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AzaudioCrawler categoryCrawler = new AzaudioCrawler(context);
                            categoryCrawler.crawlHtmlFromCategoryAzaudio(currentEntry.getKey(), currentEntry.getValue());
                        }
                    });
                    crawlingThread.start();

                    synchronized (this) {
                        while (BaseThread.isSuspended()) {
                            wait();
                        }
                    }
                }//End for Each category
                sleep(TimeUnit.DAYS.toMillis(AppConstant.breakTimeCrawling));
                synchronized (this) {
                    while (BaseThread.isSuspended()) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AzaudioThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
