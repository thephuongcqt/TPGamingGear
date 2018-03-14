var Model = {};
var Controller = {};
var View = {};

//Begin Initialize Control
View.txtSearchVaue = document.getElementsByClassName('searchTerm')[0];
View.divGridContainer = document.getElementsByClassName('gridContainer')[0];
//End Initialize control

View.hideButtonLoadMore = function(){
    View.divLoadMore.style.display = "none";
};
View.showButtonLoadMore = function(){
    View.divLoadMore.style.display = "block";
}

Controller.onCategoryClick = function (categoryId) {
    var url = 'ProcessServlet?btnAction=loadCategory&categoryID=' + categoryId;
    window.location.href = url;
};

Controller.loadCategories = function(categories){
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

Controller.getXmlHttpObject = function(){
    var xmlHttp = null;
    try{
        xmlHttp = new XMLHttpRequest();        
    } catch(e){
        try{
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch(e){
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
};

Controller.onSearchButtonClick = function(){
    var searchValue = View.txtSearchVaue.value;
    if(searchValue){
        Controller.removeAllChilds(View.divGridContainer);
        Controller.searchByProductName(searchValue);
    }
    View.hideButtonLoadMore();
};

Controller.searchByProductName = function(searchValue){
    for(var i = 0; i < Model.listProducts.length; i++){
        var currentProduct = Model.listProducts[i];
        var productName = currentProduct.productName;
        if(productName.indexOf(searchValue) !== -1){
            Controller.addProductToGrid(currentProduct);
        }
    }
};

Controller.addProductToModel = function(product){
    if(Model.listProducts == null){
        Model.listProducts = [];
    }
    Model.listProducts.push(product);
};

Controller.removeAllChilds = function(node){
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
    divProductName.setAttribute("class", "productName")
    var divPrice = document.createElement("div");
    divPrice.setAttribute("class", "productPrice");

    imgThumbnail.setAttribute("src", product.thumbnail);
    divProductName.innerHTML = product.productName;
    divPrice.innerHTML = product.price;

    divGridProductItem.appendChild(imgThumbnail);
    divGridProductItem.appendChild(divProductName);
    divGridProductItem.appendChild(divPrice);
    View.divGridContainer.appendChild(divGridProductItem)
};