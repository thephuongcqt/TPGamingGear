/* global Controller, View, Model */

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

Controller.checkOut = function(){
  alert('this time to check out');  
};