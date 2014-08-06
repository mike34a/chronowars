describe('Chronowars game helper tests', function() {
  beforeEach(module('chronoWarsServices'));
 
	describe('setClass', function() {
		it('should set the class of the tile to his matching color and add ui-widget-header', inject(
			function(gameHelper) {
				var tile = document.createElement("td");
				tile.setAttribute("id", "25");
				gameHelper.setClass(tile);
				expect(tile.getAttribute("class")).toBe("WHITE ui-widget-header");

				tile = document.createElement("td");
				tile.setAttribute("id", "26");
				gameHelper.setClass(tile);
				expect(tile.getAttribute("class")).toBe("BLACK ui-widget-header");
			}
		));
	});
  
	describe('getTileColor', function() {
		it('should return the tile color"', inject(
			function(gameHelper) {
				var tile = document.createElement("td");
				tile.setAttribute("id", "25");
				gameHelper.setClass(tile);
				expect(gameHelper.getTileColor(tile)).toBe("WHITE");

				tile = document.createElement("td");
				tile.setAttribute("id", "26");
				gameHelper.setClass(tile);
				expect(gameHelper.getTileColor(tile)).toBe("BLACK");
			}
		));
	});
	
	describe('getScore', function() {
		it('should get the proper player score', inject(
			function(gameHelper) {
				var blackColor = "BLACK";
				var whiteColor = "WHITE";
				var board = {
						"whiteScore" : "10",
						"blackScore" : "20",
				};
				expect(gameHelper.getScore(whiteColor, board)).toBe(board.whiteScore);
				expect(gameHelper.getScore(blackColor, board)).toBe(board.blackScore);
			}
		));
	});
	
	describe('getDirection', function() {
		it('should get the proper direction', inject(
			function(gameHelper) {
				var originTile = document.createElement("td");
				originTile.setAttribute("id", "44");
				expect(gameHelper.getDirection(originTile.id, originTile.id)).toBe("BAD_MOVE");
				
				var upTile = document.createElement("td");
				upTile.setAttribute("id", "24");
				expect(gameHelper.getDirection(originTile.id, upTile.id)).toBe("UP");
				
				var upRightTile = document.createElement("td");
				upRightTile.setAttribute("id", "35");
				expect(gameHelper.getDirection(originTile.id, upRightTile.id)).toBe("UP_RIGHT");
				
				var rightTile = document.createElement("td");
				rightTile.setAttribute("id", "46");
				expect(gameHelper.getDirection(originTile.id, rightTile.id)).toBe("RIGHT");
				
				var downRightTile = document.createElement("td");
				downRightTile.setAttribute("id", "55");
				expect(gameHelper.getDirection(originTile.id, downRightTile.id)).toBe("DOWN_RIGHT");
				
				var downTile = document.createElement("td");
				downTile.setAttribute("id", "64");
				expect(gameHelper.getDirection(originTile.id, downTile.id)).toBe("DOWN");
				
				var downLeftTile = document.createElement("td");
				downLeftTile.setAttribute("id", "53");
				expect(gameHelper.getDirection(originTile.id, downLeftTile.id)).toBe("DOWN_LEFT");
				
				var leftTile = document.createElement("td");
				leftTile.setAttribute("id", "42");
				expect(gameHelper.getDirection(originTile.id, leftTile.id)).toBe("LEFT");
				
				var upLeftTile = document.createElement("td");
				upLeftTile.setAttribute("id", "33");
				expect(gameHelper.getDirection(originTile.id, upLeftTile.id)).toBe("UP_LEFT");
			}
		));
	});
	
	
	describe('addImg', function() {
		it('should add the token image to the tile', inject(
			function(gameHelper) {
				//TODO: Uncomment to test with mvn jasmine:bdd, childElementCount seems to be undefined in standard tests
				//*
				var tile = document.createElement("td");
				tile.setAttribute("id", "26");
				expect(tile.childElementCount).toBe(0);
				gameHelper.addImg(tile, "/img/bluetoken.png");
				expect(tile.childElementCount).toBe(1);
				expect(tile.firstChild.getAttribute("src")).toBe("/img/bluetoken.png");
				expect(tile.firstChild.getAttribute("class")).toBe('token ui-widget-content');
				expect(tile.firstChild.getAttribute("id")).toBe("img26");
				//*/
			}
		));
	});
	
	describe('removeImg', function() {
		it('should remove the token img', inject(
			function(gameHelper) {
				//TODO: Uncomment to test with mvn jasmine:bdd, childElementCount seems to be undefined in standard tests
				//*
				var tile = document.createElement("td");
				tile.setAttribute("id", "26");
				expect(tile.childElementCount).toBe(0);
				gameHelper.addImg(tile, "/img/bluetoken.png");
				expect(tile.childElementCount).toBe(1);
				gameHelper.removeImg(tile);
				expect(tile.childElementCount).toBe(0);
				//*/
			}
		));
	});
	
	describe('setInShape', function() {
		it('should set a tile to inshape class', inject(
				function(gameHelper) {
					var tile = document.createElement("td");
					tile.setAttribute("id", "26");
					expect(tile.getAttribute("class")).not.toBe('inShape');
					gameHelper.setInShape(tile);
					expect(tile.getAttribute("class")).toBe('inShape');
				}
			))
	});
	
	describe('isMovable', function() {
		it('should return if a move is valid"', inject(
			function(gameHelper) {
				//TODO: Uncomment to test with mvn jasmine:bdd, childElementCount seems to be undefined in standard tests
				//*
				var testDiv = document.createElement("div");
				document.body.appendChild(testDiv);
				var originTile = document.createElement("td");
				testDiv.appendChild(originTile);
				originTile.setAttribute("id", "44");
				gameHelper.addImg(originTile, "/img/bluetoken.png");
				var directionTile = document.createElement("td");
				testDiv.appendChild(directionTile);
				var opponentTile = document.createElement("td");
				testDiv.appendChild(opponentTile);
				gameHelper.addImg(opponentTile, "/img/bluetoken.png");
				
				directionTile.setAttribute("id", "55");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);
				
				directionTile.setAttribute("id", "53");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);
				
				directionTile.setAttribute("id", "35");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);
				
				directionTile.setAttribute("id", "33");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);
				
				gameHelper.addImg(directionTile, "/img/bluetoken.png");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(false);
				
				gameHelper.removeImg(directionTile);
				opponentTile.setAttribute("id", "45");
				directionTile.setAttribute("id", "46");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);

				opponentTile.setAttribute("id", "43");
				directionTile.setAttribute("id", "42");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);
				
				opponentTile.setAttribute("id", "34");
				directionTile.setAttribute("id", "24");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);
				
				opponentTile.setAttribute("id", "54");
				directionTile.setAttribute("id", "64");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);
				
				opponentTile.setAttribute("id", "45");
				directionTile.setAttribute("id", "46");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(true);

				opponentTile.setAttribute("id", "33");
				directionTile.setAttribute("id", "46");
				expect(gameHelper.isMovable(originTile, directionTile)).toBe(false);
				
				document.body.removeChild(testDiv);
				//TODO: Uncomment to test with mvn jasmine:bdd, childElementCount seems to be undefined in standard tests
				//*
			}
		));
	});
});