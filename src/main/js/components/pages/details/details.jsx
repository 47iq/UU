import React, {Component} from "react";
import {getDetails, refresh} from "../../../api/request";
import Header from "../../organisms/header/header";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";
import GoogleMapReact from 'google-map-react';
import "./details.css"
import {getDistance} from "../../../app/utils";

class Details extends Component {

    constructor(props) {
        super(props);
        this.state = {
            id: null,
            item: null,
        }
    }

    componentDidMount() {
        this.state.mounted = true;
        store.subscribe(() => {
            if (this.state.mounted)
                this.setState({reduxState: store.getState()});
        })
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
                    refresh().then(response => response.json().then(json => {
                        if (response.ok) {
                            sessionStorage.setItem("token", json.accessToken)
                            sessionStorage.setItem("refreshToken", json.refreshToken)
                            this.getChecks()
                        } else {
                            this.setError("important", "Session has expired");
                            setTimeout(() => {
                                this.setError("important", '')
                                store.dispatch({type: "changeLogin", value: null})
                            }, 3000)
                        }
                    }))
                }
            })
    }

    componentWillUnmount() {
        this.state.mounted = false;
    }

    render() {
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
                        <div className={"details-image-wrapper"}>
                            <img src={this.state.item.imageURL}
                                 alt={"Фото недоступно"}/>
                        </div>
                        <div className={"outputs-wrapper"}>
                                <span id={"item-name"}>{this.state.item.name}</span>
                                <span>Магазин: {this.state.item.userName}</span>
                                <span>Цена: {this.state.item.price} рублей</span>
                                <span>Расстояние: {getDistance(store.getState().coordinates, {longitude: this.state.item.coordinatesX, latitude: this.state.item.coordinatesY})}</span>
                        </div>
                    </div>
                    <div className={"details-description"}>Описание: {this.state.description}</div>
                </div>}
                <Footer/>
            </div>)
    }
}

export default Details