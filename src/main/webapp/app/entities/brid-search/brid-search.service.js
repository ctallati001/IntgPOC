(function() {
    'use strict';
    angular
        .module('intgPocApp')
        .factory('BridSearch', BridSearch);

    BridSearch.$inject = ['$resource'];

    function BridSearch ($resource) {
        var resourceUrl =  'api/brid-searches/:id';

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
