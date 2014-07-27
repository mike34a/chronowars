'use strict';

/* Filters */

angular.module('chronoWarsFilters', [])
	.filter('userNameFilter', function() {
	  return function(input) {
	    return !input || input.length == 0 ? 'Please enter  a username' : input.length < 4 ? 'Too short !' : 'Click New Game to launch';
	  };
	})
	.filter('funcActionFilter', function() {
	  return function(input) {
		  return input == '' ? '' : input == 'move' ? 'moveToken()' : 'placeToken()';
	  };
	})
	.filter('textActionFilter', function() {
	  return function(input) {
	    return input == 'move' ? 'Move Token' :
	    	input == 'place' ? 'Place Token' :
	    	input == 'wait' ? 'Please Wait...' :
	    	input == 'selectTile' ? 'Select Tile' :
	    	input == 'selectToken' ? 'Select Token' : 'Undefined Action';
	  };
	})
	.filter('gameStatusFilter', function() {
		return function(input) {
			return input == '0' ? 'New Game' : 'Finding you a game...';
		}
	});
