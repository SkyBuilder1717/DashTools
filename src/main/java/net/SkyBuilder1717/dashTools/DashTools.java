package net.SkyBuilder1717.dashTools;

import org.bukkit.plugin.java.JavaPlugin;

public final class DashTools extends JavaPlugin {
    @Override
    public void onEnable() {
        JavaPlugin server = this;
        server.getLogger().info("DashTools enabled!");
    }

    @Override
    public void onDisable() {
        JavaPlugin server = this;
        server.getLogger().info("DashTools disabled!");
    }
}
