package com.miksinouf.chronowars.domain.server;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import com.miksinouf.chronowars.domain.games.GamesQueue;
import com.miksinouf.chronowars.domain.games.GamesQueueSingleton;
public class EventSocket extends WebSocketAdapter
{
	@Override
	public void onWebSocketConnect(Session sess)
	{
		sess.setIdleTimeout(-1);
		super.onWebSocketConnect(sess);
		GamesQueueSingleton.INSTANCE.setSession(sess);
	}
	@Override
	public void onWebSocketText(String message)
	{
		super.onWebSocketText(message);
	}
	@Override
	public void onWebSocketClose(int statusCode, String reason)
	{
		super.onWebSocketClose(statusCode,reason);
		try {
			getRemote().sendString("Server closed connexion, reason : " + reason);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onWebSocketError(Throwable cause)
	{
		super.onWebSocketError(cause);
		cause.printStackTrace(System.err);
	}
}