package AnonymousRand.AntiRelog;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class RelogListeners implements Listener {

    private JavaPlugin plugin;
    private HashMap<Player, Long> jointime = new HashMap<>();

    public RelogListeners(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() //delay by 1 tick or else the server does not re-apply the status effects, thinking that the player doesn't exist yet
        {
            public void run()
            {
                if (event.getPlayer().getHealth() <= 17.0) { /**if the player has 8.5 hearts or less when rejoining, apply these status effects*/
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 255));
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 255));
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 255));
                    Bukkit.broadcastMessage(event.getPlayer().getName() + " is a cheater");
                    jointime.put(event.getPlayer(), event.getPlayer().getWorld().getFullTime());
                }
            }
        }, 1);
    }

    @EventHandler
    public void itemConsumed(PlayerItemConsumeEvent event) {

        if (event.getItem().getType() == Material.MILK_BUCKET) { /**milk bad*/
            if (event.getPlayer().getWorld().getFullTime() - jointime.getOrDefault(event.getPlayer(), (long) 0) < 100) { //joined less than 5 sec ago
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() //delay by 1 tick or else the server does not re-apply the status effects, thinking that the player doesn't exist yet
                {
                    public void run()
                    {
                        Bukkit.broadcastMessage("You thought...");
                        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 255));
                        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 255));
                        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 255));
                        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 400, 0));
                        jointime.put(event.getPlayer(), event.getPlayer().getWorld().getFullTime());
                    }
                }, 1);
            }
        }
    }
}
