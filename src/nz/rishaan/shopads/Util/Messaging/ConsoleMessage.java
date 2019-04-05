package nz.rishaan.shopads.Util.Messaging;


import java.util.logging.Logger;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.Util.ShopAdsConfig;

public class ConsoleMessage
        extends ShopAdsMessage
{
    public void numberOfShopsLoaded()
    {
        if (plugin.shopHandler.shopsEmpty())
        {
            plugin.log.info("[ShopAds2] No shops were found.");
            return;
        }
        if (plugin.shopHandler.getSize() == 1) {
            plugin.log.info("[ShopAds2] 1 shop was loaded.");
        } else {
            plugin.log.info("[ShopAds2] " + plugin.shopHandler.getSize() + " shops were loaded.");
        }
    }

    public void checkConfigOption(String parameter)
    {
        plugin.log.info("[ShopAds2] Check the config option '" + parameter + "'.");
    }

    public void debug(String message)
    {
        if (plugin.config.getDebug()) {
            plugin.log.info("[ShopAds2 DEBUG-MODE] " + message);
        }
    }

    public void hookedEconomy(String name)
    {
        plugin.log.info("[ShopAds2] Hooked into " + name);
    }

    public void hookedPermissions(String name)
    {
        plugin.log.info("[ShopAds2] Hooked into " + name);
    }

    public void disablePlugin()
    {
        plugin.log.info("[ShopAds2] Plugin disabled.");
    }

    public void playersFileReset()
    {
        plugin.log.info("[ShopAds2] PLAYERS FILE DOESN'T MATCH UP WITH SHOPS, SUGGEST DELETING SHOPS.DAT FILE.");
    }

    public void savingShops()
    {
        plugin.log.info("[ShopAds2] Saving shops.");
    }

    public void savingPlayers()
    {
        plugin.log.info("[ShopAds2] Saving players.");
    }

    public void announceDebug(String string)
    {
        if (plugin.config.getAnnounceDebug()) {
            plugin.log.info("[ShopAds2 ANNOUNCE DEBUG] " + string);
        }
    }

    public void dynampFound()
    {
        plugin.log.info("[ShopAds2] Dynmap was found and will be used.");
    }

    public void customMessage(String custom)
    {
        plugin.log.info("[ShopAds2] " + custom);
    }
}
