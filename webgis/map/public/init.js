import { resetCSS } from './resetCss';


export function init() {
    $("#button_billiard").html('<button id="play_billiards">I want to play billiards!</button>');
    $("#play_billiard").css("cursor", "pointer");

    resetCSS();
}