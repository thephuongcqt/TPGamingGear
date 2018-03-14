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
        <link rel="stylesheet" href="webcontent/font-awesome/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="webcontent/font-awesome/css/font-awesome.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="bodyPage">
            <div class="categoryTrending">

            </div>
            <div class="advantageSearch" onclick="">
                <p class="textLoadMore">Advantage search</p>
            </div>

            <div class="gridContainer">
                <c:import charEncoding="UTF-8" url="webcontent/xsl/trending.xsl" var="xsltTrending"/>
                <c:set var="trendingProducts" value="${requestScope.TrendingProducts}"/>
                <x:transform xml="${trendingProducts}" xslt="${xsltTrending}" />
            </div>
        </div>

        <jsp:include page="footer.html" />
    </body>

    <script src="webcontent/script/common.js"></script>
    <script>
        var xmlCategoriesString = '${requestScope.CATEGORIES}';
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(xmlCategoriesString, "text/xml");
        var categories = xmlDoc.getElementsByTagName("ns2:Category");
        Controller.loadCategories(categories);
        View.pTagTrending.innerHTML = "Sản phẩm nổi bật";
    </script>
</html>
