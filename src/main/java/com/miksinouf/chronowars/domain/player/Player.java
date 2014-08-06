package com.miksinouf.chronowars.domain.player;

import static com.miksinouf.chronowars.domain.board.MoveResultType.SUCCESS;

import com.miksinouf.chronowars.domain.board.*;

public class Player {

    public final String nickname;
    public final String identifier;
    private final Color color;
    private Integer timeBonus;
    private Integer score;
    private final Board board;
    private Player opponent;

    public Player(String nickname, Color color, Integer numberOfTokens,
            String identifier, Board board) {
        this.nickname = nickname;
        this.color = color;
        this.identifier = identifier;
        this.board = board;
        this.timeBonus = 0;
        this.score = 0;
    }

    public Player(WaitingPlayer waitingPlayer, Board board, Color color) {
        this.nickname = waitingPlayer.nickname;
        this.color = color;
        this.identifier = waitingPlayer.identifier;
        this.board = board;
        this.timeBonus = 0;
        this.score = 0;
    }

    /**
     * 
     * @param x
     *            x
     * @param y
     *            y
     * @return is state legal ?
     */
    public MoveResult set(Integer x, Integer y) throws IllegalMoveException, TooManyTokensException {
        if (this.color != this.board.colorToPlay)
            throw new IllegalMoveException(MoveResultType.BAD_PLAYER, x, y);
        board.placeToken(x, y);
        refreshPlayerScore(new Position(x, y));
        return new MoveResult(SUCCESS, x, y);
    }

    public MoveResult move(Integer oldX, Integer oldY, Move move) throws IllegalMoveException {
    	MoveResult moveResult;
        if (this.color != this.board.colorToPlay)
            throw new IllegalMoveException(MoveResultType.BAD_PLAYER, oldX, oldY);
        moveResult = board.moveToken(oldX, oldY, move);
        refreshPlayerScore(moveResult.position);
        return moveResult;
    }

    public void refreshPlayerScore(Position p) {
    	for(Shape shape : this.board.getShapes()){
    		if (shape.tokens.contains(p))
    			this.board.maxShape = shape.getScore() > this.board.maxShape.getScore() ? shape : this.board.maxShape;
    	}
    	this.score += this.board.maxShape.getScore();
    }
    
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
    
    public Board getBoard() {
    	return this.board;
    }

	public Color getColor() {
		return color;
	}
	
	public Integer getScore() {
		return score;
	}
}
