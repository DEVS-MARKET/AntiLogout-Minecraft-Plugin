package uk.whitedev.antilogout.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import uk.whitedev.antilogout.Antilogout;
import uk.whitedev.antilogout.tasks.MessageTask;
import uk.whitedev.antilogout.utils.MessageType;

public class Reload {

    private final CommandSender sender;
    private final Plugin plugin = Antilogout.getPlugin(Antilogout.class);
    private final MessageTask messageTask = new MessageTask();

    public Reload(CommandSender sender){
        this.sender = sender;
    }

    public boolean runCommand(){
        plugin.reloadConfig();
        messageTask.sendMessageToPlayer((Player) sender, "reload-message", MessageType.STRING);
        return true;
    }
}