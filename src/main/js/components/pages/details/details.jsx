import React, {Component} from "react";
import {getDetails, addToBasket, refresh, addToFavourites, getShopItems} from "../../../api/request";
import Header from "../../organisms/header/header";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";
import "./details.css"
import {Navigate} from "react-router-dom";

class Details extends Component {

    constructor(props) {
        super(props);
        this.state = {
            id: null,
            item: null,
        }
    }

    componentWillMount() {
        this.state.mounted = true;
        store.subscribe(() => {
            if (this.state.mounted)
                this.setState({reduxState: store.getState()});
        })
        if(!this.state.item)
            this.getData()
    }

    getData = () => {
        const query = new URLSearchParams(window.location.search);
        this.setState({id: query.get("id")})
        console.log(query.get("id"))
        if (query.get("id")) {
            this.getDetails(query.get("id"))
        }
    }

    getDetails = (id) => {
        getDetails(id)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({item: JSON.parse(text)})
                    })
                } else {
                    this.tryToRefresh(this.getDetails, response, id)
                }
            })
    }

    getShopItems = (id) => {
        getShopItems(id)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({items: JSON.parse(text)})
                    })
                } else {
                    this.tryToRefresh(this.getDetails, response, id)
                }
            })
    }

    tryToRefresh = (func, response, body = null) => {
        response.json().then(json => {
            if (json.message === "Expired or invalid JWT token" || json.message === "Access Denied") {
                refresh().then(response => response.json().then(json => {
                    if (response.ok) {
                        sessionStorage.setItem("token", json.accessToken)
                        sessionStorage.setItem("refreshToken", json.refreshToken)
                        if(body)
                            func(body)
                        else
                            func()
                    } else {
                        this.setError("important",json.message);
                        setTimeout(() => {
                            this.setError("important", '')
                            this.setState({redirect: "/login"})
                            store.dispatch({type: "changeLogin", value: null})
                        }, 3000)
                    }
                }))
            } else {
                this.setError("important", json.message)
                setTimeout(() => this.setError("important", ''), 3000)
            }
        })
    }

    setError(name, message) {
        let form = Object.assign({}, this.state.formErrors);
        form[name] = message;
        if(this.state.component_mounted)
            this.setState({formErrors: form})
    }

    componentWillUnmount() {
        this.state.mounted = false;
    }

    render() {
        if (this.state.redirect) {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        if (!this.state.items) {
            this.getShopItems(this.state.id)
        }
        if (!this.state.item) {
            return (<div id="main">
                <Header login={true} getChecks={this.getChecks} search={false}/>
                {<div className={"details-wrapper"}/>}
                <Footer/>
            </div>)
        }
        const handleFavourite = (e) => {
            addToFavourites(e.target.name)
        }
        const handleClick = (e) => {
            addToBasket(e.target.name)
        }
        return (
            <div id="main">
                <Header login={true} search={false}/>
                {<div className={"details-wrapper"}>
                    <div className={"details-row-wrapper"}>
                        <div className={"details-image-wrapper"}>
                            <img src={this.state.item.imageUrl}
                                 alt={"Фото недоступно"}/>
                        </div>
                        <div className={"outputs-wrapper"}>
                                <span id={"item-name"}>{this.state.item.name}</span>
                                <button className={"fav_button"} name={this.state.item.id} onClick={handleFavourite}>В избранное</button>
                        </div>
                    </div>
                    <div className={"details-description"}>Описание: {this.state.item.description}</div>
                    <table className="table is-bordered is-hoverable is-fullwidth has-text-centered">
                        <thead>
                        <tr>
                            <th name="shop">
                                Магазин
                            </th>
                            <th name="price">
                                Стоимость
                            </th>
                            <th>
                                Добавить в корзину
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {(this.state.items) ? this.state.items.map(function (check) {
                                return (
                                    <tr key={check.shopName}>
                                        <td>{check.shopName}</td>
                                        <td>{check.price.toString() + ' рублей'}</td>
                                        <td>
                                            <button className={"item_button"} name={check.id} onClick={handleClick}>Добавить</button>
                                        </td>
                                    </tr>
                                );
                            }) :
                            <tr>
                                <td colSpan={3}>Loading...</td>
                            </tr>}
                        </tbody>
                    </table>
                    <div className={"push"}/>
                </div>}
                <Footer/>
            </div>)
    }
}


export default Details