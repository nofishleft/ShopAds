package nz.rishaan.shopads.Util.Messaging;


import nz.rishaan.shopads.ShopAds;

public class ConsoleMessage
        extends ShopAdsMessage {
    public void numberOfShopsLoaded() {
        if (ShopAds.shopHandler.shopsEmpty()) {
            ShopAds.log.info("[ShopAds2] No shops were found.");
            return;
        }
        if (ShopAds.shopHandler.getSize() == 1) {
            ShopAds.log.info("[ShopAds2] 1 shop was loaded.");
        } else {
            ShopAds.log.info("[ShopAds2] " + ShopAds.shopHandler.getSize() + " shops were loaded.");
        }
    }

    public void checkConfigOption(String parameter) {
        ShopAds.log.info("[ShopAds2] Check the config option '" + parameter + "'.");
    }

    public void debug(String message) {
        if (ShopAds.config.getDebug()) {
            ShopAds.log.info("[ShopAds2 DEBUG-MODE] " + message);
        }
    }

    public void hookedEconomy(String name) {
        ShopAds.log.info("[ShopAds2] Hooked into " + name);
    }

    public void hookedPermissions(String name) {
        ShopAds.log.info("[ShopAds2] Hooked into " + name);
    }

    public void disablePlugin() {
        ShopAds.log.info("[ShopAds2] Plugin disabled.");
    }

    public void playersFileReset() {
        ShopAds.log.info("[ShopAds2] PLAYERS FILE DOESN'T MATCH UP WITH SHOPS, SUGGEST DELETING SHOPS.DAT FILE.");
    }

    public void savingShops() {
        ShopAds.log.info("[ShopAds2] Saving shops.");
    }

    public void savingPlayers() {
        ShopAds.log.info("[ShopAds2] Saving players.");
    }

    public void announceDebug(String string) {
        if (ShopAds.config.getAnnounceDebug()) {
            ShopAds.log.info("[ShopAds2 ANNOUNCE DEBUG] " + string);
        }
    }

    public void dynampFound() {
        ShopAds.log.info("[ShopAds2] Dynmap was found and will be used.");
    }

    public void customMessage(String custom) {
        ShopAds.log.info("[ShopAds2] " + custom);
    }
}
