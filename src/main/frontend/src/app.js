// @flow

import React from 'react';
import ReactDOM from 'react-dom';

import Greeter from './components/Greeter';

const ivanGreeter = <Greeter name="Ivan"/>;

ReactDOM.render(
    ivanGreeter,
    document.getElementById('test')
);
