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
            <div class="main-nav">
                <button id="buttonLogin" onclick="return Controller.onButtonLoginPress()">Đăng nhập</button>
                <button id="buttonRegister" onclick="return Controller.onButtonRegisterPress()">Đăng ký</button>
            </div>

            <button type="submit" class="buttonShoppingCart" onclick="Controller.moveToCartDetail()">
                <i class="fa fa-shopping-cart"></i>
            </button>

        </div>
    </div>	
    <div class="categories">

    </div>

    <div id="divLoginRegister" class="modal">
        <span onclick="return Controller.closeModalLogin()" class="close" title="Close Modal">&times;</span>
        <form id="FormLoginRegister" action="ProcessServlet" class="modal-content animate" method="POST">
            <div class="container">
                <p>Đăng nhập</p>
                
                <input id="inputHiddenButtonAction" type="hidden" name="btnAction" value="Login">
                
                <label for="txtEmail"><b>Email</b></label>
                <input type="email" placeholder="Nhập Email" name="txtEmail" required>

                <label for="txtPassword"><b>Mật khẩu</b></label>
                <input type="password" placeholder="Nhập mật khẩu" name="txtPassword" required>
                
                <div class="divInputName">
                    <label for="txtFullName"><b>Họ và tên</b></label>
                    <input id="txtFullName" type="text" placeholder="Nhập họ và tên" name="txtFullName" required>
                </div>
                
                <button type="submit" id="buttonSubmitLoginRegister">Gửi</button>
                <label>
                    <input type="checkbox" checked="checked" name="remember"> Nhớ mập khẩu
                </label>
            </div>

            <div class="container" style="background-color:#f1f1f1">
                <button type="button" class="cancelbtn" onclick="return Controller.closeModalLogin()">Hủy</button>
                <span class="psw">Quên <a href="#">Mật khẩu?</a></span>
            </div>
        </form>
    </div>
</div>