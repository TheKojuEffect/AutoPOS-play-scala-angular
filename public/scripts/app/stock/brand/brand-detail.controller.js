'use strict';

angular.module('autopos')
    .controller('BrandDetailController', function ($scope, $rootScope, $stateParams, entity, Brand) {
        $scope.brand = entity;
        $scope.load = function (id) {
            Brand.get({id: id}, function(result) {
                $scope.brand = result;
            });
        };
        $rootScope.$on('autopos:brandUpdate', function(event, result) {
            $scope.brand = result;
        });
    });
