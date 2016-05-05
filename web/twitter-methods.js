/**
 * Created by richardfenton on 4/10/16.
 */
function TwitterController($scope, $http, $sce, $interval) {


    $interval(function () {


            return $http.get('api/hello/mood/get/tweets').
            success(function (data, status, headers, config) {
                console.log("tweets: ", data);

                $scope.tweets = data;
                return data;

                // document.getElementById("tweets").innerHTML=$scope.tweets;

            }).
            error(function (data, status, headers, config) {
                console.log("error returning data");
            })

            //$scope.getURLs();

        }, 5000);



    $scope.getTweets = function () {

        return $http.get('api/hello/mood/get/tweets').
        success(function (data, status, headers, config) {
            console.log("tweets: ", data);

            $scope.tweets = data;
            return data;

            // document.getElementById("tweets").innerHTML=$scope.tweets;

        }).
        error(function (data, status, headers, config) {
            console.log("error returning data");
        })

    }




    $scope.searchFor = function () {

        var noSpace = document.getElementById("textfield").value.replace(" ", "-");

        return $http.get('api/hello/mood/search/' + noSpace).
        success(function (data, status, headers, config) {
            console.log("data: ", data);

        })
    }

    $scope.searchForTop = function (top) {

        document.getElementById("textfield").value = top;
        var topNoHash = top.replace('#', "");
        topNoHash=topNoHash.replace(" ","-");

        return $http.get('api/hello/mood/search/' + topNoHash).
        success(function (data, status, headers, config) {
            console.log("data: ", data);

        })


    }

    $scope.getMood = function () {

        setTimeout($scope.getMood(), 1000);

        return $http.get('api/hello/mood').
        success(function (data, status, headers, config) {
            console.log("data: ", data);
            document.getElementById("mood").innerHTML = "Current Mood: " + data;

        })
    }

    $scope.getURLs = function () {

        //setTimeout($scope.getURLs(), 5000);

        return $http.get('api/hello/mood/url').
        success(function (data, status, headers, config) {
            console.log("data: ", data);
            $scope.urls = data;

            $scope.website = $sce.trustAsResourceUrl("http://www.google.com");

            return data;

        }).
        error(function (data, status, headers, config) {
            console.log("error returning data");
        });
    }

    $scope.itemDetail = function(link){
        $scope.detailFrame = $sce.trustAsResourceUrl(link);
        return $scope.detailFrame;
    };

    $scope.getTopTweets = function () {

        //setTimeout($scope.getTopTweets(), 5000);

        return $http.get('api/hello/mood/top').
        success(function (data, status, headers, config) {
            console.log("data: ", data);
            $scope.top = data;

            return data;

        }).
        error(function (data, status, headers, config) {
            console.log("error returning data");
        });
    }




}

angular.module('testingFrameworks').controller('TwitterController', TwitterController)