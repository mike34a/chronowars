'use strict';

/* Services */

var chronoWarsServices = angular.module('chronoWarsServices', [ 'ngResource' ]);

chronoWarsServices.factory('gameApi', function($http) {
	return {
		
		registerPlayer: function(name) {
			return $http.get('/register/' + name).then(function(result) {
				return result.data;
			});
		},

		hasGameStarted: function(playerId) {
			return $http.get('/have_i_running_game/' + playerId).then(
					function(result) {
						return result.data;
					});
		},

		getBoard: function(playerId) {
			return $http.get('/get_game/' + playerId).then(function(result) {
				return result.data;
			})
		},

		setToken: function(playerId, row, col) {
			return $http.put('/set_token/' + playerId + '/' + col + '/' + row)
					.then(function(result) {
						return result.data;
					})
		},

		moveToken: function(playerId, row, col, direction) {
			$http.defaults.headers.patch = {
			        'Content-Type': 'application/json;charset=utf-8'
			    };
			return $http({
			    url: '/move_token/' + playerId + '/' + col + '/' + row + '/' + direction,
			    method: "PATCH",
					}).then(function(result) {
				return result.data;
			})
		}
	}
});

chronoWarsServices.factory('gameHelper', function() {
	return {
		setClass: function(tile) {
			var color = (parseInt(tile.id[0]) + parseInt(tile.id[1])) % 2 == 0 ? 'BLACK' : 'WHITE';
			tile.setAttribute("class", color.concat(' ui-widget-header'));
		},
		
		getTileColor: function(tile) {
			return ((parseInt(tile.id[0]) + parseInt(tile.id[1])) % 2 == 0 ? 'BLACK' : 'WHITE');
		},
		
		getScore: function(color, board) {
			return color == "WHITE" ? board.whiteScore : board.blackScore;
		},
		
		getDirection: function(selectedTile, directionTile) {
			var rs = parseInt(selectedTile[0]);
			var cs = parseInt(selectedTile[1]);
			var rd = parseInt(directionTile[0]);
			var cd = parseInt(directionTile[1]);
			if (rd > rs) {
				return cd > cs ? 'DOWN_RIGHT' : cd < cs ? 'DOWN_LEFT' : 'DOWN';
			}
			if (rd < rs) {
				return cd > cs ? 'UP_RIGHT' : cd < cs ? 'UP_LEFT' : 'UP';
			}
			if (rd == rs) {
				return cd > cs ? 'RIGHT' : cd < cs ? 'LEFT' : 'BAD_MOVE';
			}
		},
		
		addImg: function(tile, src) {
			var tokenImg;
			if (tile.childElementCount == 0) {
				tokenImg = document.createElement("img");
				tokenImg.setAttribute('src', src);
				tokenImg.setAttribute('class', 'token ui-widget-content');
				tokenImg.setAttribute('id', 'img'.concat(tile.id));
				tile.appendChild(tokenImg);
			}	
		},
		
		removeImg: function(tile) {
			while (tile.firstChild) {
				tile.removeChild(tile.firstChild);
			}
		},
		
		setInShape: function(tile) {
			tile.setAttribute("class", tile.className + " inShape");
		},
		
		isMovable: function(selectedTile, newTile) {
			var sRow = parseInt(selectedTile.id[0]);
			var sCol = parseInt(selectedTile.id[1]);
			var newRow = parseInt(newTile.id[0]);
			var newCol = parseInt(newTile.id[1]);
			if (newTile.childElementCount == 0)
				return ((Math.abs(sRow - newRow) == 1 && Math.abs(sCol - newCol) == 1)
				|| ((sRow - newRow == 0 && sCol - newCol == 2)
					&& document.getElementById(parseInt(selectedTile.id) - 1) != null && document.getElementById(parseInt(selectedTile.id) - 1).childElementCount > 0)
				|| ((sRow - newRow == 0 && sCol - newCol == -2)
						&& document.getElementById(parseInt(selectedTile.id) + 1) != null && document.getElementById(parseInt(selectedTile.id) + 1).childElementCount > 0)
				|| ((sRow - newRow == 2 && sCol - newCol == 0)
						&& document.getElementById(parseInt(selectedTile.id) - 10) != null && document.getElementById(parseInt(selectedTile.id) - 10).childElementCount > 0)
				|| ((sRow - newRow == -2 && sCol - newCol == 0)
						&& document.getElementById(parseInt(selectedTile.id) + 10) != null && document.getElementById(parseInt(selectedTile.id) + 10).childElementCount > 0));
			else
				return false;
		},
		
		setDraggable: function(tile) {
			$("#" + tile.firstElementChild.id).draggable({revert: true});
		},
		
		getWinner: function(boardResponse) {
			return (boardResponse.whiteScore > boardResponse.blackScore ? boardResponse.whiteNick :
				boardResponse.whiteScore < boardResponse.blackScore ? boardResponse.blackNick :
					'Tie')
		}
	}
});
