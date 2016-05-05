/**
 * Created by richardfenton on 4/10/16.
 */
'use strict'

var app = angular.module('testingFrameworks', ['ngRoute', 'ui.bootstrap']);

app.config(function($locationProvider, $routeProvider, $provide, $httpProvider){

    $routeProvider
        .when('/', {
            templateUrl: 'index.jsp',
            controller: TwitterController,
            resolve: TwitterController.resolve
        });
})

app.filter('webFrame', ['$sce', function($sce) {
    return function(val) {
        return $sce.trustAsResourceUrl(val+ "&output=embed");
    };
}])