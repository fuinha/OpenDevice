/*
 * *****************************************************************************
 * Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Ricardo JL Rufino - Initial API and Implementation
 * *****************************************************************************
 */

'use strict';

var urlParams;

// Declare app level module which depends on filters, and services
var app = angular.module('opendevice', [
    'ngRoute',
    'ngSanitize',
    'ui.select',
    //'opendevice.filters',
    //'opendevice.directives',
    'opendevice.services',
    'opendevice.controllers'
]);

// Constants
// ===================
// app.constant('opendevice_url', 'http://'+window.location.host);
// OpenDevice.setAppID = OpenDevice.findAppID() || 'clientname-123456x';
OpenDevice.setAppID = "*";

// Global variables
app.run(function($rootScope) {
    $rootScope.ext = {}; // Extension support
    $rootScope.ext.menu = [];
});

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {templateUrl: 'pages/dashboard.html', controller: 'DashboardController',  controllerAs: 'dashCtrl'});
    //$routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
    $routeProvider.otherwise({redirectTo: '/'});
}]);

// Configuration to Work like JSP templates
app.config(['$interpolateProvider', function($interpolateProvider) {
    $interpolateProvider.startSymbol('${');
    $interpolateProvider.endSymbol('}');
}]);


app.filter('propsFilter', function() {
    return function(items, props) {
        var out = [];

        if (angular.isArray(items)) {
            items.forEach(function(item) {
                var itemMatches = false;

                var keys = Object.keys(props);
                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }

        return out;
    };
});


