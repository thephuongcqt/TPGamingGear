/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
    
    private void crawlHtmlForEachPage(String url, String categoryName) throws XMLStreamException{        
        BufferedReader reader = null;
        try {
            //START crawl html fragment for each category 
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "<document>";
            boolean isStart = false;
            boolean isEnding = false;
            int divCounter = 0;
            int divOpen = 0, divClose = 0;
            while ((line = reader.readLine()) != null) {
                if(line.contains("<ul class=\"thumbnail")){
                    isStart = true;
                }
                if(isStart){
                    document += line;
                }
                if(line.contains("</ul>")){
                    isStart = false;
                }                
            }
            document += "</document>";
            System.out.println("=====================" + categoryName + "=====================");            
            stAXparserForEachPage(document, categoryName);
            System.out.println("=====================" + categoryName + "=====================");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void stAXparserForEachPage(String document, String categoryName) throws UnsupportedEncodingException, XMLStreamException{
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        boolean isCategoryLink = false;
        boolean isCategoryValue = false;
        Map<String, String> categories = new HashMap<String, String>();
        String link = "";
        String img = "";
        String price = "";
        boolean isStart = false;
        while (eventReader.hasNext()) {
            String tagName = "";
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();                
                if("li".equals(tagName)){
                    event = eventReader.nextTag();
                    startElement = event.asStartElement();
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    link = attrHref == null ? "" : attrHref.getValue();
                    event = eventReader.nextTag();
                    if(event.isStartElement()){
                        startElement = event.asStartElement();
                        Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
                        img = attrSrc == null ? "" : attrSrc.getValue();                        
                    } else if (event.isCharacters()) {
                        System.out.println("character");
                    } else if (event.isEndElement()){
                        System.out.println("end");
                    }
                    System.out.println(categoryName + " | " + img  + " | "+ link);
                }
                
            }
        }
        
    }
}
