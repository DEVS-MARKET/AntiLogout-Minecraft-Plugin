package uk.whitedev.antilogout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import uk.whitedev.antilogout.commands.Reload;
import uk.whitedev.antilogout.listeners.PlayerListener;
import uk.whitedev.antilogout.tasks.TimeTask;

public final class Antilogout extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initializeModules();
        getLogger().info("Anti-Logout Plugin - by 0WhiteDev [devs.market] has been enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (cmd.getName()){
            case "alreload":
                return new Reload(sender).runCommand();
        }
        return false;
    }

    @Override
    public void onDisable() {
        getLogger().info("Anti-Logout Plugin - by 0WhiteDev [devs.market] has been disabled");
    }

    private void initializeModules(){
        new PlayerListener().registerListeners();
        new TimeTask().runTaskTimer(this, 0, 20L);
        getCommand("alreload").setExecutor(this);
    }
}
