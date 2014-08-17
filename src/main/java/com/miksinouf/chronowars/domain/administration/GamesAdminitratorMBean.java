package com.miksinouf.chronowars.domain.administration;

import java.util.Set;

public interface GamesAdminitratorMBean {

    public Set<String> listPlayers();

    public void killGame(String userId);

    public void clearAllGames();

}
