'use strict';

angular.module('autopos')
    .controller('ItemController', function ($scope, Item, ParseLinks) {
        $scope.items = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Item.query({page: $scope.page, per_page: 20}, function(result, headers) {
                //$scope.links = ParseLinks.parse(headers('link'));
                $scope.items = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Item.get({id: id}, function(result) {
                $scope.item = result;
                $('#deleteItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Item.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.item = {name: null, description: null, markedPrice: null, id: null};
        };
    });
