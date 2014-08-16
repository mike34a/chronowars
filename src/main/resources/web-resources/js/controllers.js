'use strict';

/* Controllers */
var chronoWarsControllers = angular.module('chronoWarsControllers', []);

chronoWarsControllers.controller('HomeCtrl', [
    '$scope',
    'gameApi',
	'$location',
	function($scope, gameApi, $location) {
		$scope.registerPlayer = function(name) {
			gameApi.registerPlayer(encodeURI(name)).then(function(pidres) {
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
		
		gameApi.hasGameStarted($routeParams.playerId).then(function(color) {
			if (color != 'false') {
				$scope.color = color;
			}
		});
		
		setInterval(function() {
			gameApi.getBoard($routeParams.playerId).then(function(board) {
				$scope.colorToPlay = board.colorToPlay;
				var tile;
				var numberOfTokens = 0;
				var cells = document.getElementById("damier").querySelectorAll("td");
				$scope.score = gameHelper.getScore($scope.color, board);
				for (var i = 0; i < cells.length; i++) {
										
					$scope.playerName = $scope.color == "WHITE" ? board.whiteNick : board.blackNick;
					$scope.opponentName = $scope.color == "WHITE" ? board.blackNick : board.whiteNick;
					$scope.playerScore = $scope.color == "WHITE" ? board.whiteScore : board.blackScore;
					$scope.opponentScore = $scope.color == "WHITE" ? board.blackScore : board.whiteScore;
					$scope.playerImage = $scope.color == "WHITE" ? 'img/whitetoken.png' : 'img/blacktoken.png';
					$scope.opponentImage = $scope.color == "WHITE" ? 'img/blacktoken.png' : 'img/whitetoken.png';
					
					tile = cells[i];
					gameHelper.setClass(tile);
					var found = 0;
					board.whiteTokens.tokensPositions.forEach(function(token) {
						if ((token.y == parseInt(tile.id[0])) && (token.x == parseInt(tile.id[1]))) {
							numberOfTokens++;
							found = 1;
							gameHelper.addImg(tile, "img/blacktoken.png");
							if (isDraggable(tile))
								gameHelper.setDraggable(tile);
						}
					});
					board.blackTokens.tokensPositions.forEach(function(token) {
						if ((token.y == parseInt(tile.id[0])) && (token.x == parseInt(tile.id[1]))) {
							numberOfTokens++;
							found = 1;
							gameHelper.addImg(tile, "img/whitetoken.png");
							if (isDraggable(tile))
								gameHelper.setDraggable(tile);
						}
					});
					if (!found) {
						gameHelper.removeImg(tile);
						if (maxTokensPlaced() && gameHelper.getTileColor(tile) == $scope.color) {
							$( "#" + tile.id ).droppable({
								drop: function( event, ui ) {
									var directionTile = document.getElementById($(this).attr("id"));
									var baseTile = document.getElementById($(ui.draggable).attr("id")).parentNode;
									if (isDroppable(directionTile, baseTile)) {
										$('.ui-draggable-dragging').hide();
										moveToken(baseTile, directionTile);
									}
								}
							});
						}
					}
			    }
				board.maxShape.tokens.forEach(function(token) {
					gameHelper.setInShape(document.getElementById(token.y + '' + token.x));
				})
				$scope.numberOfTokens = numberOfTokens;
			});
		}, 1000);

		var placeToken = function(tileId) {
			gameApi.setToken($routeParams.playerId,
							tileId[0],
							tileId[1]).then(function(data) {
				if (data == 'success') {
				}
			});
		}

		var moveToken = function(baseTile, directionTile) {
			var direction = gameHelper.getDirection(baseTile.id, directionTile.id);
			gameApi.moveToken($routeParams.playerId,
					baseTile.id[0],
					baseTile.id[1],
					direction).then(function(data) {
				if (data == 'success') {
				}
			});
		}

		$scope.selectTile = function(tileId) {
			var tile = document.getElementById(tileId);
			var tileColor = (parseInt(tileId[0]) + parseInt(tileId[1])) % 2 ? "WHITE" : "BLACK";
			if (tileColor == $scope.color && $scope.color == $scope.colorToPlay) {
				if (!maxTokensPlaced()) {
					placeToken(tileId);
				}
			}
		}

		$scope.getActionText = function() {
			if ($scope.colorToPlay != $scope.color)
				return 'wait';
			else if (!maxTokensPlaced())
				return 'place';
			else
				return 'move';
		}
		
		var maxTokensPlaced = function() {
			return $scope.numberOfTokens == 10;
		}
		
		var isDraggable = function(tile) {
			return (maxTokensPlaced() && gameHelper.getTileColor(tile) == $scope.color && $scope.color == $scope.colorToPlay);
		}
		
		var isDroppable = function(tile, baseTile) {
			return (gameHelper.getTileColor(tile) == $scope.color &&
					$scope.color == $scope.colorToPlay &&
					maxTokensPlaced() &&
					gameHelper.isMovable(baseTile, tile));
		}
	} ]);