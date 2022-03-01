// import VectorSource from "../ol/source/Vector.js";

var feature = new ol.Feature({
    geometry: new ol.geom.Point([117, 30]),
});

var vectorSource = new ol.source.VectorSource({
    features: feature,
    wrapx: false
});

const vector = new ol.layer.VectorLayer({
    source: vectorSource,
    style: new ol.style.Style({
        image: new ol.style.Style.CircleStyle({
            radius: 5,
            fill: new ol.style.Fill({ color: '#CBA7F9' }),
            stroke: new ol.style.Stroke({ color: '#bada55', width: 1 })
        })
    })
});

const map2 = new ol.Map({
    layers: [vector],
    target: "map",
    view: mapView
});