package com.miksinouf.chronowars.domain.player;

import static com.miksinouf.chronowars.domain.board.MoveResultType.SUCCESS;

import java.util.HashSet;
import java.util.Set;

import com.miksinouf.chronowars.domain.board.*;

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
            throw new IllegalMoveException(MoveResultType.TOKEN_ALREADY_HERE, x, y);
        }
        if (!color.isPositionValid(position)) {
            throw new IllegalMoveException(MoveResultType.NOT_ON_PROPER_COLOR, x, y);
        }

        tokensPositions.add(new Position(x, y));
    }

    private void checkBounds(int x, int y)
            throws IllegalMoveException {
        if (x < 0 || y < 0 || x >= boardSize || y >= boardSize) {
            throw new IllegalMoveException(MoveResultType.TOKEN_OUT_OF_BOUNDS, x, y);
        }
    }

    private void checkNumberOfTokens() throws TooManyTokensException {
        if (tokensPositions.size() >= numberOfTokens) {
            throw new TooManyTokensException();
        }
    }

    public MoveResult moveToken(Integer oldX, Integer oldY, Move move) throws IllegalMoveException {
        final Position oldPosition = tokensPositions.stream().
                filter(position -> position.equals(oldX, oldY)).
                findFirst().
                orElseThrow(() -> new IllegalMoveException(MoveResultType.NO_TOKEN_HERE, oldX, oldY));

        final Position newPosition = Position.from(oldPosition).moving(move);
        tokensPositions.add(newPosition);
        tokensPositions.remove(oldPosition);

        return new MoveResult(SUCCESS, newPosition);
    }

    public Set<Position> tokensPositions() {
        return new HashSet<>(tokensPositions);
    }
}
