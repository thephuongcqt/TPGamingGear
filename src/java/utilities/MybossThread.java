/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

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
                MybossCrawler crawler = new MybossCrawler(context);
                Map<String, String> categories = new HashMap<String, String>();
                categories.put("http://www.myboss.vn/chuot-choi-game-c10", Name.CHUỘT_CHƠI_GAME.value());
                categories.put("http://www.myboss.vn/ban-phim-choi-game-c12", Name.BÀN_PHÍM_CHƠI_GAME.value());
                categories.put("http://www.myboss.vn/ghe-choi-game-c15", Name.GHẾ_CHƠI_GAME.value());
                categories.put("http://www.myboss.vn/tay-cam-choi-game-c14", Name.TAY_CẦM_CHƠI_GAME.value());
                categories.put("http://www.myboss.vn/ban-di-chuot-choi-game-c11", Name.BÀN_DI_CHUỘT_CHƠI_GAME.value());
                categories.put("http://www.myboss.vn/tai-nghe-choi-game-c13", Name.TAI_NGHE_CHƠI_GAME.value());
                categories.put("http://www.myboss.vn/phu-kien-gaming-c6", Name.PHỤ_KIỆN_GEAR_GAMING.value());
                categories.put("http://www.myboss.vn/loa-gaming-c44", Name.LOA_GAMING.value());

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
