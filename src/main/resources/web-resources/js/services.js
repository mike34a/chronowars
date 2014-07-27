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
			return $http.put('/set_token/' + playerId + '/' + row + '/' + col)
					.then(function(result) {
						return result.data;
					})
		},

		moveToken: function(playerId, row, col, direction) {
			$http.defaults.headers.patch = {
			        'Content-Type': 'application/json;charset=utf-8'
			    };
			return $http({
			    url: '/move_token/' + playerId + '/' + row + '/' + col + '/' + direction,
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
		}
	}
});
