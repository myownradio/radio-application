// @flow

import React, { Component, PropTypes } from 'react';

class Greeter extends Component {
    static propTypes = {
        name: PropTypes.string.isRequired
    };

    render() {
        return <li className="bar">Hello, {this.props.name}!</li>;
    }
}

export default Greeter;
