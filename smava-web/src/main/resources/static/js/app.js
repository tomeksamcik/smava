'use strict';
 
var App = angular
	.module('myApp', ['ngSanitize', 'ngAnimate', 'ngToast'])
	.config(['$httpProvider', function($httpProvider) {
	    if (!$httpProvider.defaults.headers.get) {
	        $httpProvider.defaults.headers.get = {};    
	    }	
	    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
	    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
	    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
	}])
	.config(['ngToastProvider', function(ngToast) {
		ngToast.configure({
			animation: 'fade',
			dismissButton: true,			
			verticalPosition: 'top',
			horizontalPosition: 'right',
			maxNumber: 10
		})
	}]);