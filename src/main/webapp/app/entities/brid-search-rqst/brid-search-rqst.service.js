(function() {
    'use strict';
    angular
        .module('intgPocApp')
        .factory('BridSearchRqst', BridSearchRqst);

    BridSearchRqst.$inject = ['$resource'];

    function BridSearchRqst ($resource) {
        var resourceUrl =  'api/brid-search-rqsts/:id';

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
