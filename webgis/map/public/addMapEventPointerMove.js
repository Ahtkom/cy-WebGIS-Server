import { toLonLat } from "ol/proj";
import { toStringXY } from "ol/coordinate";
import Map from "ol/Map";

import {map} from "./createMap";

export function addMapEventPointerMove() {
    map.on("pointermove", (evt) => {
        let lonlat = toLonLat(evt.coordinate, "EPSG:3857");
        $("#mousepos").text(toStringXY(lonlat, 3));
    });
}