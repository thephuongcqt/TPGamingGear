<%-- 
    Document   : viewCart
    Created on : Mar 16, 2018, 2:18:07 AM
    Author     : PhuongNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" type="text/css" href="webcontent/css/home.css">
        <link rel="stylesheet" href="webcontent/font-awesome/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="webcontent/css/cart.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="bodyPage">
            <div class="categoryTrending">

            </div>
            <div class="advantageSearch" onclick="Controller.onAdvantageSearchClick()">
                <p>Advantage search</p>
            </div>

            <div class="gridContainer">

            </div>
            
        </div>

        <jsp:include page="footer.html" />
    </body>

    <script src="webcontent/script/mvc.js"></script>
    <script src="webcontent/script/utilities.js"></script>
    <script src="webcontent/script/common.js"></script>
    <script src="webcontent/script/search.js"></script>
    <script src="webcontent/script/cart.js"></script>
    <script src="webcontent/script/cartDetail.js"></script>
    <script>
                var xmlCategoriesString = '${requestScope.CATEGORIES}';
                var xmlDoc = Controller.parserXMLFromStringToDOM(xmlCategoriesString);
                var categories = xmlDoc.getElementsByTagName("ns2:Category");
                Controller.loadCategories(categories);
                View.pTagTrending.innerHTML = "Giỏ hàng của bạn";
    </script>
</html>