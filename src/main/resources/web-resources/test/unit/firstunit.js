describe('Chronowars first tests', function() {
  beforeEach(module('chronoWarsServices'));
 
  // ...
 
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
  // ...
});