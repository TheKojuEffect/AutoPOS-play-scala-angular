'use strict';
angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stock', {
                parent: 'site',
                abstract: true,
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Stock'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/stock/stock.html',
                        controller: 'StockCtrl'
                    }
                }
            });
    });
