import React, {Component} from "react";
import CoordinatesForm from "../../organisms/coordinatesForm/coordinatesForm";
import Table from "../../molecules/table/table";
import Graph from "../../atoms/graph/graph";
import {check, clear, create, getAll, refresh} from "../../../api/request";
import Header from "../../organisms/header/header";
import {clearCanvas, drawCanvas, drawPoint} from "../../../app/canvas";
import store from "../../../app/store";

class Main extends Component {

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
        create(information)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({refreshAttempted: false})
                    })
                } else {
                    this.tryToRefresh(this.submitInfo, response, information)
                }
            })
    }

    tryToRefresh = (func, response, body = null) => {
        response.json().then(json => {
            if (json.message === "Expired or invalid JWT token" || json.message === "Access denied") {
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
            case "photo_url": {
                this.setState({photo_url: value})
                break
            }
            case "price": {
                this.setState({price: value})
                break
            }
            case "longitude": {
                this.setState({longitude: value})
                break
            }
            case "latitude": {
                this.setState({longitude: value})
                break
            }
        }
        this.setState({name: value})
    }

    sendData = (e) => {
        let information = {
            "name": this.state.name,
            "imageUrl": this.state.photo_url,
            "price": this.state.price,
            "coordinatesX": this.state.longitude,
            "coordinatesY": this.state.latitude,
            "description": this.state.description,
            "tags": ["TEST1"]
        };
        this.submitInfo(information)
    }

    render() {
        return (
            <div id="main">
                <Header login={true} getChecks={this.getChecks} search={false}/>
                {<div className={"create-wrapper"}>
                    <input type={"text"} name={"name"} onChange={this.handleUserInput} placeholder={"Название"}/>
                    <input type={"textarea"} name={"description"} onChange={this.handleUserInput} placeholder={"Описание"}/>
                    <input type={"text"} name={"photo_url"} onChange={this.handleUserInput} placeholder={"Ссылка на фото"}/>
                    <input type={"text"} name={"price"} onChange={this.handleUserInput} placeholder={"Стоимость (руб)"}/>
                    <input type={"text"} name={"longitude"} onChange={this.handleUserInput} placeholder={"Долгота"}/>
                    <input type={"text"} name={"latitude"} onChange={this.handleUserInput} placeholder={"Широта"}/>
                    <button type={"submit"} onClick={this.sendData}/>
                </div>}
            </div>)
    }


}

export default Main