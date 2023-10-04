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
    distanceString = getDistanceFromLatLonInKm(fromY, fromX, toY, toX) * 1000
    if (distanceString > 1000) {
        return Math.round(distanceString / 1000) + ' километров'
    }
    else
        return distanceString + ' метров'
}

function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) {
    let R = 6371; // Radius of the earth in km
    let dLat = deg2rad(lat2-lat1);  // deg2rad below
    let dLon = deg2rad(lon2-lon1);
    let a =
        Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon/2) * Math.sin(dLon/2)
    ;
    let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    let d = R * c; // Distance in km
    return d;
}

function deg2rad(deg) {
    return deg * (Math.PI/180)
}
