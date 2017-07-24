(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .controller('BridSearchRspController', BridSearchRspController);

    BridSearchRspController.$inject = ['BridSearchRsp'];

    function BridSearchRspController(BridSearchRsp) {

        var vm = this;

        vm.bridSearchRsps = [];

        loadAll();

        function loadAll() {
            BridSearchRsp.query(function(result) {
                vm.bridSearchRsps = result;
                vm.searchQuery = null;
            });
        }
    }
})();
