/* global Controller, View, Model */

Controller.onLoadMoreClick = function () {
    View.buttonLoadMore.innerHTML = "Loading...";
    var xmlHttp = Controller.getXmlHttpObject();
    if (xmlHttp == null) {
        alert('Your browser does not support AJAx');
        return;
    }
    var url = 'ProcessServlet?btnAction=LoadMore&categoryID=' + Model.currentCategoryID + '&page=' + (Model.currentPage + 1);
    xmlHttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                Model.xmlDOM = xmlHttp.responseXML;
                if (Model.xmlDOM != null) {
                    Model.currentPage += 1;
//                if(Model.xmlDOM.parseError.errorCode != 0){
//                    alert('Error: ' + Model.xmlDOM.parseError.reason);
//                } else{
//                }
                    Controller.traversalDOMTree(Model.xmlDOM);
                } else{
                    alert('Something went wrong, please try again!');
                }

            } else {
                alert('Something went wrong, please try again!');
            }
            View.buttonLoadMore.innerHTML = "load More";
            if (Model.currentPage * 8 >= Model.productCounter) {
                View.hideButtonLoadMore();
            }
        }
    };
    xmlHttp.open("GET", url, true);
    xmlHttp.send();
};

Controller.traversalDOMTree = function(node) {
    if (node == null) {
        return;
    }
    if (node.localName == 'ProductType') {
        //begin product node
        var product = {};
        var childs = node.childNodes;
        for (var i = 0; i < childs.length; i++) {
            var childNode = childs[i];
            if (childNode.localName == 'ProductName') {
                product.productName = childNode.textContent;
            } else if (childNode.localName == 'Price') {
                product.price = childNode.textContent;
            } else if (childNode.localName == 'Thumbnail') {
                product.thumbnail = childNode.textContent;
            }
        }
        Controller.addProductToGrid(product);
        Controller.addProductToModel(product);
    }
    var childs = node.childNodes;
    for (var i = 0; i < childs.length; i++) {
        Controller.traversalDOMTree(childs[i]);
    }
};

