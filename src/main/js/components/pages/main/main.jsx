import React, {Component} from "react";
import Table from "../../molecules/table/table";
import "./main.css"
import {clear, getAllItems, refresh} from "../../../api/request";
import Header from "../../organisms/header/header";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";

class Main extends Component {

    constructor(props) {
        super(props);
        this.state = {
            output: React.createRef(),
            x_form: '',
            y_form: '',
            r_form: '',
            refreshAttempted: false,
            formErrors: {
                x: '',
                y: '',
                r: '',
                important: ''
            },
        }
    }

    componentDidMount() {
        this.state.mounted = true;
        store.subscribe(() => {
            if (this.state.mounted)
                this.setState({reduxState: store.getState()});
        })
        this.getChecks("")
    }

    componentWillUnmount() {
        this.state.mounted = false;
    }

    getChecks = (query) => {
        if (query === undefined)
            query = ""
        getAllItems(query)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        store.dispatch({type: "setChecks", value: JSON.parse(text)})
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

    clear = () => {
        clear().then(response => {
            if(this.state.mounted)
                if (response.ok) {
                    this.getChecks()
                } else {
                    this.tryToRefresh(this.clear, response)
                }
        })
    }

    setError = (name, message) => {
        let form = Object.assign({}, this.state.formErrors);
        form[name] = message;
        if(this.state.mounted)
            this.setState({formErrors: form})
    }

    render() {
        return (
            <div id="main">
                <Header login={true} getChecks={this.getChecks} search={true}/>
                {<div className={"main-wrapper"}>
                    <div className={"filter-wrapper"}>
                        В разработке...
                    </div>
                    <div className={"main-table"}>
                        <Table photo={"Фото"} submit={"Ссылка"} coordinateX={"Название"} coordinateY={"Y"} radius={"R"} shop={"Магазин"} price={"Цена"} distance={"Расстояние"} checks={store.getState().checks}/>
                    </div>
                </div>}
                <div className={"push"}/>
                <Footer redirect={"/create"}/>
            </div>)
    }


}

export default Main