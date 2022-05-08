import React, {Component} from "react";
import './appHeader.css'
import SearchForm from "../../organisms/search/search";

class AppHeader extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <header className={"header"}>
                <div className={"header-name-wrapper"}>
                    <h1><span>Your</span><span>Union</span></h1>
                </div>
                <div className={"search"}>
                    {this.props.isLoggedIn ? <SearchForm getChecks={this.props.getChecks}/> : ""}
                </div>
                <div>
                    {this.props.isLoggedIn ? <button className={"logout"} onClick={this.props.logout}>Выйти</button> : ""}
                </div>
            </header>
        )
    }
}

export default AppHeader