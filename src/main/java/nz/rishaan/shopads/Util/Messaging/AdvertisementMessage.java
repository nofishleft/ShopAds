package nz.rishaan.shopads.Util.Messaging;


import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Player.PlayerHandler;
import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.Util.Mathematical;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsIO;

public class AdvertisementMessage
        extends ShopAdsMessage
{
    public void advertise(Shop shop)
    {
        if (shop.shopExpired())
        {
            plugin.shopHandler.removeShop(shop);
            plugin.message.shopExpired(shop);
            plugin.iO.saveShops();
            plugin.iO.loadShops();
            plugin.invokeAnnounceThread();
        }
        ShopAdsMessage.console.announceDebug("Choosing advertisement method");
        if (plugin.config.getSendToAll())
        {
            if (!plugin.config.getAdsOverWorlds()) {
                advertiseNotOverWorldsToAll(shop);
            } else {
                advertiseToAll(shop);
            }
        }
        else if (!plugin.config.getAdsOverWorlds()) {
            advertiseNotOverWorlds(shop);
        } else {
            advertiseShop(shop);
        }
    }

    public void advertiseNotOverWorldsToAll(Shop shop)
    {
        ShopAdsMessage.console.announceDebug("advertiseNotOverWorldsToAll shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) plugin.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++)
        {
            Player p = arrayOfPlayer[i];
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++)
            {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld)
                {
                    if (plugin.config.getAnnounceRadius() > 0)
                    {
                        if (plugin.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop))
                        {
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

    public void advertiseNotOverWorlds(Shop shop)
    {
        ShopAdsMessage.console.announceDebug("advertiseNotOverWorldToAll shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) plugin.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++)
        {
            Player p = arrayOfPlayer[i];
            if ((plugin.playerHandler.playerExists(p.getName())) &&
                    (!plugin.playerHandler.getPlayer(p.getName()).getWantsAds())) {
                return;
            }
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++)
            {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld)
                {
                    if (plugin.config.getAnnounceRadius() > 0)
                    {
                        if (plugin.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop))
                        {
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

    public void advertiseToAll(Shop shop)
    {
        ShopAdsMessage.console.announceDebug("advertiseToAll shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) plugin.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++)
        {
            Player p = arrayOfPlayer[i];
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++)
            {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld)
                {
                    if (plugin.config.getAnnounceRadius() > 0)
                    {
                        if (plugin.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop))
                        {
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

    public void advertiseShop(Shop shop)
    {
        ShopAdsMessage.console.announceDebug("advertise shop message");
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) plugin.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++)
        {
            Player p = arrayOfPlayer[i];
            if ((plugin.playerHandler.playerExists(p.getName())) &&
                    (!plugin.playerHandler.getPlayer(p.getName()).getWantsAds())) {
                return;
            }
            World[] arrayOfWorld;
            int m = (arrayOfWorld = shop.getWorldsToAdvertiseIn()).length;
            for (int k = 0; k < m; k++)
            {
                World shopWorld = arrayOfWorld[k];
                if (p.getWorld() == shopWorld)
                {
                    if (plugin.config.getAnnounceRadius() > 0)
                    {
                        ShopAdsMessage.console.debug("Announcement radius: " + plugin.config.getAnnounceRadius());
                        ShopAdsMessage.console.debug(p.getName() + "'s distance from " + shop.getShopName() + " :" + math.playerDistanceFromShop(p, shop));
                        if (plugin.config.getAnnounceRadius() > math.playerDistanceFromShop(p, shop))
                        {
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
