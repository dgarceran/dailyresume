/**
 * Created by David on 30/05/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .controller('TumblrController', function ($scope, $sce, Principal, Dashboard, TumblrActivity) {
        $scope.loadTweets = function () {
            Dashboard.query(function(result) {
                $scope.posts = new Array();
                for(var i = 0; i<10; i++){
                    if(result[i].type == "video" || result[i].type == "audio" || result[i].type == "text"){
                        $scope.posts = $scope.posts.concat(
                            [
                                {user: result[i].blogname, type: result[i].type, extra: $sce.trustAsHtml(result[i].extraresource), postUrl: result[i].posturl, notes: result[i].notes}
                            ]
                        );
                    }
                    else{
                        $scope.posts = $scope.posts.concat(
                            [
                                {user: result[i].blogname, type: result[i].type, extra: result[i].extraresource, postUrl: result[i].posturl, notes: result[i].notes}
                            ]
                        );
                    }
                }
            });
        };
        $scope.loadActivity = function(){
          TumblrActivity.query(function(result){
              $scope.activity = result;
              $scope.labels = ["22-24", "20-22", "18-20", "16-18", "14-16", "12-14", "10-12", "08-10", "06-08", "04-06", "02-04", "now-02"];
              $scope.series = ['Posts registered today: '+$scope.activity.day];
              $scope.data = [
                  [$scope.activity.hours2, $scope.activity.hours4, $scope.activity.hours6, $scope.activity.hours8, $scope.activity.hours10, $scope.activity.hours12, $scope.activity.hours14, $scope.activity.hours16, $scope.activity.hours18, $scope.activity.hours10, $scope.activity.hours22, $scope.activity.hours24]
              ];
              $scope.onClick = function (points, evt) {
                  console.log(points, evt);
              };
            });
        };

        $scope.loadTweets();
        $scope.loadActivity();
    });
