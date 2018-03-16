/* global Controller, View, Model */

Controller.addToCart = function(button){
    var divProductItem = button.parentNode.parentNode;
    var productId = divProductItem.getAttribute("ProductID");
    var addProduct = null;
    if(Model.listProducts == null){
        return;
    }
    if(Model.listProducts.has(productId) == false){
//        var newProduct = {};
//        newProduct.productId = productId;
//        newProduct.categoryId = divProductItem.getAttribute("categoryid");
//        newProduct.isActive = divProductItem.getAttribute("isActive");
//        
//        for(var i = 0; i < divProductItem.childNodes.length; i++){
//            var child = divProductItem.childNodes[i];
//            if(child.className == "productName"){
//                newProduct.productName = child.nodeValue;
//            } else if (child.className == "productPrice"){
//                newProduct.productPrice = child.productPrice;                
//            } else if(child.className = "productThumbnail"){
//                newProduct.thumbnail = child.getAttribute("src");
//            }
//        }
    } else{
        addProduct = Model.listProducts.get(productId);        
    }
    if(addProduct == null){
        alert('Something went wrong, Please try again!');
        return;
    }
    Controller.syncCartToModel();
    if(Model.myCart.has(productId)){
        var quantity = Model.myCart.get(productId) + 1;
        Model.myCart.set(productId, quantity);
    } else{
        Model.myCart.set(productId, 1);
    }
    Controller.syncCartToLocalStorage();
    alert('add to cart success');
};

Controller.moveToCartDetail = function(){
    var url = "ProcessServlet?btnAction=ShowCartDetail";
    window.location.href = url;
};

Controller.syncCartToLocalStorage = function(){
    if(Model.myCart == null){
        Model.myCart = new Map();
    }
    localStorage.myCart = JSON.stringify(Array.from(Model.myCart.entries()));
};

Controller.syncCartToModel = function(){
    if(localStorage.myCart == null){
        Model.myCart = new Map();   
        return;
    }
    Model.myCart = new Map(JSON.parse(localStorage.myCart));    
};