import store from "./store";

const options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
};

function success(pos) {
    const crd = pos.coords;

    console.log('Ваше текущее местоположение:');
    console.log(`Широта: ${crd.latitude}`);
    console.log(`Долгота: ${crd.longitude}`);
    console.log(`Плюс-минус ${crd.accuracy} метров.`);
    store.dispatch({type: "changeCoordinates", value: crd});
}

function error(err) {
    console.warn(`ERROR(${err.code}): ${err.message}`);
}

export function getGeolocation() {
    navigator.geolocation.getCurrentPosition(success, error, options);
}

export function getDistance(fromCrd, toCrd) {
    if (fromCrd == null || toCrd == null) {
        return 'Нет данных'
    }
    let fromX = fromCrd.longitude
    let fromY = fromCrd.latitude
    let toX = toCrd.longitude
    let toY = toCrd.latitude
    let distanceString = ""
    distanceString = Math.round(Math.sqrt(Math.abs((fromX - toX)*(fromX - toX) + (fromY - toY) * (fromY - toY))))
    if (distanceString > 1000) {
        return distanceString / 1000 + ' километров'
    }
    else
        return distanceString + ' метров'
}