(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRqstController', BridSearchRqstController);

    BridSearchRqstController.$inject = ['BridSearchRqst'];

    function BridSearchRqstController(BridSearchRqst) {

        var vm = this;

        vm.bridSearchRqsts = [];

        loadAll();

        function loadAll() {
            BridSearchRqst.query(function(result) {
                vm.bridSearchRqsts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
