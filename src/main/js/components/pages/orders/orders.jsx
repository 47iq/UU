import React, {Component} from "react";
import CoordinatesForm from "../../organisms/coordinatesForm/coordinatesForm";
import Table from "../../molecules/table/table";
import Graph from "../../atoms/graph/graph";
import {addToBasket, check, clear, getOrders, refresh} from "../../../api/request";
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

    getChecks = () => {
        getOrders()
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

    setError = (name, message) => {
        let form = Object.assign({}, this.state.formErrors);
        form[name] = message;
        if(this.state.mounted)
            this.setState({formErrors: form})
    }

    render() {
        const handleClick = (e) => {
            this.setState({redirect: "/order/?id=" + e.target.name})
        }
        return (
            <div id="main">
                <Header login={true} getChecks={this.getChecks} search={true}/>
                {<div className={"main-wrapper"}>
                    <div className={"filter-wrapper"}>
                        В разработке...
                    </div>
                    <div className={"main-table"}>
                        <table className="table is-bordered is-hoverable is-fullwidth has-text-centered">
                            <thead>
                            <tr>
                                <th>
                                    Дата создания
                                </th>
                                <th className={"name-column"} name="name">
                                    Статус
                                </th>
                                <th name="price">
                                    Код заказа
                                </th>
                                <th>
                                    Ссылка
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            {(this.props.checks) ? this.props.checks.map(function (check) {
                                    return (
                                        <tr key={check.id}>
                                            <td>{check.created_at}</td>
                                            <td>{check.orderStatus}</td>
                                            <td>{check.id}</td>
                                            <td>
                                                <button className={"item_button"} name={check.id} onClick={handleClick}>Смотреть</button>
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
                </div>}
                <div className={"push"}/>
                <Footer/>
            </div>)
    }


}

export default Favourites