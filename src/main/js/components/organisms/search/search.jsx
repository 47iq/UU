import React, {Component} from "react";
import './search.css'
import {getAutocomplete, refresh} from "../../../api/request";
import store from "../../../app/store";

class SearchForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            xValid: false,
            yValid: false,
            rValid: false,
            list: []
        }
        this.search = React.createRef();
    }

    getList = (query) => {
        getAutocomplete(query)
            .then(response => {
                if (response.ok) {
                    response.text().then(text => {
                        this.setState({list: JSON.parse(text)})
                    })
                } else {
                    this.tryToRefresh(this.getDetails, response, query)
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

    submitSearch = (e) => {
        if(e.key === 'Enter') {
            this.props.getChecks(e.target.value)
        } else {
            this.getList(e.target.value)
        }
    }

    submitSearchButton = (e) => {
        let search = this.search.current.value
        this.props.getChecks(search)
    }

    render() {
        return (
            <div className={"search-wrapper"}>
                <input list={"options"} ref={this.search} type={"text"} id={"search-input"} placeholder={"Введите строку для поиска"} onKeyDown={this.submitSearch}/>
                <datalist id={"options"}>
                    {this.state.list.map((item, key)=>
                        <option key={key} value={item}/>
                    )}
                </datalist>
                <div className="input-group-btn">
                    <button className="btn btn-default search-button" type="submit" onClick={this.submitSearchButton}>Искать</button>
                </div>
            </div>
        )
    }
}

export default SearchForm