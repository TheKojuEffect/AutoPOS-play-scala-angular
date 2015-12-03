'use strict';

angular.module('autopos')
    .controller('PriceHistoryController', function ($scope, PriceHistory, ParseLinks) {
        $scope.priceHistorys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PriceHistory.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.priceHistorys.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.priceHistorys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PriceHistory.get({id: id}, function(result) {
                $scope.priceHistory = result;
                $('#deletePriceHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PriceHistory.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deletePriceHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.priceHistory = {markedPrice: null, date: null, remarks: null, id: null};
        };
    });
