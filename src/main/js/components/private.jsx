import React, {Component} from 'react';
import Main from "./pages/main/main";
import './app.css'
import Login from "./pages/login/login";
import store from "../app/store";

import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link,
    useRouteMatch,
    useParams
} from "react-router-dom";

class App extends Component {

    componentDidMount() {
        store.subscribe(() => {
            this.setState({reduxState: store.getState()});
        })
    }

    render() {
        return (
            <Router>
                <Routes>
                    <Route path="/login">
                        <Login/>
                    </Route>
                    <Route path="/create" element={<PrivateRoute/>}>
                        <Main/>
                    </Route>
                    <Route path="/">
                        <Main/>
                    </Route>
                </Routes>
            </Router>
            /*<div className="first-page">
                {store.getState().login && store.getState().login !== "null" ? <Main/> : <Login/>}
            </div>*/
        )
    }
}

export default App;