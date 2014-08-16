package com.miksinouf.chronowars.domain.games;

import com.miksinouf.chronowars.domain.board.Board;

public class GameResponse {
    public Board board;
    public String status;
    public String whiteScore;
    public String blackScore;
    public String whiteNick;
    public String blackNick;

    public GameResponse(Board board,
                        String status,
                        String whiteScore,
                        String blackScore,
                        String whiteNick,
                        String blackNick) {
        this.board = board;
        this.status = status;
        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
        this.whiteNick = whiteNick;
        this.blackNick = blackNick;
    }
}
