<%-- 
    Document   : index
    Created on : Mar 8, 2018, 10:30:34 AM
    Author     : PhuongNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<c:import url="webcontent/xslt/trending.xsl" var="xsltTrending"/>
<c:set var="trendingProducts" value="${requestScope.TrendingProducts}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" type="text/css" href="webcontent/css/home.css">
        <script src="webcontent/script/common.js"></script>
    </head>
    <body>
        <jsp:include page="header.html"/>

        <div class="bodyPage">
            <div class="categoryTrending">
                <p>Sản phẩm nổi bật</p>
            </div>

            <div class="gridContainer">
                <x:transform xml="${trendingProducts}" xslt="${xsltTrending}" />
            </div>
        </div>

        <jsp:include page="footer.html" />
    </body>

    <script>
        var xmlCategoriesString = '${requestScope.CATEGORIES}';
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(xmlCategoriesString, "text/xml");
        var categories = xmlDoc.getElementsByTagName("ns2:Category");
        loadCategories(categories);        
    </script>
</html>
