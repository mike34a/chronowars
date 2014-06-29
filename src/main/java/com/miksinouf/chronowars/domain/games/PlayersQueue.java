package com.miksinouf.chronowars.domain.games;

import java.util.LinkedList;
import java.util.Queue;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.WaitingPlayer;

public class PlayersQueue {
    private final static int TOKENS_NUMBER = 5;
    private final Queue<WaitingPlayer> playersQueue = new LinkedList<>();

    public Player poll() {
        final WaitingPlayer whiteWaitingPlayer = playersQueue.poll();
        return new Player(whiteWaitingPlayer.nickname, Color.WHITE, TOKENS_NUMBER, whiteWaitingPlayer.identifier, new Board());
    }

    public int size() {
        return playersQueue.size();
    }

    public void add(WaitingPlayer waitingPlayer) {
        playersQueue.add(waitingPlayer);
    }
}
