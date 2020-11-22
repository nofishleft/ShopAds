package nz.rishaan.shopads.Player;

import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ShopAdsPlayerListener
        implements Listener {
    private final ShopAds plugin;

    public ShopAdsPlayerListener(ShopAds p) {
        this.plugin = p;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        ShopAdsMessage.console.debug("Is player in shopadsplayers: " + ShopAds.playerHandler.playerExists(event.getPlayer().getName()));
        if (!ShopAds.playerHandler.playerExists(event.getPlayer().getName())) {
            ShopAds.playerHandler.addPlayer(new ShopAdsPlayer(event.getPlayer().getName(), true, 0));
        }
        if ((!this.plugin.pluginUpToDate) &&
                (ShopAds.permissions.hasAdmin(event.getPlayer()))) {
            ShopAds.message.newerVersionAvailable(event.getPlayer());
        }
    }
}
