(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRqstDeleteController',BridSearchRqstDeleteController);

    BridSearchRqstDeleteController.$inject = ['$uibModalInstance', 'entity', 'BridSearchRqst'];

    function BridSearchRqstDeleteController($uibModalInstance, entity, BridSearchRqst) {
        var vm = this;

        vm.bridSearchRqst = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BridSearchRqst.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
