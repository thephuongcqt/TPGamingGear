/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import constant.AppConstant;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PhuongNT
 */
public class ProcessServlet extends HttpServlet {
    
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String url = AppConstant.errorPage;
            String btnAction = request.getParameter(AppConstant.paramAction);
            if(btnAction == null){
                url = AppConstant.homePage;
            } else if(btnAction.equalsIgnoreCase(AppConstant.actionLoadCategory)){
                url = AppConstant.categoryPage;
            } else if(btnAction.equalsIgnoreCase(AppConstant.actionLoadMore)){
                url = AppConstant.ajaxHandlerServlet;
            } else if(btnAction.equalsIgnoreCase(AppConstant.actionLoadListProductsForSearch)){
                url = AppConstant.ajaxLoadListProducsServlet;
            } else if(btnAction.equalsIgnoreCase(AppConstant.actionAdvantageSearch)){
                url = AppConstant.advantageSearchPage;
            } else if(btnAction.equalsIgnoreCase(AppConstant.actionShowDetailCart)){
                url = AppConstant.viewCartPage;
            } else if(btnAction.equalsIgnoreCase(AppConstant.actionShowDetailCart)){
                url = AppConstant.checkOutServlet;
            } else if(btnAction.equals(AppConstant.actionLogin)){
                url = AppConstant.loginServlet;
            } else if(btnAction.equals(AppConstant.actionRegister)){
                url = AppConstant.registerServlet;
            } else if(btnAction.equals(AppConstant.actionLoadProduct)){
                url = AppConstant.loadProductServlet;
            }
            
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
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
