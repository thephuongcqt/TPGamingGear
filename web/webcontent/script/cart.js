/* global Controller, View, Model */
Controller.syncCartToModel = function(){
    if(localStorage.myCart == null){
        Model.myCart = new Map();   
        return;
    }
    Model.myCart = new Map(JSON.parse(localStorage.myCart));    
};

Controller.syncCartToLocalStorage = function(){
    if(Model.myCart == null){
        Model.myCart = new Map();
    }
    localStorage.myCart = JSON.stringify(Array.from(Model.myCart.entries()));
};


Controller.addToCart = function(button){
    var divProductItem = button.parentNode.parentNode;
    var productId = divProductItem.getAttribute("ProductID");
    var addProduct = null;
    if(Model.listProducts == null){
        return;
    }
    if(Model.listProducts.has(productId) == false){
        
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