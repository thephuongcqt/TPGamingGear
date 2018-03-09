/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author PhuongNT
 */
public class MybossCrawler extends Crawler{
    
    public MybossCrawler(ServletContext context) {
        super(context);
    }
    
    public void crawlHtmlFromCategoryAzaudio(String url, String categoryName) {
        BufferedReader reader = null;
        try {
            //START crawl html fragment for each category 
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isEnding = false;
            int divCounter = 0;
            int divOpen = 0, divClose = 0;
            while ((line = reader.readLine()) != null) {
                if(line.contains("<div id=\"phantrang\">")){
                    isStart = true;
                }
                if(isStart){
                    document += line;
                    if(line.contains("</div>")){
                        break;
                    }
                }
            }
            int lastPage = getLastPage(document);
            for (int i = 0; i < lastPage; i++) {
                String pageUrl = url + "?page=" + (i + 1);                
                crawlHtmlForEachPage(pageUrl, categoryName);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getLastPage(String document) throws UnsupportedEncodingException, XMLStreamException{
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
        if(link != null && !link.isEmpty()){
            String regex = "[0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(link);
            if(matcher.find()){
                String result = matcher.group();
                try{
                    int number = Integer.parseInt(result);
                    return number;                    
                } catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
        return 1;
    }
    
    private void crawlHtmlForEachPage(String url, String categoryName){
        System.out.println(url);
        BufferedReader reader = null;
        try {
            //START crawl html fragment for each category 
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isEnding = false;
            int divCounter = 0;
            int divOpen = 0, divClose = 0;
            while ((line = reader.readLine()) != null) {
                if(line.contains("<ul class=\"thumbnail\">")){
                    isStart = true;
                }
                if(line.contains("<img")){
                    line += "</img>";
                }
                if(isStart){
                    document += line;
                }
                if(line.contains("</ul>")){
                    break;
                }
            }
            System.out.println("=====================" + categoryName + "=====================");
            System.out.println(document);
            System.out.println("=====================" + categoryName + "=====================");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
