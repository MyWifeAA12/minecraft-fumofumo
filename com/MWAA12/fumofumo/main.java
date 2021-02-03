package com.MWAA12.fumofumo;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("FumoFumo enabled");
        manager.init();
        getServer().getPluginManager().registerEvents(new fumoevents(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("FumoFumo disabled");
    }
}
