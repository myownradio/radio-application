// @flow

import Angular from 'angular';

import TestAppControllers from './controllers';

import styles from './index.css';

export default Angular.module("TestApp", [])
    .run(($rootScope) => $rootScope.styles = styles)
    .controller(TestAppControllers);
