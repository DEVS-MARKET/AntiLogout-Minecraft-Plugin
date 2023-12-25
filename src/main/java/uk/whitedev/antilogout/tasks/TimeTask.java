package uk.whitedev.antilogout.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.whitedev.antilogout.data.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class TimeTask extends BukkitRunnable {
    private final FightTask fightTask = new FightTask();
    private final PlayerData playerData = PlayerData.getInstance();

    public HashMap<Player, Integer> timeDecrementation(HashMap<Player, Integer> oldPlayerMap){
        HashMap<Player, Integer> newPlayerMap = new HashMap<>();
        if(!oldPlayerMap.isEmpty()) {
            for (Map.Entry<Player, Integer> entry : oldPlayerMap.entrySet()) {
                Player player = entry.getKey();
                int time = entry.getValue() - 1;
                if (time != 0 && !player.isDead()) {
                    newPlayerMap.put(player, time);
                    fightTask.updateFightInfo(player, time);
                } else {
                    fightTask.finishFight(player);
                }
            }
        }
        return newPlayerMap;
    }

    @Override
    public void run() {
        playerData.setPlayersMap(timeDecrementation(playerData.getPlayersMap()));
    }
}
