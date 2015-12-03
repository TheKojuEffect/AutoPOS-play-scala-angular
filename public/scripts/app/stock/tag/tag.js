'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tag', {
                parent: 'stock',
                url: '/tags',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Tags'
                },
                templateUrl: 'scripts/app/stock/tag/tags.html',
                controller: 'TagController',
                resolve: {}
            })
            .state('tag.detail', {
                url: '/tag/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Tag'
                },
                templateUrl: 'scripts/app/stock/tag/tag-detail.html',
                controller: 'TagDetailController',
                resolve: {
                    entity: ['$stateParams', 'Tag', function ($stateParams, Tag) {
                        return Tag.get({id: $stateParams.id});
                    }]
                }
            })
            .state('tag.new', {
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/tag/tag-dialog.html',
                        controller: 'TagDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('tag', null, {reload: true});
                        }, function () {
                            $state.go('tag');
                        })
                }]
            })
            .state('tag.edit', {
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/stock/tag/tag-dialog.html',
                        controller: 'TagDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Tag', function(Tag) {
                                return Tag.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('tag', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
