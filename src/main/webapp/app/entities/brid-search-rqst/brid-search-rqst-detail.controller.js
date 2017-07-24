(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRqstDetailController', BridSearchRqstDetailController);

    BridSearchRqstDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BridSearchRqst'];

    function BridSearchRqstDetailController($scope, $rootScope, $stateParams, previousState, entity, BridSearchRqst) {
        var vm = this;

        vm.bridSearchRqst = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intgPocApp:bridSearchRqstUpdate', function(event, result) {
            vm.bridSearchRqst = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
