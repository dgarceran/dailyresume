/**
 * Created by david.garceran on 21/4/16.
 */

'use strict';

angular.module('dailyresumeApp')
    .controller('TimelineController', function ($scope, Principal, Timeline, TwitterActivity) {
        $scope.loadTweets = function () {
            Timeline.query(function(result) {
                $scope.tweets = new Array();
                $scope.ids = new Array();
                for(var i = 0; i<10; i++){
                    if(result[i].idrt != null){
                        $scope.tweets = $scope.tweets.concat(
                            [
                                {text: result[i].text, profile: result[i].profileimage, idStr: result[i].idrt }
                            ]
                        );
                    }else{
                        $scope.tweets = $scope.tweets.concat(
                            [
                                {text: result[i].text, profile: result[i].profileimage, idStr: result[i].tweetid.toString() }
                            ]
                        );
                    }
                }
            });
        };
        $scope.loadActivity = function(){
            TwitterActivity.query(function(result){
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
