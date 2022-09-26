package somebottle.gamemodepermissions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Interceptor implements Listener {
    // 初始化输出到命令行的对象及方法
    private final Outputer outputer = new Outputer();

    /**
     * 构造方法
     *
     * @param plugin  Plugin实例
     * @param manager PluginManager实例
     */
    public Interceptor(Plugin plugin, PluginManager manager) {
        // 在PluginManager实例上注册事件
        manager.registerEvents(this, plugin);
        outputer.toConsole("Event registered.");
    }

    /**
     * 拦截玩家执行指令时的事件
     *
     * @param event PlayerCommandPreprocessEvent对象
     */
    @EventHandler
    public void onExecuteCommand(PlayerCommandPreprocessEvent event) {
        // 获得命令发送人
        Player sender = event.getPlayer();
        // 获得发送的指令字符串（开头会带上/）
        String command = event.getMessage();
        // 分割后的指令
        String[] splittedCommand = command.split("\\s+");
        // 检查指令是不是gamemode（用substring去除首部的/）
        if (splittedCommand[0].substring(1).equals("gamemode")) {
            String targetNode = "gamemode"; // 记录权限节点(这里从gamemode开始，因为Permission类中的方法是以minecraft.command为基础的)
            // 至少存在第二个参数，即adventure|creative|spectator|survival
            if (splittedCommand.length >= 2) {
                switch (splittedCommand[1]) {
                    case "adventure": // 冒险模式
                    case "creative": // 创造模式
                    case "spectator": // 观察者模式
                    case "survival": // 生存模式
                        targetNode = targetNode + "." + splittedCommand[1]; // 得到特定指令的权限节点
                        break;
                    default:
                        return; // 没匹配上就不做处理，留给服务端处理
                }
            }
            // 存在第三个参数，指定玩家切换到某个游戏模式
            // 比如切换某个玩家到创造模式，权限节点就是minecraft.command.gamemode.creative.player
            if (splittedCommand.length == 3) {
                targetNode = targetNode + ".player";
            }
            // 玩家如果没有对应权限就取消事件
            Permission checker = new Permission();
            // 如果玩家是Operator就默认拥有所有权限, 不作拦截处理
            if (sender.isOp()) {
                return;
            }
            // 检查是否拥有权限
            if (!checker.test(sender, targetNode)) {
                event.setCancelled(true);
                // 提醒玩家没有权限
                outputer.toPlayer(sender, "Permission denied.");
            }
        }
    }
}
