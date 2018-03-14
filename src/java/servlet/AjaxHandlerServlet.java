/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import constant.AppConstant;
import dao.ProductDao;
import entities.Products;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import utilities.XMLUtilities;

/**
 *
 * @author PhuongNT
 */
@WebServlet(name = "AjaxHandlerServlet", urlPatterns = {"/AjaxHandlerServlet"})
public class AjaxHandlerServlet extends HttpServlet {

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
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String categoryID = request.getParameter("categoryID");
        String pageString = request.getParameter("page");
        try {
            int page = 1;
            try {
                page = Integer.parseInt(pageString);
            } catch (NumberFormatException ex) {  
                Logger.getLogger(AjaxHandlerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int offset = (page - 1) * AppConstant.defaultLimit;
            Products products = ProductDao.getInstance().getProductsInCategory(categoryID, offset, AppConstant.defaultLimit);            
            XMLUtilities.marshallerToTransfer(products, response.getWriter());
            
        } catch (NumberFormatException ex) {
            Logger.getLogger(AjaxHandlerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(AjaxHandlerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            
        }
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
