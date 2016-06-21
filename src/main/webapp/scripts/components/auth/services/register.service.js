'use strict';

angular.module('dailyresumeApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


