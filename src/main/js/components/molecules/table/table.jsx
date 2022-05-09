import React, {Component} from "react";

import './table.css';
import store from "../../../app/store";
import {getDistance} from "../../../app/utils";
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
        const setSortedField = (e) => {
            let name = e.target.name
            let checks = store.getState().checks
            checks.sort((a, b) => {
                if (a[name] < b[name]) {
                    return -1;
                }
                if (a[name] > b[name]) {
                    return 1;
                }
                return 0;
            })
            store.dispatch({type: "setChecks", value: checks})
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
                        <th className={"name-column"} name="name" onClick={setSortedField}>
                            {this.props.coordinateX}
                        </th>
                        <th name="shop" onClick={setSortedField}>
                            {this.props.shop}
                        </th>
                        <th name="distance" onClick={setSortedField}>
                            {this.props.distance}
                        </th>
                        <th name="price" onClick={setSortedField}>
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
                                <tr key={check.pointId}>
                                    <td><img src={check.imageURL} /></td>
                                    <td>{check.name}</td>
                                    <td>{check.userName}</td>
                                    <td>{getDistance(store.getState().coordinates, {longitude: check.coordinatesX, latitude: check.coordinatesY})}</td>
                                    <td>{check.price.toString() + ' рублей'}</td>
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