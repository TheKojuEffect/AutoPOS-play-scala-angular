'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('item', {
                parent: 'stock',
                url: '/items',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Items'
                },
                templateUrl: 'scripts/app/stock/item/items.html',
                controller: 'ItemController',
                resolve: {}
            })
            .state('item.detail', {
                url: '/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Item'
                },
                templateUrl: 'scripts/app/stock/item/item-detail.html',
                controller: 'ItemDetailController',
                resolve: {
                    entity: ['$stateParams', 'Item', function ($stateParams, Item) {
                        return Item.get({id: $stateParams.id});
                    }]
                }
            })
            .state('item.new', {
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/item/item-dialog.html',
                        controller: 'ItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, description: null, markedPrice: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('item', null, {reload: true});
                        }, function () {
                            $state.go('item');
                        })
                }]
            })
            .state('item.edit', {
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/item/item-dialog.html',
                        controller: 'ItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Item', function (Item) {
                                return Item.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('item', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
