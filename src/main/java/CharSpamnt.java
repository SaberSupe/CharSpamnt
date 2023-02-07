import events.CharSpamListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class CharSpamnt extends JavaPlugin {

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new CharSpamListener(), this);
        this.getLogger().log(Level.INFO, "CharSpamnt loaded Successfully");
    }
}
