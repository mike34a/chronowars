package com.miksinouf.chronowars.domain.player;

import static com.miksinouf.chronowars.domain.board.MoveResultType.NO_MORE_TOKENS_AVAILABLE;
import static com.miksinouf.chronowars.domain.board.MoveResultType.SUCCESS;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.board.Move;
import com.miksinouf.chronowars.domain.board.MoveResult;

public class Player {

    private final String nickname;
    public final String identifier;
    private final Color color;
    private Integer timeBonus;
    private Integer score;
    private final Tokens tokens;
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
        this.tokens = new Tokens(board.size(), numberOfTokens, color);
    }

    public Player(WaitingPlayer waitingPlayer, Board board, Color color) {
        this.nickname = waitingPlayer.nickname;
        this.color = color;
        this.identifier = waitingPlayer.identifier;
        this.board = board;
        this.timeBonus = 0;
        this.score = 0;
        this.tokens = new Tokens(board.size(), 8, color);
    }

    /**
     * 
     * @param x
     *            x
     * @param y
     *            y
     * @return is state legal ?
     */
    public MoveResult set(Integer x, Integer y) {
        try {
            tokens.addToken(x, y);
        } catch (TooManyTokensException e) {
            return new MoveResult(NO_MORE_TOKENS_AVAILABLE, x, y);
        } catch (IllegalMoveException e) {
            return e.invalidMoveResult;
        }

        return new MoveResult(SUCCESS, x, y);
    }

    public MoveResult move(Integer oldX, Integer oldY, Move move) {
        try {
            return tokens.moveToken(oldX, oldY, move);
        } catch (IllegalMoveException e) {
            return e.invalidMoveResult;
        }
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}
