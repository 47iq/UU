const BASE_URL = "/api/"
const AUTH_BASE = BASE_URL + "users/"
const ITEMS_BASE = BASE_URL + "items/"
const ORDER_BASE = BASE_URL + "orders/"
const ADDERSS_BASE = BASE_URL + "order_address/"
const CART_BASE = BASE_URL + "carts/"
const SHOPS_BASE = BASE_URL + "shops/"


export function register(login, password) {
    return getResponse(AUTH_BASE + 'register', {username: login, password: password, roles: []}, 'POST', false)
}

export function login(login, password) {
    return getResponse(AUTH_BASE + 'login', {username: login, password: password}, 'POST', false)
}

export function getAllItems(query) {
    return getResponse(ITEMS_BASE + 'items', {query: query}, 'GET')
}

export function getFavouriteItems() {
    return getResponse(ITEMS_BASE + '/favourite', null, 'GET')
}

export function getOrders() {
    return getResponse(ITEMS_BASE, null, 'GET')
}

export function getOrderInfo(query) {
    return getResponse(ORDER_BASE + query, null,'GET')
}

export function getBasket() {
    return getResponse(CART_BASE, null, 'GET')
}

export function getAddresses() {
    return getResponse(ADDERSS_BASE, null, 'GET')
}

export function addAddress(data) {
    return getResponse(ADDERSS_BASE, data, 'POST')
}

export function createOrder(data) {
    return getResponse(ORDER_BASE, data, 'POST')
}

export function deleteFromBasket(id) {
    return getResponse(CART_BASE + 'cart/remove/' + id, null, 'POST')
}

export function deleteFromFavourites(id) {
    return getResponse(ITEMS_BASE + 'favorite/' + id, null, 'DELETE')
}

export function addToBasket(query) {
    return getResponse(CART_BASE + 'cart/add/' + query, null)
}

export function getDetails(query) {
    return getResponse(ITEMS_BASE + query,null,'GET')
}

export function getAutocomplete(query) {
    return getResponse(ITEMS_BASE + 'autocomplete/', {query: query},'GET')
}

export function createItem(query) {
    return getResponse(ITEMS_BASE, query,'POST')
}

export function createShopItem(query) {
    return getResponse(SHOPS_BASE + 'shop_items/', query,'POST')
}

export function getShopItems(query) {
    return getResponse(SHOPS_BASE + 'shop_items/' + query, null,'GET')
}

export function addToFavourites(query) {
    return getResponse(ITEMS_BASE + 'favorite/' + query, null,'POST')
}

export function create(item) {
    return getResponse(ITEMS_BASE, item)
}

export function refresh() {
    return fetch('/api/users/refresh', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        headers: {
            'Content-type': 'application/json',
            'Accept' : 'application/json'
        },
        body: JSON.stringify({refreshToken: sessionStorage.getItem("refreshToken")}) // body data type must match "Content-Type" header
    });
}

export function clear() {
    return getResponse('/api/points/clear')
}

function getResponse(url = '', data = null, method='POST', tokenNeeded = true) {
    let token = sessionStorage.getItem("token")
    let httpHeaders
    if(tokenNeeded && token && token !== 'null'){
        httpHeaders = {
            'Content-type': 'application/json',
            'Accept' : 'application/json',
            'Authorization' : `Bearer ${token}`
        };
    } else {
        httpHeaders = {
            'Content-type': 'application/json',
            'Accept' : 'application/json'
        };
    }
    let body = {}
    if(method === 'POST') {
        body = data
        return fetch(url, {
            method: method, // *GET, POST, PUT, DELETE, etc.
            headers: httpHeaders,
            body: JSON.stringify(data) // body data type must match "Content-Type" header
        });
    } else {
        if(data !== null)
            url += '?' + new URLSearchParams(data).toString();
        return fetch(url, {
            method: method, // *GET, POST, PUT, DELETE, etc.
            headers: httpHeaders
        });
    }

}