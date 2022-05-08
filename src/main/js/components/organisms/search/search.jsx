import React, {Component} from "react";
import './search.css'

class SearchForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            xValid: false,
            yValid: false,
            rValid: false,
        }
    }

    handleSearch() {
        this.props.getChecks()
    }

    render() {
        return (
            <div className={"search-wrapper"}>
                <form id="search-form">
                    <input type={"text"} id={"search-input"} placeholder={"Введите строку для поиска"} onSubmit={this.handleSearch}/>
                </form>
            </div>
        )
    }
}

export default SearchForm