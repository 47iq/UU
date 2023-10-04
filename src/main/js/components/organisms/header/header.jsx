import React, {Component} from "react";
import AppHeader from "../../molecules/appHeader/appHeader"
import store from "../../../app/store";

class Header extends Component {

    constructor(props) {
        super(props);
    }

    logout = () => {
        sessionStorage.setItem("token", null)
        store.dispatch({type: "changeLogin", value: null});
    }

    render() {
        return (
            <AppHeader isLoggedIn={this.props.login} logout={this.logout} search={this.props.search} getChecks={this.props.getChecks}/>
        )
    }
}

export default Header