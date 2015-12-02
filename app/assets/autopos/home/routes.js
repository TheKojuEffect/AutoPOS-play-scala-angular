/**
 * Home routes.
 */
define(['angular', './controllers', 'common'], function (angular, controllers) {
    'use strict';

    var mod = angular.module('home.routes', ['autopos.common']);
    mod.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: '/assets/autopos/home/home.html', controller: controllers.HomeCtrl})
            .otherwise({templateUrl: '/assets/autopos/home/notFound.html'});
    }]);
    return mod;
});
