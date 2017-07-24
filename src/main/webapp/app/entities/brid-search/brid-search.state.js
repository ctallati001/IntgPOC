(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('brid-search', {
            parent: 'entity',
            url: '/brid-search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BridSearches'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brid-search/brid-searches.html',
                    controller: 'BridSearchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('brid-search-detail', {
            parent: 'brid-search',
            url: '/brid-search/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BridSearch'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brid-search/brid-search-detail.html',
                    controller: 'BridSearchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BridSearch', function($stateParams, BridSearch) {
                    return BridSearch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'brid-search',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('brid-search-detail.edit', {
            parent: 'brid-search-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search/brid-search-dialog.html',
                    controller: 'BridSearchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BridSearch', function(BridSearch) {
                            return BridSearch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brid-search.new', {
            parent: 'brid-search',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search/brid-search-dialog.html',
                    controller: 'BridSearchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ofacRqstInfold: null,
                                status: null,
                                addStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('brid-search', null, { reload: 'brid-search' });
                }, function() {
                    $state.go('brid-search');
                });
            }]
        })
        .state('brid-search.edit', {
            parent: 'brid-search',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search/brid-search-dialog.html',
                    controller: 'BridSearchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BridSearch', function(BridSearch) {
                            return BridSearch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brid-search', null, { reload: 'brid-search' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brid-search.delete', {
            parent: 'brid-search',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search/brid-search-delete-dialog.html',
                    controller: 'BridSearchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BridSearch', function(BridSearch) {
                            return BridSearch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brid-search', null, { reload: 'brid-search' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
