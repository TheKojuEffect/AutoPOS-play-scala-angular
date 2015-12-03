'use strict';

angular.module('autopos')
    .factory('Account', function Account($resource) {
        return $resource('account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });
