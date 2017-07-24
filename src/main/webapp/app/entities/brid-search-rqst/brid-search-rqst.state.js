(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('brid-search-rqst', {
            parent: 'entity',
            url: '/brid-search-rqst',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BridSearchRqsts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brid-search-rqst/brid-search-rqsts.html',
                    controller: 'BridSearchRqstController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('brid-search-rqst-detail', {
            parent: 'brid-search-rqst',
            url: '/brid-search-rqst/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BridSearchRqst'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brid-search-rqst/brid-search-rqst-detail.html',
                    controller: 'BridSearchRqstDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BridSearchRqst', function($stateParams, BridSearchRqst) {
                    return BridSearchRqst.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'brid-search-rqst',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('brid-search-rqst-detail.edit', {
            parent: 'brid-search-rqst-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rqst/brid-search-rqst-dialog.html',
                    controller: 'BridSearchRqstDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BridSearchRqst', function(BridSearchRqst) {
                            return BridSearchRqst.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brid-search-rqst.new', {
            parent: 'brid-search-rqst',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rqst/brid-search-rqst-dialog.html',
                    controller: 'BridSearchRqstDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                content: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('brid-search-rqst', null, { reload: 'brid-search-rqst' });
                }, function() {
                    $state.go('brid-search-rqst');
                });
            }]
        })
        .state('brid-search-rqst.edit', {
            parent: 'brid-search-rqst',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rqst/brid-search-rqst-dialog.html',
                    controller: 'BridSearchRqstDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BridSearchRqst', function(BridSearchRqst) {
                            return BridSearchRqst.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brid-search-rqst', null, { reload: 'brid-search-rqst' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brid-search-rqst.delete', {
            parent: 'brid-search-rqst',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rqst/brid-search-rqst-delete-dialog.html',
                    controller: 'BridSearchRqstDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BridSearchRqst', function(BridSearchRqst) {
                            return BridSearchRqst.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brid-search-rqst', null, { reload: 'brid-search-rqst' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
