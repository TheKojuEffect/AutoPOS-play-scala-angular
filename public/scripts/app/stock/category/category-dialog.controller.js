'use strict';

angular.module('autopos').controller('CategoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Category',
        function ($scope, $stateParams, $modalInstance, entity, Category) {

            $scope.category = entity;
            $scope.load = function (id) {
                Category.get({id: id}, function (result) {
                    $scope.category = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('autopos:categoryUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.category.id != null) {
                    Category.update({id: $scope.category.id}, $scope.category, onSaveFinished);
                } else {
                    Category.save($scope.category, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
