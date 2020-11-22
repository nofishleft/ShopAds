package nz.rishaan.shopads.Util.Messaging;


import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ErrorMessage
        extends ShopAdsMessage {
    public void overMaxRunTime(Player player, int parseInt) {
        ShopAdsMessage.console.debug("overMaxRunTime message");
        player.sendMessage(ShopAds.prefix + "The maximum allowed ad time is " + ShopAds.config.getMaxAdRunTime() + " hours. " + ChatColor.RED + "(" + parseInt + ")");
    }

    public void insufficientFunds(Player player, double d) {
        player.sendMessage(ShopAds.prefix + "You do not have " + ShopAds.economy.getEconomy().format(d) + " to spare.");
    }

    public void noShopEntered(Player player) {
        player.sendMessage(ShopAds.prefix + "You must enter a shop name.");
    }

    public void noAdEntered(Player player) {
        player.sendMessage(ShopAds.prefix + "You must enter an advertisement.");
        commandUsage.setCommand(player);
    }

    public void inputIgnored(Player player, String string) {
        player.sendMessage(ShopAds.prefix + "Parameters ignored after " + string + ".");
    }

    public void noNameEntered(Player player) {
        player.sendMessage(ShopAds.prefix + "You must enter a name.");
        commandUsage.setCommand(player);
    }

    public void notYourShop(Player player, Shop shop) {
        player.sendMessage(ShopAds.prefix + "The shop '" + shop.getShopName() + " is not owned by you.");
    }

    public void noCommandPermission(Player player, String command) {
        player.sendMessage(ShopAds.prefix + "You do not have permission to use the " + command + " command.");
    }

    public void noShopFound(Player player, String shop) {
        player.sendMessage(ShopAds.prefix + "No shop by that name found. (" + shop + ")");
    }

    public void maxShopsReached(Player player) {
        player.sendMessage(ShopAds.prefix + "You have reached the maximum number of shops.");
    }

    public void noColorEntered(Player player) {
        player.sendMessage(ShopAds.prefix + "You must enter a color.");
    }

    public void worldNotFoundInShopsList(Player player) {
        player.sendMessage(ShopAds.prefix + "That world could not be found in the advertise list.");
    }

    public void shopAlreadyExists(Player player) {
        player.sendMessage(ShopAds.prefix + "A shop with that name already exists!");
    }
}
