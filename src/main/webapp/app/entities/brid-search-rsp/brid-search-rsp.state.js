(function() {
    'use strict';

    angular
        .module('intgPocApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('brid-search-rsp', {
            parent: 'entity',
            url: '/brid-search-rsp',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BridSearchRsps'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brid-search-rsp/brid-search-rsps.html',
                    controller: 'BridSearchRspController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('brid-search-rsp-detail', {
            parent: 'brid-search-rsp',
            url: '/brid-search-rsp/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BridSearchRsp'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/brid-search-rsp/brid-search-rsp-detail.html',
                    controller: 'BridSearchRspDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BridSearchRsp', function($stateParams, BridSearchRsp) {
                    return BridSearchRsp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'brid-search-rsp',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('brid-search-rsp-detail.edit', {
            parent: 'brid-search-rsp-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rsp/brid-search-rsp-dialog.html',
                    controller: 'BridSearchRspDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BridSearchRsp', function(BridSearchRsp) {
                            return BridSearchRsp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brid-search-rsp.new', {
            parent: 'brid-search-rsp',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rsp/brid-search-rsp-dialog.html',
                    controller: 'BridSearchRspDialogController',
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
                    $state.go('brid-search-rsp', null, { reload: 'brid-search-rsp' });
                }, function() {
                    $state.go('brid-search-rsp');
                });
            }]
        })
        .state('brid-search-rsp.edit', {
            parent: 'brid-search-rsp',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rsp/brid-search-rsp-dialog.html',
                    controller: 'BridSearchRspDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BridSearchRsp', function(BridSearchRsp) {
                            return BridSearchRsp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brid-search-rsp', null, { reload: 'brid-search-rsp' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brid-search-rsp.delete', {
            parent: 'brid-search-rsp',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/brid-search-rsp/brid-search-rsp-delete-dialog.html',
                    controller: 'BridSearchRspDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BridSearchRsp', function(BridSearchRsp) {
                            return BridSearchRsp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brid-search-rsp', null, { reload: 'brid-search-rsp' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
