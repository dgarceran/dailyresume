/**
 * Created by David on 02/06/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .factory('drm', function ($resource) {
        return $resource('api/drm', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('drmTumblr', function ($resource) {
        return $resource('api/drm/tumblr', {}, {
            'query': {method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('drmSocial', function ($resource) {
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
    })
    .factory('drmAnalyticsTumblr', function ($resource) {
        return $resource('api/drm/analytics/tumblr', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('drmAnalyticsTwitter', function ($resource) {
        return $resource('api/drm/analytics/twitter', {}, {
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
