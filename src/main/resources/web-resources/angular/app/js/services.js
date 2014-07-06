'use strict';

/* Services */

var chronoWarsServices = angular.module('chronoWarsServices', [ 'ngResource' ]);

chronoWarsServices.factory('launchGame', function($http) {
    return {
    	/*TODO : find a way to work with promises in templates wight angular 1.2*/
        getPlayerId: function (name) {
        	return $http.get('/register/' + name).then(function(result) {
        		alert(result.data);
        		console.log(result.data);
                return result.data;
            });
        }
    }
});
