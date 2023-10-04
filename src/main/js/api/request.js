export function register(login, password) {
    return getResponse('/api/users/register', {username: login, password: password, roles: []}, 'POST', false)
}

export function login(login, password) {
    return getResponse('/api/users/login', {username: login, password: password}, 'POST', false)
}

export function getAll() {
    return getResponse('/api/points/get', {}, 'GET')
}

export function getAllItems(query) {
    return getResponse('/api/items/items', {query: query}, 'GET')
}

export function getFavouriteItems() {
    return getResponse('/api/items/favourite_item', null, 'GET')
}

export function getOrders() {
    return getResponse('/api/orders/all', null, 'GET')
}

export function getOrderInfo(query) {
    return getResponse('/api/orders/' + query, null,'GET')
}

export function getBasket() {
    return getResponse('/api/cart/cart/', null, 'GET')
}

export function getAddresses() {
    return getResponse('/api/order_address/', null, 'GET')
}

export function addAddress(data) {
    return getResponse('/api/order_address/create', data, 'POST')
}

export function createOrder(data) {
    return getResponse('/api/orders/create', data, 'POST')
}

export function deleteFromBasket(id) {
    return getResponse('/api/cart/cart/remove/' + id, null, 'POST')
}

export function deleteFromFavourites(id) {
    return getResponse('/api/items/favorite_item/remove/' + id, null, 'POST')
}

export function addToBasket(query) {
    return getResponse('/api/cart/cart/add/' + query, null)
}

export function getDetails(query) {
    return getResponse('/api/items/item/' + query,null,'GET')
}

export function getAutocomplete(query) {
    return getResponse('/api/items/autocomplete/', {query: query},'GET')
}

export function createItem(query) {
    return getResponse('/api/items/create/', query,'POST')
}

export function createShopItem(query) {
    return getResponse('/api/shop/shop_items/add', query,'POST')
}

export function getShopItems(query) {
    return getResponse('/api/shop/shop_items/item_id/' + query, null,'GET')
}

export function addToFavourites(query) {
    return getResponse('/api/items/favorite_item/add/' + query, null,'POST')
}

export function check(point) {
    return getResponse('/api/points/check', point)
}

export function create(item) {
    return getResponse('/api/items/create', item)
}

export function refresh() {
    //todo
    return fetch('/api/users/refresh', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        headers: {
            'Content-type': 'application/json',
            'Accept' : 'application/json'
        },
        body: JSON.stringify({refreshToken: sessionStorage.getItem("refreshToken")}) // body data type must match "Content-Type" header
    });
    //return getResponse('/api/users/refresh', {refreshToken: sessionStorage.getItem("refreshToken")})
}

export function clear() {
    return getResponse('/api/points/clear')
}

// Пример отправки POST запроса:
function getResponse(url = '', data = null, method='POST', tokenNeeded = true) {
    // Default options are marked with *
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