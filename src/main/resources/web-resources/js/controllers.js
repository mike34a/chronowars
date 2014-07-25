'use strict';

/* Controllers */

var chronoWarsControllers = angular.module('chronoWarsControllers', []);

chronoWarsControllers.controller('HomeCtrl', [
    '$scope',
    'gameApi',
	'$location',
	function($scope, gameApi, $location) {
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
		
		setInterval(function() {
			gameApi.getBoard($routeParams.playerId).then(function(board) {
				$scope.colorToPlay = board.colorToPlay;
				var tile;
				var tokenImg;
				board.whiteTokens.tokensPositions.forEach(function(token) {
					tile = document.getElementById(token.x + '' + token.y);
					if (tile.childElementCount == 0) {
						tokenImg = document.createElement("img");
						tokenImg.setAttribute('src', 'img/token.png')
						tile.appendChild(tokenImg);
					}
				});
				board.blackTokens.tokensPositions.forEach(function(token) {
					tile = document.getElementById(token.x + '' + token.y);
					if (tile.childElementCount == 0) {
						tokenImg = document.createElement("img");
						tokenImg.setAttribute('src', 'img/token.png')
						tile.appendChild(tokenImg);
					}
				});
			});
		}, 1000);

		gameApi.getBoard($routeParams.playerId).then(function(board) {
			$scope.colorToPlay = board.colorToPlay;
		});

		gameApi.hasGameStarted($routeParams.playerId).then(function(color) {
			if (color != 'false') {
				$scope.color = color;
			}
		});
		
		var countPlayerToken = function(playerColor, board) {
			return 7;
		}

		var placeToken = function() {
			gameApi.setToken($routeParams.playerId,
							$scope.selectedTile[0],
							$scope.selectedTile[1]).then(function(data) {
				if (data == 'success') {
					var tile = document.getElementById($scope.selectedTile);
					tile.removeAttribute('style');
				}
			});
		}

		var moveToken = function() {
			console.log('moving token');
		}

		$scope.selectTile = function(tileId) {
			var row = parseInt(tileId[0]);
			var col = parseInt(tileId[1]);
			var tileColor = (row + col) % 2;
			var tile = document.getElementById(tileId);
			if ((tile.childElementCount == 0) && ((tileColor == 0 && $scope.color == 'BLACK') || (tileColor == 1 && $scope.color == 'WHITE'))
					&& $scope.color == $scope.colorToPlay) {
				tile.setAttribute('style','border: 3px solid red');
				if ($scope.selectedTile)
					tile.removeAttribute('style');
				$scope.selectedTile = tileId;
			}
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
			if (!$scope.board
					|| $scope.board == 'Optionnal.empty'
					|| countPlayerToken($scope.playerId,
							$scope.board) < 8)
				placeToken();
			else
				moveToken();
		}
		/*
		 * $("#tatami").draggable({ revert : "invalid" });
		 * $("#a2").droppable(); $("#a1").droppable();
		 */
	} ]);