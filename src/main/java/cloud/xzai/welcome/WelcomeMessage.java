package cloud.xzai.welcome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public class WelcomeMessage implements Listener {
    private final Permission WELCOME_PERMISSION = new Permission("cloud.xzai.welcome", PermissionDefault.OP);

    public WelcomeMessage(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().addPermission(WELCOME_PERMISSION);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String message = getWelcomeMessage(player);
        for (Player recipient : Bukkit.getOnlinePlayers()) {
            if (recipient.hasPermission(WELCOME_PERMISSION) || recipient.isOp()) {
                recipient.sendMessage(message);
            }
        }
    }

    private String getWelcomeMessage(Player player) {
        Location loc = player.getLocation();
        String playerName = player.getName();
        String playerUuid = player.getUniqueId().toString();
        String worldName = player.getWorld().getName();
        double health = player.getHealth();
        int level = player.getLevel();

        return ChatColor.YELLOW + "--------------------\n"
                + ChatColor.BLUE + "[玩家] " + ChatColor.GREEN + playerName + "进入了服务器\n"
                + ChatColor.BLUE + "[世界] " + ChatColor.GREEN + worldName + "\n"
                + ChatColor.BLUE + "[血量] " + ChatColor.GREEN + health + "\n"
                + ChatColor.BLUE + "[等级] " + ChatColor.GREEN + level + "\n"
                + ChatColor.BLUE + "[uuid] " + ChatColor.GREEN + playerUuid + "\n"
                + ChatColor.BLUE + "[位置] " + ChatColor.GREEN + loc.getX()
                + ", " + loc.getY() + ", " + loc.getZ() + "\n"
                + ChatColor.YELLOW + "--------------------\n";
    }
}
