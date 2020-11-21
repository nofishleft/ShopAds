package nz.rishaan.shopads.Util.Messaging.Command;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Shop.AdLocation;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;

public class SetCommandMessage
        extends ShopAdsMessage
{
    public void displayShopSettings(Player player, Shop shop)
    {
        player.sendMessage(plugin.prefix + shop.getShopName() + "'s settings");
        player.sendMessage(plugin.config.getMessageColor() + "Location: " + shop.getLocation().getLocation().toString());
        player.sendMessage(plugin.config.getMessageColor() + worldsToAdvertiseInMessage(shop));
        player.sendMessage(plugin.config.getMessageColor() + "Ad runs forever: " + String.valueOf(shop.runsForever()));
        player.sendMessage(plugin.config.getMessageColor() + "Shop: " + shop.getShopColor() + "color");
        player.sendMessage(plugin.config.getMessageColor() + "Shop ad: " + shop.getAdColor() + "color");
    }

    private String worldsToAdvertiseInMessage(Shop shop)
    {
        ShopAdsMessage.console.debug("Building worlds to advertise in");
        String message = "Worlds advertising: ";
        ShopAdsMessage.console.debug("Worlds to advertise in size: " + shop.getWorldsToAdvertiseInAsString().length);
        String[] worlds = shop.getWorldsToAdvertiseInAsString();

        message = message + worlds[0];
        for (int i = 1; i < worlds.length; i++) {
            message = message + ", " + worlds[i];
        }
        return message;
    }

    public void shopNameChanged(Player player, String from, String to)
    {
        player.sendMessage(plugin.prefix + "Shop name successfully changed: " + from + " --> " + to);
    }

    public void shopLocationChanged(Player player)
    {
        player.sendMessage(plugin.prefix + "Shop location updated.");
    }

    public void shopWorldsChanged(Player player, String worldAdded, String worldRemoved)
    {
        if (worldAdded == null) {
            player.sendMessage(plugin.prefix + worldRemoved + " was successfully removed.");
        } else {
            player.sendMessage(plugin.prefix + worldAdded + " was successfully added.");
        }
    }

    public void shopAdChanged(Player player, String ad)
    {
        player.sendMessage(plugin.prefix + "The ad will now be: " + ad);
    }

    public void configOption(String key, String value, Player player)
    {
        player.sendMessage(plugin.prefix + key + " was changed to " + value + ".");
    }
}