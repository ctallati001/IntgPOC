(function() {
    'use strict';
    angular
        .module('intgPocApp')
        .factory('BridSearchRsp', BridSearchRsp);

    BridSearchRsp.$inject = ['$resource'];

    function BridSearchRsp ($resource) {
        var resourceUrl =  'api/brid-search-rsps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
