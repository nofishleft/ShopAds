package nz.rishaan.shopads.Util.Messaging.Command;


import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.entity.Player;

public class SetCommandMessage
        extends ShopAdsMessage {
    public void displayShopSettings(Player player, Shop shop) {
        player.sendMessage(ShopAds.prefix + shop.getShopName() + "'s settings");
        player.sendMessage(ShopAds.config.getMessageColor() + "Location: " + shop.getLocation().getLocation().toString());
        player.sendMessage(ShopAds.config.getMessageColor() + worldsToAdvertiseInMessage(shop));
        player.sendMessage(ShopAds.config.getMessageColor() + "Ad runs forever: " + shop.runsForever());
        player.sendMessage(ShopAds.config.getMessageColor() + "Shop: " + shop.getShopColor() + "color");
        player.sendMessage(ShopAds.config.getMessageColor() + "Shop ad: " + shop.getAdColor() + "color");
    }

    private String worldsToAdvertiseInMessage(Shop shop) {
        ShopAdsMessage.console.debug("Building worlds to advertise in");
        StringBuilder message = new StringBuilder("Worlds advertising: ");
        ShopAdsMessage.console.debug("Worlds to advertise in size: " + shop.getWorldsToAdvertiseInAsString().length);
        String[] worlds = shop.getWorldsToAdvertiseInAsString();

        message.append(worlds[0]);
        for (int i = 1; i < worlds.length; i++) {
            message.append(", ").append(worlds[i]);
        }
        return message.toString();
    }

    public void shopNameChanged(Player player, String from, String to) {
        player.sendMessage(ShopAds.prefix + "Shop name successfully changed: " + from + " --> " + to);
    }

    public void shopLocationChanged(Player player) {
        player.sendMessage(ShopAds.prefix + "Shop location updated.");
    }

    public void shopWorldsChanged(Player player, String worldAdded, String worldRemoved) {
        if (worldAdded == null) {
            player.sendMessage(ShopAds.prefix + worldRemoved + " was successfully removed.");
        } else {
            player.sendMessage(ShopAds.prefix + worldAdded + " was successfully added.");
        }
    }

    public void shopAdChanged(Player player, String ad) {
        player.sendMessage(ShopAds.prefix + "The ad will now be: " + ad);
    }

    public void configOption(String key, String value, Player player) {
        player.sendMessage(ShopAds.prefix + key + " was changed to " + value + ".");
    }
}