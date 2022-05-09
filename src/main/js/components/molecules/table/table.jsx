import React, {Component} from "react";

import './table.css';
import store from "../../../app/store";
import {getDistance} from "../../../app/utils";

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

    render() {
        if (!this.props.checks || this.props.checks.length === 0)
            return (
                <div className={"table-wrapper"}/>
            )

        function setSortedField(name) {
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

        function handleSubmit(id) {
            console.log(id)
        }

        return (
            <div className={"table-wrapper"}>
                <table className="table is-bordered is-hoverable is-fullwidth has-text-centered">
                    <thead>
                    <tr>
                        <th>
                            {this.props.photo}
                        </th>
                        <th className={"name-column"} onClick={() => setSortedField('X')}>
                            {this.props.coordinateX}
                        </th>
                        <th onClick={() => setSortedField('Y')}>
                            {this.props.shop}
                        </th>
                        <th onClick={() => setSortedField('ldt')}>
                            {this.props.distance}
                        </th>
                        <th onClick={() => setSortedField('ldt')}>
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
                                    <td><img src={'https://images.pexels.com/photos/20787/pexels-photo.jpg?auto=compress&cs=tinysrgb&h=350'} /></td>
                                    <td>{check.name}</td>
                                    <td>{check.userId}</td>
                                    <td>{getDistance(store.getState().coordinates, {longitude: check.coordinatesX, latitude: check.coordinatesY})}</td>
                                    <td>{check.price.toString() + ' рублей'}</td>
                                    <td>
                                        <button className={"item_button"} onClick={handleSubmit(check.id)}>Смотреть</button>
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