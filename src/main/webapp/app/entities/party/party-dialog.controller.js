(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('PartyDialogController', PartyDialogController);

    PartyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Party', 'AddressInfo'];

    function PartyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Party, AddressInfo) {
        var vm = this;

        vm.party = entity;
        vm.clear = clear;
        vm.save = save;
        vm.addressinfos = AddressInfo.query({filter: 'party-is-null'});
        $q.all([vm.party.$promise, vm.addressinfos.$promise]).then(function() {
            if (!vm.party.addressInfo || !vm.party.addressInfo.id) {
                return $q.reject();
            }
            return AddressInfo.get({id : vm.party.addressInfo.id}).$promise;
        }).then(function(addressInfo) {
            vm.addressinfos.push(addressInfo);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.party.id !== null) {
                Party.update(vm.party, onSaveSuccess, onSaveError);
            } else {
                Party.save(vm.party, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intgPocApp:partyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
