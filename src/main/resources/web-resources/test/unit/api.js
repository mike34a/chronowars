describe('Chronowars api tests', function() {
  beforeEach(module('chronoWarsServices'));
 
  describe('RegisterPlayer', function() {
    var data, responseData,
      requestMethod, requestUrl;
 
    it('should send get request to "/register/logar"', inject(
      function($httpBackend, gameApi) {
        $httpBackend.expectGET('/register/logar').respond(data);
        gameApi.registerPlayer('logar');
        $httpBackend.flush();
      }
    ));
  });
  
  describe('hasGameStarted', function() {
	    var data, responseData,
	      requestMethod, requestUrl;
	 
	    it('should send get request to "/have_i_running_game/1234"', inject(
	      function($httpBackend, gameApi) {
	        $httpBackend.expectGET('/have_i_running_game/1234').respond(data);
	        gameApi.hasGameStarted(1234);
	        $httpBackend.flush();
	      }
	    ));
	  });
  
  describe('getBoard', function() {
	    var data, responseData,
	      requestMethod, requestUrl;
	 
	    it('should send get request to "/get_game/1234"', inject(
	      function($httpBackend, gameApi) {
	        $httpBackend.expectGET('/get_game/1234').respond(data);
	        gameApi.getBoard(1234);
	        $httpBackend.flush();
	      }
	    ));
	  });

  describe('setToken', function() {
	    var data, responseData,
	      requestMethod, requestUrl;
	 
	    it('should send get request to "/set_token/:playerid/:col/:row"', inject(
	      function($httpBackend, gameApi) {
	        $httpBackend.expectPUT('/set_token/1234/2/1').respond(data);
	        gameApi.setToken(1234, 1, 2);
	        $httpBackend.flush();
	      }
	    ));
	  });

  describe('moveToken', function() {
	    var data, responseData,
	      requestMethod, requestUrl;
	 
	    it('should send patch request to "/move_token/:playerid/:col/:row/:direction"', inject(
	      function($httpBackend, gameApi) {
	        $httpBackend.expectPATCH('/move_token/1234/2/1/UP').respond(data);
	        gameApi.moveToken(1234, 1, 2, 'UP');
	        $httpBackend.flush();
	      }
	    ));
	  });
});