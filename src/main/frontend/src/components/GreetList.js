// @flow

import React, { Component, PropTypes } from 'react';

import Greeter from './Greeter';

import './GreeterList.less';

class GreetList extends Component {
    static propTypes = {
        names: PropTypes.arrayOf(PropTypes.string).isRequired
    };

    render() {
        const greeterList = this.props.names.map((name, i) =>
            <Greeter key={i} name={name} />
        );

        return <ul className="foo">{greeterList}</ul>;
    }
}

export default GreetList;