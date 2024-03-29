import {createStore} from "redux";

const initialState = {
    login: sessionStorage.getItem("login"),
    checks: null,
    coordinates: null
};

function reducer(state, action) {
    switch (action.type) {
        case "changeLogin":
            sessionStorage.setItem("login", action.value)
            if(action.value == null) {
               state.radius = null
            }
            state.login = action.value
            state.checks = null
            state.formErrors = initialState.formErrors
            return state;
        case "setChecks":
            sessionStorage.setItem("checks", action.value)
            state.checks = action.value
            return state;
        case "changeCoordinates":
            sessionStorage.setItem("coordinates", action.value)
            state.coordinates = action.value
            return state
        default:
            return state;
    }
}

const store = createStore(reducer, initialState);
export default store;