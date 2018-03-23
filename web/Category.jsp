<%-- 
    Document   : Category
    Created on : Mar 13, 2018, 4:13:37 PM
    Author     : PhuongNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category</title>
        <link rel="stylesheet" type="text/css" href="webcontent/css/category.css">
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
                <c:import charEncoding="UTF-8" url="webcontent/xsl/category.xsl" var="xslCategory"/>
                <c:set var="listProducts" value="${requestScope.ListProductsInCategory}"/>
                <c:if test="${not empty listProducts}">
                    <x:transform xml="${listProducts}" xslt="${xslCategory}" />
                </c:if>
            </div>

            <div class="loadMore" onclick="Controller.onLoadMoreClick()">
                <p class="textLoadMore">Load More</p>
            </div>
        </div>

        <jsp:include page="footer.html" />
    </body>

    <script src="webcontent/font-awesome/jquery-min.js" type="text/javascript"></script>
    <script src="webcontent/script/mvc.js" type="text/javascript"></script>
    <script src="webcontent/script/utilities.js" type="text/javascript"></script>
    <script src="webcontent/script/common.js" type="text/javascript"></script>
    <script src="webcontent/script/category.js" type="text/javascript"></script>
    <script src="webcontent/script/search.js" type="text/javascript"></script>
    <script src="webcontent/script/cart.js" type="text/javascript"></script>
    <script src="webcontent/script/login.js" type="text/javascript"></script>
    <script src="webcontent/script/lastScript.js" type="text/javascript"></script>
    <script>
                Model.currentPage = parseInt('1');
                Model.selectedCategoryname = '${requestScope.CategoryName}';
                Model.currentCategoryID = '${param.categoryID}';
                Model.productCounter = '${requestScope.productCounters}';

                var xmlCategoriesString = '${requestScope.CATEGORIES}';
                var xmlDoc = Controller.parserXMLFromStringToDOM(xmlCategoriesString);
                var categories = xmlDoc.getElementsByTagName("ns2:Category");
                Controller.loadCategories(categories);

                View.pTagTrending.innerHTML = Model.selectedCategoryname;
                View.showButtonLoadMore();
    </script>
</html>
