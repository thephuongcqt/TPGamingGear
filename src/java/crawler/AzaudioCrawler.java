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
public class AzaudioCrawler extends BaseCrawler implements Runnable {

    private String url;
    private String categoryName;
    protected TblCategory category = null;

    public AzaudioCrawler(ServletContext context, String url, String categoryName) {
        super(context);
        this.url = url;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        category = createCategory(categoryName);
        if (category == null) {
            Logger.getLogger(AzaudioCrawler.class.getName()).log(Level.SEVERE, null, new Exception("Error: category null"));
            return;
        }
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
                if (line.contains("<div class=\"col-xs-6 col-md-4 col-lg-4 mrb30\">")) {
                    isStart = true;
                    divOpen = 0;
                    divClose = 0;
                }
                if (isStart && line.contains("<div")) {
                    divOpen++;
                }
                if (isStart && line.contains("</div")) {
                    divClose++;
                    if (divClose > divOpen) {
                        isStart = false;
                    }
                }
                if (isStart) {
                    if (line.contains("<img")) {
                        line += "</img>";
                    }
                    document += line;
                }
                if (line.contains("class=\"ajaxpagerlink\"")) {
                    document += line + " </a>";
                    break;
                }
                if (line.contains("</main")) {
                    break;
                }
            }
            document += "</root>";
            //END crawl html fragment for each category   
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(AzaudioCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            StAXParserForEachCategory(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void StAXParserForEachCategory(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        String productName = "";
        String price = "";
        String imgLink = "";
        String detailLink = "";
        String tagName = "";
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();

                if ("a".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null) {
                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                        if ("center-block".equals(attrClass.getValue())) {
                            detailLink = attrHref != null ? attrHref.getValue() : ""; // get detail link
                        } else if ("ajaxpagerlink".equals(attrClass.getValue())) {
                            //Handle load more
                            final String loadmoreLink = AppConstant.urlAzAudioHomePage + (attrHref != null ? attrHref.getValue() : "");

                            Thread crawlingThread = new Thread(new AzaudioCrawler(this.getContext(), loadmoreLink, categoryName));
                            try {
                                synchronized (BaseThread.getInstance()) {
                                    while (BaseThread.isSuspended()) {
                                        BaseThread.getInstance().wait();
                                    }
                                }
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AzaudioCrawler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            crawlingThread.start();
                        }
                    }
                } else if ("img".equals(tagName)) {
                    Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
                    imgLink = AppConstant.urlAzAudioHomePage + (attrSrc != null ? attrSrc.getValue() : ""); //get image link
                } else if ("div".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null && "code".equals(attrClass.getValue())) {
                        eventReader.nextTag();
                        event = eventReader.nextEvent();
                        Characters codeCharacter = event.asCharacters();
                        productName = codeCharacter.getData(); //get product code
                    }
                } else if ("span".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null && "price-new".equals(attrClass.getValue())) {
                        event = eventReader.nextEvent();
                        Characters character = event.asCharacters();
                        price = character.getData();

                        try {
                            price = price.replaceAll("\\D+", "");
                            BigInteger realPrice = new BigInteger(price);
                            String categoryId = this.category.getCategoryId();
                            TblProduct product = new TblProduct(new Long(1), productName, realPrice, imgLink, categoryId, true, AppConstant.domainAzAudio);
                            
                            try {
                                synchronized (BaseThread.getInstance()) {
                                    while (BaseThread.isSuspended()) {
                                        BaseThread.getInstance().wait();
                                    }
                                }
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AzaudioCrawler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ProductDao.getInstance().saveProductWhenCrawling(product);
                        } catch (NumberFormatException e) {
                            System.out.println(e);
                        }
                    }
                }
            }//End if start element            
        }//End while
    }
}
