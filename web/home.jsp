<%-- 
    Document   : index
    Created on : Mar 8, 2018, 10:30:34 AM
    Author     : PhuongNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
                        
        </div>
        
        <jsp:include page="footer.html" />
    </body>

    <script>
        var xmlString = '${requestScope.CATEGORIES}';
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(xmlString, "text/xml");
        var categories = xmlDoc.getElementsByTagName("ns2:Category");
        loadCategories(categories);

        function loadCategories(categories) {
            var divCategories = document.getElementsByClassName("categories")[0];
            for (var i = 0; i < categories.length; i++) {
                var category = categories[i];
                var categoryName = category.childNodes[0];
                var categoryId = category.getAttribute("CategoryId");

                var divCategory = document.createElement("div");
                divCategory.setAttribute("class", "category");
                var divContent = document.createElement("div");
                divCategories.appendChild(divContent);
                divContent.appendChild(divCategory);

                divCategory.innerHTML = categoryName.childNodes[0].nodeValue;
                divCategory.setAttribute("CategoryID", categoryId);
            }
        }
    </script>
</html>
