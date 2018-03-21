/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import constant.AppConstant;
import dao.DetailOrderDao;
import dao.OrderDao;
import dao.ProductDao;
import dao.UserDao;
import entities.TblDetailOrder;
import entities.TblDetailOrderPK;
import entities.TblOrder;
import entities.TblProduct;
import entities.TblUser;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import jaxb.CartItem;
import jaxb.Order;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.SAXException;
import utilities.MyUtilities;
import utilities.XMLUtilities;

/**
 *
 * @author PhuongNT
 */
public class CheckOutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
//        PrintWriter out = response.getWriter();
        try {
            
            String xmlOrderString = null;
            byte[] xmlData = new byte[request.getContentLength()];
            //Start reading XML Request as a Stream of Bytes
            InputStream sis = request.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(sis);
            bis.read(xmlData, 0, xmlData.length);
            if (request.getCharacterEncoding() != null) {
                xmlOrderString = new String(xmlData, request.getCharacterEncoding());
            } else {
                xmlOrderString = new String(xmlData);
            }

            String path = request.getServletContext().getRealPath("/");
            String xslFile = path + AppConstant.xslOrderFilePath;
            String foPath = path + AppConstant.foOrderFilePath;
            
            boolean isValidation = XMLUtilities.checkValidationXML(xmlOrderString, path + AppConstant.xsdOrderFilePath);
            if(!isValidation){
                return;
            }            
            
            Order order = XMLUtilities.unmarshalXMLString(xmlOrderString, Order.class);
            saveOrderToDatabase(order);
            
            methodTrax(xslFile, xmlOrderString, foPath, path); // transform xml & xsl to fo
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setUserConfig(path + AppConstant.fontsConfigFilePath);
            FOUserAgent fua = fopFactory.newFOUserAgent();
            fua.setAuthor("Phuong Nguyen");
            fua.setCreationDate(new Date());
            fua.setTitle("Sales Invoices");
            
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, fua, out);

            TransformerFactory tff = TransformerFactory.newInstance();
            
            Transformer trans = tff.newTransformer();
            File fo = new File(foPath);
            Source src = new StreamSource(fo);
            Result result = new SAXResult(fop.getDefaultHandler());
            trans.transform(src, result);

            byte[] content = out.toByteArray();
            
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
            response.getOutputStream().flush();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FOPException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            out.close();
        }
    }
    
    private void saveOrderToDatabase(Order order){
        TblUser user = order.getUserType();
        
        UserDao userDao = UserDao.getInstance();
        TblUser dbUser = userDao.findByID(user.getEmail());
        if(dbUser != null){
            TblOrder dbOrder = new TblOrder(order.getAddress(), order.getPhoneNumber(), new Date(), null, dbUser);
            
            List<CartItem> items = order.getCart().getCartItem();
            OrderDao orderDao = OrderDao.getInstance();
            TblOrder result =  orderDao.create(dbOrder);
            if(result != null){
                ProductDao productDao = ProductDao.getInstance();
                DetailOrderDao detailOrderDao = DetailOrderDao.getInstance();
                for(CartItem item : items){
                    long productId = item.getProductID();
                    TblProduct product = productDao.findByID(productId);
                    if(product != null){
                        TblDetailOrderPK pk = new TblDetailOrderPK(result.getOrderID(), productId);
                        TblDetailOrder detailOrder = new TblDetailOrder(pk, item.getQuantity(), result, product);
                        detailOrderDao.create(detailOrder);
                    }
                }
            }
        }
    }

    private void methodTrax(String xslPath, String xmlString, String output, String path) throws TransformerConfigurationException, FileNotFoundException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        StreamSource xsltFile = new StreamSource(xslPath);
        Transformer trans = tf.newTransformer(xsltFile);
        trans.setParameter("serverPath", path);
        trans.setParameter("createDate", MyUtilities.getStringDate(new Date()));

        InputStream is = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        StreamSource xmlFile = new StreamSource(is);
        
        StreamResult foFile = new StreamResult(new FileOutputStream(output));
        trans.transform(xmlFile, foFile);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
