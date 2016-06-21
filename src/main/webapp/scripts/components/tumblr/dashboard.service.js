/**
 * Created by David on 30/05/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .factory('Dashboard', function ($resource) {
        return $resource('api/tumblr/drm', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
