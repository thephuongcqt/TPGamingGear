/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import constant.AppConstant;
import dao.ProductDao;
import entities.TblCategory;
import entities.TblProduct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
public class MybossEachPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private TblCategory category;

    public MybossEachPageCrawler(ServletContext context, String url, TblCategory category) {
        super(context);
        this.url = url;
        this.category = category;
    }

    @Override
    public void run() {
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
        } catch (XMLStreamException ex) {
            Logger.getLogger(MybossEachPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
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

                    try {
                        price = price.replaceAll("\\D+", "");
                        BigInteger realPrice = new BigInteger(price);
                        String categoryId = this.category.getCategoryId();

                        TblProduct product = new TblProduct(new Long(1), productName, realPrice, imgLink, categoryId, true, AppConstant.domainMyBoss);
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
                    } catch (NumberFormatException ex) {
                        Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }//END IF start Element           
        }//End while Event reader

    }
}
