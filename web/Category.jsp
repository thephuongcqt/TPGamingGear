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
        <link rel="stylesheet" type="text/css" href="webcontent/css/home.css">
        <link rel="stylesheet" type="text/css" href="webcontent/css/category.css"> 
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
                <c:set var="listProducts" value="${requestScope.ListProductsInCategory}"/>
                <x:transform xml="${listProducts}" xslt="${xsltTrending}" />
            </div>

            <div class="loadMore" onclick="Controller.onLoadMoreClick()">
                <p class="textLoadMore">Load More</p>
            </div>
        </div>

        <jsp:include page="footer.html" />
    </body>

    <script src="webcontent/script/common.js"></script>
    <script src="webcontent/script/category.js"></script>
    <script>
                Model.currentPage = parseInt('1');
                Model.selectedCategoryname = '${requestScope.CategoryName}';
                Model.currentCategoryID = '${param.categoryID}';
                Model.productCounter = '${requestScope.productCounters}';

                var xmlCategoriesString = '${requestScope.CATEGORIES}';
                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(xmlCategoriesString, "text/xml");
                var categories = xmlDoc.getElementsByTagName("ns2:Category");
                Controller.loadCategories(categories);

                
                View.pTagTrending.innerHTML = Model.selectedCategoryname;
                View.showButtonLoadMore();                
    </script>
</html>
