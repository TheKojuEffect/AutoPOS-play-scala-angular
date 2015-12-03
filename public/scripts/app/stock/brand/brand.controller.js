'use strict';

angular.module('autopos')
    .controller('BrandController', function ($scope, Brand, ParseLinks) {
        $scope.brands = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Brand.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.brands = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Brand.get({id: id}, function(result) {
                $scope.brand = result;
                $('#deleteBrandConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Brand.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBrandConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.brand = {name: null, id: null};
        };
    });
