// import Map from '/ol/Map.js';
// import Overlay from '/ol/Overlay.js';
// import View from '/ol/View.js';
// import {toStringHDMS} from '/ol/coordinate.js';
// import TileLayer from '/ol/layer/Tile.js';
// import {fromLonLat, toLonLat} from '/ol/proj.js';
// import OSM from '/ol/source/OSM.js';

mapView = new ol.View({
    center: ol.proj.fromLonLat([117, 30]),
    zoom: 4
});

var map = new ol.Map({
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    target: 'map',
    controls: ol.control.defaults({
            attributionOptions: {
                collapsible: false
            }
    }),
    view: mapView
});

map.on("pointermove", function (evt) {

    var lonlat = ol.proj.toLonLat(evt.coordinate, "EPSG:3857");

    document.getElementById("mousepos").innerHTML = ol.coordinate.toStringXY(lonlat, 3);

    // console.log();
    // console.log(pos);
    // console.log(evt);
    // console.log(ol.proj.getUserProjection(map));
});

// $("#map").on("pointermove", function (evt) {
//     console.log(evt.coordinates);
    
// });

// console.log(map);

document.getElementById('zoom-out').onclick = function() {
    var view = map.getView();
    var zoom = view.getZoom();
    view.setZoom(zoom - 1);
};

document.getElementById('zoom-in').onclick = function() {
    var view = map.getView();
    var zoom = view.getZoom();
    view.setZoom(zoom + 1);
};

