/* global Controller, View, Model */
//BEGIN HANDLE SEARCH
Controller.onSearchButtonClick = function () {
    Model.searchValue = View.txtSearchVaue.value.trim();
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
    Model.searchValue = View.txtSearchVaue.value.trim();
    if (Model.searchValue){
        var advantageSearchUrl = 'ProcessServlet?btnAction=advantageSearch&searchValue=' + Model.searchValue;
        window.location.href = advantageSearchUrl;        
    }
};

//END HANDLE SEARCH

