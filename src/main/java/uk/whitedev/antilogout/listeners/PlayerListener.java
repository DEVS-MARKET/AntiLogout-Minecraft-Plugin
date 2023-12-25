package uk.whitedev.antilogout.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import uk.whitedev.antilogout.Antilogout;
import uk.whitedev.antilogout.data.PlayerData;
import uk.whitedev.antilogout.tasks.MessageTask;

import java.util.List;

public class PlayerListener {

    private final Plugin plugin = Antilogout.getPlugin(Antilogout.class);
    private final PlayerData playerData = PlayerData.getInstance();
    private final FileConfiguration config = plugin.getConfig();
    private final MessageTask messageTask = new MessageTask();

    public void registerListeners() {
        attackEvent();
        quitEvent();
    }

    private void attackEvent(){
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
                if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                    Player attacker = (Player) event.getDamager();
                    Player target = (Player) event.getEntity();
                    int time = plugin.getConfig().getInt("time") + 1;
                    List<String> msgList = config.getStringList("fight-info-player");
                    for(String line : msgList) {
                        if(!playerData.getPlayersMap().containsKey(attacker))
                            messageTask.sendClearMessageToPlayer(attacker, line.replace("%time%", String.valueOf(time)));
                        if(!playerData.getPlayersMap().containsKey(target))
                            messageTask.sendClearMessageToPlayer(target, line.replace("%time%", String.valueOf(time)));
                    }
                    playerData.addPlayersToMap(attacker, time);
                    playerData.addPlayersToMap(target, time);
                }
            }
        }, plugin);
    }

    private void quitEvent(){
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                Player player = event.getPlayer();
                if(playerData.getPlayersMap().containsKey(player)){
                    player.setHealth(0.0);
                    List<String> msgList = config.getStringList("player-quit");
                    for(String line : msgList) {
                        messageTask.sendMessageToEveryone(line.replace("%player%", player.getName()));
                    }
                }
            }
        }, plugin);
    }
}
