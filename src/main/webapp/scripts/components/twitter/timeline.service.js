/**
 * Created by david.garceran on 21/4/16.
 */
'use strict';

angular.module('dailyresumeApp')
    .factory('Timeline', function ($resource) {
        return $resource('api/twitter/timeline', {}, {
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
