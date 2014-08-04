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
		$scope.score = 0;
		
		setInterval(function() {
			gameApi.getBoard($routeParams.playerId).then(function(board) {
				$scope.colorToPlay = board.colorToPlay;
				var tile;
				var tokenImg;
				var shapedTile;
				var numberOfTokens = 0;
				var table = document.getElementById("damier");
				var cells = table.querySelectorAll("td");
				if ($scope.color == "WHITE")
					$scope.score = board.whiteScore;
				if ($scope.color == "BLACK")
					$scope.score = board.blackScore;
				for (var i = 0; i < cells.length; i++) {
					tile = cells[i];
					tile.setAttribute("class", (parseInt(tile.id[0]) + parseInt(tile.id[1])) % 2 == 0 ? 'BLACK' : 'WHITE');
					var found = 0;
					board.whiteTokens.tokensPositions.forEach(function(token) {
						if ((token.y == parseInt(tile.id[0])) && (token.x == parseInt(tile.id[1]))) {
							numberOfTokens++;
							found = 1;
							gameApi.addImg(tile, "img/bluetoken.png");
						}
					});
					board.blackTokens.tokensPositions.forEach(function(token) {
						if ((token.y == parseInt(tile.id[0])) && (token.x == parseInt(tile.id[1]))) {
							numberOfTokens++;
							found = 1;
							gameApi.addImg(tile, "img/bluetoken.png");
						}
					});
					if (!found)
						gameApi.removeImg(tile);
			    }
				board.maxShape.tokens.forEach(function(token) {
					gameApi.setInShape(token);
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
			var direction = gameApi.getDirection($scope.selectedTile, $scope.directionTile);
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
			if (tileColor == $scope.color) {
				if (!maxTokensPlaced()) {
					if (tile.childElementCount == 0) {
						gameApi.setSelectedStyle(tile);
						if ($scope.selectedTile)
							removeSelectedTile();
						$scope.selectedTile = tileId;
					}
				}
				else if (tile.childElementCount == 1) {
					gameApi.setSelectedStyle(tile);
					if ($scope.directionTile)
						removeDirectionTile();
					if ($scope.selectedTile)
						removeSelectedTile();
					$scope.selectedTile = tileId;
				}
				else if ($scope.selectedTile && tile.childElementCount == 0) {
					if (gameApi.isMovable($scope.selectedTile, tileId)) {
						if ($scope.directionTile)
							removeDirectionTile();
						$scope.directionTile = tileId;
						gameApi.setSelectedStyle(tile);
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