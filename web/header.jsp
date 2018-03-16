<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="header">
    <div class="topFragment">
        <div class="logoBlock">
            <a href="ProcessServlet">
                <img src="webcontent/images/logo.png">
            </a>
        </div>
        <div class="searchBlock">
            <div class="search">
                <input type="text" class="searchTerm" placeholder="Nhập tên sản phẩm bạn muốn tìm kiếm..." onkeypress="Controller.onKeyPressSearchValue(event)">
                <button type="submit" class="searchButton" onclick="Controller.onSearchButtonClick()">
                    <i class="fa fa-search"></i>
                </button>
            </div>
        </div>
        <div class="topRightBlock">
            <button type="submit" class="buttonShoppingCart" onclick="Controller.moveToCartDetail()">
                <i class="fa fa-shopping-cart"></i>
            </button>
        </div>
    </div>	
    <div class="categories">

    </div>
</div>