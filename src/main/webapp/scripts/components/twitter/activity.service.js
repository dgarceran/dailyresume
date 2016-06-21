/**
 * Created by David on 01/06/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .factory('TwitterActivity', function ($resource) {
        return $resource('api/twitter/activity', {}, {
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
