package com.miksinouf.chronowars.domain.games;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.board.Move;
import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.board.MoveResult;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;
import com.miksinouf.chronowars.domain.player.UnknownPlayerException;
import com.google.gson.*;

public class Players {
    private final Map<String, Player> players = new HashMap<>();

    public void addPlayers(Player whitePlayer, Player blackPlayer) {
        players.put(whitePlayer.identifier, whitePlayer);
        players.put(blackPlayer.identifier, blackPlayer);
        whitePlayer.setOpponent(blackPlayer);
        blackPlayer.setOpponent(whitePlayer);
    }
    
    private Player getByColor(Color c) {
    	Iterator<Entry<String, Player>> i = players.entrySet().iterator();
    	while (i.hasNext()) {
    		Player p = i.next().getValue();
    		if (p.getColor() == c)
    			return p;
    	}
    	return null;
    }

    public MoveResult setToken(String playerIdentifier, Integer x, Integer y) throws UnknownPlayerException, IllegalMoveException, TooManyTokensException {
        checkUserExists(playerIdentifier);
        return players.get(playerIdentifier).set(x, y);
    }

    public MoveResult moveToken(String playerIdentifier, Integer oldX, Integer oldY, Move move) throws UnknownPlayerException, IllegalMoveException {
        checkUserExists(playerIdentifier);
        return players.get(playerIdentifier).move(oldX, oldY, move);
    }

    private void checkUserExists(String playerIdentifier) throws UnknownPlayerException {
        if (!players.containsKey(playerIdentifier)) {
            throw new UnknownPlayerException();
        }
    }

    public String hasPlayerAGame(String playerIdentifier) {
        return players.containsKey(playerIdentifier) ? 
        		players.get(playerIdentifier).getColor().toString() : "false";
    }
    
    public String getGame(String playerIdentifier) throws UnknownPlayerException {
    	checkUserExists(playerIdentifier);
    	Board board = players.get(playerIdentifier).getBoard();
    	String whiteScore = getByColor(Color.WHITE).getScore().toString();
    	String blackScore = getByColor(Color.BLACK).getScore().toString();
    	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    	JsonElement jsonElement = gson.toJsonTree(board);
    	jsonElement.getAsJsonObject().addProperty("status", "running");
    	jsonElement.getAsJsonObject().addProperty("lastRoundPoints", "0");
    	jsonElement.getAsJsonObject().addProperty("whiteScore", whiteScore);
    	jsonElement.getAsJsonObject().addProperty("blackScore", blackScore);
    	return gson.toJson(jsonElement);
    }
}
