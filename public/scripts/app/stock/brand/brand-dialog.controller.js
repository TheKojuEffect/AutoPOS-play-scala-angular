'use strict';

angular.module('autopos').controller('BrandDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Brand',
        function($scope, $stateParams, $modalInstance, entity, Brand) {

        $scope.brand = entity;
        $scope.load = function(id) {
            Brand.get({id : id}, function(result) {
                $scope.brand = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('autopos:brandUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.brand.id != null) {
                Brand.update($scope.brand, onSaveFinished);
            } else {
                Brand.save($scope.brand, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
