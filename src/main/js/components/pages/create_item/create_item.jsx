import React, {Component} from "react";
import {refresh, createItem, createShopItem} from "../../../api/request";
import Header from "../../organisms/header/header";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";
import "./create.css"
import {Navigate} from "react-router-dom";

class CreateItem extends Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            photo_url: "",
            price: 0,
            longitude: 0,
            latitude: 0,
            description: ""
        }
    }

    componentDidMount() {
        this.state.mounted = true;
        store.subscribe(() => {
            if (this.state.mounted)
                this.setState({reduxState: store.getState()});
        })
    }

    componentWillUnmount() {
        this.state.mounted = false;
    }

    submitInfo = (information) => {
        createItem(information)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({refreshAttempted: false})
                        this.setState({successMessage: "Успешно создано"})
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

    submitInfoShopItem = (information) => {
        createShopItem(information)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({refreshAttempted: false})
                        this.setState({successMessage: "Успешно создано"})
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

    tryToRefresh = (func, response, body = null) => {
        response.json().then(json => {
            if (json.message === "Expired or invalid JWT token" || json.message === "Access Denied") {
                refresh().then(response => response.json().then(json => {
                    if (response.ok) {
                        sessionStorage.setItem("token", json.accessToken)
                        sessionStorage.setItem("refreshToken", json.refreshToken)
                        if (body)
                            func(body)
                        else
                            func()
                    } else {
                        this.setError("important", json.message);
                        setTimeout(() => {
                            this.setError("important", '')
                            store.dispatch({type: "changeLogin", value: null})
                            this.setState({redirect: "/login"})
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
            case "photo_url": {
                this.setState({photo_url: value})
                break
            }
            case "tags": {
                this.setState({tags: value})
                break
            }
            case "shop_name": {
                this.setState({shop_name: value})
                break
            }
            case "item_id": {
                this.setState({item_id: value})
                break
            }
            case "item_price": {
                this.setState({item_price: value})
                break
            }
        }
    }

    sendData = (e) => {
        let information = {
            "name": this.state.name,
            "imageUrl": this.state.photo_url,
            "description": this.state.description,
            "categories": this.state.tags.split(",")
        };
        this.submitInfo(information)
    }

    sendDataShopItem = (e) => {
        let information = {
            "shopName": this.state.shop_name,
            "item_id": this.state.item_id,
            "price": this.state.item_price,
        };
        this.submitInfoShopItem(information)
    }

    render() {
        if (this.state.redirect) {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        return (
            <div id="main">
                <Header login={true} getChecks={this.getChecks} search={false}/>
                {<div className={"create-wrapper"}>
                    <span>Создание товара</span>
                    <div className={"row-wrapper"}>
                        <div className={"preview-wrapper"}>
                            <img src={this.state.photo_url}
                                 alt={"Введите корректную ссылку на фото для предпросмотра"}/>
                        </div>
                        <div className={"inputs-wrapper"}>
                            <div className={"main-inputs-wrapper"}>
                                <input type={"text"} name={"name"} onChange={this.handleUserInput}
                                       placeholder={"Название"}/>
                                <input type={"text"} name={"photo_url"} onChange={this.handleUserInput}
                                       placeholder={"Ссылка на фото"}/>
                                <input type={"text"} name={"tags"} onChange={this.handleUserInput}
                                       placeholder={"Тэги для поиска (через запятую)"}/>
                            </div>
                        </div>
                    </div>
                    <textarea name={"description"} onChange={this.handleUserInput}
                              placeholder={"Описание"}/>
                    <button onClick={this.sendData}>Создать</button>
                    <br/>
                    <br/>
                    <span>Привязка товара к магазину</span>
                    <div className={"row-wrapper"}>
                        <div className={"inputs-wrapper"}>
                            <div className={"main-inputs-wrapper"}>
                                <input type={"text"} name={"shop_name"} onChange={this.handleUserInput}
                                       placeholder={"Название магазина"}/>
                                <input type={"text"} name={"item_id"} onChange={this.handleUserInput}
                                       placeholder={"ID товара"}/>
                                <input type={"text"} name={"item_price"} onChange={this.handleUserInput}
                                       placeholder={"Цена товара"}/>
                            </div>
                        </div>
                    </div>
                    <button onClick={this.sendDataShopItem}>Создать</button>
                    <div className={"success-message"}>{this.state.successMessage}</div>
                </div>}
                <div className={"push"}/>
                <Footer/>
            </div>)
    }


}

export default CreateItem