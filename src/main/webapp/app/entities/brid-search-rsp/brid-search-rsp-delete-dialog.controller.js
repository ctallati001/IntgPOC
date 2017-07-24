(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRspDeleteController',BridSearchRspDeleteController);

    BridSearchRspDeleteController.$inject = ['$uibModalInstance', 'entity', 'BridSearchRsp'];

    function BridSearchRspDeleteController($uibModalInstance, entity, BridSearchRsp) {
        var vm = this;

        vm.bridSearchRsp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BridSearchRsp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
