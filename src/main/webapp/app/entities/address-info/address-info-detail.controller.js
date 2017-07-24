(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('AddressInfoDetailController', AddressInfoDetailController);

    AddressInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AddressInfo'];

    function AddressInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, AddressInfo) {
        var vm = this;

        vm.addressInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intgPocApp:addressInfoUpdate', function(event, result) {
            vm.addressInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
