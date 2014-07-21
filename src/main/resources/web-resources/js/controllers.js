'use strict';

/* Controllers */

var chronoWarsControllers = angular.module('chronoWarsControllers', []);

chronoWarsControllers.controller('HomeCtrl', [ '$scope', 'gameApi',
		'$location', function($scope, gameApi, $location) {
			$scope.registerPlayer = function(name) {
				var gameId;
				gameApi.registerPlayer(name).then(function(pidres) {
					$scope.startgame = setInterval(function(){
						gameApi.getGameId(pidres).then(function(gidres) {
							if (gidres != 'No game started yet.') {
								clearInterval($scope.startgame);
								$location.path('/game/' + pidres);
							}
						});
					},1000);
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
				return 7;
			}
			
			var placeToken = function() {
				gameApi.getGameId($routeParams.playerId).then(function(data) {
					$scope.gameId = data;
				});
				gameApi.setToken($routeParams.playerId, $scope.gameId, $scope.selectedTile[0], $scope.selectedTile[1]).then(function(data) {
					/*
					 * Server API not ready
					 */
				});
			}
			
			var moveToken = function() {
				console.log('moving token');
			}
			
			gameApi.getGameId($routeParams.playerId).then(function(data) {
				$scope.gameId = data;
				$scope.board = gameApi.getBoard(data);
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