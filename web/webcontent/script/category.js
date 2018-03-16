/* global Controller, View, Model */
Controller.displayListProducts = function (node) {
    var trendingXSLUrl = Model.constant.urlXSLCategory;
    Controller.getXMLDoc(trendingXSLUrl, function (xsl) {
        var xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        var resultDocument = xsltProcessor.transformToFragment(node, document);
        View.divGridContainer.appendChild(resultDocument);
    });
};

Controller.onLoadMoreClick = function () {
    View.setLoadMoreText("Loading...");
    var ajaxLoadMoreUrl = 'ProcessServlet?btnAction=LoadMore&categoryID=' + Model.currentCategoryID + '&page=' + (Model.currentPage + 1);
    Controller.getXMLDoc(ajaxLoadMoreUrl, function (xmlDom) {
        if (xmlDom !== null) {
            Controller.displayListProducts(xmlDom);
            Model.currentPage += 1;
            Controller.traversalDOMTreeCategories(xmlDom);
            Controller.syncProductsDomToLocalStorage();
        } else {
            alert('Something went wrong, please try again!');
        }
        View.setLoadMoreText("Load more");
        if (Model.currentPage * 8 >= Model.productCounter) {
            View.hideButtonLoadMore();
        }
    });
};

Controller.traversalDOMTreeCategories = function (node) {
    if (node === null) {
        return;
    }
    if (node.localName === 'ProductType') {
        //begin product node
        var product = {};
        product.productID = node.getAttribute('ProductID');
        product.categoryID = node.getAttribute('CategoryID');
        product.ssActive = node.getAttribute('IsActive');
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
//        View.addProductToGrid(product);
        Controller.addProductToModel(product);
    } else {
        var childs = node.childNodes;
        for (var i = 0; i < childs.length; i++) {
            Controller.traversalDOMTreeCategories(childs[i]);
        }
    }
};

