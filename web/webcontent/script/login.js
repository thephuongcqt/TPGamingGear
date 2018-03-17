/* global Controller, View, Model */
View.buttonLogin = document.getElementById("buttonLogin");
View.buttonRegister = document.getElementById("buttonRegister");
View.divLoginRegister = document.getElementById("divLoginRegister");
View.divInputName = document.getElementsByClassName("divInputName")[0];
View.buttonSubmit = document.getElementById("buttonSubmitLoginRegister");
View.textFullName = document.getElementById("txtFullName");
View.formLoginRegister = $("#FormLoginRegister");
View.inputBtnAction = document.getElementById("inputHiddenButtonAction");
Model.isLogin = false;

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


View.formLoginRegister.submit(function (e) {
    alert(View.formLoginRegister.serialize());
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: View.formLoginRegister.attr('action'),
        data: View.formLoginRegister.serialize(), // serializes the form's elements.
        success: function (data) {
            console.log('Submission was successful.');
            console.log(data);
        },
        error: function (data) {
            console.log('An error occurred.');
            console.log(data);
        },
    });
});

