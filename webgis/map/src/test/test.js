import Feature from "ol/Feature";
import Geometry from "ol/geom/Geometry";
import Point from "ol/geom/Point";

var f = new Feature({
    geometry: null
});

f.getGeometry();

console.log(f);