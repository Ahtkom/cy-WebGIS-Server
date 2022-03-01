var provinceName = "";
var provinceID = -1;
var cityName = "";
var cityID = -1;

var provinceSelector = document.getElementById("select_province");
var xhr = new XMLHttpRequest();
xhr.open("GET", "/webgis/data/district/province");

xhr.onreadystatechange = function() {
    if (xhr.readyState == 4 && xhr.status == 200) {
        const json = JSON.parse(xhr.responseText);
        for (let idx in json) {
            provinceSelector.innerHTML +=
                "<option value=" + json[idx]["id"] + ">" + 
                json[idx]["name"] + "</option>";
        }
    }
};
xhr.send();

function selectProvince(id) {
    var citySelector = document.getElementById("select_city");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/webgis/data/district/city?provinceID="+id+"&cityID=");
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            citySelector.innerHTML = "<option value=-1>--请选择--</option>";
            const json = JSON.parse(xhr.responseText);
            for (let idx in json["cities"]) {
                citySelector.innerHTML +=
                    "<option value=" + json["cities"][idx]["id"] + ">" + 
                    json["cities"][idx]["name"] + "</option>";
            }
            provinceName = json["province"]["name"];
            provinceID = id;
        }
    };
    xhr.send();
};

function selectCity(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/webgis/data/district/city?provinceID=" +
                    provinceID + "&cityID=" + id);

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var json = JSON.parse(xhr.responseText);
            cityName = json["name"];
            cityID = id;
        }
    };
    xhr.send();
};

function surprise() {
    if (provinceID != -1 && cityID != -1) {
        while (true) {
            alert("败坏之先，人心骄纵，尊荣以前，必有谦卑");
        }
    } else {
        alert("Requirements not met!");
    }
}