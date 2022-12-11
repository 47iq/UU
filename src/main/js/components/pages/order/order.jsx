import React, {Component} from "react";
import {getOrderInfo, refresh} from "../../../api/request";
import Header from "../../organisms/header/header";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";
import GoogleMapReact from 'google-map-react';
import {getDistance} from "../../../app/utils";
import {Navigate} from "react-router-dom";
import Table from "../../molecules/table/table";

class Order extends Component {

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
            this.getOrder(query.get("id"))
        }
    }

    getOrder = (id) => {
        getOrderInfo(id)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({item: JSON.parse(text)})
                    })
                } else {
                    this.tryToRefresh(this.getOrder, response, id)
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

    componentWillUnmount() {
        this.state.mounted = false;
    }

    render() {
        if (this.state.redirect) {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        if (!this.state.item) {
            return (<div id="main">
                <Header login={true} getChecks={this.getChecks} search={false}/>
                {<div className={"details-wrapper"}/>}
                <Footer/>
            </div>)
        }
        return (
            <div id="main">
                <Header login={true} search={false}/>
                {<div className={"details-wrapper"}>
                    <div className={"details-row-wrapper"}>
                        <span> Статус: {this.state.item.status}</span>
                        <span> Адрес: {this.state.item.address.description}</span>
                        <span> Дата заказа: {this.state.item.created_at}</span>
                        <Table photo={"Фото"} submit={"Ссылка"} coordinateX={"Название"} coordinateY={"Y"} radius={"R"} shop={"Магазин"} price={"Цена"} distance={"Расстояние"} checks={this.state.item.items}/>
                    </div>
                    <div className={"details-description"}>Описание: {this.state.item.description}</div>
                    <div className={"push"}/>
                </div>}
                <Footer/>
            </div>)
    }
}

export default Order