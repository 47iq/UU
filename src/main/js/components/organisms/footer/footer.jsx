import React, {Component} from "react";
import {Navigate} from "react-router-dom";
import './footer.css'

class Footer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            redirect: null
        }
    }

    handleClick = () => {
        this.setState({redirect: this.props.redirect})
    }

    render() {
        if (this.state.redirect) {
            return (
                <Navigate to={this.state.redirect} replace={true}/>
            )
        }
        if (this.props.redirect)
            return (
                <footer className={"footer"}>
                    <button onClick={this.handleClick}>+</button>
                </footer>

            )
        return (
            <footer className={"footer"}>
            </footer>
        )
    }
}

export default Footer