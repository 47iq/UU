import React, {Component} from "react";
import {create, getBasket, refresh} from "../../../api/request";
import Header from "../../organisms/header/header";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";
import GoogleMapReact from 'google-map-react';
import {getDistance} from "../../../app/utils";
import './basket.css';
import {Navigate} from "react-router-dom";
import Table from "../../molecules/table/table";

class Basket extends Component {

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
        this.getBasket()
    }

    // getBasket = () => {
    //     getBasket()
    //         .then(response => {
    //             if (response.ok) {
    //                 response.text().then(text => {
    //                     this.setState({item: JSON.parse(text)})
    //                 })
    //             } else {
    //                 this.tryToRefresh(this.getBasket, response, id)
    //             }
    //         })
    // }

    getBasket = () => {
        this.setState({item: {
                items: [
                    {
                        id: "7",
                        name: "11111",
                        price: "1111",
                        imageURL: "11111"
                    },
                ]
            }})
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

    handleUserInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        switch (name) {
            case "name": {
                this.setState({name: value})
                break
            }
            case "description": {
                this.setState({description: value})
                break
            }
            case "promo_code": {
                this.setState({promo_code: value})
                break
            }
        }
    }

    sendData = (e) => {
        let information = {
            "name": this.state.name,
            "description": this.state.description,
            "promo_code": this.state.promo_code,
        };
        this.submitInfo(information)
    }

    submitInfo = (information) => {
        create(information)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({refreshAttempted: false})
                        this.setState({successMessage: "Заказ оформлен"})
                        setTimeout(() => {
                            this.setState({successMessage: null})
                            this.setState({redirect: "/"})
                        }, 1000)
                    })
                } else {
                    this.tryToRefresh(this.submitInfo, response, information)
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
                    <span className={"order"}>Оформление заказа</span>
                    <Table photo={"Фото"} submit={"Ссылка"} coordinateX={"Название"} coordinateY={"Y"} radius={"R"} shop={"Магазин"} price={"Цена"} distance={"Расстояние"} checks={this.state.item.items}/>
                    <div className={"details-description"}>
                        <span className={"address"}>Адрес:</span>
                        <input type={"text"} name={"name"} onChange={this.handleUserInput}
                                   placeholder={"Название"}/>
                        <textarea name={"description"} onChange={this.handleUserInput}
                                  placeholder={"Полный адрес"}/>
                        <input type={"text"} name={"promo_code"} onChange={this.handleUserInput}
                               placeholder={"Промокод"}/>
                        <button onClick={this.sendData}>Оформить</button>
                        <div className={"success-message"}>{this.state.successMessage}</div>
                    </div>
                    <div className={"push"}/>
                </div>}
                <Footer/>
            </div>)
    }
}

export default Basket