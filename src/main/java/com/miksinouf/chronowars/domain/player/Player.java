package com.miksinouf.chronowars.domain.player;

import com.miksinouf.chronowars.domain.Board;
import com.miksinouf.chronowars.domain.token.IllegalMoveException;
import com.miksinouf.chronowars.domain.token.Move;

public class Player {

    private final String nickname;
    public final String identifier;
    private final Color color;
    private Integer timeBonus;
    private Integer score;
    private final Tokens tokens;
    private final Board board;

    public Player(String nickname, Color color, Integer numberOfTokens,
                  String identifier, Board board) {
        this.nickname = nickname;
        this.color = color;
        this.identifier = identifier;
        this.board = board;
        this.timeBonus = 0;
        this.score = 0;
        this.tokens = new Tokens(board.size(), numberOfTokens);
    }

    public Player(WaitingPlayer waitingPlayer, Board board, Color color) {
        this.nickname = waitingPlayer.nickname;
        this.color = color;
        this.identifier = waitingPlayer.identifier;
        this.board = board;
        this.timeBonus = 0;
        this.score = 0;
        this.tokens = new Tokens(board.size(), 8);
    }

    /**
     * 
     * @param x
     *            x
     * @param y
     *            y
     * @return is state legal
     */
    public boolean set(Integer x, Integer y) {
        try {
            tokens.addToken(x,y);
        } catch (TooManyTokensException | IllegalMoveException e) {
            return false;
        }

        return true;
    }

    public boolean move(Integer oldX, Integer oldY, Integer newX, Integer newY) {
        try {
            tokens.moveToken(oldX, oldY, Move.DOWN);
        } catch (IllegalMoveException e) {
            return false;
        }

        return true;
    }
}
