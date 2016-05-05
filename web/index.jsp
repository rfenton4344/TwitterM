<%--
  Created by IntelliJ IDEA.
  User: joaorocha
  Date: 22/07/14
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html id="ng-app" ng-app="testingFrameworks">
<head>
    <meta charset="utf-8">
    <title></title>
    <link href = "http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
    <script src = "http://code.jquery.com/jquery-1.10.2.js"></script>
    <script src = "http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <script src="twitterMethods.js"></script>
    <style>
        .frame {
            width: 800px;
            height: 520px;
            border: none;
            -moz-transform: scale(0.2);
            -moz-transform-origin: 0 0;
            -o-transform: scale(0.2);
            -o-transform-origin: 0 0;
            -webkit-transform: scale(0.2);
            -webkit-transform-origin: 0 0;
        }

        #box {
            width: 800px;
            height: 300px;
            border: 5px black solid;
            overflow: hidden;
            background-color: #F2F2F2;
        }
        #contentContainer {
            position: relative;

        }
        .thing {
            transition-property: transform;
            transition-timing-function: ease-in-out;
            position: absolute;
            width: 100px;
            height: 100px;
            border-radius: 50px;
            background-color: #0066CC;
            opacity: .5;
        }

        .bground {
            background-image: url("mood.jpg");
            background-repeat: no-repeat;
            background-size: 100% 100%;
        }

        #tabs {
            background: transparent;
            border: none;
        }
        #tabs .ui-widget-header {
            background: transparent;
            border: none;
            border-bottom: 1px solid #c0c0c0;
            -moz-border-radius: 0px;
            -webkit-border-radius: 0px;
            border-radius: 0px;
        }
        #tabs .ui-tabs-nav .ui-state-default {
            background: transparent;
            border: none;
        }
        #tabs .ui-tabs-nav .ui-state-active {
            background: transparent url(uiTabsArrow.png) no-repeat bottom center;
            border: none;
        }
        #tabs .ui-tabs-nav .ui-state-default a {
            color: #c0c0c0;
        }
        #tabs .ui-tabs-nav .ui-state-active a {
            color: #459e00;
        }
    </style>

    <script>
        $(function() {
            $( "#tabs" ).tabs();
        });
    </script>

</head>
<body onload="startMood(); getMood()" >

<div ng-controller="TwitterController">


    <div align="left" style="float: left" ng-init="getTopTweets()">
        <table id="topTweetsTable">
            <thead>
            <tr role="row">
                <th>Top Tweets</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="t in top">
                <td ng-click="searchForTop(t.top)">{{t.top}}</td>
            </tr>
            </tbody>
        </table>
    </div>

<div align="center">

    <h1 id="myHeader" align="center">Twitter Search</h1>
    <br>
    <input type="text" name="textfield" id="textfield" align="center" value="Search here"/>
    <button id="searchButton" ng-click="searchFor()" type="button" value="Go">Go</button>
    <button id="urlButton" ng-click="getURLs()" type="button" value="Go">Get Links</button>
    <button id="topTweetsButton" ng-click="getTopTweets()" type="button" value="Go">Top Tweets</button>
    <br>

    <p id="mood"></p>
    <br>
    </div>


    <div id="tabs">
        <ul>
            <li><a href="#tabs-1">Mood Chart</a></li>
            <li><a href="#tabs-2">Links</a></li>
            <li><a href="#tabs-3">Tweets</a></li>
        </ul>
        <div id="tabs-1">
            <!--<canvas id="canvas" width="832" height= "600"></canvas>-->
            <div id="box" class="bground" align="center">
                <div id="contentContainer">
                    <div class="thing" id="clickme"></div>
                </div>
            </div>
           </div>
        <div id="tabs-2">
            <table align="center" id="urlTable">
                <thead>
                <tr role="row">
                    <th>URL</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in urls">
                    <td align="center"><a href="{{u.url}}">{{u.url}}</a></td>
                    <!--<iframe class="frame" ng-src="{{u.url | webFrame}}"></iframe>
                    <img src="circle.png" alt="Circle" height="42" width="42" title="{{u.url}}">-->
                </tr>
                </tbody>
            </table>
            </div>
        <div id="tabs-3">
            <table align="center" id="tweetTable">
                <thead>
                <tr role="row">
                    <th>User Name</th>
                    <th>Tweet</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="t in tweets">
                    <td>{{t.username}}</td>
                    <td>{{t.tweet}}</td>
                </tr>
                </tbody>
            </table>
      </div>
    </div>


</div>



<!--<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>-->
<script type="text/javascript" language="javascript" src="https://code.angularjs.org/1.2.16/angular.js"></script>
<script type="text/javascript" language="javascript" src="https://code.angularjs.org/1.2.16/angular-route.js"></script>
<script type="text/javascript" language="javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/1.3.1/ui-bootstrap.js"></script>
<script type="text/javascript" language="javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/1.3.1/ui-bootstrap-tpls.js"></script>
<script src="js/preefixfree.min.js"></script>
<script src="js/circleMovement.js"></script>

<script src="app.js"></script>
<script src="twitter-methods.js"></script>
</body>
</html>
