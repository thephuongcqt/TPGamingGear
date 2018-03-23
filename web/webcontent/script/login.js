/* global Controller, View, Model */
View.buttonLogin = document.getElementById("buttonLogin");
View.buttonRegister = document.getElementById("buttonRegister");
View.divLoginRegister = document.getElementById("divLoginRegister");
View.divInputName = document.getElementsByClassName("divInputName")[0];
View.buttonSubmit = document.getElementById("buttonSubmitLoginRegister");
View.textFullName = document.getElementById("txtFullName");
View.formLoginRegister = $("#FormLoginRegister");
View.inputBtnAction = document.getElementById("inputHiddenButtonAction");
View.divNotLoggedIn = document.getElementById("nav-not-logged-in");
View.divLoggedIn = document.getElementById("nav-logged-in");
View.pUserFullName = document.getElementById("user-full-name");
View.divOrderDetailInformation = document.getElementById("divOrderDetailInformation");

Model.isLogin = false;

View.displayWhenLoggedIn = function () {
    if (View.divNotLoggedIn != null) {
        View.divNotLoggedIn.style.display = "none";
    }
    if (View.divLoggedIn != null) {
        View.divLoggedIn.style.display = "block";
    }
};

View.displayWhenNotLoggedIn = function () {
    if (View.divNotLoggedIn != null) {
        View.divNotLoggedIn.style.display = "block";
    }
    if (View.divLoggedIn != null) {
        View.divLoggedIn.style.display = "none";
    }
};

Controller.onButtonLoginPress = function () {
    View.divInputName.style.display = "none";
    View.divLoginRegister.style.display = "block";
    View.buttonSubmit.innerHTML = "Đăng nhập";
    View.textFullName.required = false;
    View.inputBtnAction.value = "Login";
    Model.isLogin = true;
};
Controller.onButtonRegisterPress = function () {
    View.divInputName.style.display = "block";
    View.divLoginRegister.style.display = "block";
    View.buttonSubmit.innerHTML = "Đăng ký";
    View.inputBtnAction.value = "Register";
    View.textFullName.required = true;
    Model.isLogin = false;
};

Controller.closeModalLogin = function () {
    View.divLoginRegister.style.display = "none";
    View.formLoginRegister[0].reset();
};

Controller.displayUserLoggedIn = function (xmlResponse) {
    Controller.closeModalLogin();
    Controller.storeXMLDomToLocalStorage(xmlResponse, Model.constant.localStorageUserKey);
    Controller.checkLoginAndDisplay();
};

Controller.displayLoggedInFailure = function () {
    if (Model.isLogin === true) {
        View.displayAlert("Invalid email or password!", "Login Error", false);
    } else {
        View.displayAlert("This email  already exists!", "Register fail", false);
    }
};

View.formLoginRegister.submit(function (e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: View.formLoginRegister.attr('action'),
        data: View.formLoginRegister.serialize(), // serializes the form's elements.
        success: function (data) {
            console.log('Submission was successful.');
            if (data != null) {
                Controller.displayUserLoggedIn(data);
            } else {
                Controller.displayLoggedInFailure();
            }
        },
        error: function (data) {
            console.log('An error occurred.');
            console.log(data);
            Controller.displayLoggedInFailure();
        }
    });
});


Controller.logOut = function () {
    localStorage.removeItem(Model.constant.localStorageUserKey);
    localStorage.removeItem("myCart");
    location.reload();
};

Controller.checkLoginAndDisplay = function () {
    var xmlUser = localStorage.getItem(Model.constant.localStorageUserKey);
    if (xmlUser) { 
        var xmlUserDom = Controller.parserXMLFromStringToDOM(xmlUser);
        var fullName = xmlUserDom.getElementsByTagName("FullName")[0];
        View.pUserFullName.innerHTML = fullName.textContent;
        View.displayWhenLoggedIn();
    } else {
        View.displayWhenNotLoggedIn();
    }
};

Controller.checkLoginAndDisplay();

Controller.checkLogin = function () {
    var xmlUser = localStorage.getItem(Model.constant.localStorageUserKey);
    if (xmlUser) {
        return true;
    }
    return false;
};
