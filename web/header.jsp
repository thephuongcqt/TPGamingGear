<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="header">
    <div class="topFragment">
        <div class="logoBlock">
            <a href="ProcessServlet">
                <img src="webcontent/images/logo.png">
            </a>
        </div>
        <div class="searchBlock">
            <div class="search">
                <input type="text" class="searchTerm" placeholder="Nhập tên sản phẩm bạn muốn tìm kiếm...">
                <button type="submit" class="searchButton" onclick="Controller.onSearchButtonClick()">
                    <i class="fa fa-search"></i>
                </button>
            </div>
        </div>
        <div class="topRightBlock">

        </div>
    </div>	
    <div class="categories">

    </div>
</div>