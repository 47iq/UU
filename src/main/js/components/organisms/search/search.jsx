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
        this.search = React.createRef();
    }

    submitSearch = (e) => {
        if(e.key === 'Enter') {
            this.props.getChecks(e.target.value)
        }
    }

    submitSearchButton = (e) => {
        let search = this.search.current.value
        this.props.getChecks(search)
    }

    render() {
        return (
            <div className={"search-wrapper"}>
                <input ref={this.search} type={"text"} id={"search-input"} placeholder={"Введите строку для поиска"} onKeyDown={this.submitSearch}/>
                <div className="input-group-btn">
                    <button className="btn btn-default search-button" type="submit" onClick={this.submitSearchButton}>Искать</button>
                </div>
            </div>
        )
    }
}

export default SearchForm