<%-- 
    Document   : ControlCrawling
    Created on : Mar 23, 2018, 3:15:46 PM
    Author     : PhuongNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Control Crawling</title>
        <link rel="stylesheet" type="text/css" href="webcontent/css/home.css">
        <link rel="stylesheet" type="text/css" href="webcontent/css/login.css"/>
        <link rel="stylesheet" type="text/css" href="webcontent/font-awesome/css/font-awesome.min.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="bodyPage">
            <button id="btn-stop-resume">Stop</button>
        </div>  
        <jsp:include page="footer.html" />
    </body>
    <script>
        var isSuspended = '${requestScope.IsSuspended}';
    </script>
    <script src="webcontent/font-awesome/jquery-min.js" type="text/javascript"></script>
    <script src="webcontent/script/mvc.js" type="text/javascript"></script>
    <script src="webcontent/script/utilities.js" type="text/javascript"></script>
    <script src="webcontent/script/common.js" type="text/javascript"></script>
    <script src="webcontent/script/search.js" type="text/javascript"></script>
    <script src="webcontent/script/cart.js" type="text/javascript"></script>
    <script src="webcontent/script/login.js" type="text/javascript"></script>
    <script src="webcontent/script/controlCrawling.js" type="text/javascript"></script>
    <script src="webcontent/script/lastScript.js" type="text/javascript"></script>
</html>
