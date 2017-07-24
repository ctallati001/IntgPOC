(function() {
    'use strict';
    angular
        .module('intgPocApp')
        .factory('AddressInfo', AddressInfo);

    AddressInfo.$inject = ['$resource'];

    function AddressInfo ($resource) {
        var resourceUrl =  'api/address-infos/:id';

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
