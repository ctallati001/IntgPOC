(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchController', BridSearchController);

    BridSearchController.$inject = ['BridSearch'];

    function BridSearchController(BridSearch) {

        var vm = this;

        vm.bridSearches = [];

        loadAll();

        function loadAll() {
            BridSearch.query(function(result) {
                vm.bridSearches = result;
                vm.searchQuery = null;
            });
        }
    }
})();
