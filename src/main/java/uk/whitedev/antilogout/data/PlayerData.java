package uk.whitedev.antilogout.data;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerData {
    private static PlayerData playerData;
    private HashMap<Player, Integer> playersMap = new HashMap<>();

    public static PlayerData getInstance(){
        if(playerData == null){
            playerData = new PlayerData();
        }
        return playerData;
    }

    public HashMap<Player, Integer> getPlayersMap() {
        return playersMap;
    }

    public void addPlayersToMap(Player player, int time) {
        this.playersMap.put(player, time);
    }

    public void setPlayersMap(HashMap<Player, Integer> playersMap) {
        this.playersMap = playersMap;
    }
}
