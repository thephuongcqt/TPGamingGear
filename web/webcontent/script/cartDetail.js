/* global Controller, View, Model */
//Controller.createXMLDocument = function(initString){
//    var resultDocument = Controller.parserXMLFromStringToDOM(initString);
//    return resultDocument;
//};
Controller.displayShoppingCart = function(cartNode){
    var xslUrl = Model.constant.urlXSLCartDetail;
    var htmlFragment = Controller.transformXMLWithXSL(cartNode, xslUrl);
    console.log(htmlFragment);
    View.divBodyPage = document.getElementsByClassName("bodyPage")[0];
    View.divBodyPage.appendChild(htmlFragment);
};

Controller.loadShoppingCartToDOM = function(){
    Controller.loadListProducts();
    Controller.syncCartToModel();
    
    var cartXMLDocument = Controller.parserXMLFromStringToDOM(Model.constant.xmlStringShoppingCartInitialize);
    var rootNode = cartXMLDocument.childNodes[0];
    
    Model.myCart.forEach(function(quantity, productID){
        if(Model.listProducts.has(productID) == true){
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
    Controller.storeXMLDomToLocalStorage(cartXMLDocument, "testCart");
    Controller.displayShoppingCart(cartXMLDocument);
};
Controller.loadShoppingCartToDOM();