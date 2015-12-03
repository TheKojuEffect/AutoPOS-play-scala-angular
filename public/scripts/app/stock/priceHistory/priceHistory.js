'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('priceHistory', {
                parent: 'stock',
                url: '/priceHistorys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PriceHistorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/stock/priceHistory/priceHistorys.html',
                        controller: 'PriceHistoryController'
                    }
                },
                resolve: {
                }
            })
            .state('priceHistory.detail', {
                parent: 'stock',
                url: '/priceHistory/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PriceHistory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/stock/priceHistory/priceHistory-detail.html',
                        controller: 'PriceHistoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PriceHistory', function($stateParams, PriceHistory) {
                        return PriceHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('priceHistory.new', {
                parent: 'priceHistory',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/priceHistory/priceHistory-dialog.html',
                        controller: 'PriceHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {markedPrice: null, date: null, remarks: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('priceHistory', null, { reload: true });
                    }, function() {
                        $state.go('priceHistory');
                    })
                }]
            })
            .state('priceHistory.edit', {
                parent: 'priceHistory',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/priceHistory/priceHistory-dialog.html',
                        controller: 'PriceHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PriceHistory', function(PriceHistory) {
                                return PriceHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('priceHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
