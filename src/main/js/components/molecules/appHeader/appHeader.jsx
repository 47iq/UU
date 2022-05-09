import React, {Component} from "react";
import './appHeader.css'
import SearchForm from "../../organisms/search/search";
import {Navigate} from "react-router-dom";

class AppHeader extends Component {

    constructor(props) {
        super(props);
        this.state = {
            redirect: null
        }
    }

    handleClick = () => {
        this.setState({redirect: "/"})
    }

    render() {
        if (this.state.redirect && window.location.pathname !== "/") {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        return (
            <header className={"header"}>
                <div className={"header-name-wrapper"}>
                    <h1 onClick={this.handleClick}><span>Your</span><span>Union</span></h1>
                </div>
                <div className={"search"}>
                    {this.props.isLoggedIn && this.props.search ? <SearchForm getChecks={this.props.getChecks}/> : ""}
                </div>
                <div>
                    {this.props.isLoggedIn ? <button className={"logout"} onClick={this.props.logout}>Выйти</button> : ""}
                </div>
            </header>
        )
    }
}

export default AppHeader