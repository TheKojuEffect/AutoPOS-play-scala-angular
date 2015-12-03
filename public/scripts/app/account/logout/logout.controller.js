'use strict';

angular.module('autopos')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
