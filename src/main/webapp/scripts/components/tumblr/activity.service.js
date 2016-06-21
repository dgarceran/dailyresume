/**
 * Created by David on 31/05/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .factory('TumblrActivity', function ($resource) {
        return $resource('api/tumblr/activity', {}, {
            'query': {method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
