package cloud.xzai.playerinfo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInfo implements CommandExecutor {
    private final Permission PLAYERINFO_PERMISSION = new Permission("cloud.xzai.playerinfo", PermissionDefault.OP);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission(PLAYERINFO_PERMISSION) || sender.isOp()) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "[用法] /playerinfo <玩家名>");
                return true;
            }
            Player player = sender.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "[未找到该玩家] " + args[0]);
                return true;
            }
            sender.sendMessage(ChatColor.YELLOW + "--------------------");

            Location loc = player.getLocation();
            sender.sendMessage(ChatColor.BLUE + "[显示名字] " + ChatColor.GREEN + player.getDisplayName());
            sender.sendMessage(ChatColor.BLUE + "[玩家名称] " + ChatColor.GREEN + player.getName());
            sender.sendMessage(ChatColor.BLUE + "[生命值] " + ChatColor.GREEN + player.getHealth() + ChatColor.WHITE + " / " + ChatColor.GREEN + player.getMaxHealth());
            sender.sendMessage(ChatColor.BLUE + "[饱食度] " + ChatColor.GREEN + player.getFoodLevel());
            sender.sendMessage(ChatColor.BLUE + "[水平经验] " + ChatColor.GREEN + player.getExp() + ChatColor.WHITE + " / " + ChatColor.GREEN + player.getExpToLevel());
            sender.sendMessage(ChatColor.BLUE + "[物品栏物品数] " + ChatColor.GREEN + player.getInventory().getSize());
            sender.sendMessage(ChatColor.BLUE + "[主手物品] " + ChatColor.GREEN + player.getInventory().getItemInMainHand());
            sender.sendMessage(ChatColor.BLUE + "[副手物品] " + ChatColor.GREEN + player.getInventory().getItemInOffHand());
            ItemStack[] armor = player.getInventory().getArmorContents();
            if (armor != null && armor.length == 4) {
                sender.sendMessage(ChatColor.BLUE + "[头盔] " + ChatColor.GREEN + armor[3]);
                sender.sendMessage(ChatColor.BLUE + "[胸甲] " + ChatColor.GREEN + armor[2]);
                sender.sendMessage(ChatColor.BLUE + "[护腿] " + ChatColor.GREEN + armor[1]);
                sender.sendMessage(ChatColor.BLUE + "[靴子] " + ChatColor.GREEN + armor[0]);
            }
            StringBuilder potionEffectsBuilder = new StringBuilder();
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (potionEffectsBuilder.length() > 0) {
                    potionEffectsBuilder.append(", ");
                }
                PotionEffectType type = effect.getType();
                int duration = effect.getDuration() / 20;
                int amplifier = effect.getAmplifier() + 1;
                potionEffectsBuilder.append(type.getName()).append(" (").append(duration).append("s, Lv ").append(amplifier).append(")");
            }
            if (potionEffectsBuilder.length() > 0) {
                sender.sendMessage(ChatColor.BLUE + "[药水效果] " + ChatColor.GREEN + potionEffectsBuilder);
            }
            sender.sendMessage(ChatColor.BLUE + "[所在位置] " + ChatColor.GREEN + loc.getX()
                    + ", " + loc.getY() + ", " + loc.getZ());
            sender.sendMessage(ChatColor.BLUE + "[所在世界] " + ChatColor.GREEN + loc.getWorld().getName());
            sender.sendMessage(ChatColor.BLUE + "[面向的方向] " + ChatColor.GREEN + getCardinalDirection(loc));
            sender.sendMessage(ChatColor.BLUE + "[俯仰角度] " + ChatColor.GREEN + loc.getPitch());
            sender.sendMessage(ChatColor.BLUE + "[旋转角度] " + ChatColor.GREEN + loc.getYaw());

            sender.sendMessage(ChatColor.YELLOW + "--------------------");
        }
        return true;
    }

    private String getCardinalDirection(Location loc) {
        double rotation = (loc.getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }

        if (0 <= rotation && rotation < 22.5) {
            return "西";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "西南";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "南";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "东南";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "东";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "东北";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "北";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "西北";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "西";
        } else {
            return "";
        }
    }
}

