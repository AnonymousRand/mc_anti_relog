package AnonymousRand.AntiRelog;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiRelogPlugin extends JavaPlugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() { //this runs when the plugin is first enabled (when the server starts up)
        getServer().getPluginManager().registerEvents(new RelogListeners(this), this);
    }

    @Override
    public void onDisable() {

    }
}
