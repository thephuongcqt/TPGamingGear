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
    var xmlDom;
    if (window.DOMParser)
    {
        var parser = new DOMParser();
        xmlDom = parser.parseFromString(xmlString, "text/xml");
    } else{
        xmlDom = new ActiveXObject("Microsoft.XMLDOM");
        xmlDom.async = false;
        xmlDom.loadXML(xmlString);
    }
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
                console.log('failed to load list products');
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


Controller.getFormattedNumber = function (number) {
    var separetor = ",";
    number = typeof number != "undefined" && number > 0 ? number : "";
    number = number.replace(new RegExp("^(\\d{" + (number.length % 3 ? number.length % 3 : 0) + "})(\\d{3})", "g"), "$1 $2").replace(/(\d{3})+?/gi, "$1 ").trim();
    if (typeof separetor != "undefined" && separetor != " ") {
        number = number.replace(/\s/g, separetor);
    }
    return number;
};

//End Utilities method

