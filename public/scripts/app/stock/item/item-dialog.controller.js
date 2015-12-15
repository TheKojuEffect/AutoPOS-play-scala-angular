'use strict';

angular.module('autopos').controller('ItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Item', 'Category', 'Brand', 'Tag',
        function ($scope, $stateParams, $modalInstance, entity, Item, Category, Brand, Tag) {

            $scope.item = entity;
            $scope.categorys = Category.query();
            $scope.brands = Brand.query();
            $scope.tags = Tag.query();
            $scope.load = function (id) {
                Item.get({id: id}, function (result) {
                    $scope.item = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('autopos:itemUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.item.id != null) {
                    Item.update({id: $scope.item.id}, $scope.item, onSaveFinished);
                } else {
                    Item.save($scope.item, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
