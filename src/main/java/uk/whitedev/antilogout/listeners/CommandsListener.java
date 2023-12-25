package uk.whitedev.antilogout.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import uk.whitedev.antilogout.Antilogout;
import uk.whitedev.antilogout.data.PlayerData;
import uk.whitedev.antilogout.tasks.MessageTask;
import uk.whitedev.antilogout.utils.MessageType;

import java.util.List;
import java.util.Locale;

public class CommandsListener {

    private final Plugin plugin = Antilogout.getPlugin(Antilogout.class);
    private final List<?> whitelistedCmds = plugin.getConfig().getList("whitelist-commands");
    private final MessageTask messageTask = new MessageTask();

    public void registerListeners() {
        commandEvent();
    }

    private void commandEvent(){
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
                Player player = event.getPlayer();
                if (PlayerData.getInstance().getPlayersMap().containsKey(player)) {
                    String[] commandArgs = event.getMessage().substring(1).split(" ");
                    String command = commandArgs[0].toLowerCase();

                    if (whitelistedCmds != null && !whitelistedCmds.contains(command.toLowerCase(Locale.ROOT))) {
                        messageTask.sendMessageToPlayer(player, "fight-command-access-denied", MessageType.STRING);
                        event.setCancelled(true);
                    }
                }
            }
        }, plugin);
    }


}
