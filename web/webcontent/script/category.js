/* global Controller, View, Model */
Controller.onLoadMoreClick = function () {
    View.setLoadMoreText("Loading...");
    var ajaxLoadMoreUrl = 'ProcessServlet?btnAction=LoadMore&categoryID=' + Model.currentCategoryID + '&page=' + (Model.currentPage + 1);
    Controller.getXMLDoc(ajaxLoadMoreUrl, function (xmlDom) {
        if (xmlDom !== null) {
            Controller.displayGridProductUsingXSL(xmlDom, Model.constant.urlXSLCategory, View.divGridContainer);
            Model.currentPage += 1;
            Controller.traversalDOMTreeCategories(xmlDom);
            Controller.syncProductsDomToLocalStorage();
        } else {
            View.displayAlert("Something went wrong, please try again!", "Network Error", false);
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

