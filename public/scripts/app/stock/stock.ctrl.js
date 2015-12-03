'use strict';

angular.module('autopos')
    .controller('StockCtrl', function ($scope) {

        $scope.tabData = [{
            heading: 'Items',
            route: 'item'
        }, {
            heading: 'Categories',
            route: 'category'
        }, {
            heading: 'Brands',
            route: 'brand'
        }, {
            heading: 'Tags',
            route: 'tag'
        }];
    });
