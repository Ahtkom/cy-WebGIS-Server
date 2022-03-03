import './style.css';
// import 'ol/';
import Feature from 'ol/Feature';
// import Tile from 'ol/Tile';
import TileLayer from 'ol/layer/Tile';
import Map from 'ol/Map';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import View from 'ol/View';
import { Circle as CircleStyle, Fill, Stroke, Style } from 'ol/style';
import { LineString, Point } from 'ol/geom';
import { getVectorContext } from 'ol/render';
import OSM from 'ol/source/OSM';
import { fromLonLat, toLonLat, transform } from 'ol/proj';
import Overlay from 'ol/Overlay';

document.getElementById("ppp").innerHTML =
'<button id="play_billiards">I want to play billiards!</button>';

document.getElementById("play_billiards").onclick = function () {
    // let map = document.getElementById("map");
    var overlays = [];

    // <!--  -->


    // Change the style of mouse
    map.onmouseover = () => {

    };

    map.onmouseout = () => {

    };


    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {

            // resp json form:
            // [
            //   {"name":___, "lon":___, "lat":___, "dist":___},
            //   ...,
            // ]
            const rooms = JSON.parse(xhr.responseText);
            
            let rooms_div = document.getElementById("billiard_rooms");
            rooms_div.innerHTML = "";

            for (let idx in rooms) {
                const room = rooms[idx];
                coordinate = fromLonLat([room["lon"], room["lat"]]);

                rooms_div.innerHTML +=
                    "<div id=\"room" + idx +
                    "\" class=\"billiard_room\" onmouseover=\"showRoomInfo(" +
                    room["name"] + room["dist"] + ");\"></div>";

                overlays.push(
                    new Overlay({
                        position: coordinate,
                        positioning: "center-center",
                        element: document.getElementById("room" + idx),
                        stopEvent: false
                    })
                );
                map.addOverlay(overlays[idx++]);
            }
        }
    };
    map.on("singleclick", (evt) => {
        // Remove original overlays
        for (let i = 0; i < overlays.length; i++) {
            map.removeOverlay(overlays[i]);
        }

        const lonlat = toLonLat(evt.coordinate, "EPSG:3857");
        xhr.open("GET", "/webgis/data/billiards?lon="+lonlat[0]+"&lat="+lonlat[1]);
        xhr.send();
    });


    map.on("dblclick", (evt) => {
        map.onmouseover = () => { };
        map.onmouseout = () => { };
    });
};


function showRoomInfo(name, dist) {
    console.log(name, dist);
};