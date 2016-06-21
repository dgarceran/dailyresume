 'use strict';

angular.module('dailyresumeApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-dailyresumeApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-dailyresumeApp-params')});
                }
                return response;
            }
        };
    });
