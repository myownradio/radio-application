// @flow

import React from 'react';
import ReactDOM from 'react-dom';

import GreetList from './components/GreetList';

const names = ["Ivan", "Roman", "Anya", "Maria"];

ReactDOM.render(
    <GreetList names={names}/>,
    document.getElementById('test')
);
