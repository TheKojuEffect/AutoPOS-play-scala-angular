'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('category', {
                parent: 'stock',
                url: '/categories',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Categorys'
                },
                templateUrl: 'scripts/app/stock/category/categorys.html',
                controller: 'CategoryController',
                resolve: {}
            })
            .state('category.detail', {
                url: '/category/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Category'
                },
                templateUrl: 'scripts/app/stock/category/category-detail.html',
                controller: 'CategoryDetailController',
                resolve: {
                    entity: ['$stateParams', 'Category', function ($stateParams, Category) {
                        return Category.get({id: $stateParams.id});
                    }]
                }
            })
            .state('category.new', {
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/category/category-dialog.html',
                        controller: 'CategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {shortName: null, name: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('category', null, {reload: true});
                        }, function () {
                            $state.go('category');
                        })
                }]
            })
            .state('category.edit', {
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/category/category-dialog.html',
                        controller: 'CategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Category', function (Category) {
                                return Category.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('category', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
