/* global Controller, View, Model */
//BEGIN Utilities method
Controller.getXmlHttpObject = function () {
    var xmlHttp = null;
    try {
        xmlHttp = new XMLHttpRequest();
    } catch (e) {
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
};

Controller.parserXMLFromStringToDOM = function (xmlString) {
    var parser = new DOMParser();
    var xmlDom = parser.parseFromString(xmlString, "text/xml");
    return xmlDom;
};

Controller.storeXMLDomToLocalStorage = function (dom, id) {
    var xmlSerializer = new XMLSerializer();
    var xmlString = xmlSerializer.serializeToString(dom);
    localStorage.setItem(id, xmlString);
};

Controller.getXMLDoc = function (xmlUrl, callBackMethod) {
    var xmlHttp = Controller.getXmlHttpObject();
    if (xmlHttp === null) {
        console.log('Your browser does not support AJAx');
        return;
    }
    xmlHttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            var xmlDoc = null;
            if (this.status == 200) {
                xmlDoc = xmlHttp.responseXML;
                callBackMethod(xmlDoc);
            } else {
                console.log('Load list products fail');
                callBackMethod(null);
            }
        }
    };
    xmlHttp.open("GET", xmlUrl, true);
    xmlHttp.send();
};

Controller.getXMLTProcessor = function (xslFile) {
    var xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(xslFile);
    return xsltProcessor;
};


Controller.getFormattedNumber = function(_number) {
    var _sep = ",";
    _number = typeof _number != "undefined" && _number > 0 ? _number : "";
    _number = _number.replace(new RegExp("^(\\d{" + (_number.length % 3 ? _number.length % 3 : 0) + "})(\\d{3})", "g"), "$1 $2").replace(/(\d{3})+?/gi, "$1 ").trim();
    if (typeof _sep != "undefined" && _sep != " ") {
        _number = _number.replace(/\s/g, _sep);
    }
    return _number;
};

//End Utilities method

