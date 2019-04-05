package nz.rishaan.shopads.Util.Messaging;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Util.Messaging.Command.CommandUsageMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsEconomy;

public class ErrorMessage
        extends ShopAdsMessage
{
    public void overMaxRunTime(Player player, int parseInt)
    {
        ShopAdsMessage.console.debug("overMaxRunTime message");
        player.sendMessage(plugin.prefix + "The maximum allowed ad time is " + plugin.config.getMaxAdRunTime() + " hours. " + ChatColor.RED + "(" + parseInt + ")");
    }

    public void insufficientFunds(Player player, double d)
    {
        player.sendMessage(plugin.prefix + "You do not have " + plugin.economy.getEconomy().format(d) + " to spare.");
    }

    public void noShopEntered(Player player)
    {
        player.sendMessage(plugin.prefix + "You must enter a shop name.");
    }

    public void noAdEntered(Player player)
    {
        player.sendMessage(plugin.prefix + "You must enter an advertisement.");
        commandUsage.setCommand(player);
    }

    public void inputIgnored(Player player, String string)
    {
        player.sendMessage(plugin.prefix + "Paremeters ignored after " + string + ".");
    }

    public void noNameEntered(Player player)
    {
        player.sendMessage(plugin.prefix + "You must enter a name.");
        commandUsage.setCommand(player);
    }

    public void notYourShop(Player player, Shop shop)
    {
        player.sendMessage(plugin.prefix + "The shop '" + shop.getShopName() + " is not owned by you.");
    }

    public void noCommandPermission(Player player, String command)
    {
        player.sendMessage(plugin.prefix + "You do not have permission to use the " + command + " command.");
    }

    public void noShopFound(Player player, String shop)
    {
        player.sendMessage(plugin.prefix + "No shop by that name found. (" + shop + ")");
    }

    public void noColorEntered(Player player)
    {
        player.sendMessage(plugin.prefix + "You must enter a color.");
    }

    public void worldNotFoundInShopsList(Player player)
    {
        player.sendMessage(plugin.prefix + "That world could not be found in the advertise list.");
    }

    public void shopAlreadyExists(Player player)
    {
        player.sendMessage(plugin.prefix + "A shop with that name already exists!");
    }
}
