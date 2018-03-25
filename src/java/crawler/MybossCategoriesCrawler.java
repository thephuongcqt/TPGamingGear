/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import constant.AppConstant;
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
public class MybossCategoriesCrawler extends BaseCrawler{
    
    public MybossCategoriesCrawler(ServletContext context) {
        super(context);
    }
    
    public Map<String, String> getCategories(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;  
            boolean isFound = false;
            while ((line = reader.readLine()) != null) {
                if(isStart && line.contains("</li><li  style=\"display")){
                    break;
                }
                if (isStart) {
                    document += line.trim();
                }
                if(isFound && line.contains("</a>")){
                    isStart = true;
                }
                if (line.contains("<a href=\"thiet-bi-choi-game-c1\"")) {
                    isFound = true;
                }
            }
            return stAXParserForCategories(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MybossCategoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Map<String, String> stAXParserForCategories(String document) throws UnsupportedEncodingException, XMLStreamException{
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> categories = new HashMap<String, String>();
                
        while (eventReader.hasNext()) {            
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if("a".equals(tagName)){
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    String link = AppConstant.urlMyboss + attrHref.getValue();
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    categories.put(link, character.getData());
                }
            }
        }
        return categories;
    }
}
