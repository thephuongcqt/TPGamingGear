<%-- 
    Document   : index
    Created on : Mar 8, 2018, 10:30:34 AM
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
        <link rel="stylesheet" type="text/css" href="webcontent/css/login.css"/>
        <link rel="stylesheet" type="text/css" href="webcontent/font-awesome/css/font-awesome.min.css"/>
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
                <c:import charEncoding="UTF-8" url="webcontent/xsl/trending.xsl" var="xslTrending"/>
                <c:set var="trendingProducts" value="${requestScope.TrendingProducts}"/>
                <c:if test="${not empty trendingProducts}">
                    <x:transform xml="${trendingProducts}" xslt="${xslTrending}" />
                </c:if>
            </div>
        </div>

        <jsp:include page="footer.html" />
    </body>

    <script src="webcontent/script/mvc.js"></script>
    <script src="webcontent/script/utilities.js"></script>
    <script src="webcontent/script/common.js"></script>
    <script src="webcontent/script/search.js"></script>
    <script src="webcontent/script/cart.js"></script>
    <script src="webcontent/script/login.js" type="text/javascript"></script>
    <script>
        var xmlCategoriesString = '${requestScope.CATEGORIES}';
        var xmlDoc = Controller.parserXMLFromStringToDOM(xmlCategoriesString);
        var categories = xmlDoc.getElementsByTagName("ns2:Category");
        Controller.loadCategories(categories);
        View.pTagTrending.innerHTML = "Sản phẩm nổi bật";
    </script>
</html>
