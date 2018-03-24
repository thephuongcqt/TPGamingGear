/* global Controller, View, Model */
var userNamespace = "www.user.vn";
var itemNamespace = "www.cartitem.vn";
var cartNamespace = "www.cart.vn";
var orderNamespace = "www.order.vn";

View.txtAddress = document.getElementById("txtAddress");
View.txtPhoneNumber = document.getElementById("txtPhoneNumber");
View.formOrderDetailInformation = document.getElementById("formOrderDetailInformation");

View.showDetailInformation = function(){
    View.divOrderDetailInformation.style.display = "block";
};

View.hideDetailInformationModal = function(){
    View.divOrderDetailInformation.style.display = "none";
};

Controller.closeOrderDetailInformationModel = function(){
    View.hideDetailInformationModal();
};

Controller.updateGrandTotal = function () {
    var totalGrand = 0;
    Model.myCart.forEach(function (quantity, productID) {
        var currentProduct = Model.listProducts.get(productID);
        totalGrand += (quantity * currentProduct.price);
    });

    View.divGrandTotal = document.getElementById("cart-total");
    View.divGrandTotal.innerHTML = Controller.getFormattedNumber(totalGrand.toString());
};

Controller.loadShoppingCartToDOM = function () {
    Controller.loadListProducts();
    Controller.syncCartToModel();

    var cartXMLDocument = Controller.parserXMLFromStringToDOM(Model.constant.xmlStringShoppingCartInitialize);
    var rootNode = cartXMLDocument.childNodes[0];

    Model.myCart.forEach(function (quantity, productID) {
        if (Model.listProducts.has(productID) == true) {
            var currentProduct = Model.listProducts.get(productID);

            var cartItemNode = cartXMLDocument.createElement("CartItem");
            cartItemNode.setAttribute("ProductID", productID);

            var productNameNode = cartXMLDocument.createElement("ProductName");
            var PriceNode = cartXMLDocument.createElement("Price");
            var thumbnailNode = cartXMLDocument.createElement("Thumbnail");
            var quantityNode = cartXMLDocument.createElement("Quantity");

            productNameNode.appendChild(cartXMLDocument.createTextNode(currentProduct.productName));
            PriceNode.appendChild(cartXMLDocument.createTextNode(currentProduct.price));
            thumbnailNode.appendChild(cartXMLDocument.createTextNode(currentProduct.thumbnail));
            quantityNode.appendChild(cartXMLDocument.createTextNode(quantity));

            cartItemNode.appendChild(productNameNode);
            cartItemNode.appendChild(PriceNode);
            cartItemNode.appendChild(thumbnailNode);
            cartItemNode.appendChild(quantityNode);

            rootNode.appendChild(cartItemNode);
        }
    });

    var xmlSerializer = new XMLSerializer();
    Model.cartXMLString = xmlSerializer.serializeToString(cartXMLDocument);

    Controller.displayGridProductUsingXSL(cartXMLDocument, Model.constant.urlXSLCartDetail, document.getElementsByClassName("bodyPage")[0], Controller.updateGrandTotal);
};
Controller.loadShoppingCartToDOM();

Controller.removeItemFromCart = function (button, productID) {
    var divCartItem = button.parentNode.parentNode;
    var divCartContainer = divCartItem.parentNode;
    divCartContainer.removeChild(divCartItem);
    if (Model.myCart.has(productID.toString()) == true) {
        Model.myCart.delete(productID.toString());
        Controller.syncCartToLocalStorage();
        Controller.updateGrandTotal();
    }
    Controller.updateCartNumberItems();
};

Controller.onQuantityChange = function (inputNode, productID) {
    var currentProduct = Model.listProducts.get(productID.toString());
    var quantity = inputNode.value;
    Model.myCart.set(productID.toString(), quantity);
    Controller.syncCartToLocalStorage();

    var intPrice = parseInt(currentProduct.price) * quantity;
    var linePrice = Controller.getFormattedNumber(intPrice.toString());

    var divProductLinePrice = inputNode.parentNode.parentNode.getElementsByClassName("product-line-price")[0];
    divProductLinePrice.innerHTML = linePrice;
    Controller.updateGrandTotal();
};

function getOrderXMLString(address, phoneNumber) {
//    var orderXMLStringInit = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Order xmlns="www.order.vn"></Order>';
//    var orderXMLDocument = Controller.parserXMLFromStringToDOM(orderXMLStringInit);
    var orderXMLDocument = Controller.parserXMLFromStringToDOM(Model.constant.xmlStringOrderInitialize);
    //Begin Create User node
    var userXMLDocument = Controller.parserXMLFromStringToDOM(localStorage.currentUserKey);
    var userTypeNode = userXMLDocument.childNodes[0];

    var userRootNode = userXMLDocument.createElementNS(userNamespace, "UserType");
    var fullNameNode = userXMLDocument.createElementNS(userNamespace, "FullName");
    var passwordNode = userXMLDocument.createElementNS(userNamespace, "Password");
    var roleNode = userXMLDocument.createElementNS(userNamespace, "Role");

    userRootNode.setAttribute("Email", userTypeNode.getAttribute("Email"));
    userRootNode.setAttribute("IsActive", userTypeNode.getAttribute("IsActive"));
    fullNameNode.appendChild(userXMLDocument.createTextNode(userXMLDocument.getElementsByTagName("FullName")[0].textContent));
    passwordNode.appendChild(userXMLDocument.createTextNode(userXMLDocument.getElementsByTagName("Password")[0].textContent));
    roleNode.appendChild(userXMLDocument.createTextNode(userXMLDocument.getElementsByTagName("Role")[0].textContent));

    userRootNode.appendChild(fullNameNode);
    userRootNode.appendChild(passwordNode);
    userRootNode.appendChild(roleNode);
    //End Create User node
    var orderRootNode = orderXMLDocument.childNodes[0];
    var rootNode = orderXMLDocument.createElementNS(cartNamespace, "Cart");
    orderRootNode.appendChild(userRootNode);
    orderRootNode.appendChild(rootNode);
    
    var emailNode = orderXMLDocument.createElementNS(orderNamespace, "Address");
    var phoneNode = orderXMLDocument.createElementNS(orderNamespace, "PhoneNumber");
    emailNode.appendChild(orderXMLDocument.createTextNode(address));
    phoneNode.appendChild(orderXMLDocument.createTextNode(phoneNumber));
    orderRootNode.appendChild(emailNode);
    orderRootNode.appendChild(phoneNode);
    
    Model.myCart.forEach(function (quantity, productID) {
        if (Model.listProducts.has(productID) == true) {
            var currentProduct = Model.listProducts.get(productID);

            var cartItemNode = orderXMLDocument.createElementNS(itemNamespace, "CartItem");
            cartItemNode.setAttribute("ProductID", productID);

            var productNameNode = orderXMLDocument.createElementNS(itemNamespace, "ProductName");
            var PriceNode = orderXMLDocument.createElementNS(itemNamespace, "Price");
            var thumbnailNode = orderXMLDocument.createElementNS(itemNamespace, "Thumbnail");
            var quantityNode = orderXMLDocument.createElementNS(itemNamespace, "Quantity");

            productNameNode.appendChild(orderXMLDocument.createTextNode(currentProduct.productName));
            PriceNode.appendChild(orderXMLDocument.createTextNode(currentProduct.price));
            thumbnailNode.appendChild(orderXMLDocument.createTextNode(currentProduct.thumbnail));
            quantityNode.appendChild(orderXMLDocument.createTextNode(quantity));

            cartItemNode.appendChild(productNameNode);
            cartItemNode.appendChild(PriceNode);
            cartItemNode.appendChild(thumbnailNode);
            cartItemNode.appendChild(quantityNode);

            rootNode.appendChild(cartItemNode);
        }
    });
    var xmlSerializer = new XMLSerializer();
    Model.myOrder = xmlSerializer.serializeToString(orderXMLDocument);
};

Model.isSendingOrder = false;

Controller.sendOrderDataToServer = function(address, phoneNumber){
    Model.isSendingOrder = true;
    
    getOrderXMLString(address, phoneNumber);
    var xmlHttp = Controller.getXmlHttpObject();
    if (xmlHttp === null) {
        console.log('Your browser does not support AJAx');
        return;
    }
    xmlHttp.responseType = "blob";
    xmlHttp.onreadystatechange = function (event) {
        if (this.readyState == 4) {
            Model.isSendingOrder = false;
            if (this.status == 200) {
                var response = event.target.response;
                var file = new Blob([response], {type: 'application/pdf'});
                var fileURL = window.URL.createObjectURL(file);
                var win = window.open(fileURL, '_blank');
                if (win) {

                } else {
                    //browser don't support open new tab
                    var link = document.createElement('a');
                    link.href = fileURL;
                    link.download = "TPGamingGear-Sales-Invoices-" + new Date().getTime();
                    link.click();
                }
                Controller.handleCheckedOut();
            } else {
                console.log('Load list products fail');
            }
        }
    };
    xmlHttp.open("POST", Model.constant.servletCheckOut, true);
    xmlHttp.send(Model.myOrder);
};

Controller.checkOut = function () {
    var isLoggedIn = Controller.checkLogin();
    if (isLoggedIn == false) {
        Controller.onButtonLoginPress();
        return;
    }
    View.formOrderDetailInformation.reset();
    View.showDetailInformation();
};

Controller.handleCheckedOut = function () {
    View.hideDetailInformationModal();
    localStorage.removeItem("myCart");
    window.location.href = "ProcessServlet";
};

Controller.onOrderDetailInformationSubmit = function(){
    if(Model.isSendingOrder){
        return false;
    }
    Controller.sendOrderDataToServer(View.txtAddress.value.trim(), View.txtPhoneNumber.value.trim());
    return false;
};