<%@ page language="java" pageEncoding="utf-8"%>
<head>
    <title>CY-WebGIS-Test</title>
    <link rel="stylesheet" href="https://openlayers.org/en/v4.6.5/css/ol.css" type="text/css">
    <!-- <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.13.0/build/ol.js"></script> -->
    <style>
        a.skiplink {
          position: absolute;
          clip: rect(1px, 1px, 1px, 1px);
          padding: 0;
          border: 0;
          height: 1px;
          width: 1px;
          overflow: hidden;
        }

        .map {
            height: 500px;
            width: 70%;
        }
        a.skiplink:focus {
          clip: auto;
          height: auto;
          width: auto;
          background-color: #fff;
          padding: 0.3em;
        }
        #map:focus {
          outline: #4A74A8 solid 0.15em;
        }
    </style>


</head>
<body>
    <h2>Select your current location I will give you a special gift.</h2>

    <form>
        <select name="provinceSelector" id="select_province" onchange="selectProvince(this.value)">
            <option value=-1>--请选择--</option>
        </select>
    </form>

    <form>
        <select name="citySelector" id="select_city" onchange="selectCity(this.value)">
            <option value=-1>--请选择--</option>
        </select>
    </form>

    <button onclick="surprise();">Get Gift</button>
    <br><br><br>
    <center><div id="map" class="map" tabindex="0"></div></center>
    <br>
    <center>
        <button id="zoom-out">Zoom out</button>
        <button id="zoom-in">Zoom in</button>
    </center>

    <script>
        var provinceName = "";
        var provinceID = -1;
        var cityName = "";
        var cityid = -1;

        // window.onload = function() {
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
        // };

        function selectProvince(id) {
            var citySelector = document.getElementById("select_city");
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/webgis/data/district/city?provinceID="+id);
            
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    citySelector.innerHTML = "<option value=-1>--请选择--</option>";
                    const json = JSON.parse(xhr.responseText);
                    for (let idx in json["cities"]) {
                        citySelector.innerHTML +=
                           "<option value=" + json["cities"][idx]["serial"] + ">" + 
                            json["cities"][idx]["name"] + "</option>";
                    }
                    provinceName = json["province"]["name"];
                    provinceID = id;
                }
            };
            xhr.send();
        };

        function surprise() {
            while (true) {
                alert("败坏之先，人心骄纵，尊荣以前，必有谦卑");
            }
        }
        // function selectCity(serial) {
        //     var xhr = new XMLHttpRequest();
        //     xhr.open("GET", "/webgis/data/district/city?ProvinceSerial=" +
        //                     provinceSerial + "&CitySerial=" + serial);

        //     xhr.onreadystatechange = function() {
        //         if (xhr.readyState == 4 && xhr.status == 200) {
        //             var json = JSON.parse(xhr.responseText);
        //             cityName = json["name"];
        //             citySerial = serial;
        //         }
        //     };
        //     xhr.send();

        //     if (provinceSerial != -1 && citySerial != -1) {
        //         document.getElementById("select_res").innerHTML = 
        //             "You have selected " + provinceName + " " + cityName;
        //     }
        // };

    </script>

    <script type="module" crossorigin src="./js/test.js"></script>
    
</body>
</html>