'use strict';

/* App Module */

var chronoWars = angular.module('chronoWars', [ 'ngRoute',
		'chronoWarsAnimations', 'chronoWarsControllers', 'chronoWarsFilters',
		'chronoWarsServices' ]);

chronoWars.config([ '$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/angular/app/test', {
        templateUrl: 'partials/board.html',
        controller: 'chronoWarsControllers'
      })
	.otherwise({
		templateUrl : 'partials/home.html',
		controller : 'chronoWarsControllers'
	});
} ]);
