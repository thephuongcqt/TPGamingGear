var Model = {};
var Controller = {};
var View = {};

Model.constant = {};
Model.constant.localStorageUserKey = "currentUserKey";
Model.constant.listProductsXml = "ListProductsXML";
Model.constant.urlXSLSearch = "webcontent/xsl/search.xsl";
Model.constant.urlXSLCategory = "webcontent/xsl/category.xsl";
Model.constant.urlXSLCartDetail = "webcontent/xsl/cartDetail.xsl";

Model.constant.stringSearchResultNotMatch = "Không có kết quả phù hợp cho: '";
Model.constant.stringSearchResult = "Kết quả tìm kiếm cho: '";
Model.constant.stringAdvantageSearchResultNotMatch = "Không có kết quả nào phù hợp cho: '";
Model.constant.stringAdvantageSearchResult = "Kết quả tìm kiếm nâng cao cho: '";
Model.constant.stringButtonAddToCart = "Add to cart";
Model.constant.stringYourCart = "Giỏ hàng của bạn";

Model.constant.xmlStringShoppingCartInitialize = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Cart></Cart>';
Model.constant.xmlStringListProductsInitialize = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns2:Products xmlns="www.product.vn" xmlns:ns2="www.products.vn"></ns2:Products>';

Model.myCart = null;

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

//End View fragment