/**
 * Created by David on 02/06/2016.
 */
angular.module("dailyresumeApp")
    .factory('utilsDRM', function ($resource) {
        return $resource('api/drm/utils/accountsCheck', {}, {
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
