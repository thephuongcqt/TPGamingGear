/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import constant.AppConstant;
import java.util.Map;
import javax.servlet.ServletContext;
import listener.MyContextServletListener;

/**
 *
 * @author PhuongNT
 */
public class AzaudioThread extends Thread {

    private ServletContext context;

    public AzaudioThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        AzaudioCrawler crawler = new AzaudioCrawler(context);
        Map<String, String> categories = crawler.getCategoriesForAzAudio(AppConstant.urlAzAudio);
        crawler = null;
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
            MyContextServletListener.listThreads.add(crawlingThread);
        }
    }

}
