'use strict';

/* Controllers */

var chronoWarsControllers = angular.module('chronoWarsControllers', []);

chronoWarsControllers.controller(
		'chronoWarsControllers', 
		['$scope', 'launchGame','$http',
		function($scope, launchGame, $http) {
			
			/*API*/
			// $scope.getPlayerId = launchGame.getPlayerId; TODO : We should do this but promises won't let us
			$scope.registerPlayer = function(name) {
				$http.get('/register/' + name).then(function(response) {
					$scope.playerId = response.data;
				});
			};
			
			$scope.getGameId = function(playerId) {
				$http.get('/have_i_running_game/' + playerId).then(function(response) {
					$scope.gameId = response.data;
				})
			};
			
			$scope.getBoard = function(gameId) {
				$http.get('/get_game/' + gameId).then(function(response) {
					$scope.board = response.data;
				})
			};
			
			$scope.setToken = function(playerId, gameId, row, col) {
				$http.put('/set_token/' + gameId + '/' + row + '/' + '/' + col).then(function(response) {
					$scope.board = response.data;
				})
			};
			
			$scope.moveToken = function(playerId, gameId, row, col, direction) {
				$http.patch('/set_token/' + gameId + '/' + row + '/' + '/' + col + '/' + direction).then(function(response) {
					$scope.board = response.data;
				})
			};
			
			/*Game*/
			$scope.selectTile = function(tileId) {
				document.getElementById(tileId).setAttribute('style','border: 3px solid red');
				if ($scope.selectedTile)
					document.getElementById($scope.selectedTile).removeAttribute('style');
				$scope.selectedTile = tileId;
			}
			/*
			$("#tatami").draggable({
				revert : "invalid"
			});
			$("#a2").droppable();
			$("#a1").droppable();
			*/
		}]
);