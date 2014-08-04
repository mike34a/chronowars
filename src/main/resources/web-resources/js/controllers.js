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
	'gameHelper',
	'$routeParams',
	function($scope, gameApi, gameHelper, $routeParams) {
		$scope.playerId = $routeParams.playerId;
		$scope.score = 0;
		
		setInterval(function() {
			gameApi.getBoard($routeParams.playerId).then(function(board) {
				$scope.colorToPlay = board.colorToPlay;
				var tile;
				var numberOfTokens = 0;
				var cells = document.getElementById("damier").querySelectorAll("td");
				$scope.score = gameHelper.getScore($scope.color, board);
				for (var i = 0; i < cells.length; i++) {
					tile = cells[i];
					gameHelper.setClass(tile);
					var found = 0;
					board.whiteTokens.tokensPositions.forEach(function(token) {
						if ((token.y == parseInt(tile.id[0])) && (token.x == parseInt(tile.id[1]))) {
							numberOfTokens++;
							found = 1;
							gameHelper.addImg(tile, "img/bluetoken.png");
						}
					});
					board.blackTokens.tokensPositions.forEach(function(token) {
						if ((token.y == parseInt(tile.id[0])) && (token.x == parseInt(tile.id[1]))) {
							numberOfTokens++;
							found = 1;
							gameHelper.addImg(tile, "img/bluetoken.png");
						}
					});
					if (!found)
						gameHelper.removeImg(tile);
			    }
				board.maxShape.tokens.forEach(function(token) {
					gameHelper.setInShape(token);
				})
				$scope.numberOfTokens = numberOfTokens;
			});
		}, 1000);
		
		gameApi.hasGameStarted($routeParams.playerId).then(function(color) {
			if (color != 'false') {
				$scope.color = color;
			}
		});

		var placeToken = function() {
			gameApi.setToken($routeParams.playerId,
							$scope.selectedTile[0],
							$scope.selectedTile[1]).then(function(data) {
				if (data == 'success') {
					removeSelectedTile();
				}
			});
		}

		var moveToken = function() {
			var direction = game.getDirection($scope.selectedTile, $scope.directionTile);
			gameApi.moveToken($routeParams.playerId,
					$scope.selectedTile[0],
					$scope.selectedTile[1],
					direction).then(function(data) {
				if (data == 'success') {
					removeSelectedTile();
					removeDirectionTile();
				}
			});
		}

		$scope.selectTile = function(tileId) {
			var tile = document.getElementById(tileId);
			var tileColor = (parseInt(tileId[0]) + parseInt(tileId[1])) % 2 ? "WHITE" : "BLACK";
			if (tileColor == $scope.color && $scope.color == $scope.colorToPlay) {
				if (!maxTokensPlaced()) {
					if (tile.childElementCount == 0) {
						gameHelper.setSelectedStyle(tile);
						if ($scope.selectedTile)
							removeSelectedTile();
						if ($scope.selectedTile != tileId)
							$scope.selectedTile = tileId;
					}
				}
				else if (tile.childElementCount == 1) {
					gameHelper.setSelectedStyle(tile);
					if ($scope.directionTile)
						removeDirectionTile();
					if ($scope.selectedTile)
						removeSelectedTile();
					if ($scope.selectedTile != tileId)
						$scope.selectedTile = tileId;
				}
				else if ($scope.selectedTile && tile.childElementCount == 0) {
					if (gameHelper.isMovable($scope.selectedTile, tileId)) {
						if ($scope.directionTile)
							removeDirectionTile();
						$scope.directionTile = tileId;
						gameHelper.setSelectedStyle(tile);
					}
				}
			}
		}

		$scope.getActionText = function() {
			if ($scope.colorToPlay != $scope.color)
				return 'wait';
			else if (!maxTokensPlaced()) {
				if (!$scope.selectedTile)
					return 'selectTile';
				else
					return 'place';
			}
			else {
				if (!$scope.selectedTile)
					return 'selectToken';
				else if (!$scope.directionTile)
					return 'selectTile';
				else
					return 'move';
			}
		}

		$scope.play = function() {
			var playerToken;
			if (!maxTokensPlaced())
				placeToken();
			else
				moveToken();
		}
		
		var removeSelectedTile = function() {
			var tile = document.getElementById($scope.selectedTile);
			tile.removeAttribute('style');
			delete $scope.selectedTile;
		}
		
		var removeDirectionTile = function() {
			var tile = document.getElementById($scope.directionTile);
			tile.removeAttribute('style');
			delete $scope.directionTile;
		}
		
		var maxTokensPlaced = function() {
			return $scope.numberOfTokens == 10;
		}
	} ]);