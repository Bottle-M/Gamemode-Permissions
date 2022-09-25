package somebottle.gamemodepermissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Outputer {
    /**
     * 发送消息到控制台
     *
     * @param text 待发送的消息内容
     */
    void toConsole(String text) {
        // 通过CommandSender对象的方法发送信息到控制台
        CommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(ChatColor.AQUA + "[GMPermissions] " + ChatColor.RESET + text);
    }

    /**
     * 发送消息到特定玩家
     *
     * @param target Player对象
     * @param text   待发送的消息内容
     */
    void toPlayer(Player target, String text) {
        target.sendMessage(ChatColor.AQUA + "[GMPermissions] " + ChatColor.RESET + text);
    }
}
