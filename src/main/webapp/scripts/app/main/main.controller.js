'use strict';

angular.module('dailyresumeApp')
    .controller('MainController', function ($scope, Principal, utilsDRM) {
        Principal.identity().then(function(account) {
            $scope.currentAccount = account.login;
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.loadTw = function () {
            utilsDRM.query({service:"twitter"}, function(result) {
                $scope.isConnectedWithTwitter = result.connected;
            });
        };
        $scope.loadTumblr = function () {
            utilsDRM.query({service: "tumblr"}, function (result) {
                $scope.isConnectedWithTumblr = result.connected;
            });
        };
        $scope.loadTw();
        $scope.loadTumblr();
    });
