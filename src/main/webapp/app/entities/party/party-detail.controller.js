(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('PartyDetailController', PartyDetailController);

    PartyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Party', 'AddressInfo'];

    function PartyDetailController($scope, $rootScope, $stateParams, previousState, entity, Party, AddressInfo) {
        var vm = this;

        vm.party = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intgPocApp:partyUpdate', function(event, result) {
            vm.party = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
