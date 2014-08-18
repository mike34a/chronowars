'use strict';

/* App Module */

var chronoWars = angular.module('chronoWars', [ 'ngRoute',
		'chronoWarsAnimations', 'chronoWarsControllers', 'chronoWarsFilters',
		'chronoWarsServices' ]);

chronoWars.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/home', {
        templateUrl: 'partials/home.html',
        controller: 'HomeCtrl'
      }).
      when('/game/:playerId', {
        templateUrl: 'partials/board.html',
        controller: 'GameCtrl'
      }).
      when('/faq', {
          templateUrl: 'partials/faq.html',
        }).
      otherwise({
        redirectTo: '/home'
      });
  }]);
