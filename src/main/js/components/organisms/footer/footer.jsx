import React, {Component} from "react";
import AppHeader from "../../molecules/appHeader/appHeader"
import store from "../../../app/store";
import {Navigate} from "react-router-dom";
import './footer.css'

class Footer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            redirect: null
        }
    }

    handleClick = () => {
        this.setState({redirect: "/create"})
    }

    render() {
        if (this.state.redirect) {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        return (
            <footer className={"footer"}>
                <button onClick={this.handleClick}>Добавить товар</button>
            </footer>

        )
    }
}

export default Footer