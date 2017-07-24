(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRspDialogController', BridSearchRspDialogController);

    BridSearchRspDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BridSearchRsp'];

    function BridSearchRspDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BridSearchRsp) {
        var vm = this;

        vm.bridSearchRsp = entity;
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
            if (vm.bridSearchRsp.id !== null) {
                BridSearchRsp.update(vm.bridSearchRsp, onSaveSuccess, onSaveError);
            } else {
                BridSearchRsp.save(vm.bridSearchRsp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intgPocApp:bridSearchRspUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
