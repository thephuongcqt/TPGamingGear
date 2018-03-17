<%-- 
    Document   : search
    Created on : Mar 15, 2018, 1:28:33 AM
    Author     : PhuongNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Result</title>
        <link rel="stylesheet" type="text/css" href="webcontent/css/home.css">
        <link rel="stylesheet" href="webcontent/font-awesome/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="webcontent/font-awesome/css/font-awesome.css"/>
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
                <c:import charEncoding="UTF-8" url="webcontent/xsl/trending.xsl" var="xslGridProduct"/>
                <c:set var="searchResult" value="${requestScope.AdvantageSearchResult}"/>
                <c:if test="${not empty searchResult}">
                    <x:transform xml="${searchResult}" xslt="${xslGridProduct}" />                    
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
        
        var counter = '${requestScope.SearchResultCounter}';
        var paramSearchValue = '${param.searchValue}';
        if(counter > 0){
            View.pTagTrending.innerHTML = Model.constant.stringAdvantageSearchResult + paramSearchValue + "'";            
        } else{
            View.pTagTrending.innerHTML = Model.constant.stringAdvantageSearchResultNotMatch + paramSearchValue + "'";
        }
    </script>
</html>
