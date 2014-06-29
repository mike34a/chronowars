package com.miksinouf.chronowars.domain.player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.miksinouf.chronowars.domain.Position;
import com.miksinouf.chronowars.domain.token.IllegalMoveException;
import com.miksinouf.chronowars.domain.token.Move;
import com.miksinouf.chronowars.domain.token.Token;

public class Tokens {

    private List<Token> tokens;
    private final Integer boardSize;
    private final Integer numberOfTokens;

    public Tokens(Integer boardSize, Integer numberOfTokens) {
        this.tokens = new ArrayList<>(numberOfTokens);
        this.boardSize = boardSize;
        this.numberOfTokens = numberOfTokens;
    }

    public void addToken(Integer x, Integer y) throws TooManyTokensException, IllegalMoveException {
        if (tokens.size() >= numberOfTokens) {
            throw new TooManyTokensException();
        }

        tokens.add(new Token(new Position(x, y), boardSize));
    }

    public void moveToken(Integer oldX, Integer oldY, Move move) throws IllegalMoveException {
        final Token tokenToMove = tokens.stream().
                filter(token -> token.hasPosition(oldX, oldY)).
                findFirst().
                orElseThrow(() -> new IllegalMoveException(IllegalMoveException.TOKEN_OUT_OF_BOUNDS, new Position(oldX + move.dx, oldY + move.dy)));

        tokenToMove.move(Move.DOWN);
    }

    public List<Position> tokensPositions() {
        return tokens.stream().
                map(Token::getPosition).
                collect(Collectors.toList());
    }
}
