import './style.css';
// import 'ol/';
import Feature from 'ol/Feature';
// import Tile from 'ol/Tile';
import TileLayer from 'ol/layer/Tile';
import Map from 'ol/Map';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { toStringXY } from 'ol/coordinate';
import View from 'ol/View';
import { Circle as CircleStyle, Fill, Stroke, Style } from 'ol/style';
import { LineString, Point } from 'ol/geom';
import { getVectorContext } from 'ol/render';
import OSM from 'ol/source/OSM';
import { fromLonLat, transform, toLonLat } from 'ol/proj';
import Overlay from 'ol/Overlay';
import {defaults as defaultInteractions} from 'ol/interaction';

import Polygon from 'ol/geom/Polygon';

// const map = new Map({
//     target: 'map',
//     layers: [
//         new TileLayer({
//             source: new OSM()
//         })
//     ],
//     view: new View({
//         center: [0, 0],
//         zoom: 2
//     })
// });

// let coords = polygon["coordinates"];

// const polygonFeature = new Feature(new Polygon(coords).transform('EPSG:4326','EPSG:3857'));

// var layer = new VectorLayer({
//     source: new VectorSource({
//         features: [polygonFeature]
//     }),
//     style: new Style({
//         stroke: new Stroke({
//           width: 3,
//           color: [51, 136, 255, 1]
//         }),
//         fill: new Fill({
//           color: [200, 220, 255, 0.7]
//         })
//     })
// });
// map.addLayer(layer);


const map = new Map({
    target: 'map',
    layers: [
        new TileLayer({
            source: new OSM()
        })
        // layer
    ],
    view: new View({
        center: fromLonLat([114.4, 30.5]),
        zoom: 12
    }),
    interactions: defaultInteractions({
        doubleClickZoom: false
    }),
});

document.getElementById("map").style = "left: 18%; width: 64%; height: 80%; top: 130px;";
map.on("pointermove", function (evt) {
    var lonlat = toLonLat(evt.coordinate, "EPSG:3857");
    document.getElementById("mousepos").innerHTML = toStringXY(lonlat, 3);
});

document.getElementById("ppp").innerHTML = '<button id="play_billiards">I want to play billiards!</button>';
document.getElementById("play_billiards").style.cursor = "pointer";

var inSelectState = false;
document.getElementById("play_billiards").onclick = function () {
    if (!inSelectState) {
        var overlays = [];
        var locator = null;

        // Change the style of mouse
        document.getElementById("map").style.cursor = "url(http://ahtkom.com:8080/webgis/assets/icon/bangzi.ico),auto";
        // document.getElementById("map").onmouseout = () => { };

        var xhr = new XMLHttpRequest();
        // Get response from server and load overlays on map
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // resp json form:
                // [
                //   {"name":___, "lon":___, "lat":___, "dist":___},
                //   ...,
                // ]
                let rooms = JSON.parse(xhr.responseText);

                let rooms_div = document.getElementById("billiard_rooms");
                rooms_div.innerHTML = ""; // ? Is the statement necessary?

                for (let idx in rooms) {
                    let room = rooms[idx];
                    let coordinate = fromLonLat([room["lon"], room["lat"]]);

                    rooms_div.innerHTML +=
                        '<img src="http://ahtkom.com:8080/webgis/assets/icon/locator.ico" id="room' + idx + '">';
                    
                    overlays.push(
                        new Overlay({
                            position: coordinate,
                            positioning: "center-center",
                            element: document.getElementById("room" + idx),
                            stopEvent: false
                        })
                    );
                    map.addOverlay(overlays[idx]);

                    document.getElementById("room" + idx).style.cursor = "pointer";
                    
                    document.getElementById("room" + idx).onmouseover = (evt) => {
                        let roomInfo = document.getElementById("billiard_room_info");
                        roomInfo.innerHTML =
                            '<div class="xx" id="billiard_room">' +
                            '<div>' + room["name"] + '</div>' + '<br>' +
                            '<div>距离您：' + (room["dist"]/1000).toFixed(3) + 'km</div>' +
                            '</div>';
                        let markerText = document.getElementById("billiard_room");
                        markerText.style.position = "absolute";
                        markerText.style.left = evt.pageX + "px";
                        markerText.style.top = evt.pageY + "px";
                        markerText.style.fontSize = 10;
                        markerText.style.cursor = "pointer";
                        roomInfo.onmouseout = () => {
                            roomInfo.innerHTML = "";
                        };
                    }
                }
            }
        };

        // Choose current location
        var singleclick = (evt) => {
            // Remove original overlays
            for (let i = 0; i < overlays.length; i++) {
                document.getElementById("room" + i).onmouseover = () => { };
                map.removeOverlay(overlays[i]);
            }
            overlays = [];
            if (locator != null) {
                map.removeOverlay(locator);
            }

            // Send resquest
            let lonlat = toLonLat(evt.coordinate, "EPSG:3857");
            xhr.open("GET", "/webgis/data/billiards?lon="+lonlat[0]+"&lat="+lonlat[1]);
            xhr.send();

            document.getElementById("locator").innerHTML =
                '<img src="http://ahtkom.com:8080/webgis/assets/icon/bangzi.ico" id="locator_img">';
            
            locator = new Overlay({
                position: evt.coordinate,
                positioning: "center-center",
                element: document.getElementById("locator_img"),
                stopEvent: false
            });
            map.addOverlay(locator);
            document.getElementById("locator").innerHTML = "";
            
            document.getElementById("map").style.cursor = "default";
        };
        map.on("singleclick", singleclick);

        // Leave selection state
        map.on("dblclick", (evt) => {
            // Unlisten the select event
            map.un("singleclick", singleclick);
            // Remove original overlays
            if (locator != null) {
                map.removeOverlay(locator);
            }
            for (let i = 0; i < overlays.length; i++) {
                document.getElementById("room" + i).onmouseover = () => { };
                map.removeOverlay(overlays[i]);
            }
            overlays = [];

            map.un("singleclick", () => { });

            document.getElementById("map").style.cursor = "default";
            inSelectState = false;
        });
        inSelectState = true;
    }
};



// var coordinates = [];
// var xhr = new XMLHttpRequest();
// xhr.open('GET', '/webgis/data/point');
// xhr.onreadystatechange = function () {
//     if (xhr.readyState == 4 && xhr.status == 200) {
//         const json = JSON.parse(xhr.responseText);
//         var i = 0;
//         for (let idx in json) {
//             var pJson = JSON.parse(json[idx]);
//             coordinates.push(fromLonLat(pJson["coordinates"]));
//             console.log(coordinates[i]);
//             document.getElementById("markers").innerHTML +=
//                 "<div id=\"marker"+i.toString()+"\" class=\"marker\"></div>"
//             var marker = new Overlay({
//                 position: coordinates[i],
//                 positioning: "center-center",
//                 element: document.getElementById("marker"+i.toString()),
//                 stopEvent: false
//             });
//             map.addOverlay(marker);
//             i++;

//             console.log(idx);
//         }
//     }
// };
// xhr.send();





// const 


// const count = 20000;
// const features = new Array(count);
// const e = 180000000;
// for (let i = 0; i < count; ++i) {
//   features[i] = new Feature({
//     'geometry': new Point([
//       2 * e * Math.random() - e,
//       2 * e * Math.random() - e,
//     ]),
//     'i': i,
//     'size': i % 2 ? 5 : 10,
//   });
// }

// const styles = {
//   '10': new Style({
//     image: new CircleStyle({
//       radius: 5,
//       fill: new Fill({color: '#666666'}),
//       stroke: new Stroke({color: '#bada55', width: 1}),
//     }),
//   }),
//   '20': new Style({
//     image: new CircleStyle({
//       radius: 10,
//       fill: new Fill({color: '#666666'}),
//       stroke: new Stroke({color: '#bada55', width: 1}),
//     }),
//   }),
// };

// const vectorSource = new VectorSource({
//   features: features,
//   wrapX: false,
// });
// const vector = new VectorLayer({
//   source: vectorSource,
//   style: function (feature) {
//     return styles[feature.get('size')];
//   },
// });

// const map = new Map({
//   // layers: [vector, new Tile({source: new OSM()})],
//   layers: [vector],
//   target: document.getElementById('map'),
//   view: new View({
//     center: [0, 0],
//     zoom: 2,
//   }),
// });

// let point = null;
// let line = null;
// const displaySnap = function (coordinate) {
//   const closestFeature = vectorSource.getClosestFeatureToCoordinate(coordinate);
//   if (closestFeature === null) {
//     point = null;
//     line = null;
//   } else {
//     const geometry = closestFeature.getGeometry();
//     const closestPoint = geometry.getClosestPoint(coordinate);
//     if (point === null) {
//       point = new Point(closestPoint);
//     } else {
//       point.setCoordinates(closestPoint);
//     }
//     if (line === null) {
//       line = new LineString([coordinate, closestPoint]);
//     } else {
//       line.setCoordinates([coordinate, closestPoint]);
//     }
//   }
//   map.render();
// };

// map.on('pointermove', function (evt) {
//   if (evt.dragging) {
//     return;
//   }
//   const coordinate = map.getEventCoordinate(evt.originalEvent);
//   displaySnap(coordinate);
// });

// map.on('click', function (evt) {
//   displaySnap(evt.coordinate);
// });

// const stroke = new Stroke({
//   color: 'rgba(255,255,0,0.9)',
//   width: 3,
// });
// const style = new Style({
//   stroke: stroke,
//   image: new CircleStyle({
//     radius: 10,
//     stroke: stroke,
//   }),
// });

// vector.on('postrender', function (evt) {
//   const vectorContext = getVectorContext(evt);
//   vectorContext.setStyle(style);
//   if (point !== null) {
//     vectorContext.drawGeometry(point);
//   }
//   if (line !== null) {
//     vectorContext.drawGeometry(line);
//   }
// });

// map.on('pointermove', function (evt) {
//   if (evt.dragging) {
//     return;
//   }
//   const pixel = map.getEventPixel(evt.originalEvent);
//   const hit = map.hasFeatureAtPixel(pixel);
//   if (hit) {
//     map.getTarget().style.cursor = 'pointer';
//   } else {
//     map.getTarget().style.cursor = '';
//   }
// });