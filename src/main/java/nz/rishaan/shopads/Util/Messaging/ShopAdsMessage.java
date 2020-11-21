package nz.rishaan.shopads.Util.Messaging;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import nz.rishaan.shopads.Player.TeleportedPlayer;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Mathematical;
import nz.rishaan.shopads.Util.Messaging.Command.CommandMessage;
import nz.rishaan.shopads.Util.Messaging.Command.CommandUsageMessage;
import nz.rishaan.shopads.Util.Messaging.Command.SetCommandMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsEconomy;

public class ShopAdsMessage
{
    public static ShopAds plugin;
    public ShopAdsMessage() {}

    public static final ConsoleMessage console = new ConsoleMessage();
    public static final CommandUsageMessage commandUsage = new CommandUsageMessage();
    public static final CommandMessage command = new CommandMessage();
    public static final SetCommandMessage setCommand = new SetCommandMessage();
    public static final AdvertisementMessage advertise = new AdvertisementMessage();
    public static final ErrorMessage error = new ErrorMessage();
    public static final Mathematical math = new Mathematical();
    private DecimalFormat df = new DecimalFormat("#0.00");

    public void tpTimeout(Player player)
    {
        player.sendMessage(plugin.prefix + "You have been returned to your previous location.");
    }

    public void chargePlayer(Player player, double amount)
    {
        player.sendMessage(plugin.prefix + "You were charged " + plugin.economy.format(amount));
    }

    public void newerVersionAvailable(Player player)
    {
        player.sendMessage(plugin.prefix + "There is a newer version of this plugin available.");
        player.sendMessage(plugin.prefix + "Current: " + plugin.getDescription().getVersion() + " Update: " + plugin.updateVersion + " http://dev.bukkit.org/server-mods/shopads/");
    }

    public void payPlayer(Player player, double amount)
    {
        player.sendMessage(plugin.prefix + "You just recieved " + this.df.format(amount) + " ");
    }

    public void playerTeleportedToYourShop(Player player, Player teleporter, Shop shop)
    {
        player.sendMessage(plugin.prefix + teleporter.getName() + " just teleported to your shop, " + shop.getShopName() + ".");
    }

    public ChatColor getColor(String color)
    {
        if (color.equalsIgnoreCase("aqua")) {
            return ChatColor.AQUA;
        }
        if (color.equalsIgnoreCase("black")) {
            return ChatColor.BLACK;
        }
        if (color.equalsIgnoreCase("blue")) {
            return ChatColor.BLUE;
        }
        if ((color.equalsIgnoreCase("dark_aqua")) || (color.equalsIgnoreCase("dark aqua"))) {
            return ChatColor.DARK_AQUA;
        }
        if ((color.equalsIgnoreCase("dark_blue")) || (color.equalsIgnoreCase("dark blue"))) {
            return ChatColor.DARK_BLUE;
        }
        if ((color.equalsIgnoreCase("dark_gray")) || (color.equalsIgnoreCase("dark gray"))) {
            return ChatColor.DARK_GRAY;
        }
        if ((color.equalsIgnoreCase("dark_green")) || (color.equalsIgnoreCase("dark green"))) {
            return ChatColor.DARK_GREEN;
        }
        if ((color.equalsIgnoreCase("dark_purple")) || (color.equalsIgnoreCase("dark purple"))) {
            return ChatColor.DARK_PURPLE;
        }
        if ((color.equalsIgnoreCase("dark_red")) || (color.equalsIgnoreCase("dark red"))) {
            return ChatColor.DARK_RED;
        }
        if (color.equalsIgnoreCase("gold")) {
            return ChatColor.GOLD;
        }
        if (color.equalsIgnoreCase("gray")) {
            return ChatColor.GRAY;
        }
        if (color.equalsIgnoreCase("green")) {
            return ChatColor.GREEN;
        }
        if ((color.equalsIgnoreCase("light_purple")) || (color.equalsIgnoreCase("light purple"))) {
            return ChatColor.LIGHT_PURPLE;
        }
        if (color.equalsIgnoreCase("red")) {
            return ChatColor.RED;
        }
        if (color.equalsIgnoreCase("white")) {
            return ChatColor.WHITE;
        }
        if (color.equalsIgnoreCase("yellow")) {
            return ChatColor.YELLOW;
        }
        return ChatColor.WHITE;
    }

    public void playerTeleportExtended(TeleportedPlayer player)
    {
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        int timeLeft = (int)(player.getTpExpire().getTime() - dateNow.getTime()) / 1000;
        player.getPlayer().sendMessage(plugin.prefix + "Your visit has been extended " + plugin.config.getTpTimeout() + " seconds.");
        player.getPlayer().sendMessage(plugin.prefix + "You now have " + timeLeft + " seconds left.");
    }

    public void playerTeleported(Player player)
    {
        player.sendMessage(plugin.prefix + "You will be returned in " + plugin.config.getTpTimeout() + " seconds.");
    }

    public void tpTimeoutInFiveSecond(Player player)
    {
        player.sendMessage(plugin.prefix + "5 seconds.");
    }

    public void tpTimeoutInThirtySecond(Player player)
    {
        player.sendMessage(plugin.prefix + "30 seconds until you are returned.");
    }

    public void shopExpired(Shop shop)
    {
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) plugin.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++)
        {
            Player p = arrayOfPlayer[i];
            if (p.getName().equalsIgnoreCase(shop.getShopOwner())) {
                p.sendMessage(plugin.prefix + "Your shop, " + shop.getShopName() + ", has expired.");
            }
        }
    }
}
