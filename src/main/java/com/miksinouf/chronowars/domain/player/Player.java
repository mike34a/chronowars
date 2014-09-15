package com.miksinouf.chronowars.domain.player;

import static com.miksinouf.chronowars.domain.board.MoveResultType.SUCCESS;

import org.eclipse.jetty.websocket.api.Session;

import com.miksinouf.chronowars.domain.board.*;
import com.miksinouf.chronowars.domain.games.GamesQueue;

public class Player {

    private final String nickname;
    private final String identifier;
    private final Color color;
    private Integer score;
    private final Board board;
    private Player opponent;
    public Session session;
    public Player(String nickname, Color color,
                  String identifier, Board board) {
        this.nickname = nickname;
        this.color = color;
        this.identifier = identifier;
        this.board = board;
        this.score = 0;
    }

    public Player(WaitingPlayer waitingPlayer, Board board, Color color) {
        this.nickname = waitingPlayer.nickname;
        this.color = color;
        this.session = waitingPlayer.sess;
        this.identifier = waitingPlayer.identifier;
        this.board = board;
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
        checkPositionForPlayer(x, y);
        placeToken(x, y);
        return new MoveResult(SUCCESS, x, y);
    }

    public MoveResult move(Integer oldX, Integer oldY, Move move) throws IllegalMoveException {
        checkPositionForPlayer(oldX, oldY);
        MoveResult moveResult = board.moveToken(oldX, oldY, move);
        refreshPlayerScore(moveResult.position);

        return moveResult;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public Player getOpponent() {
        return opponent;
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

    private void placeToken(Integer x, Integer y) throws IllegalMoveException, TooManyTokensException {
        board.placeToken(x, y);
        refreshPlayerScore(new Position(x, y));
    }

    private void checkPositionForPlayer(Integer oldX, Integer oldY) throws IllegalMoveException {
        if (this.color != this.board.colorToPlay) {
            throw new IllegalMoveException(MoveResultType.BAD_PLAYER, oldX, oldY);
        }
    }

    private void refreshPlayerScore(Position p) {
        this.score += this.board.maxShape.score;
        this.score = this.score > GamesQueue.MAX_SCORE ? GamesQueue.MAX_SCORE : this.score;
    }

    public String getNickname() {
        return nickname;
    }
}
