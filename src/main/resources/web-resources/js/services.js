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
			return $http.patch(
					'/move_token/' + playerId + '/' + row + '/' + col + '/'
							+ direction).then(function(result) {
				return result.data;
			})
		}
	}
});
