/* global Controller, View, Model */
View.buttonStopResume = document.getElementById("btn-stop-resume");
Controller.denyAccess = function(){   
    window.location.href = "ProcessServlet";
};

Controller.checkUserRole = function(){
    var loggedIn = Controller.checkLogin();
    if(loggedIn == true){
        var userXMLDocument = Controller.parserXMLFromStringToDOM(localStorage.currentUserKey);
        var role = userXMLDocument.getElementsByTagName("Role")[0].textContent;
        console.log(role);
        if(role != "1"){
            Controller.denyAccess();            
        }
    } else{
        Controller.denyAccess();
    }
};

Controller.checkUserRole();

Model.isLoading = false;
Model.isSuspended = (isSuspended == 'true');

Controller.initButtonStopResume = function(){
    if(Model.isSuspended){
        View.buttonStopResume.innerHTML = "Resume Crawling";
    } else{
        View.buttonStopResume.innerHTML = "Stop Crawling";
    }    
};
Controller.initButtonStopResume();

View.buttonStopResume.onclick = function(){
    if(Model.isLoading == true){        
        return;
    }
    View.buttonStopResume.innerHTML = "Processing...";
    var urlControlCrawling = "ProcessServlet?btnAction=";
    if(Model.isSuspended){
        urlControlCrawling += "ResumeCrawling";
    } else{
        urlControlCrawling += "StopCrawling";
    }
    
    var xmlHttp = Controller.getXmlHttpObject();
    if (xmlHttp === null) {
        console.log('Your browser does not support AJAx');
        return;
    }
    xmlHttp.onreadystatechange = function () {
        if (this.readyState == 4) {            
            if (this.status == 200) {
                var result = xmlHttp.responseText.trim();
                if(result == 'Success'){
                    View.displayAlert("Success", "Control Crawling", true);
                    Model.isSuspended = !Model.isSuspended;
                    Controller.initButtonStopResume();
                } else{
                    View.displayAlert("Fail to control crawling", "Control Crawling", false);
                }
            } else {
                console.log('Fail to control crawling');
                View.displayAlert("Fail to receive response", "Control Crawling", false);
            }
        }
    };
    xmlHttp.open("GET", urlControlCrawling, true);
    xmlHttp.send();
};