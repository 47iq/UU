import React, {Component} from 'react';
import Main from "./pages/main/main";
import './app.css'
import Login from "./pages/login/login";
import store from "../app/store";
import {getGeolocation} from "../app/utils";
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link,
    useRouteMatch,
    useParams,
    Navigate,
    Outlet
} from "react-router-dom";
import Create from "./pages/create/create";
import Details from "./pages/details/details";
import Order from "./pages/order/order";
import Favourites from "./pages/favourites/favourites";
import Basket from "./pages/basket/basket";

const PrivateRoute = () => {
    const auth = store.getState().login; // determine if authorized, from context or however you're doing it

    // If authorized, return an outlet that will render child elements
    // If not, return element that will navigate to login page
    return auth ? <Outlet /> : <Navigate to="/login" />;
}

class App extends Component {

    componentDidMount() {
        store.subscribe(() => {
            this.setState({reduxState: store.getState()});
        })
        getGeolocation()
        function relMouseCoords(event) {
            let totalOffsetX = 0;
            let totalOffsetY = 0;
            let canvasX = 0;
            let canvasY = 0;
            let currentElement = this;
            do {
                totalOffsetX += currentElement.offsetLeft - currentElement.scrollLeft;
                totalOffsetY += currentElement.offsetTop - currentElement.scrollTop;
            }
            while (currentElement === currentElement.offsetParent)

            canvasX = event.pageX - totalOffsetX;
            canvasY = event.pageY - totalOffsetY;

            return {x: canvasX, y: canvasY}
        }

        HTMLCanvasElement.prototype.relMouseCoords = relMouseCoords;
    }

    render() {
        return (
            <Router>
                <Routes>
                    <Route exact path="/create" element={<PrivateRoute/>}>
                        <Route exact path="/create" element={<Create/>}/>
                    </Route>
                    <Route exact path="/" element={<PrivateRoute/>}>
                        <Route exact path="/" element={<Main/>}/>
                    </Route>
                    <Route path="/details" element={<PrivateRoute/>}>
                        <Route path="/details" element={<Details/>}/>
                    </Route>
                    <Route exact path="/favourites" element={<PrivateRoute/>}>
                        <Route exact path="/favourites" element={<Favourites/>}/>
                    </Route>
                    <Route path="/basket" element={<PrivateRoute/>}>
                        <Route path="/basket" element={<Basket/>}/>
                    </Route>
                    <Route path="/order" element={<PrivateRoute/>}>
                        <Route path="/order" element={<Order/>}/>
                    </Route>
                    <Route exact path="/login" element={<Login/>}/>
                </Routes>
            </Router>
            /*<div className="first-page">
                {store.getState().login && store.getState().login !== "null" ? <Main/> : <Login/>}
            </div>*/
        )
    }
}

export default App;