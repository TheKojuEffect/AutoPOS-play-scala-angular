'use strict';

angular.module('autopos')
    .controller('CategoryDetailController', function ($scope, $rootScope, $stateParams, entity, Category) {
        $scope.category = entity;
        $scope.load = function (id) {
            Category.get({id: id}, function(result) {
                $scope.category = result;
            });
        };
        $rootScope.$on('autopos:categoryUpdate', function(event, result) {
            $scope.category = result;
        });
    });
