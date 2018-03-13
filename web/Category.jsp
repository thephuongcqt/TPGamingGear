<%-- 
    Document   : Category
    Created on : Mar 13, 2018, 4:13:37 PM
    Author     : PhuongNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category</title>
        <link rel="stylesheet" type="text/css" href="webcontent/css/home.css">
        <script src="webcontent/script/common.js"></script>
    </head>
    <body>
        <jsp:include page="header.html"/>

        <div class="bodyPage">
            <div class="categoryTrending">
                
            </div>
        </div>

        <jsp:include page="footer.html" />
    </body>

    <script>
        var selectedCategoryname = '${requestScope.CategoryName}';
        var xmlCategoriesString = '${requestScope.CATEGORIES}';
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(xmlCategoriesString, "text/xml");
        var categories = xmlDoc.getElementsByTagName("ns2:Category");
        loadCategories(categories);
        
        var divTrending = document.getElementsByClassName('categoryTrending')[0];
        var pTag = document.createElement('p');
        pTag.innerHTML = selectedCategoryname;
        divTrending.appendChild(pTag);
    </script>
</html>
