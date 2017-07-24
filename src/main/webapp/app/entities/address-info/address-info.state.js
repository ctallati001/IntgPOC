(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('address-info', {
            parent: 'entity',
            url: '/address-info',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AddressInfos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/address-info/address-infos.html',
                    controller: 'AddressInfoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('address-info-detail', {
            parent: 'address-info',
            url: '/address-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AddressInfo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/address-info/address-info-detail.html',
                    controller: 'AddressInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AddressInfo', function($stateParams, AddressInfo) {
                    return AddressInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'address-info',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('address-info-detail.edit', {
            parent: 'address-info-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-info/address-info-dialog.html',
                    controller: 'AddressInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AddressInfo', function(AddressInfo) {
                            return AddressInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('address-info.new', {
            parent: 'address-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-info/address-info-dialog.html',
                    controller: 'AddressInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                line1: null,
                                line2: null,
                                state: null,
                                city: null,
                                zipCode: null,
                                countryOfResidence: null,
                                taxCountry: null,
                                countriesOfInc: null,
                                kyaActCountries: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('address-info', null, { reload: 'address-info' });
                }, function() {
                    $state.go('address-info');
                });
            }]
        })
        .state('address-info.edit', {
            parent: 'address-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-info/address-info-dialog.html',
                    controller: 'AddressInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AddressInfo', function(AddressInfo) {
                            return AddressInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('address-info', null, { reload: 'address-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('address-info.delete', {
            parent: 'address-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/address-info/address-info-delete-dialog.html',
                    controller: 'AddressInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AddressInfo', function(AddressInfo) {
                            return AddressInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('address-info', null, { reload: 'address-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
