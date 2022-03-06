import './style.css';
import { init } from './src/initMap/init';
import { addMapEvent } from './src/initMap/addMapEvent';
import { playBilliards } from './src/services/playBilliards';

init();

addMapEvent();

playBilliards();