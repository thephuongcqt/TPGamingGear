/* global Controller, View, Model */
Controller.syncCartToModel = function () {
    if (localStorage.myCart == null) {
        Model.myCart = new Map();
        return;
    }
    Model.myCart = new Map(JSON.parse(localStorage.myCart));
};

Controller.syncCartToLocalStorage = function () {
    if (Model.myCart == null) {
        Model.myCart = new Map();
    }
    localStorage.myCart = JSON.stringify(Array.from(Model.myCart.entries()));
};


Controller.addToCart = function (button) {
    var divProductItem = button.parentNode.parentNode;
    var productId = divProductItem.getAttribute("ProductID");
    var addProduct = null;
    if (Model.listProducts == null) {
        return;
    }
    if (Model.listProducts.has(productId) == false) {
        var urlLoadProduct = 'ProcessServlet?btnAction=LoadProduct&ProductID=' + productId;
        Controller.getXMLDoc(urlLoadProduct, function (xmlDom) {
            if (xmlDom !== null) {     
                Controller.traversalDOMTreeProducts(xmlDom);
                Controller.syncProductsDomToLocalStorage();
                Controller.addProductToCart(productId);
            } else {
                alert('Something went wrong, please try again!');
            }
        });
    } else {
//        addProduct = Model.listProducts.get(productId);
        Controller.addProductToCart(productId);
    }    
};

Controller.addProductToCart = function(productID){
    Controller.syncCartToModel();
    if (Model.myCart.has(productID)) {
        var quantity = Model.myCart.get(productID) + 1;
        Model.myCart.set(productID, quantity);
    } else {
        Model.myCart.set(productID, 1);
    }
    Controller.syncCartToLocalStorage();
    alert('add to cart success');
};

Controller.moveToCartDetail = function () {
    var url = "ProcessServlet?btnAction=ShowCartDetail";
    window.location.href = url;
};