var Model = {};
var Controller = {};
var View = {};

Model.constant = {};
Model.constant.listProductsXml = "ListProductsXML";
Model.constant.urlXSLSearch = "webcontent/xsl/search.xsl";

//Begin View fragment
View.txtSearchVaue = document.getElementsByClassName('searchTerm')[0];
View.divGridContainer = document.getElementsByClassName('gridContainer')[0];

View.hideButtonLoadMore = function () {
    View.divLoadMore.style.display = "none";
};
View.showButtonLoadMore = function () {
    View.divLoadMore.style.display = "block";
};

View.setLoadMoreText = function (textValue) {
    View.buttonLoadMore.innerHTML = textValue;
};
//End View fragment

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

Controller.getXMLDoc = function (url, callBackMethod) {
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
    xmlHttp.open("GET", url, true);
    xmlHttp.send();
};

Controller.loadListProducts = function () {
    var xmlString = localStorage.getItem(Model.constant.listProductsXml);
    if (xmlString) {
        return;
    }
    var url = 'ProcessServlet?btnAction=LoadMore&categoryID=de2d566e-b970-4a6a-9ba5-1d4d95f0ea2e&page=2';
    Controller.getXMLDoc(url, function (xmlDoc) {
        Model.xmlDOM = xmlDoc;
        if (Model.xmlDOM !== null) {
            Controller.storeXMLDomToLocalStorage(Model.xmlDOM, Model.constant.listProductsXml);
        }
    });

};
Controller.loadListProducts();

Controller.onCategoryClick = function (categoryId) {
    var url = 'ProcessServlet?btnAction=loadCategory&categoryID=' + categoryId;
    window.location.href = url;
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

Controller.onSearchButtonClick = function () {
    var searchValue = View.txtSearchVaue.value;
    if (searchValue) {
        var xmlString = localStorage.getItem(Model.constant.listProductsXml);
        if (xmlString) {
            console.log(xmlString);
            //get xslt file
            var xsltUrl = Model.constant.urlXSLSearch;
            Controller.getXMLDoc(xsltUrl, function (xsl) {
                var xsltProcessor = new XSLTProcessor();
                xsltProcessor.importStylesheet(xsl);
                
                var node = Controller.parserXMLFromStringToDOM(xmlString);
                xsltProcessor.setParameter(null, "searchValue", searchValue);
                var resultDocument = xsltProcessor.transformToFragment(node, document);
                
                Controller.removeAllChilds(View.divGridContainer);
                View.divGridContainer.appendChild(resultDocument);
//                View.hideButtonLoadMore();
            });
        } else {
            Controller.loadListProducts();
        }

    }
};

Controller.searchByProductName = function (searchValue) {
    for (var i = 0; i < Model.listProducts.length; i++) {
        var currentProduct = Model.listProducts[i];
        var productName = currentProduct.productName;
        if (productName.indexOf(searchValue) !== -1) {
            Controller.addProductToGrid(currentProduct);
        }
    }
};

Controller.addProductToModel = function (product) {
    if (null == Model.listProducts) {
        Model.listProducts = [];
    }
    Model.listProducts.push(product);
};

Controller.removeAllChilds = function (node) {
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
};

Controller.addProductToGrid = function (product) {
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