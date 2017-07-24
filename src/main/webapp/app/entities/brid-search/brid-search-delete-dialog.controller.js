(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchDeleteController',BridSearchDeleteController);

    BridSearchDeleteController.$inject = ['$uibModalInstance', 'entity', 'BridSearch'];

    function BridSearchDeleteController($uibModalInstance, entity, BridSearch) {
        var vm = this;

        vm.bridSearch = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BridSearch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
