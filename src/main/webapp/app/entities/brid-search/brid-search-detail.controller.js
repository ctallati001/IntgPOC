(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchDetailController', BridSearchDetailController);

    BridSearchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BridSearch', 'BridSearchRqst', 'BridSearchRsp'];

    function BridSearchDetailController($scope, $rootScope, $stateParams, previousState, entity, BridSearch, BridSearchRqst, BridSearchRsp) {
        var vm = this;

        vm.bridSearch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intgPocApp:bridSearchUpdate', function(event, result) {
            vm.bridSearch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
