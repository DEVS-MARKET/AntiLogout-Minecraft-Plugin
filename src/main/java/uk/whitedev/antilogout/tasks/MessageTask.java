package uk.whitedev.antilogout.tasks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import uk.whitedev.antilogout.Antilogout;
import uk.whitedev.antilogout.utils.MessageType;

import java.util.List;

public class MessageTask {
    private final Plugin plugin = Antilogout.getPlugin(Antilogout.class);
    private final FileConfiguration config = plugin.getConfig();

    public void sendMessageToPlayer(Player player, String configPath, MessageType type){
        switch(type) {
            case LIST: {
                List<String> msgList = config.getStringList(configPath);
                for(String line : msgList) {
                    Component component = MiniMessage.miniMessage().deserialize(line);
                    player.sendMessage(component);
                }
                break;
            }
            case STRING: {
                String text = config.getString(configPath);
                Component component = MiniMessage.miniMessage().deserialize(text);
                player.sendMessage(component);
                break;
            }
        }
    }

    public void sendClearMessageToPlayer(Player player, String text){
        Component component = MiniMessage.miniMessage().deserialize(text);
        player.sendMessage(component);
    }

    public void sendMessageToEveryone(String text){
        Component component = MiniMessage.miniMessage().deserialize(text);
        plugin.getServer().broadcast(component);
    }

}
