/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;


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
            String xml = null;
            byte[] xmlData = new byte[request.getContentLength()];
            //Start reading XML Request as a Stream of Bytes
            InputStream sis = request.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(sis);
            bis.read(xmlData, 0, xmlData.length);
            if (request.getCharacterEncoding() != null) {
                xml = new String(xmlData, request.getCharacterEncoding());
            } else {
                xml = new String(xmlData);
            }
            System.out.println(xml);
            
            String path = request.getServletContext().getRealPath("/");
            String xmlFile = path + "test.xml";
            String xslFile = path + "webcontent/xsl/orderFO.xsl";
            String foPath = path + "webcontent/orderFO.fo";
            
            methodTrax(xslFile, xmlFile, foPath, path);
//            File file = new File(foPath);
//            FileInputStream input = new FileInputStream(file);
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FopFactory fopFactory = FopFactory.newInstance();
            FOUserAgent fua = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop( MimeConstants.MIME_PDF, fua, out);
            
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
        } finally {
//            out.close();
        }
    }
    
    private void methodTrax(String xslPath, String xmlPath, String output, String path) throws TransformerConfigurationException, FileNotFoundException, TransformerException{
        TransformerFactory tf = TransformerFactory.newInstance();
        StreamSource xsltFile = new StreamSource(xslPath);
        Transformer trans = tf.newTransformer(xsltFile);
        
        StreamSource xmlFile = new StreamSource(xmlPath);
        StreamResult htmlFile = new StreamResult(new FileOutputStream(output));
        trans.transform(xmlFile, htmlFile);
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
