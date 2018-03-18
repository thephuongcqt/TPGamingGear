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

Controller.checkOut = function () {
    var isLoggedIn = Controller.checkLogin();
    if (isLoggedIn == false) {
        Controller.onButtonLoginPress();
        return;
    }
    var myData = {};
    myData.cartXml = Model.cartXMLString;
    $.ajax({
        url: 'CheckOutServlet',
        processData: false,
        type: "POST", // type should be POST
        data: Model.cartXMLString, // send the string directly
        contentType: "text/xml",
        dataType: "xml",
        responseType: "blob",
        success: function (response) {
            console.log(response);
            window.open(escape(response), "Title", "");
        },
        error: function (response) {
            console.log('error: ' + response);
//            window.open(escape(response), "Title", "");
            var filename = "PdfName-" + new Date().getTime() + ".pdf";
            if (typeof window.chrome !== 'undefined') {
                // Chrome version
                var link = document.createElement('a');
//                link.href = window.URL.createObjectURL(response);
                var binaryData = [];
                binaryData.push(response);
                link.href = window.URL.createObjectURL(new Blob(binaryData, {type: "application/zip"}))
                link.download = "PdfName-" + new Date().getTime() + ".pdf";
                link.click();
            } else if (typeof window.navigator.msSaveBlob !== 'undefined') {
                // IE version
                var blob = new Blob([response], {type: 'application/pdf'});
                window.navigator.msSaveBlob(blob, filename);
            } else {
                // Firefox version
                var file = new File([response], filename, {type: 'application/force-download'});
                window.open(URL.createObjectURL(file));
            }
        }
    });
};