'use strict';

/* Controllers */

var chronoWarsControllers = angular.module('chronoWarsControllers', []);

chronoWarsControllers.controller('HomeCtrl', [ '$scope', 'gameApi',
		'$location', function($scope, gameApi, $location) {
			$scope.registerPlayer = function(name) {
				gameApi.registerPlayer(name).then(function(pidres) {
					$scope.startgame = setInterval(function() {
						gameApi.hasGameStarted(pidres).then(function(started) {
							if (started != 'false') {
								clearInterval($scope.startgame);
								$location.path('/game/' + pidres);
							}
						});
					}, 1000);
				});
			};
		} ]);

chronoWarsControllers.controller('GameCtrl', [
		'$scope',
		'gameApi',
		'$routeParams',
		function($scope, gameApi, $routeParams) {
			$scope.playerId = $routeParams.playerId;
			gameApi.hasGameStarted($routeParams.playerId).then(function(color) {
				if (color != 'false') {
					$scope.color = color;
				}
			});
			var countPlayerToken = function(playerColor, board) {
				return 7;
			}

			var placeToken = function() {
				gameApi.setToken($routeParams.playerId, $scope.selectedTile[0],
						$scope.selectedTile[1]).then(function(data) {
					/*
					 * Server API not ready
					 */
				});
			}

			var moveToken = function() {
				console.log('moving token');
			}

			$scope.selectTile = function(tileId) {
				var row = parseInt(tileId[0]);
				var col = parseInt(tileId[1]);
				var tileColor = (row + col) % 2;
				if ((tileColor == 0 && $scope.color == 'WHITE')
						|| (tileColor == 1 && $scope.color == 'BLACK')) {
					document.getElementById(tileId).setAttribute('style',
							'border: 3px solid red');
					if ($scope.selectedTile)
						document.getElementById($scope.selectedTile)
								.removeAttribute('style');
					$scope.selectedTile = tileId;

				}
			}

			$scope.getActionText = function() {
				var playerToken;
				if (!$scope.board || $scope.board == 'Optionnal.empty')
					return $scope.selectedTile ? 'place' : '';
				else {
					playerToken = countPlayerToken($scope.playerId,
							$scope.board);
					return (playerToken < 8 ? 'place' : 'move');
				}
			}

			$scope.play = function() {
				var playerToken;
				if (!$scope.board || $scope.board == 'Optionnal.empty'
						|| countPlayerToken($scope.playerId, $scope.board) < 8)
					placeToken();
				else
					moveToken();
			}

			/*
			 * $("#tatami").draggable({ revert : "invalid" });
			 * $("#a2").droppable(); $("#a1").droppable();
			 */
		} ]);