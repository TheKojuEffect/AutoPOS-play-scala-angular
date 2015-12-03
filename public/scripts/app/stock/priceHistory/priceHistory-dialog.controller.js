'use strict';

angular.module('autopos').controller('PriceHistoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PriceHistory', 'Item',
        function($scope, $stateParams, $modalInstance, entity, PriceHistory, Item) {

        $scope.priceHistory = entity;
        $scope.items = Item.query();
        $scope.load = function(id) {
            PriceHistory.get({id : id}, function(result) {
                $scope.priceHistory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('autopos:priceHistoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.priceHistory.id != null) {
                PriceHistory.update($scope.priceHistory, onSaveFinished);
            } else {
                PriceHistory.save($scope.priceHistory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
