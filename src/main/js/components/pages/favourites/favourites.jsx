import React, {Component} from "react";
import CoordinatesForm from "../../organisms/coordinatesForm/coordinatesForm";
import Table from "../../molecules/table/table";
import Graph from "../../atoms/graph/graph";
import {check, clear, getFavouriteItems, refresh} from "../../../api/request";
import Header from "../../organisms/header/header";
import {clearCanvas, drawCanvas, drawPoint} from "../../../app/canvas";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";

class Favourites extends Component {

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

    // getChecks = () => {
    //     getFavouriteItems({username: store.getState().login})
    //         .then(response => {
    //             if (response.ok) {
    //                 response.text().then(text => {
    //                     store.dispatch({type: "setChecks", value: JSON.parse(text)})
    //                 })
    //             } else {
    //                 refresh().then(response => response.json().then(json => {
    //                     if (response.ok) {
    //                         sessionStorage.setItem("token", json.accessToken)
    //                         sessionStorage.setItem("refreshToken", json.refreshToken)
    //                         this.getChecks()
    //                     } else {
    //                         this.setError("important", "Session has expired");
    //                         setTimeout(() => {
    //                             this.setError("important", '')
    //                             store.dispatch({type: "changeLogin", value: null})
    //                         }, 3000)
    //                     }
    //                 }))
    //             }
    //         })
    // }

    getChecks = () => {
        this.setState({
            checks: [
                    {
                        id: "7",
                        name: "11111",
                        price: "1111",
                        imageURL: "11111"
                    },
                ]
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
                        <Table photo={"Фото"} submit={"Ссылка"} coordinateX={"Название"} coordinateY={"Y"} radius={"R"} shop={"Магазин"} price={"Цена"} distance={"Расстояние"} checks={this.state.checks}/>
                    </div>
                </div>}
                <div className={"push"}/>
                <Footer/>
            </div>)
    }


}

export default Favourites