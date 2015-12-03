'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('brand', {
                parent: 'stock',
                url: '/brands',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Brands'
                },
                templateUrl: 'scripts/app/stock/brand/brands.html',
                controller: 'BrandController',
                resolve: {}
            })
            .state('brand.detail', {
                url: '/brand/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Brand'
                },
                templateUrl: 'scripts/app/stock/brand/brand-detail.html',
                controller: 'BrandDetailController',
                resolve: {
                    entity: ['$stateParams', 'Brand', function ($stateParams, Brand) {
                        return Brand.get({id: $stateParams.id});
                    }]
                }
            })
            .state('brand.new', {
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/brand/brand-dialog.html',
                        controller: 'BrandDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('brand', null, {reload: true});
                        }, function () {
                            $state.go('brand');
                        })
                }]
            })
            .state('brand.edit', {
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/brand/brand-dialog.html',
                        controller: 'BrandDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Brand', function (Brand) {
                                return Brand.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('brand', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
