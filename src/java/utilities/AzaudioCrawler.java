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
public class AzaudioCrawler extends Crawler{
    
    public AzaudioCrawler(ServletContext context) {
        super(context);
    }
    
    public Map<String, String> getCategoriesForAzAudio(String filePath, String url) {
        BufferedReader reader = null;
        try {
            //START crawl html fragment for category
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            int divCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<div class=\"panel panel-default\">")) {
                    divCounter++;
                    if (divCounter == 2) {
                        isStart = true;
                    } else if (divCounter > 2) {
                        break;
                    }
                }
                if (isStart) {
                    document += line;
                }
            }
            //END crawl html section for category
            
            return StAXParserForCategory(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public Map<String, String> StAXParserForCategory(String document) throws UnsupportedEncodingException, XMLStreamException{
        //START using Stax to parse document
            document = document.trim();
            XMLEventReader eventReader = parseStringToXMLEventReader(document);
            boolean isCategoryLink = false;
            boolean isCategoryValue = false;
            Map<String, String> categories = new HashMap<String, String>();
            String link = "";
            while (eventReader.hasNext()) {
                String tagName = "";
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    tagName = startElement.getName().getLocalPart();
                    if ("li".equals(tagName)) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && "has-sub".equals(attrClass.getValue())) {
                            isCategoryLink = true;
                        }
                    } else if ("a".equals(tagName) && isCategoryLink) {
                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                        link = attrHref.getValue();
                        isCategoryLink = false;
                        isCategoryValue = true;
                    }
                }
                if (event.isCharacters()) {
                    Characters character = event.asCharacters();
                    if (isCategoryValue) {
                        categories.put(link, character.getData());
                        isCategoryValue = false;
                    }
                }
                if (event.isEndElement()) {
                }
            }
            return categories;
            //END using Stax to parse document
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
            while ((line = reader.readLine()) != null) {
                if (line.contains("<div class=\"prdlist-wrap product-list\">")) {
                    isStart= true;                    
                } else if (line.contains("class=\"ajaxpagerlink\"")){
                    isEnding = true;
                }
                if (isStart) {
                    if (line.contains("<img")){
                        line += "</img>";
                    }
                    document += line;
                }
                if (isEnding && line.contains("div")){
                    divCounter++;
                    if (divCounter == 2){
                        break;
                    }
                }
            }
            //END crawl html fragment for each category 
            StAXParserForEachCategory(document, categoryName);
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void StAXParserForEachCategory(String document, String categoryName) throws UnsupportedEncodingException, XMLStreamException{
        //START using Stax to parse document
            document = document.trim();
            XMLEventReader eventReader = parseStringToXMLEventReader(document);
            while (eventReader.hasNext()) {
                String tagName = "";
                XMLEvent event = (XMLEvent) eventReader.next();                
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    tagName = startElement.getName().getLocalPart();
                    if("div".equals(tagName)){
                        
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && "code".equals(attrClass.getValue())){                            
                            eventReader.nextTag();                            
                            event = (XMLEvent) eventReader.nextEvent();
                            Characters character = event.asCharacters();
                            System.out.println(categoryName + " | " + character.getData());
                        }
                    }
                }
            }
            //END using Stax to parse document
    }
}
