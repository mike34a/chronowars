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

			var countPlayerToken = function(playerColor, board) {
				return 8;
			}
			
			var placeToken = function() {
				var gameId = gameApi.getGameId($routeParams.playerId).then(function(data) {
					$scope.gameId = data;
				});
				console.log($scope.gameid);
			}
			
			var moveToken = function() {
				console.log('moving token');
			}
			
			gameApi.getGameId($routeParams.playerId).then(function(data) {
				$scope.gameId = data;
				$scope.board = gameApi.getBoard(data)
			});
			
			$scope.selectTile = function(tileId) {
				document.getElementById(tileId).setAttribute('style',
						'border: 3px solid red');
				if ($scope.selectedTile)
					document.getElementById($scope.selectedTile)
							.removeAttribute('style');
				$scope.selectedTile = tileId;
			}
			
			$scope.getActionText = function() {
				var playerToken;
				if (!$scope.board || $scope.board == 'Optionnal.empty')
					return $scope.selectedTile ? 'place' : '';
				else {
					playerToken = countPlayerToken($scope.playerId, $scope.board);
					return (playerToken < 8 ? 'place' : 'move');
				}
			}
			
			$scope.play = function() {
				var playerToken;
				if (!$scope.board || $scope.board == 'Optionnal.empty' || countPlayerToken($scope.playerId, $scope.board) < 8)
					placeToken();
				else
					moveToken();
			}
			
			/*
			 * $("#tatami").draggable({ revert : "invalid" });
			 * $("#a2").droppable(); $("#a1").droppable();
			 */
		} ]);