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

    handleBasket = () => {
        this.setState({redirect: "/basket"})
    }

    handleFavourites = () => {
        this.setState({redirect: "/favourites"})
    }

    handleOrders = () => {
        this.setState({redirect: "/orders"})
    }

    render() {
        if (this.state.redirect && !(window.location.pathname === "/" && this.state.redirect === "/" || window.location.pathname === "/basket" && this.state.redirect === "/basket"
            || window.location.pathname === "/orders" && this.state.redirect === "/orders")) {
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
                <div className={"header-buttons"}>
                    {this.props.isLoggedIn && window.location.pathname !== "/basket" ? <button className={"logout"} onClick={this.handleBasket}>Корзина</button> : ""}
                </div>
                <div className={"header-buttons"}>
                    {this.props.isLoggedIn && window.location.pathname !== "/favourites" ? <button className={"logout"} onClick={this.handleFavourites}>Избранное</button> : ""}
                </div>
                <div className={"header-buttons"}>
                    {this.props.isLoggedIn && window.location.pathname !== "/orders" ? <button className={"logout"} onClick={this.handleOrders}>Заказы</button> : ""}
                </div>
                <div className={"header-buttons"}>
                    {this.props.isLoggedIn ? <button className={"logout"} onClick={this.props.logout}>Выйти</button> : ""}
                </div>
            </header>
        )
    }
}

export default AppHeader