// @flow

import React from 'react';
import { Component, PropTypes } from 'react';

class Greeter extends Component {
    static propTypes = {
        name: PropTypes.string.isRequired
    };

    render() {
        return <span>Hello, {this.props.name}!</span>
    }
}

export default Greeter;
