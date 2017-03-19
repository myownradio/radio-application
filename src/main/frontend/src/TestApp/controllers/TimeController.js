// @flow

export default ($scope, $interval) => {
    $interval(() => {
        $scope.time = new Date().toTimeString()
    }, 1000);
};