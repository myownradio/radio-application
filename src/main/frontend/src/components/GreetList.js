// @flow

import React, { PropTypes } from 'react';

import Greeter from './Greeter';

import css from './GreeterList.less';

const GreetList = ({ names }) => {
    const greeterList = names.map((name, i) =>
        <Greeter key={i} name={name} />
    );

    return <ul className={css.foo}>{greeterList}</ul>;
};

GreetList.propTypes = {
    names: PropTypes.arrayOf(PropTypes.string).isRequired
};

export default GreetList;