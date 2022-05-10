import Map from "ol/Map"
import TileLayer from 'ol/layer/Tile';
import View from 'ol/View';
import OSM from 'ol/source/OSM';
import { fromLonLat } from "ol/proj";
import { defaults as defaultInteractions } from "ol/interaction";


export var map = new Map({
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
})