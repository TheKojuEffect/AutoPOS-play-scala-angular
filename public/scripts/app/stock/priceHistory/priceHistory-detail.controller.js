'use strict';

angular.module('autopos')
    .controller('PriceHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, PriceHistory, Item) {
        $scope.priceHistory = entity;
        $scope.load = function (id) {
            PriceHistory.get({id: id}, function(result) {
                $scope.priceHistory = result;
            });
        };
        $rootScope.$on('autopos:priceHistoryUpdate', function(event, result) {
            $scope.priceHistory = result;
        });
    });
