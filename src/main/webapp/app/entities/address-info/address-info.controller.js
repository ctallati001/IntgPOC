(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('AddressInfoController', AddressInfoController);

    AddressInfoController.$inject = ['AddressInfo'];

    function AddressInfoController(AddressInfo) {

        var vm = this;

        vm.addressInfos = [];

        loadAll();

        function loadAll() {
            AddressInfo.query(function(result) {
                vm.addressInfos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
