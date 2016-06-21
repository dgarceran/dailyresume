'use strict';

angular.module('dailyresumeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    },
                    "drm@home": {
                        templateUrl: 'scripts/app/drm/drm.html',
                        controller: 'DrmController'
                    }
                },
                resolve: {

                }
            });
    });
