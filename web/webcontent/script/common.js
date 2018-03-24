/* global Controller, View, Model */
View.modalAlert = document.getElementById("myModalAlert");
View.spanAlert = document.getElementsByClassName("closeAlert")[0];
View.alertHeader = document.getElementsByClassName("modal-header")[0];
View.titleAlertModal = document.getElementById("titleAlertModal");
View.messageAlertModel = document.getElementById("messageAlertModal");
View.positiveColor = "#5cb85c";
View.negativeColor = "#f41313";


View.closeAlertModal = function(){
    View.modalAlert.style.display = "none";
};

View.displayAlert = function(message, title, isPositiveAlert){
    View.modalAlert.style.display = "block";
    if(message != null){
        View.messageAlertModel.innerHTML = message;
    }
    if(title != null){
        View.titleAlertModal.innerHTML = title;
    }
    if(isPositiveAlert != null && isPositiveAlert == true){
        View.alertHeader.style.backgroundColor = View.positiveColor;
    } else{
        View.alertHeader.style.backgroundColor = View.negativeColor;
    }
};

Controller.checkListProductExpiredDate = function () {
    var expiredDateString = localStorage.getItem(Model.constant.localStorageListProductsExpiredDate);
    var expiredDate = Date.parse(expiredDateString);
    if (new Date() > expiredDate) {
//        localStorage.removeItem(Model.constant.localStoragelistProductsXml);
        localStorage.removeItem(Model.constant.localStorageListProductsExpiredDate);
        return true;
    }
    return false;
};

Controller.setExpiredDateForListProduct = function(){
    var expiredDate = new Date();
    expiredDate.setHours(expiredDate.getHours() + 1);
    localStorage.setItem(Model.constant.localStorageListProductsExpiredDate, expiredDate);
};

View.addProductToGrid = function (product) {
    var divGridProductItem = document.createElement('div');
    divGridProductItem.setAttribute("class", "gridProductItem");
    divGridProductItem.setAttribute("ProductID", product.productID);

    var imgThumbnail = document.createElement("img");
    imgThumbnail.setAttribute("class", "productThumbnail");
    var divProductName = document.createElement("div");
    divProductName.setAttribute("class", "productName");
    var divPrice = document.createElement("div");
    divPrice.setAttribute("class", "productPrice");
    var divLayerBlur = document.createElement("div");
    divLayerBlur.setAttribute("class", "layerBlur");
    var divLayerAddToCart = document.createElement("div");
    divLayerAddToCart.setAttribute("class", "layerAddToCart");
    var buttonAddToCart = document.createElement("button");
    buttonAddToCart.innerHTML = Model.constant.stringButtonAddToCart;
    divLayerAddToCart.appendChild(buttonAddToCart);

    imgThumbnail.setAttribute("src", product.thumbnail);
    divProductName.innerHTML = product.productName;
    divPrice.innerHTML = product.price;

    divGridProductItem.appendChild(imgThumbnail);
    divGridProductItem.appendChild(divProductName);
    divGridProductItem.appendChild(divPrice);
    divGridProductItem.appendChild(divLayerBlur);
    divGridProductItem.appendChild(divLayerAddToCart);
    View.divGridContainer.appendChild(divGridProductItem);
};

//BEGIN Ajax load list product for search in background

Controller.traversalDOMTreeProducts = function (node) {
    if (node === null) {
        return;
    }
    if (node.localName === 'ProductType') {
        //begin product node
        var product = {};
        product.productID = node.getAttribute('ProductID');
        product.categoryID = node.getAttribute('CategoryID');
        product.isActive = node.getAttribute('IsActive');
        var childs = node.childNodes;
        for (var i = 0; i < childs.length; i++) {
            var childNode = childs[i];
            if (childNode.localName === 'ProductName') {
                product.productName = childNode.textContent;
            } else if (childNode.localName === 'Price') {
                product.price = childNode.textContent;
            } else if (childNode.localName === 'Thumbnail') {
                product.thumbnail = childNode.textContent;
            }
        }
//        Controller.addProductToModel(product);
        if (null == Model.listProducts) {
            Model.listProducts = new Map();
        }
        Model.listProducts.set(product.productID, product);        
    } else {
        var childs = node.childNodes;
        for (var i = 0; i < childs.length; i++) {
            Controller.traversalDOMTreeProducts(childs[i]);
        }
    }
};

Controller.syncListProductsToModel = function () {
    var xmlString = localStorage.getItem(Model.constant.localStoragelistProductsXml);
    if (xmlString) {
        if(Controller.checkListProductExpiredDate() == true){
            return false;
        }
        var myXmlDom = Controller.parserXMLFromStringToDOM(xmlString);
        Controller.traversalDOMTreeProducts(myXmlDom);
        return true;
    }
    return false;
};

Controller.syncProductsDomToLocalStorage = function(){
    if(Model.listProducts == null){
        Controller.syncListProductsToModel();
        if(Model.listProducts == null){
            return;
        }
    }   
    
    var currentDoc = Controller.parserXMLFromStringToDOM(Model.constant.xmlStringListProductsInitialize);
    var rootNode = currentDoc.childNodes[0];

    Model.listProducts.forEach(function(product, key){
        var productType = currentDoc.createElementNS("www.products.vn", "ns2:ProductType");
        var productName = currentDoc.createElementNS('www.product.vn', "ProductName");
        var productPrice = currentDoc.createElementNS('www.product.vn', "Price");
        var thumbnail = currentDoc.createElementNS('www.product.vn', "Thumbnail");
        
        productName.appendChild(currentDoc.createTextNode(product.productName));
        productPrice.appendChild(currentDoc.createTextNode(product.price));
        thumbnail.appendChild(currentDoc.createTextNode(product.thumbnail));
        
        productType.setAttribute("ProductID", product.productID);
        productType.setAttribute("CategoryID", product.categoryID);
        productType.setAttribute("IsActive", product.isActive);
        
        productType.appendChild(productName);
        productType.appendChild(productPrice);
        productType.appendChild(thumbnail);
        rootNode.appendChild(productType);        
    });
    
    Controller.storeXMLDomToLocalStorage(currentDoc, Model.constant.localStoragelistProductsXml);
};

Controller.loadListProducts = function () {
    var syncSuccess = Controller.syncListProductsToModel();
    if(syncSuccess == true){
        return;
    }
    var ajaxUrl = 'ProcessServlet?btnAction=LoadListProductForSearch';
    Controller.getXMLDoc(ajaxUrl, function (xmlDoc) {
        Model.xmlDOM = xmlDoc;
        if (Model.xmlDOM !== null) {
            Controller.setExpiredDateForListProduct();
            Controller.traversalDOMTreeProducts(Model.xmlDOM);
            Controller.syncProductsDomToLocalStorage();
        }
    });
};
Controller.loadListProducts();


//END Ajax load list product for search in backgro

//BEGIN Handle category
Controller.onCategoryClick = function (categoryId) {
    var url = 'ProcessServlet?btnAction=loadCategory&categoryID=' + categoryId;
    window.location.href = url;
};
//END Handle category

Controller.addProductToModel = function (product) {
    if (Model.listProducts == null) {
        Controller.syncListProductsToModel();
        if (Model.listProducts == null) {
            return;
        }
    }
    Model.listProducts.set(product.productID, product);
};

Controller.removeAllChilds = function (node) {
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
};

Controller.displayGridProductUsingXSL = function (node, xslUrl, gridRoot, callBackMethod){
    Controller.getXMLDoc(xslUrl, function (xsl) {
        var xsltProcessor = Controller.getXMLTProcessor(xsl);
        var htmlFragment = xsltProcessor.transformToFragment(node, document);
        gridRoot.appendChild(htmlFragment);
        if(callBackMethod != null){
            callBackMethod();
        }
    });
};
