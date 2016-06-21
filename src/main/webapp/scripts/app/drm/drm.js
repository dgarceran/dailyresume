/**
 * Created by David on 02/06/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('drm', {
                parent: 'site',
                url: '/drm',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/drm/drm.html',
                        controller: 'DrmController'
                    }
                },
                resolve: {

                }
            })
            .state('drmAnalytics', {
            parent: 'site',
            url: '/analytics',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/drm/analytics.html',
                    controller: 'AnalyticsController'
                }
            },
            resolve: {

            }
        });
    });
