var Model = {};
var Controller = {};
var View = {};

Model.constant = {};
Model.constant.listProductsXml = "ListProductsXML";
Model.constant.urlXSLSearch = "webcontent/xsl/search.xsl";

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

View.hideAdvantageSearch = function(){
    View.divAdvantageSearch.style.display = "none";
};

View.showAdvantageSearch = function(){
    View.divAdvantageSearch.style.display = "block";
};

View.hideAdvantageSearch();

View.setLoadMoreText = function (textValue) {
    View.buttonLoadMore.innerHTML = textValue;
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
        }
    });

};
Controller.loadListProducts();

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
                xsltProcessor.setParameter(null, "searchValue", Model.searchValue);
                var resultDocument = xsltProcessor.transformToFragment(node, document);

                Controller.removeAllChilds(View.divGridContainer);
                View.divGridContainer.appendChild(resultDocument);
                if(View.divLoadMore != null){
                    View.hideButtonLoadMore();
                }
                if(View.divGridContainer.childNodes.length === 0){
                    View.pTagTrending.innerHTML = "Không có kết quả phù hợp cho: '" + Model.searchValue + "'";
                } else{
                    View.pTagTrending.innerHTML = "Kết quả tìm kiếm cho: '" + Model.searchValue + "'";                    
                }
                View.showAdvantageSearch();
            });
        } else {
            Controller.loadListProducts();
        }

    }
};

Controller.onAdvantageSearchClick = function(){
    Model.searchValue = View.txtSearchVaue.value;
    var advantageSearchUrl = 'ProcessServlet?btnAction=advantageSearch&searchValue=' + Model.searchValue;
    window.location.href = advantageSearchUrl;
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