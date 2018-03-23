/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import constant.AppConstant;
import crawler.MybossCrawler;
import static java.lang.Thread.MIN_PRIORITY;
import jaxb.Name;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
/**
 *
 * @author PhuongNT
 */
public class MybossThread extends BaseThread implements Runnable{

    private ServletContext context;

    public MybossThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("again my boss");
                MybossCategoriesCrawler categoriesCrawler = new MybossCategoriesCrawler(context);
                Map<String, String> categories = categoriesCrawler.getCategories(AppConstant.urlMyboss);

                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    final String key = entry.getKey();
                    final String value = entry.getValue();
                    Thread crawlingThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MybossCrawler categoryCrawler = new MybossCrawler(context);
                            categoryCrawler.crawlHtmlFromCategoryMyboss(key, value);
                        }
                    });
                    crawlingThread.start();
                    
                    synchronized (this) {
                        while (BaseThread.isSuspended()) {
                            wait();
                        }
                    }
                }//End for each Category
                MybossThread.sleep(TimeUnit.DAYS.toMillis(AppConstant.breakTimeCrawling));
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
