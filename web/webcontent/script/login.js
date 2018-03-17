/* global Controller, View, Model */
View.buttonLogin = document.getElementById("buttonLogin");
View.buttonRegister = document.getElementById("buttonRegister");
View.divLoginRegister = document.getElementById("divLoginRegister");
View.divInputName = document.getElementsByClassName("divInputName")[0];
View.buttonSubmit = document.getElementById("buttonSubmitLoginRegister");
View.textFullName = document.getElementById("txtFullName");
Model.isLogin = false;

Controller.onButtonLoginPress = function(){
    View.divInputName.style.display = "none";
    View.divLoginRegister.style.display = "block";
    View.buttonSubmit.innerHTML = "Đăng nhập";
    View.buttonSubmit.value = "Login";
    View.textFullName.required = false;
    Model.isLogin = true;
};
Controller.onButtonRegisterPress = function(){
    View.divInputName.style.display = "block";
    View.divLoginRegister.style.display = "block";
    View.buttonSubmit.innerHTML = "Đăng ký";
    View.buttonSubmit.value = "Register";
    View.textFullName.required = true;
    Model.isLogin = false;
};

Controller.closeModalLogin = function(){
    View.divLoginRegister.style.display = "none";
};

Controller.onButtonSubmit = function(){
    alert('submit');
};

