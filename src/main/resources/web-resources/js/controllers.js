'use strict';

/* Controllers */

var chronoWarsControllers = angular.module('chronoWarsControllers', []);

chronoWarsControllers.controller('HomeCtrl', [ '$scope', 'gameApi',
		'$location', function($scope, gameApi, $location) {
			$scope.registerPlayer = function(name) {
				gameApi.registerPlayer(name).then(function(data) {
					$location.path('/game/' + data)
				});
			};
		} ]);

chronoWarsControllers.controller('GameCtrl', [
		'$scope',
		'gameApi',
		'$routeParams',
		function($scope, gameApi, $routeParams) {
			$scope.playerId = $routeParams.playerId;

			gameApi.getGameId($routeParams.playerId).then(function(data) {
				$scope.gameId = data;
			});
			$scope.selectTile = function(tileId) {
				document.getElementById(tileId).setAttribute('style',
						'border: 3px solid red');
				if ($scope.selectedTile)
					document.getElementById($scope.selectedTile)
							.removeAttribute('style');
				$scope.selectedTile = tileId;
			}
			/*
			$("#tatami").draggable({
				revert : "invalid"
			});
			$("#a2").droppable();
			$("#a1").droppable();
			 */
		} ]);