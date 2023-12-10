import React, {Component} from "react";
import {deleteFromFavourites, getFavouriteItems, refresh} from "../../../api/request";
import Header from "../../organisms/header/header";
import store from "../../../app/store";
import Footer from "../../organisms/footer/footer";
import {Navigate} from "react-router-dom";

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
        getFavouriteItems()
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({items: JSON.parse(text)})
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
            this.setState({redirect: "/details/?id=" + e.target.name})
        }
        const handleDelete = (e) => {
            deleteFromFavourites(e.target.name)
            setTimeout(this.getChecks, 1000)
        }
        if (this.state.redirect) {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        return (
            <div id="main">
                <Header login={true} getChecks={this.getChecks} search={true}/>
                {<div className={"main-wrapper"}>
                    <div className={"filter-wrapper"}>
                        В разработке...
                    </div>
                    <div className={"main-table"}>
                        <div className={"table-wrapper"}>
                            <table className="table is-bordered is-hoverable is-fullwidth has-text-centered">
                                <thead>
                                <tr>
                                    <th>
                                        Фото
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
                                        Убрать из избранного
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                {(this.state.items) ? this.state.items.map(function (check) {
                                        return (
                                            <tr key={check.id}>
                                                <td><img src={check.imageUrl} /></td>
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
                    </div>
                </div>}
                <div className={"push"}/>
                <Footer/>
            </div>)
    }


}

export default Favourites