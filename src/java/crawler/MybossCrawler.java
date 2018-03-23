/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import constant.AppConstant;
import dao.ProductDao;
import entities.TblProduct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author PhuongNT
 */
public class MybossCrawler extends Crawler {
    public MybossCrawler(ServletContext context) {
        super(context);
    }

    public void crawlHtmlFromCategoryMyboss(String url, String categoryName) {
        createCategory(categoryName);
        if(category == null){
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, new Exception("Error: category null"));
            return;
        }
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
                if (line.contains("<div id=\"phantrang\">")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line;
                    if (line.contains("</div>")) {
                        break;
                    }
                }
            }
            int lastPage = getLastPage(document);
            for (int i = 0; i < lastPage; i++) {
                String pageUrl = url + "?page=" + (i + 1);
                crawlHtmlForEachPage(pageUrl);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getLastPage(String document) throws UnsupportedEncodingException, XMLStreamException {
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
        if (link != null && !link.isEmpty()) {
            String regex = "[0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(link);
            if (matcher.find()) {
                String result = matcher.group();
                try {
                    int number = Integer.parseInt(result);
                    return number;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;
    }

    private void crawlHtmlForEachPage(String url) throws XMLStreamException {
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
                if (line.contains("<ul class=\"thumbnail")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line;
                }
                if (line.contains("</ul>")) {
                    isStart = false;
                }
            }
            document += "</document>";
            stAXparserForEachPage(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void stAXparserForEachPage(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        boolean isCategoryLink = false;
        boolean isCategoryValue = false;
        Map<String, String> categories = new HashMap<String, String>();
        String detailLink = "";
        String imgLink = "";
        String price = "";
        String productName = "";
        boolean isStart = false;

        while (eventReader.hasNext()) {
            String tagName = "";
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("li".equals(tagName)) {
                    isStart = true;
                } else if ("a".equals(tagName) && isStart) {
                    eventReader.next();
                    event = (XMLEvent) eventReader.next(); //move to img tag
                    startElement = event.asStartElement(); //get img tag
                    Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
                    imgLink = attrSrc == null ? "" : attrSrc.getValue(); //get img link

                    eventReader.next();
                    eventReader.nextTag();
                    eventReader.next();
                    event = eventReader.nextTag();
                    startElement = event.asStartElement(); // move to tag <a> contains product name
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    detailLink = attrHref == null ? "" : attrHref.getValue(); //get detail link
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    productName = character.getData().trim();

                    eventReader.nextTag();
                    eventReader.next();
                    eventReader.nextTag();
                    eventReader.next();
                    eventReader.nextTag();
                    event = (XMLEvent) eventReader.next();//move to <span> tag 
                    character = event.asCharacters();
                    price = character.getData().trim(); //get price

                    if (!detailLink.isEmpty()) {
                        detailLink = AppConstant.urlMyboss + detailLink;
                    }
                    if (!imgLink.isEmpty()) {
                        imgLink = AppConstant.urlMyboss + imgLink;
                    }
                    isStart = false;

                    try{
                        price = price.replaceAll("\\D+","");
                        BigInteger realPrice = new BigInteger(price);
                        String categoryId = category.getCategoryId();
                        
                        TblProduct product = new TblProduct(new Long(1), productName, realPrice, imgLink, this.category.getCategoryId(), true, AppConstant.domainMyBoss);
                        ProductDao.getInstance().saveProductWhenCrawling(product);
                        
                        
//                        String realPath = MyContextServletListener.getRealPath();
//                        String productPath = realPath + AppConstant.xsdProductFilePath;
//                        String xmlObj = XMLUtilities.marshallerToString(product);    
//                        boolean isValid = XMLUtilities.checkValidationXML(xmlObj, productPath);
//                        if(isValid){                       
//                            //this product is validation
//                        } else{
//                            System.out.println("invalidate");
//                        }
                    } catch(NumberFormatException ex){
                        Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }//End while Event reader
        
    }
}
