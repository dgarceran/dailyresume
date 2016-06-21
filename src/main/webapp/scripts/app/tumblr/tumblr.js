/**
 * Created by David on 30/05/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('Dashboard', {
                parent: 'site',
                url: '/tumblr',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/tumblr/tumblr.html',
                        controller: 'TumblrController'
                    }
                },
                resolve: {

                }
            })
        .state('TumblrActivity', {
            parent: 'site',
            url: '/tumblr',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/tumblr/tumblr.html',
                    controller: 'TumblrController'
                }
            },
            resolve: {

            }
        });

    });

