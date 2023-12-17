import React, {Component} from "react";

import './table.css';
import store from "../../../app/store";
import {Navigate} from "react-router-dom";

class Table extends Component {

    constructor(props) {
        super(props);
        this.state = {data: []};
    }

    componentDidMount() {
        store.subscribe(() => {
            this.setState({reduxState: store.getState()});
        })
    }

    handleSubmit = (e) => {
        console.log(e)
    }

    render() {
        if (!this.props.checks || this.props.checks.length === 0)
            return (
                <div className={"no-results"}>
                    Нет подходящих результатов по запросу
                </div>
            )

        const handleClick = (e) => {
            this.setState({redirect: "/details/?id=" + e.target.name})
        }
        if (this.state.redirect) {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        return (

            <div className={"table-wrapper"}>
                <table className="table is-bordered is-hoverable is-fullwidth has-text-centered">
                    <thead>
                    <tr>
                        <th>
                            {this.props.photo}
                        </th>
                        <th className={"name-column"} name="name">
                            {this.props.name}
                        </th>
                        <th name="price">
                            {this.props.price}
                        </th>
                        <th>
                            {this.props.submit}
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {(this.props.checks) ? this.props.checks.map(function (check) {
                            return (
                                <tr key={check.id}>
                                    <td><img src={check.imageUrl} /></td>
                                    <td>{check.name}</td>
                                    <td>{check.price + ' рублей'}</td>
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
            </div>)
    }
}

export default Table