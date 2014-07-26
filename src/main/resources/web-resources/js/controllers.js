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
				var numberOfTokens = 0;
				board.whiteTokens.tokensPositions.forEach(function(token) {
					numberOfTokens++;
					tile = document.getElementById(token.x + '' + token.y);
					if (tile.childElementCount == 0) {
						tokenImg = document.createElement("img");
						tokenImg.setAttribute('src', 'img/token.png')
						tile.appendChild(tokenImg);
					}
				});
				board.blackTokens.tokensPositions.forEach(function(token) {
					numberOfTokens++;
					tile = document.getElementById(token.x + '' + token.y);
					if (tile.childElementCount == 0) {
						tokenImg = document.createElement("img");
						tokenImg.setAttribute('src', 'img/token.png')
						tile.appendChild(tokenImg);
					}
				});
				$scope.numberOfTokens = numberOfTokens;
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
					delete $scope.selectedTile;
				}
			});
		}

		var moveToken = function() {
			var direction = gameApi.getDirection($scope.selectedTile, $scope.directionTile);
			gameApi.moveToken($routeParams.playerId,
					$scope.selectedTile[0],
					$scope.selectedTile[1],
					direction).then(function(data) {
				if (data == 'success') {
					var tile = document.getElementById($scope.selectedTile);
					tile.removeAttribute('style');
					while (tile.firstChild) {
					    tile.removeChild(tile.firstChild);
					}
					delete $scope.selectedTile;
					tile = document.getElementById($scope.directionTile);
					tile.removeAttribute('style');
					delete $scope.directionTile;
				}
			});
			console.log(direction);
		}

		$scope.selectTile = function(tileId) {
			var row = parseInt(tileId[0]);
			var col = parseInt(tileId[1]);
			var tileColor = (row + col) % 2;
			var tile = document.getElementById(tileId);
			if ($scope.color == $scope.colorToPlay
					&& ((tileColor == 0 && $scope.color == 'BLACK')
							|| (tileColor == 1 && $scope.color == 'WHITE'))) {
				if ($scope.numberOfTokens < 8 && tile.childElementCount == 0) {
						tile.setAttribute('style','border: 3px solid red');
						if ($scope.selectedTile)
							document.getElementById($scope.selectedTile).removeAttribute('style');
						$scope.selectedTile = tileId;
				}
				else if (tile.childElementCount == 1) {
					tile.setAttribute('style','border: 3px solid red');
					if ($scope.selectedTile)
						document.getElementById($scope.selectedTile).removeAttribute('style');
					if ($scope.directionTile)
						document.getElementById($scope.directionTile).removeAttribute('style');
					$scope.selectedTile = tileId;
				}
				else if ($scope.selectedTile && tile.childElementCount == 0) {
					var sRow = parseInt($scope.selectedTile[0]);
					var sCol = parseInt($scope.selectedTile[1]);
					var newRow = parseInt(tileId[0]);
					var newCol = parseInt(tileId[1]);
					if ((Math.abs(sRow - newRow) == 1 && Math.abs(sCol - newCol) <= 1) 
							|| (Math.abs(sRow - newRow) == 0 && Math.abs(sCol - newCol) == 1)) {
						if ($scope.directionTile)
							document.getElementById($scope.directionTile).removeAttribute('style');
						$scope.directionTile = tileId;
						tile.setAttribute('style','border: 3px solid red');
					}
				}
			}
		}

		$scope.getActionText = function() {
			return $scope.selectedTile ? $scope.numberOfTokens >= 8 ? 'move' : 'place' : '';
		}

		$scope.play = function() {
			var playerToken;
			if ($scope.numberOfTokens < 8)
				placeToken();
			else
				moveToken();
		}
		/*
		 * $("#tatami").draggable({ revert : "invalid" });
		 * $("#a2").droppable(); $("#a1").droppable();
		 */
	} ]);