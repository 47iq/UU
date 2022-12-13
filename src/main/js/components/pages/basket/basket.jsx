import React, {Component} from "react";
import {create, deleteFromBasket, getBasket, refresh, getAddresses, addAddress, createOrder} from "../../../api/request";
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
            newAddress: false,
            address_id: null
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
        this.getAddresses()
    }

    getBasket = () => {
        getBasket()
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({item: JSON.parse(text)})
                    })
                } else {
                    this.tryToRefresh(this.getBasket, response)
                }
            })
    }

    getAddresses = () => {
        getAddresses()
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({addresses: JSON.parse(text)})
                    })
                } else {
                    this.tryToRefresh(this.getBasket, response)
                }
            })
    }

    addAddress = (info) => {
        addAddress(info)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({addresses: JSON.parse(text)})
                    })
                } else {
                    this.tryToRefresh(this.getBasket, response)
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
        if (this.state.addresses && this.state.addresses.length > 0)
            this.setState({address_id: this.state.addresses[0].name})
        if (this.state.newAddress) {
            let information = {
                "name": this.state.name,
                "description": this.state.description,
                "longitude": 0,
                "latitude": 0,
            }
            this.addAddress(information)
            this.setState({address_id: this.state.name})
            setTimeout(this.getData, 500)
        }
        setTimeout(() => {
            console.log(this.state.addresses.filter(x => x.name === this.state.address_id))
            let information = {
                "addrId": this.state.addresses.filter(x => x.name === this.state.address_id)[0].id,
                "promo_code": this.state.promo_code,
            };
            this.submitInfo(information)
        }, 1000)
    }

    submitInfo = (information) => {
        createOrder(information)
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
        const handleClick = (e) => {
            this.setState({redirect: "/details/?id=" + e.target.name})
        }
        const handleDelete = (e) => {
            deleteFromBasket(e.target.name)
            setTimeout(this.getData, 1000)
        }
        const onAddressChanged = (e) => {
            if (e.target.value === "Использовать существующий")
                this.setState({
                    newAddress: false,
                });
            else
                this.setState({
                    newAddress: true,
                });
        };
        const onAddressOptionChanged = (e) => {
            this.setState({ [e.target.name]: e.target.value });
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
                    <div className={"table-wrapper"}>
                        <table className="table is-bordered is-hoverable is-fullwidth has-text-centered">
                            <thead>
                            <tr>
                                <th>
                                    Магазин
                                </th>
                                <th className={"name-column"} name="name">
                                    Название
                                </th>
                                <th name="price">
                                    Стоимость
                                </th>
                                <th>
                                    Ссылка
                                </th>
                                <th>
                                    Убрать из корзины
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            {(this.state.item.shopItems) ? this.state.item.shopItems.map(function (check) {
                                    return (
                                        <tr key={check.id}>
                                            <td>{check.shopName}</td>
                                            <td>{check.name}</td>
                                            <td>{check.price + ' рублей'}</td>
                                            <td>
                                                <button className={"item_button"} name={check.id} onClick={handleClick}>Смотреть</button>
                                            </td>
                                            <td>
                                                <button className={"item_button"} name={check.id} onClick={handleDelete}>Убрать</button>
                                            </td>
                                        </tr>
                                    );
                                }) :
                                <tr>
                                    <td colSpan={5}>Loading...</td>
                                </tr>}
                            </tbody>
                        </table>
                    </div>
                    <div className={"details-description"}>
                        <span className={"address"}>Адрес:</span>
                        <div><input type="radio" id="address1" name="address" checked={!this.state.newAddress} onChange={onAddressChanged} value="Использовать существующий"/>Использовать существующий</div>
                        <div>
                            <select name="address_id" id="address_id" value={this.state.address_id} onChange={onAddressOptionChanged}>
                                {(!this.state.newAddress && this.state.addresses) ? this.state.addresses.map(function (address) {
                                    return (
                                        <option value={address.name}>{address.name}</option>
                                    );
                                }) :
                                    <option value=""/>
                                }
                            </select>
                        </div>
                        <div><input type="radio" id="address2" name="address" checked={this.state.newAddress} onChange={onAddressChanged} value="Создать новый"/>Создать новый</div>
                        {(this.state.newAddress) &&
                            <div>
                                <input type={"text"} name={"name"} onChange={this.handleUserInput}
                                       placeholder={"Название"}/>
                                <textarea name={"description"} onChange={this.handleUserInput}
                                      placeholder={"Полный адрес"}/>)}
                            </div>
                        }
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