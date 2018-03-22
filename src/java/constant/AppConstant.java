/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constant;

/**
 *
 * @author PhuongNT
 */
public class AppConstant {
    public static final String urlAzAudioHomePage = "http://www.azaudio.vn";
    public static final String urlAzAudio = "http://www.azaudio.vn/gaming-gear";
    public static final String urlH2Gaming = "http://h2gaming.vn/gaming-gear";
    public static final String urlMyboss = "http://www.myboss.vn/";
    
    public static final String categoryPage = "Category.jsp";
    public static final String ajaxHandlerServlet = "AjaxHandlerServlet";
    public static final String ajaxLoadListProducsServlet = "AjaxLoadListProductsServlet";
    public static final String advantageSearchPage = "search.jsp";
    public static final String viewCartPage = "viewCart.jsp";
    public static final String checkOutServlet = "CheckOutServlet";
    public static final String loginServlet = "LoginServlet";
    public static final String registerServlet = "RegisterServlet";
    public static final String homePage = "home.jsp";
    public static final String errorPage = "error.html";
    public static final String loadProductServlet = "LoadProductServlet";
    
    public static final String namedQueryGetAllCategories = "TblCategory.findAll";
    public static final int defaultLimit = 8;
    public static final int defaultListProductsLimit = 200;
    
    public static final String xsdProductFilePath = "webcontent/xsd/Product.xsd";
    public static final String xsdUserFilePath = "webcontent/xsd/User.xsd";
    public static final String xsdOrderFilePath = "webcontent/xsd/Order.xsd";
    
    public static final String foOrderFilePath = "webcontent/orderFO.fo";
    public static final String fontsConfigFilePath = "/WEB-INF/config.xml";
    public static final String xslOrderFilePath = "webcontent/xsl/orderFO.xsl";
    public static final int defaultRole = 2;
    
    public static final String paramEmail = "txtEmail";
    public static final String paramPassword = "txtPassword";
    public static final String paramFullName = "txtFullName";
    public static final String paramAction = "btnAction";
    public static final String paramCategoryID = "categoryID";
    public static final String paramSearchValue = "searchValue";
    
    public static final String actionLoadCategory = "loadCategory";
    public static final String actionLoadMore = "LoadMore";
    public static final String actionLoadListProductsForSearch = "LoadListProductForSearch";
    public static final String actionAdvantageSearch = "advantageSearch";
    public static final String actionShowDetailCart = "ShowCartDetail";
    public static final String actionCheckOut = "CheckOut";
    public static final String actionLogin = "Login";
    public static final String actionRegister = "Register";
    public static final String actionLoadProduct = "LoadProduct";  
}
