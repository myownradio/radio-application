// @flow

import React, { Component, PropTypes } from 'react';

import css from './GreeterList.less';

class Greeter extends Component {
    static propTypes = {
        name: PropTypes.string.isRequired
    };

    render() {
        return <li className={css.bar}>Hello, {this.props.name}!</li>;
    }
}

export default Greeter;
