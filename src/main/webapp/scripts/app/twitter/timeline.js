/**
 * Created by david.garceran on 21/4/16.
 */
'use strict';

angular.module('dailyresumeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeline', {
                parent: 'site',
                url: '/twitter',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/twitter/timeline.html',
                        controller: 'TimelineController'
                    }
                },
                resolve: {

                }
            })
            .state('activity', {
            parent: 'site',
            url: '/twitter',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/twitter/timeline.html',
                    controller: 'TimelineController'
                }
            },
            resolve: {

            }
        });
    });
