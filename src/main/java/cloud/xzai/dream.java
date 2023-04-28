package cloud.xzai;

import cloud.xzai.message.MessageEvent;
import cloud.xzai.welcome.WelcomeMessage;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class dream extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MessageEvent(), this);
        getCommand("playerinfo").setExecutor(new cloud.xzai.playerinfo.PlayerInfo());
        new WelcomeMessage(this);
        getLogger().info(ChatColor.GREEN + "--------------------");
        getLogger().info(ChatColor.GREEN + "插件加载成功");
        getLogger().info(ChatColor.GREEN + "--------------------");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.GREEN + "--------------------");
        getLogger().info(ChatColor.GREEN + "插件卸载成功");
        getLogger().info(ChatColor.GREEN + "--------------------");
    }
}