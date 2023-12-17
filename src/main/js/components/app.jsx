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
    Navigate,
    Outlet
} from "react-router-dom";
import Details from "./pages/details/details";
import Order from "./pages/order/order";
import Favourites from "./pages/favourites/favourites";
import Basket from "./pages/basket/basket";
import CreateItem from "./pages/create_item/create_item";
import Orders from "./pages/orders/orders";

const PrivateRoute = () => {
    const auth = store.getState().login;
    return auth ? <Outlet /> : <Navigate to="/login" />;
}

class App extends Component {

    componentDidMount() {
        store.subscribe(() => {
            this.setState({reduxState: store.getState()});
        })
        getGeolocation()
    }

    render() {
        return (
            <Router>
                <Routes>
                    <Route exact path="/create" element={<PrivateRoute/>}>
                        <Route exact path="/create" element={<CreateItem/>}/>
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
                    <Route path="/orders" element={<PrivateRoute/>}>
                        <Route path="/orders" element={<Orders/>}/>
                    </Route>
                    <Route exact path="/login" element={<Login/>}/>
                </Routes>
            </Router>
        )
    }
}

export default App;