(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('AddressInfoDialogController', AddressInfoDialogController);

    AddressInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AddressInfo'];

    function AddressInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AddressInfo) {
        var vm = this;

        vm.addressInfo = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.addressInfo.id !== null) {
                AddressInfo.update(vm.addressInfo, onSaveSuccess, onSaveError);
            } else {
                AddressInfo.save(vm.addressInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intgPocApp:addressInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
