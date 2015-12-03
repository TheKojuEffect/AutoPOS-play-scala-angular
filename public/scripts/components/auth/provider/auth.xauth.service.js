'use strict';

angular.module('autopos')
    .factory('AuthServerProvider', function loginService($http, localStorageService, Base64) {
        return {
            login: function (credentials) {
                var data = {
                    username: credentials.username,
                    password: credentials.password
                };
                return $http.post('authenticate', data, {
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    }
                }).success(function (response) {
                    localStorageService.set('token', response);
                    return response;
                });
            },
            logout: function () {
                //Stateless API : No server logout
                localStorageService.clearAll();
            },
            getToken: function () {
                return localStorageService.get('token');
            },
            hasValidToken: function () {
                var token = this.getToken();
                return token && token.expires && token.expires > new Date().getTime();
            }
        };
    });