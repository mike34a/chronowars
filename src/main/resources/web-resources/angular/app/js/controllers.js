'use strict';

/* Controllers */

var chronoWarsControllers = angular.module('chronoWarsControllers', []);

chronoWarsControllers.controller(
		'PhoneListCtrl', 
		['$scope', 'launchGame','$http',
		function($scope, launchGame, $http) {
			// $scope.getPlayerId = launchGame.getPlayerId; TODO : We should do this but promises won't let us
			$scope.getPlayerId = function(name) {
				return $http.get('/register/' + name).then(function(result) {
					$scope.playerId = result.data;
				});
			}
			$("#tatami").draggable({
				revert : "invalid"
			});
			$("#a2").droppable();
			$("#a1").droppable();
		}
		]
);