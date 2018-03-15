var Model = {};
var Controller = {};
var View = {};

Model.constant = {};
Model.constant.listProductsXml = "ListProductsXML";
Model.constant.urlXSLSearch = "webcontent/xsl/search.xsl";
Model.constant.stringSearchResultNotMatch = "Không có kết quả phù hợp cho: '";
Model.constant.stringSearchResult = "Kết quả tìm kiếm cho: '";
Model.constant.stringAdvantageSearchResultNotMatch = "Không có kết quả nào phù hợp cho: '";
Model.constant.stringAdvantageSearchResult = "Kết quả tìm kiếm nâng cao cho: '";

//Begin View fragment
View.txtSearchVaue = document.getElementsByClassName('searchTerm')[0];
View.divGridContainer = document.getElementsByClassName('gridContainer')[0];
View.buttonLoadMore = document.getElementsByClassName("textLoadMore")[0];
View.divLoadMore = document.getElementsByClassName("loadMore")[0];
View.divTrending = document.getElementsByClassName('categoryTrending')[0];
View.divAdvantageSearch = document.getElementsByClassName("advantageSearch")[0];
View.pTagTrending = document.createElement('p');
View.divTrending.appendChild(View.pTagTrending);

View.hideButtonLoadMore = function () {
    View.divLoadMore.style.display = "none";
};
View.showButtonLoadMore = function () {
    View.divLoadMore.style.display = "block";
};

View.hideAdvantageSearch = function () {
    View.divAdvantageSearch.style.display = "none";
};

View.showAdvantageSearch = function () {
    View.divAdvantageSearch.style.display = "block";
};

View.hideAdvantageSearch();

View.setLoadMoreText = function (textValue) {
    View.buttonLoadMore.innerHTML = textValue;
};

View.addProductToGrid = function (product) {
    var divGridProductItem = document.createElement('div');
    divGridProductItem.setAttribute("class", "gridProductItem");

    var imgThumbnail = document.createElement("img");
    imgThumbnail.setAttribute("class", "productThumbnail");
    var divProductName = document.createElement("div");
    divProductName.setAttribute("class", "productName");
    var divPrice = document.createElement("div");
    divPrice.setAttribute("class", "productPrice");

    imgThumbnail.setAttribute("src", product.thumbnail);
    divProductName.innerHTML = product.productName;
    divPrice.innerHTML = product.price;

    divGridProductItem.appendChild(imgThumbnail);
    divGridProductItem.appendChild(divProductName);
    divGridProductItem.appendChild(divPrice);
    View.divGridContainer.appendChild(divGridProductItem);
};
//End View fragment

//BEGIN Utilities method
Controller.getXmlHttpObject = function () {
    var xmlHttp = null;
    try {
        xmlHttp = new XMLHttpRequest();
    } catch (e) {
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
};

Controller.parserXMLFromStringToDOM = function (xmlString) {
    var parser = new DOMParser();
    var xmlDom = parser.parseFromString(xmlString, "text/xml");
    return xmlDom;
};

Controller.storeXMLDomToLocalStorage = function (dom, id) {
    var xmlSerializer = new XMLSerializer();
    var xmlString = xmlSerializer.serializeToString(dom);
    localStorage.setItem(id, xmlString);
};

Controller.getXMLDoc = function (xmlUrl, callBackMethod) {
    var xmlHttp = Controller.getXmlHttpObject();
    if (xmlHttp === null) {
        console.log('Your browser does not support AJAx');
        return;
    }
    xmlHttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            var xmlDoc = null;
            if (this.status == 200) {
                xmlDoc = xmlHttp.responseXML;
                callBackMethod(xmlDoc);
            } else {
                console.log('Load list products fail');
                callBackMethod(null);
            }
        }
    };
    xmlHttp.open("GET", xmlUrl, true);
    xmlHttp.send();
};

//End Utilities method

//BEGIN Ajax load list product for search in background
Controller.loadListProducts = function () {
    var xmlString = localStorage.getItem(Model.constant.listProductsXml);
    if (xmlString) {
        return;
    }
    var ajaxUrl = 'ProcessServlet?btnAction=LoadListProductForSearch';
    Controller.getXMLDoc(ajaxUrl, function (xmlDoc) {
        Model.xmlDOM = xmlDoc;
        if (Model.xmlDOM !== null) {
            Controller.storeXMLDomToLocalStorage(Model.xmlDOM, Model.constant.listProductsXml);
            Controller.traversalDOMTreeProducts(Model.xmlDOM);
        }
    });

};
Controller.loadListProducts();

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
        if (Model.listProducts.has(product.productID) == false) {
            Model.listProducts.set(product.productID, product);
        }
    } else {
        var childs = node.childNodes;
        for (var i = 0; i < childs.length; i++) {
            Controller.traversalDOMTreeProducts(childs[i]);
        }
    }
};

Controller.syncListProductsToModel = function () {
    var xmlString = localStorage.getItem(Model.constant.listProductsXml);
    if (xmlString) {
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
    var xmlRootString = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns2:Products xmlns="www.product.vn" xmlns:ns2="www.products.vn"></ns2:Products>';
    var currentDoc = Controller.parserXMLFromStringToDOM(xmlRootString);
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
    
    Controller.storeXMLDomToLocalStorage(currentDoc, Model.constant.listProductsXml);
};
//END Ajax load list product for search in backgro

//BEGIN Handle category
Controller.onCategoryClick = function (categoryId) {
    var url = 'ProcessServlet?btnAction=loadCategory&categoryID=' + categoryId;
    window.location.href = url;
};

Controller.loadCategories = function (categories) {
    var divCategories = document.getElementsByClassName("categories")[0];
    for (var i = 0; i < categories.length; i++) {
        var category = categories[i];
        var categoryNameTag = category.childNodes[0];
        var categoryId = category.getAttribute("CategoryId");

        var divCategory = document.createElement("div");
        divCategory.setAttribute("class", "category");
        var divContent = document.createElement("div");
        divCategories.appendChild(divContent);
        divContent.appendChild(divCategory);

        var categoryName = categoryNameTag.childNodes[0].nodeValue;
        divCategory.innerHTML = categoryName;
        divCategory.setAttribute("CategoryID", categoryId);
        divContent.addEventListener('click', function () {
            Controller.onCategoryClick(this.childNodes[0].getAttribute("CategoryId"));
        });
    }
};
//END Handle category

//BEGIN HANDLE SEARCH
Controller.onSearchButtonClick = function () {
    Model.searchValue = View.txtSearchVaue.value;
    if (Model.searchValue) {
        var xmlString = localStorage.getItem(Model.constant.listProductsXml);
        if (xmlString) {
            //get xslt file
            var xsltUrl = Model.constant.urlXSLSearch;
            Controller.getXMLDoc(xsltUrl, function (xsl) {
                var xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(xsl);

                var node = Controller.parserXMLFromStringToDOM(xmlString);
                xsltProcessor.setParameter(null, "searchValue", Model.searchValue.toLowerCase());
                var resultDocument = xsltProcessor.transformToFragment(node, document);

                Controller.removeAllChilds(View.divGridContainer);
                if (resultDocument) {
                    View.divGridContainer.appendChild(resultDocument);
                }
                if (View.divLoadMore != null) {
                    View.hideButtonLoadMore();
                }
                if (View.divGridContainer.childNodes.length === 0) {
                    View.pTagTrending.innerHTML = Model.constant.stringSearchResultNotMatch + Model.searchValue + "'";
                } else {
                    View.pTagTrending.innerHTML = Model.constant.stringSearchResult + Model.searchValue + "'";
                }
                View.showAdvantageSearch();
            });
        } else {
            Controller.loadListProducts();
        }

    }
};

Controller.onKeyPressSearchValue = function (event) {
    if (event.keyCode == 13) {
        Controller.onSearchButtonClick();
    }
};

Controller.onAdvantageSearchClick = function () {
    Model.searchValue = View.txtSearchVaue.value;
    var advantageSearchUrl = 'ProcessServlet?btnAction=advantageSearch&searchValue=' + Model.searchValue;
    window.location.href = advantageSearchUrl;
};

//END HANDLE SEARCH

Controller.addProductToModel = function (product) {
    if (Model.listProducts == null) {
        Controller.syncListProductsToModel();
        if (Model.listProducts == null) {
            return;
        }
    }
    if (Model.listProducts.has(product.productID) == false) {
        Model.listProducts.set(product.productID, product);
    }
};



Controller.removeAllChilds = function (node) {
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
};

