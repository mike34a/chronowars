package com.miksinouf.chronowars.domain.player;

import java.util.HashSet;
import java.util.Set;

import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.board.Move;
import com.miksinouf.chronowars.domain.board.Position;

public class Tokens {

    private Color color;
    private final Set<Position> tokensPositions;
    private final Integer boardSize;
    private final Integer numberOfTokens;

    public Tokens(Integer boardSize, Integer numberOfTokens, Color color) {
        this.tokensPositions = new HashSet<>(numberOfTokens);
        this.boardSize = boardSize;
        this.numberOfTokens = numberOfTokens;
        this.color = color;
    }

    public void addToken(Integer x, Integer y) throws TooManyTokensException, IllegalMoveException {
        checkNumberOfTokens();
        checkBounds(x, y);

        final Position position = new Position(x, y);
        if (tokensPositions.contains(position)) {
            throw new IllegalMoveException(IllegalMoveException.TOKEN_ALREADY_HERE, position.toString());
        }
        if (!color.isPositionValid(position)) {
            throw new IllegalMoveException(IllegalMoveException.ILLEGAL_POSITION_FOR_PLAYER, position.toString(), color.toString().toLowerCase());
        }

        tokensPositions.add(new Position(x, y));
    }

    private void checkBounds(int x, int y)
            throws IllegalMoveException {
        if (x < 0 || y < 0 || x >= boardSize || y >= boardSize) {
            throw new IllegalMoveException(IllegalMoveException.TOKEN_OUT_OF_BOUNDS, new Position(x, y).toString());
        }
    }

    private void checkNumberOfTokens() throws TooManyTokensException {
        if (tokensPositions.size() >= numberOfTokens) {
            throw new TooManyTokensException();
        }
    }

    public void moveToken(Integer oldX, Integer oldY, Move move) throws IllegalMoveException {
        final Position oldPosition = tokensPositions.stream().
                filter(position -> position.equals(oldX, oldY)).
                findFirst().
                orElseThrow(() -> new IllegalMoveException(IllegalMoveException.NO_TOKEN_HERE, new Position(oldX + move.dx, oldY + move.dy).toString()));

        tokensPositions.add(Position.from(oldPosition).moving(Move.DOWN));
        tokensPositions.remove(oldPosition);
    }

    public Set<Position> tokensPositions() {
        return new HashSet<>(tokensPositions);
    }
}
