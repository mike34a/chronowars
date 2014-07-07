'use strict';

/* Filters */

angular.module('chronoWarsFilters', []).filter('userNameFilter', function() {
  return function(input) {
    return !input || input.length == 0 ? 'Please enter  a username' : input.length < 4 ? 'Too short !' : 'Click New Game to launch';
  };
});
