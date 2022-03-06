import { fromLonLat, toLonLat } from "ol/proj";
import Overlay from "ol/Overlay";
import { map } from "../initMap/createMap";


/**
 * @abstract Provide billiard rooms selection service, user can click the `I
 * Want to Play Billiards!` button to enter the selection mode, then click the
 * map to set current location, nearby billiard rooms will show up.
 * 
 * Double click the map can leave the selection mode.
 */
export function playBilliards() {
    var inSelectMode = false;       // Whether in selection mode
    var overlays = [];              // Map overlays
    var locator = null;             // Locator(overlay) for user's location
    var xhr = new XMLHttpRequest(); // Send HTTP request

    // Click the button to enter the selection mode
    $("#play_billiards").on("click", function () {
        if (!inSelectMode) {
            // Update selection mode
            inSelectMode = true;

            // Change the style of mouse(as locator)
            $("#map").css("cursor", "url(http://ahtkom.com:8080/webgis/assets/icon/bangzi.ico),auto");

            // Get response from server and load overlays on map
            xhr.onreadystatechange = () => {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    // response json form:
                    // [
                    //   {"name":___, "lon":___, "lat":___, "dist":___},
                    //   ...,
                    // ]
                    let rooms = JSON.parse(xhr.responseText);

                    for (let idx in rooms) {
                        let room = rooms[idx];
                        let coordinate = fromLonLat([room["lon"], room["lat"]]);

                        $("#billiard_rooms").append(
                            '<img src="http://ahtkom.com:8080/webgis/assets/icon/locator.ico" id="room' + idx + '">'
                        );

                        overlays.push(
                            new Overlay({
                                position: coordinate,
                                positioning: "center-center",
                                element: document.getElementById("room" + idx),
                                stopEvent: false
                            })
                        );
                        map.addOverlay(overlays[idx]);

                        $("#room" + idx).css("cursor", "pointer");
                        $("#room" + idx).on({
                            mouseenter: (evt) => {
                                $("#billiard_room_info").html(
                                    '<div id="box"><center>' +
                                    '<div class="xx" id="billiard_room">' +
                                    '<div>' + room["name"] + '</div>' + '<br>' +
                                    '<div>距离您：' + (room["dist"] / 1000).toFixed(3) + 'km</div>' +
                                    '</div></center></div>'
                                );
                                $("#box")
                                    .css("position", "absolute")
                                    .css("left", (evt.pageX - 100) + "px")
                                    .css("top", (evt.pageY - 130) + "px")
                                    .css("fontSize", "middle")
                                    .css("fontFamily", "Impact,Charcoal,sans-serif")
                                    .css("cursor", "pointer")
                                    .css("backgroundColor", "#aad3df")
                                    .css("width", "200px")
                                    .css("paddingTop", "30px")
                                    .css("paddingBottom", "30px")
                                    .css("backgroundImage", "url(http://ahtkom.com:9877/gallery/newsnooker.png)")
                                    .css("borderRadius", "15px");
                                $("#billiard_room_info").on({
                                    mouseleave: () => {
                                        $("#billiard_room_info").html("");
                                    }
                                });
                            }
                        });
                    }
                }
            };

            // Listen to mouse click events
            map.on("singleclick", singleclick);
            map.on("dblclick", doubleclick);
        }
    });

    // Single click to set current location
    function singleclick(evt) {
        $("#map").css("cursor", "default");

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
        
        $("#locator").html('<img src="http://ahtkom.com:8080/webgis/assets/icon/bangzi.ico" id="locator_img">');
        locator = new Overlay({
            position: evt.coordinate,
            positioning: "center-center",
            element: document.getElementById("locator_img"),
            stopEvent: false
        });
        map.addOverlay(locator);
    }

    // Double click to leave the selection mode
    function doubleclick(evt) {
        $("#map").css("cursor", "default");

        // Update selection mode 
        inSelectMode = false;

        // Unlisten the select event
        map.un("singleclick", singleclick);

        // Remove original overlays
        for (let i = 0; i < overlays.length; i++) {
            document.getElementById("room" + i).onmouseover = () => { };
            map.removeOverlay(overlays[i]);
        }
        overlays = [];
        if (locator != null) {
            map.removeOverlay(locator);
        }
    }
}
