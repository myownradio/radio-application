// @flow

import React, { PropTypes } from 'react';

import css from './GreeterList.less';

const Greeter = ({ name }) => {
    return <li className={css.bar}>Hello, {name}!</li>;
};

Greeter.propTypes = {
    name: PropTypes.string.isRequired
};

export default Greeter;
