/**
 * Created by David on 02/06/2016.
 */
'use strict';

angular.module('dailyresumeApp')
    .controller('DrmController', function ($scope, $state, $uibModal, $sce, Principal, drm, ParseLinks, drmTumblr) {

        $scope.page = 0;
        $scope.loadDrm = function () {
            drm.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.resources = new Array();
                for(var i = 0; i<10; i++){
                    if(result[i].service == "tumblr"){
                        drmTumblr.query({reblogKey: result[i].idresource}, function(result2) {
                            if(result2.type == "video" || result2.type == "audio" || result2.type == "text"){
                                $scope.resources = $scope.resources.concat(
                                    [
                                        {res: $sce.trustAsHtml(result2.extraresource), service: "tumblr", type: result2.type, postUrl: result2.posturl, notes: result2.notes}
                                    ]
                                );
                            }else{
                                $scope.resources = $scope.resources.concat(
                                    [
                                        {res: result2.extraresource, service: "tumblr", type: result2.type, postUrl: result2.posturl, notes: result2.notes}
                                    ]
                                );
                            }
                        });
                    }else{
                        $scope.resources = $scope.resources.concat(
                            [
                                {res: result[i].idresource, service: "twitter" }
                            ]
                        );
                    }
                }
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadDrm();
        };
        $scope.loadDrm();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };
    })
    .controller('AnalyticsController', function($scope, $state, $uibModal, $sce, Principal, drm,TumblrActivity, drmAnalyticsTumblr, drmAnalyticsTwitter){
        $scope.loadTumblrWeeklyActivity = function(){
            drmAnalyticsTumblr.query(function(result){
                $scope.activity = result;
                $scope.dataWeeklyTumblr = new Array();
                $scope.dataWeeklyTumblr2 = new Array();
                $scope.labelsWeeklyTumblr = new Array();
                $scope.dataDailyTumblr = new Array();
                $scope.dataDailyTumblr2 = new Array();
                var size = result.length;
                for(var i = 0; i<size; i++){
                    $scope.dataWeeklyTumblr = $scope.dataWeeklyTumblr.concat([result[i].day]);
                    $scope.labelsWeeklyTumblr = $scope.labelsWeeklyTumblr.concat([i]+" days ago");

                    $scope.dataDailyTumblr = $scope.dataDailyTumblr.concat([[result[i].hours2, result[i].hours4, result[i].hours6, result[i].hours8, result[i].hours10, result[i].hours12, result[i].hours14, result[i].hours16, result[i].hours18, result[i].hours10, result[i].hours22, result[i].hours24]]);

                }
                $scope.dataWeeklyTumblr2 = $scope.dataWeeklyTumblr2.concat([$scope.dataWeeklyTumblr]);
                $scope.seriesWeeklyTumblr = ['Posts/day'];
                $scope.labelsDailyTumblr = ["22-24", "20-22", "18-20", "16-18", "14-16", "12-14", "10-12", "08-10", "06-08", "04-06", "02-04", "00-02"];
                $scope.seriesDailyTumblr = $scope.labelsWeeklyTumblr;
                $scope.dataDailyTumblr2 = $scope.dataDailyTumblr2.concat($scope.dataDailyTumblr);
            });
        };
        $scope.loadTwitterWeeklyActivity = function(){
            drmAnalyticsTwitter.query(function(result){
                $scope.dataWeeklyTwitter = new Array();
                $scope.dataWeeklyTwitter2 = new Array();
                $scope.labelsWeeklyTwitter = new Array();
                $scope.dataDailyTwitter = new Array();
                $scope.dataDailyTwitter2 = new Array();
                var size = result.length;
                for(var i = 0; i<size; i++){
                    $scope.dataWeeklyTwitter = $scope.dataWeeklyTwitter.concat([result[i].day]);
                    $scope.labelsWeeklyTwitter = $scope.labelsWeeklyTwitter.concat([i]+" days ago");

                    $scope.dataDailyTwitter = $scope.dataDailyTwitter.concat([[result[i].hours2, result[i].hours4, result[i].hours6, result[i].hours8, result[i].hours10, result[i].hours12, result[i].hours14, result[i].hours16, result[i].hours18, result[i].hours10, result[i].hours22, result[i].hours24]]);

                }
                $scope.dataWeeklyTwitter2 = $scope.dataWeeklyTwitter2.concat([$scope.dataWeeklyTwitter]);
                $scope.seriesWeeklyTwitter = ['Posts/day'];
                $scope.labelsDailyTwitter = ["22-24", "20-22", "18-20", "16-18", "14-16", "12-14", "10-12", "08-10", "06-08", "04-06", "02-04", "00-02"];
                $scope.seriesDailyTwitter = $scope.labelsWeeklyTwitter;
                $scope.dataDailyTwitter2 = $scope.dataDailyTwitter2.concat($scope.dataDailyTwitter);
            });
        };
        $scope.loadTumblrWeeklyActivity();
        $scope.loadTwitterWeeklyActivity();
    });
