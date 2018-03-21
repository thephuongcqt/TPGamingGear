window.onclick = function(event) {
    if (event.target == View.divLoginRegister) {
        Controller.closeModalLogin();
    } else if (event.target == View.modalAlert) {
        View.closeAlertModal();
    } else if (event.target == View.divOrderDetailInformation){
        Controller.closeOrderDetailInformationModel();
    }
};
