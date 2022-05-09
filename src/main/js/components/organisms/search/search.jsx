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

    submitSearch = (e) => {
        if(e.key === 'Enter') {
            this.props.getChecks(e.target.value)
        }
    }

    render() {
        return (
            <div className={"search-wrapper"}>
                <input type={"text"} id={"search-input"} placeholder={"Введите строку для поиска"} onKeyDown={this.submitSearch}/>
                <div className="input-group-btn">
                    <button className="btn btn-default" type="submit">🔎</button>
                </div>
            </div>
        )
    }
}

export default SearchForm