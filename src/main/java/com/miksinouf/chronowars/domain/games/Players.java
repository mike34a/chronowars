package com.miksinouf.chronowars.domain.games;

import java.util.HashMap;
import java.util.Map;

import com.miksinouf.chronowars.domain.board.Board;
import com.miksinouf.chronowars.domain.board.IllegalMoveException;
import com.miksinouf.chronowars.domain.board.Move;
import com.miksinouf.chronowars.domain.board.MoveResult;
import com.miksinouf.chronowars.domain.player.Color;
import com.miksinouf.chronowars.domain.player.Player;
import com.miksinouf.chronowars.domain.player.TooManyTokensException;
import com.miksinouf.chronowars.domain.player.UnknownPlayerException;

public class Players {
    public final Map<String, Player> players = new HashMap<>();

    public void addPlayers(Player whitePlayer, Player blackPlayer) {
        players.put(whitePlayer.getIdentifier(), whitePlayer);
        players.put(blackPlayer.getIdentifier(), blackPlayer);
        whitePlayer.setOpponent(blackPlayer);
        blackPlayer.setOpponent(whitePlayer);
    }
    
    public MoveResult setToken(String playerIdentifier, Integer x, Integer y) throws UnknownPlayerException, IllegalMoveException, TooManyTokensException {
        checkUserExists(playerIdentifier);
        return players.get(playerIdentifier).set(x, y);
    }

    public MoveResult moveToken(String playerIdentifier, Integer oldX, Integer oldY, String move) throws UnknownPlayerException, IllegalMoveException {
        checkUserExists(playerIdentifier);
        return players.get(playerIdentifier).move(oldX, oldY, Move.valueOf(move));
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
    
    public GameResponse getGame(String playerIdentifier) throws UnknownPlayerException {
    	checkUserExists(playerIdentifier);
    	Board board = players.get(playerIdentifier).getBoard();
//    	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//    	JsonElement jsonElement = gson.toJsonTree(board);
//    	jsonElement.getAsJsonObject().addProperty("status", "running");
//    	jsonElement.getAsJsonObject().addProperty("lastRoundPoints", "0");
//    	jsonElement.getAsJsonObject().addProperty("whiteScore", whiteScore);
//    	jsonElement.getAsJsonObject().addProperty("blackScore", blackScore);
//    	jsonElement.getAsJsonObject().addProperty("whiteNick", whiteNick);
//    	jsonElement.getAsJsonObject().addProperty("blackNick", blackNick);
//    	return gson.toJson(jsonElement);


        final Player player = players.get(playerIdentifier);
        final Player opponent = player.getOpponent();

        Player whitePlayer = getPlayerOfColor(Color.WHITE, player, opponent);
        Player blackPlayer = getPlayerOfColor(Color.BLACK, player, opponent);

        return new GameResponse(
                board,
                "running",
                whitePlayer.getScore().toString(),
                blackPlayer.getScore().toString(),
                whitePlayer.getNickname(),
                blackPlayer.getNickname());
    }

    private Player getPlayerOfColor(Color color, Player player, Player opponent) {
        return player.getColor() == color ? player : opponent;
    }

}
