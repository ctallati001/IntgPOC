(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchDialogController', BridSearchDialogController);

    BridSearchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'BridSearch', 'BridSearchRqst', 'BridSearchRsp'];

    function BridSearchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, BridSearch, BridSearchRqst, BridSearchRsp) {
        var vm = this;

        vm.bridSearch = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bridsearchrqsts = BridSearchRqst.query({filter: 'bridsearch-is-null'});
        $q.all([vm.bridSearch.$promise, vm.bridsearchrqsts.$promise]).then(function() {
            if (!vm.bridSearch.bridSearchRqst || !vm.bridSearch.bridSearchRqst.id) {
                return $q.reject();
            }
            return BridSearchRqst.get({id : vm.bridSearch.bridSearchRqst.id}).$promise;
        }).then(function(bridSearchRqst) {
            vm.bridsearchrqsts.push(bridSearchRqst);
        });
        vm.bridsearchrsps = BridSearchRsp.query({filter: 'bridsearch-is-null'});
        $q.all([vm.bridSearch.$promise, vm.bridsearchrsps.$promise]).then(function() {
            if (!vm.bridSearch.bridSearchRsp || !vm.bridSearch.bridSearchRsp.id) {
                return $q.reject();
            }
            return BridSearchRsp.get({id : vm.bridSearch.bridSearchRsp.id}).$promise;
        }).then(function(bridSearchRsp) {
            vm.bridsearchrsps.push(bridSearchRsp);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bridSearch.id !== null) {
                BridSearch.update(vm.bridSearch, onSaveSuccess, onSaveError);
            } else {
                BridSearch.save(vm.bridSearch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intgPocApp:bridSearchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
