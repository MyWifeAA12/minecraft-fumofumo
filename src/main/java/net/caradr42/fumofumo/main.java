package net.caradr42.fumofumo;

import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("FumoFumo enabled");
        manager.init();
        getServer().getPluginManager().registerEvents(new fumoevents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("FumoFumo disabled");
    }
}
