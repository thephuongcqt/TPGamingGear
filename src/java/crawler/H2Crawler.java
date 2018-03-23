/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author PhuongNT
 */
public class H2Crawler extends BaseCrawler{
    
    public H2Crawler(ServletContext context) {
        super(context);
    }
    
    public Map<String, String> getCategoriesForAzAudio(String url) {
        BufferedReader reader = null;
        try {
            //START crawl html fragment for each category 
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isEnding = false;
            int divCounter = 0;
            int divOpen = 0, divClose = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<div class=\"cat-child-item\">")){
                    isStart = true;
                    divOpen = 0;
                    divClose = 0;
                }
                if (isStart && line.contains("<div")){
                    divOpen++;
                }
                if (isStart && line.contains("</div")){
                    divClose++;
                    if (divClose > divOpen){
                        isStart = false;
                    }
                }
                if (isStart) {
                    document += line;
                }
            }    
            document += "</root>";
            return stAXParserForCategories(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(H2Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(H2Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(H2Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    private Map<String, String> stAXParserForCategories(String document) throws UnsupportedEncodingException, XMLStreamException{
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> categories = new HashMap<String, String>();        
        while (eventReader.hasNext()) {
            String tagName = "";
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if("h2".equals(tagName)){
                    event = eventReader.nextTag();
                    startElement = event.asStartElement();
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    if(attrHref !=  null){
                        String link = attrHref.getValue();
                        event = eventReader.nextEvent();
                        Characters characters = event.asCharacters();
                        categories.put(link, characters.getData());
                    }
                }
            }
        }
        return categories;
    }
    
    
    public void crawlHtmlFromCategoryAzaudio(String url, String categoryName) {
        BufferedReader reader = null;
        try {
            //START crawl html fragment for each category 
            reader = getBufferedReaderForURL(url);
            if(reader == null){
                return;
            }
            String document = "";
            String line = "";            
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
//                if (line.contains("<div class=\"pagination\"")){
//                    line = "<div>" + line;
//                    break;
//                }
                document += line;
            }
            if(line == null || line.isEmpty()){
                line = "<root><a href=\"http://sg.h2gaming.vn/CHU?T-GAMING?page=1\">CHU?T GAMING</a></root>";
            }
            System.out.println("=====================" + categoryName + "======================");
            System.out.println(document);
            System.out.println("=====================" + categoryName + "======================");
//            System.out.println("url" + url + "line: " + line);
            int lastPage = getLastPage(line);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(H2Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(H2Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(H2Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private int getLastPage(String document) throws UnsupportedEncodingException, XMLStreamException{
//        System.out.println(document);
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        String tagName = "";  
        String link = "";
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();

                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    link = attrHref == null ? "" : attrHref.getValue();
                }
            }//End if start element
        }//End while
//        System.out.println("Link: " + link);
        return 1;
    }
}
