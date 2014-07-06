'use strict';

/* App Module */

var chronoWars = angular.module('chronoWars', [
  'ngRoute',
  'chronoWarsAnimations',
  'chronoWarsControllers',
  'chronoWarsFilters',
  'chronoWarsServices'
]);

chronoWars.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      otherwise({
        templateUrl: 'partials/board.html',
        controller: 'PhoneListCtrl'
      });
  }]);
