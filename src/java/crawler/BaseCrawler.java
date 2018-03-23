/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import constant.CategoryEnum;
import dao.CategoryDao;
import entities.TblCategory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import utilities.MyUtilities;

/**
 *
 * @author PhuongNT
 */
public class BaseCrawler {

    protected TblCategory category = null;
    private ServletContext context;

    public BaseCrawler(ServletContext context) {
        this.context = context;
    }

    public ServletContext getContext() {
        return context;
    }

    protected BufferedReader getBufferedReaderForURL(String urlString) throws MalformedURLException, UnsupportedEncodingException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    protected XMLEventReader parseStringToXMLEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }

    private static final Object LOCK = new Object();

    protected void createCategory(String categoryName) {        
        synchronized (LOCK) {
            String realCategoryName = CategoryEnum.getRealCategoryName(categoryName);
            if(realCategoryName != null){
                CategoryDao dao = CategoryDao.getInstance();
                category = dao.getFirstCategoryByName(realCategoryName);
                if (category == null) {
                    //this category didn't exist, insert new one
                    category = new TblCategory(MyUtilities.generateUUID(), realCategoryName);
                    dao.create(category);

                }                
            }//End if category name null
        }//End Lock
    }
}
