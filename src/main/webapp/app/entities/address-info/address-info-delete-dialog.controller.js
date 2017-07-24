(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('AddressInfoDeleteController',AddressInfoDeleteController);

    AddressInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'AddressInfo'];

    function AddressInfoDeleteController($uibModalInstance, entity, AddressInfo) {
        var vm = this;

        vm.addressInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AddressInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
