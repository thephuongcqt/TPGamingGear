/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import constant.AppConstant;
import dao.CategoryDao;
import dao.ProductDao;
import entities.Categories;
import entities.Products;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import utilities.XMLUtilities;

/**
 * Web application lifecycle listener.
 *
 * @author PhuongNT
 */
public class MyRequestServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Request destroyed...");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("Request Initialized");
        ServletRequest request = sre.getServletRequest();
        ServletContext context = sre.getServletContext();
        HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();
        String path = context.getContextPath();
        String button = request.getParameter("btnAction");
        if (button == null) {            
            CategoryDao dao = CategoryDao.getInstance();
            try {
                Categories categories = new Categories();
                categories.getCategory().addAll(dao.getAll(AppConstant.namedQueryGetAllCategories));
                String xmlString = XMLUtilities.marshallerToString(categories);
                request.setAttribute("CATEGORIES", xmlString);
                
                Products trendingProducts = ProductDao.getInstance().getTrendingProducts(10);
                String xmlTrendingProducts = XMLUtilities.marshallerToString(trendingProducts);
                request.setAttribute("TrendingProducts", xmlTrendingProducts);
                
            } catch (JAXBException ex) {
                Logger.getLogger(MyRequestServletListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
