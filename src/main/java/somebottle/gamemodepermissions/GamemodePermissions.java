package somebottle.gamemodepermissions;

import org.bukkit.plugin.java.JavaPlugin;

public final class GamemodePermissions extends JavaPlugin {
    // 初始化输出到命令行的对象及方法
    private final Outputer outputer = new Outputer();

    @Override
    public void onEnable() {
        // 插件载入后的启动
        outputer.toConsole("GamemodePermissions loaded! Thank you for using - SomeBottle");
        // gamemode指令拦截器（构造的时候传入PluginManager实例）
        new Interceptor(this, getServer().getPluginManager());
    }

    @Override
    public void onDisable() {
        // 插件被卸载
        outputer.toConsole("GamemodePermissions unloaded!");
    }
}
