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

		getGameId: function(playerId) {
			return $http.get('/have_i_running_game/' + playerId).then(
					function(result) {
						return result.data;
					});
		},

		getBoard: function(gameId) {
			$http.get('/get_game/' + gameId).then(function(result) {
				return result.data;
			})
		},

		setToken: function(playerId, gameId, row, col) {
			$http.put('/set_token/' + gameId + '/' + playerId + '/' + row + '/' + col)
					.then(function(result) {
						return result.data;
					})
		},

		moveToken: function(playerId, gameId, row, col, direction) {
			$http.patch(
					'/set_token/' + gameId + '/' + playerId + '/' + row + '/' + col + '/'
							+ direction).then(function(result) {
				return result.data;
			})
		}
	}
});
