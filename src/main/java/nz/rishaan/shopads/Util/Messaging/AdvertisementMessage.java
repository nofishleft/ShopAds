package nz.rishaan.shopads.Util.Messaging;


import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class AdvertisementMessage
        extends ShopAdsMessage {

    public void SendMessage(Shop shop, Player player) {
        player.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
    }

    public void advertise(Shop shop) {
        if (shop.shopExpired()) {
            ShopAds.shopHandler.removeShop(shop);
            ShopAds.message.shopExpired(shop);
            ShopAds.iO.saveShops();
            ShopAds.iO.loadShops();
            ShopAds.shopads.invokeAnnounceThread();
        }
        
        ShopAdsMessage.console.announceDebug("Choosing advertisement method");

        if (ShopAds.config.getSendToAll()) {
            if (ShopAds.config.getAdsOverWorlds()) {
                advertiseToAll(shop);
            } else {
                advertiseNotOverWorldsToAll(shop);
            }
        } else {
            if (ShopAds.config.getAdsOverWorlds()) {
                advertiseShop(shop);
            } else {
                advertiseNotOverWorlds(shop);
            }
        }
    }

    // SendToAll = true
    // OverWorlds = false
    //
    // Ignores ads preference
    // Sticks to same world
    // Should probably follow announce radius
    public void advertiseNotOverWorldsToAll(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertiseNotOverWorldsToAll shop message");

        for (World shopWorld : shop.getWorldsToAdvertiseIn()) {
            for (Player p : shopWorld.getPlayers()) {
                if (ShopAds.config.getAnnounceRadius() > 0) {
                    if (ShopAds.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop)) {
                        SendMessage(shop, p);
                    }
                } else {
                    SendMessage(shop, p);
                }
            }
        }
    }

    // SendToAll = false
    // OverWorlds = false
    //
    // Follows ads preference
    // Sticks to same world
    // Should probably follow announce radius
    public void advertiseNotOverWorlds(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertiseNotOverWorldToAll shop message");

        for (World shopWorld : shop.getWorldsToAdvertiseIn()) {
            for (Player p : shopWorld.getPlayers()) {
                ShopAdsPlayer sap = ShopAds.playerHandler.getPlayer(p.getUniqueId());
                if (sap != null && sap.getWantsAds()) {
                    if (ShopAds.config.getAnnounceRadius() > 0) {
                        ShopAdsMessage.console.debug("Announcement radius: " + ShopAds.config.getAnnounceRadius());
                        ShopAdsMessage.console.debug(p.getName() + "'s distance from " + shop.getShopName() + " :" + math.playerDistanceFromShop(p, shop));
                        if (ShopAds.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop)) {
                            SendMessage(shop, p);
                        }
                    } else {
                        SendMessage(shop, p);
                    }
                }
            }
        }
    }

    // SendToAll = true
    // OverWorlds = true
    //
    // Ignores ads preference
    // Ignores worlds
    // As it ignores worlds, it should probably also ignore announceRadius
    public void advertiseToAll(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertiseToAll shop message");

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            SendMessage(shop, p);
        }
    }

    // SendToAll = false
    // OverWorlds = true
    //
    // Follows ads preference
    // Ignores worlds
    // As it ignores worlds, it should probably also ignore announceRadius
    public void advertiseShop(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertise shop message");

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ShopAdsPlayer sap = ShopAds.playerHandler.getPlayer(p.getUniqueId());
            if (sap != null && sap.getWantsAds()) {
                SendMessage(shop, p);
            }
        }
    }
}
