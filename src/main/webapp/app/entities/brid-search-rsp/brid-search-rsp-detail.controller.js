(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRspDetailController', BridSearchRspDetailController);

    BridSearchRspDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BridSearchRsp'];

    function BridSearchRspDetailController($scope, $rootScope, $stateParams, previousState, entity, BridSearchRsp) {
        var vm = this;

        vm.bridSearchRsp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intgPocApp:bridSearchRspUpdate', function(event, result) {
            vm.bridSearchRsp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
