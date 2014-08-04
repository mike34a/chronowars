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
				tokenImg.setAttribute('src', src)
				tokenImg.setAttribute('class', 'token')
				tile.appendChild(tokenImg);
			}	
		},
		
		removeImg: function(tile) {
			while (tile.firstChild) {
				tile.removeChild(tile.firstChild);
			}
		},
		
		setInShape: function(token) {
			document.getElementById(token.y + '' + token.x).setAttribute("class", "inShape");
		},
		
		setSelectedStyle: function(tile) {
			tile.setAttribute('style','background-color:red');
		},
		
		unselectTile: function(tile) {
			tile.removeAttribute('style');
		},
		
		isMovable: function(selectedTile, newTile) {
			var sRow = parseInt(selectedTile[0]);
			var sCol = parseInt(selectedTile[1]);
			var newRow = parseInt(newTile[0]);
			var newCol = parseInt(newTile[1]);
			return ((Math.abs(sRow - newRow) == 1 && Math.abs(sCol - newCol) <= 1)
			|| (sRow - newRow == 0 && Math.abs(sCol - newCol) == 1)
			|| ((sRow - newRow == 0 && sCol - newCol == 2)
				&& document.getElementById(parseInt(selectedTile) - 1).childElementCount > 0)
			|| ((sRow - newRow == 0 && sCol - newCol == -2)
					&& document.getElementById(parseInt(selectedTile) + 1).childElementCount > 0)
			|| ((sRow - newRow == 2 && sCol - newCol == 0)
					&& document.getElementById(parseInt(selectedTile) - 10).childElementCount > 0)
			|| ((sRow - newRow == -2 && sCol - newCol == 0)
					&& document.getElementById(parseInt(selectedTile) + 10).childElementCount > 0))
		}
	}
});
