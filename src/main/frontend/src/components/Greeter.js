// @flow

import React from 'react';
import { Component, PropTypes } from 'react';

const Greet = (name: number): number => `Hello, ${name}!`;

class Greeter extends Component {
    static propTypes = {
        name: PropTypes.string.isRequired
    };

    render() {
        return <span>{Greet(this.props.name)}</span>
    }
}

export default Greeter;
