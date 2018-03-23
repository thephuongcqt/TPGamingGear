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
public class AzaudioCategoriesCrawler extends Crawler{

    public AzaudioCategoriesCrawler(ServletContext context) {
        super(context);
    }
    public Map<String, String> getCategoriesForAzAudio(String url) {
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

            return stAXParserForCategories(document);
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

    public Map<String, String> stAXParserForCategories(String document) throws UnsupportedEncodingException, XMLStreamException {
        //START using Stax to parse document
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
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
                        event = eventReader.nextTag(); // move to A Tag
                        startElement = event.asStartElement();
                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                        link = attrHref.getValue();
                        
                        eventReader.next(); // move to start i tag
                        event = eventReader.nextTag(); // move to end i tag
                        event = (XMLEvent)eventReader.next();
                        Characters character = event.asCharacters();
                        categories.put(link, character.getData().trim());
                    }
                } 
            }
        }
        return categories;
        //END using Stax to parse document
    }
}
