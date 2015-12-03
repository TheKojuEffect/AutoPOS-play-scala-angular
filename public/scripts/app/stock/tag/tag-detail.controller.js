'use strict';

angular.module('autopos')
    .controller('TagDetailController', function ($scope, $rootScope, $stateParams, entity, Tag) {
        $scope.tag = entity;
        $scope.load = function (id) {
            Tag.get({id: id}, function(result) {
                $scope.tag = result;
            });
        };
        $rootScope.$on('autopos:tagUpdate', function(event, result) {
            $scope.tag = result;
        });
    });
