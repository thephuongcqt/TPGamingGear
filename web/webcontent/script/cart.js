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
    var urlLoadProduct = 'ProcessServlet?btnAction=LoadProduct&ProductID=' + productId;
    if (Model.listProducts.has(productId) == false) {
        Controller.getXMLDoc(urlLoadProduct, function (xmlDom) {
            if (xmlDom !== null) {     
                Controller.traversalDOMTreeProducts(xmlDom);
                Controller.syncProductsDomToLocalStorage();
                Controller.addProductToCart(productId);
            } else {
                View.displayAlert('Something went wrong, please try again!', "Shopping Cart Alert", false);
            }
        });
    } else {
        Controller.addProductToCart(productId);        
        Controller.getXMLDoc(urlLoadProduct, function (xmlDom) {
            if (xmlDom !== null) {     
                Controller.traversalDOMTreeProducts(xmlDom);
                Controller.syncProductsDomToLocalStorage();
            }
        });
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
//    View.displayAlert('Add item to cart success', "Shopping Cart Alert", true);
};

Controller.moveToCartDetail = function () {
    var url = "ProcessServlet?btnAction=ShowCartDetail";
    window.location.href = url;
};