(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRqstDialogController', BridSearchRqstDialogController);

    BridSearchRqstDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BridSearchRqst'];

    function BridSearchRqstDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BridSearchRqst) {
        var vm = this;

        vm.bridSearchRqst = entity;
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
            if (vm.bridSearchRqst.id !== null) {
                BridSearchRqst.update(vm.bridSearchRqst, onSaveSuccess, onSaveError);
            } else {
                BridSearchRqst.save(vm.bridSearchRqst, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intgPocApp:bridSearchRqstUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
