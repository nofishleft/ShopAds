package nz.rishaan.shopads.Util.Messaging;


import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class AdvertisementMessage
        extends ShopAdsMessage {
    public void advertise(Shop shop) {
        if (shop.shopExpired()) {
            ShopAds.shopHandler.removeShop(shop);
            ShopAds.message.shopExpired(shop);
            ShopAds.iO.saveShops();
            ShopAds.iO.loadShops();
            plugin.invokeAnnounceThread();
        }
        ShopAdsMessage.console.announceDebug("Choosing advertisement method");
        if (ShopAds.config.getSendToAll()) {
            if (!ShopAds.config.getAdsOverWorlds()) {
                advertiseNotOverWorldsToAll(shop);
            } else {
                advertiseToAll(shop);
            }
        } else if (!ShopAds.config.getAdsOverWorlds()) {
            advertiseNotOverWorlds(shop);
        } else {
            advertiseShop(shop);
        }
    }

    public void advertiseNotOverWorldsToAll(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertiseNotOverWorldsToAll shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) ShopAds.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++) {
            Player p = arrayOfPlayer[i];
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++) {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld) {
                    if (ShopAds.config.getAnnounceRadius() > 0) {
                        if (ShopAds.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop)) {
                            p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                            return;
                        }
                        return;
                    }
                    p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                }
            }
        }
    }

    public void advertiseNotOverWorlds(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertiseNotOverWorldToAll shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) ShopAds.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++) {
            Player p = arrayOfPlayer[i];
            if ((ShopAds.playerHandler.playerExists(p.getName())) &&
                    (!ShopAds.playerHandler.getPlayer(p.getName()).getWantsAds())) {
                return;
            }
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++) {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld) {
                    if (ShopAds.config.getAnnounceRadius() > 0) {
                        if (ShopAds.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop)) {
                            p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                            return;
                        }
                        return;
                    }
                    p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                }
            }
        }
    }

    public void advertiseToAll(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertiseToAll shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) ShopAds.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++) {
            Player p = arrayOfPlayer[i];
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++) {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld) {
                    if (ShopAds.config.getAnnounceRadius() > 0) {
                        if (ShopAds.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop)) {
                            p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                            return;
                        }
                        return;
                    }
                    p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                }
            }
        }
    }

    public void advertiseShop(Shop shop) {
        ShopAdsMessage.console.announceDebug("advertise shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) ShopAds.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++) {
            Player p = arrayOfPlayer[i];
            if ((ShopAds.playerHandler.playerExists(p.getName())) &&
                    (!ShopAds.playerHandler.getPlayer(p.getName()).getWantsAds())) {
                return;
            }
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++) {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld) {
                    if (ShopAds.config.getAnnounceRadius() > 0) {
                        ShopAdsMessage.console.debug("Announcement radius: " + ShopAds.config.getAnnounceRadius());
                        ShopAdsMessage.console.debug(p.getName() + "'s distance from " + shop.getShopName() + " :" + math.playerDistanceFromShop(p, shop));
                        if (ShopAds.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop)) {
                            p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                            return;
                        }
                        return;
                    }
                    p.sendMessage(shop.getShopColor() + "[" + shop.getShopName() + "] " + shop.getAdColor() + shop.getAd());
                }
            }
        }
    }
}
