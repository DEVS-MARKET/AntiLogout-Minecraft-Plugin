package uk.whitedev.antilogout.tasks;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import uk.whitedev.antilogout.Antilogout;
import uk.whitedev.antilogout.utils.ColorUtils;
import uk.whitedev.antilogout.utils.MessageType;

import java.util.Objects;

public class FightTask {

    private final Plugin plugin = Antilogout.getPlugin(Antilogout.class);
    private final MessageTask messageTask = new MessageTask();
    private final FileConfiguration config = plugin.getConfig();

    public void finishFight(Player player) {
        if (player != null) {
            messageTask.sendMessageToPlayer(player, "fight-finished-player", MessageType.LIST);
            BossBar bossBar = Bukkit.createBossBar(ColorUtils.formatColor(config.getString("fight-finished-bossbar")), BarColor.GREEN, BarStyle.SOLID);
            int time = config.getInt("fight-finished-info-time");
            bossBar.addPlayer(player);
            bossBar.setVisible(true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> bossBar.removePlayer(player), time * 20L);
        }
    }

    public void updateFightInfo(Player player, int time) {
        if (player != null) {
            BossBar bossBar = Bukkit.createBossBar(ColorUtils.formatColor(Objects.requireNonNull(config.getString("fight-info-bossbar")).replace("%time%", String.valueOf(time))), BarColor.RED, BarStyle.SOLID);
            int defaulttime = config.getInt("time");
            double[] progress = divideNumber(defaulttime);
            bossBar.setProgress(progress[defaulttime - time]);
            bossBar.addPlayer(player);
            bossBar.setVisible(true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> bossBar.removePlayer(player), 20L);
        }
    }

    private double[] divideNumber(int number) {
        double[] result = new double[number];
        for (int i = 0; i < number; i++) {
            result[i] = 1.0 - i * (1.0 / number);
        }
        return result;
    }
}
