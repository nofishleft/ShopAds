package nz.rishaan.shopads.Util.Messaging.Command;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.World;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Player.PlayerHandler;
import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsEconomy;

public class CommandMessage
        extends ShopAdsMessage
{
    public void rates(Player player)
    {
        player.sendMessage(plugin.prefix + "The current rates are " + plugin.economy.getEconomy().format(plugin.config.getAdCost()) + " per hour.");
    }

    public void shopStats(Player player, Shop shop)
    {
        DecimalFormat df = new DecimalFormat("#0.00");
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        double difference = 0.0D;
        if (!shop.shopExpired())
        {
            if (!shop.runsForever())
            {
                ShopAdsMessage.console.debug(" Shop time: " + shop.getTimeToEnd().getTime());
                ShopAdsMessage.console.debug("  Time now: " + dateNow.getTime());
                ShopAdsMessage.console.debug("Difference: " + (shop.getTimeToEnd().getTime() - dateNow.getTime()));
                difference = shop.getTimeToEnd().getTime() - dateNow.getTime();
            }
            player.sendMessage(plugin.config.getLabelColor() + "[" + shop.getShopName() + "]");
            if (!shop.runsForever()) {
                player.sendMessage(plugin.config.getMessageColor() + "Time left: " + df.format(difference / 3600000.0D) + " hrs");
            } else {
                player.sendMessage(plugin.config.getMessageColor() + "Time left: Forever");
            }
            player.sendMessage(plugin.config.getMessageColor() + "Times teleported to: " + shop.getTimesTeleportedTo());
            if (plugin.config.getTpCostDestination().equalsIgnoreCase("shop")) {
                player.sendMessage(plugin.config.getMessageColor() + "Money earned: " + df.format(shop.getMoneyEarned()));
            }
        }
    }

    public void ownedShopsSelf(Player player)
    {
        if (plugin.playerHandler.playerExists(player.getName()))
        {
            ShopAdsMessage.console.debug("ownedShopsSelf - Player exists");
            if (plugin.playerHandler.getPlayer(player.getName()).getOwnedShops() == 0)
            {
                player.sendMessage(plugin.prefix + "You are not currently advertising.");
                return;
            }
            if (plugin.playerHandler.getPlayer(player.getName()).getOwnedShops() == 1)
            {
                player.sendMessage(plugin.prefix + "You currently are advertising one shop:");
                ArrayList<Shop> playersShops = plugin.shopHandler.getPlayersShops(player);
                for (Shop s : playersShops)
                {
                    ShopAdsMessage.console.debug("ownedShopsSelf - displaying stats for " + s.getShopName());
                    shopStats(player, s);
                }
                return;
            }
            if (plugin.playerHandler.getPlayer(player.getName()).getOwnedShops() > 1)
            {
                player.sendMessage(plugin.prefix + "You currently are advertising " + plugin.playerHandler.getPlayer(player.getName()).getOwnedShops() + " shops:");
                ArrayList<Shop> playersShops = plugin.shopHandler.getPlayersShops(player);
                ShopAdsMessage.console.debug("ownedShopsSelf - size of playersShops: " + playersShops.size());
                for (Shop s : playersShops)
                {
                    ShopAdsMessage.console.debug("ownedShopsSelf - displaying stats for " + s.getShopName());
                    shopStats(player, s);
                }
                return;
            }
        }
        ShopAdsMessage.console.debug("Player doesn't exist");
    }

    public void ownedShopsOther(Player requester, String player)
    {
        ShopAdsMessage.console.debug("ownedShopOther message");
        if (plugin.playerHandler.playerExists(player))
        {
            if (plugin.playerHandler.getPlayer(player).getOwnedShops() == 0) {
                requester.sendMessage(plugin.prefix + player + " is not currently advertising.");
            }
            if (plugin.playerHandler.getPlayer(player).getOwnedShops() == 1)
            {
                requester.sendMessage(plugin.prefix + player + " currently is advertising one shop.");
                ArrayList<Shop> playersShops = plugin.shopHandler.getPlayersShops(player);
                for (Shop s : playersShops)
                {
                    ShopAdsMessage.console.debug("displaying stats for " + s.getShopName());
                    shopStats(requester, s);
                }
                return;
            }
            if (plugin.playerHandler.getPlayer(player).getOwnedShops() > 1)
            {
                requester.sendMessage(plugin.prefix + player + " currently is advertising " + plugin.playerHandler.getPlayer(player).getOwnedShops() + " shops.");
                ArrayList<Shop> playersShops = plugin.shopHandler.getPlayersShops(player);
                for (Shop s : playersShops)
                {
                    ShopAdsMessage.console.debug("displaying stats for " + s.getShopName());
                    shopStats(requester, s);
                }
                return;
            }
        }
        requester.sendMessage(plugin.prefix + player + " does not have any shops or doesn't exist.");
    }

    public void shopCreated(Player player, Shop newShop)
    {
        if (!newShop.runsForever()) {
            player.sendMessage(plugin.prefix + "You just created " + newShop.getShopName() + " which will run until " + newShop.getTimeToEnd().toString());
        } else {
            player.sendMessage(plugin.prefix + "You just created " + newShop.getShopName() + " which will run until deleted");
        }
    }

    public void worldAddedToShop(Shop shop, Player player, World world)
    {
        player.sendMessage(plugin.prefix + "You added " + world.getName() + " to " + shop.getShopName());
    }

    public void shopNameChanged(Player player, Shop shop, String string)
    {
        player.sendMessage(plugin.prefix + "You changed the name of " + shop.getShopName() + " to " + string + ".");
    }

    public void listAds(Player player)
    {
        if (plugin.shopHandler.shopsEmpty())
        {
            player.sendMessage(plugin.prefix + "There are no current ads.");
            return;
        }
        player.sendMessage(plugin.prefix + "These are all the current ads.");
        for (Shop s : plugin.shopHandler.getShops())
        {
            ShopAdsMessage.console.debug("listAds shop advertising: " + s.shopAdvertising() + " and is shop expired: " + s.shopExpired());
            if ((s.shopAdvertising()) && (!s.shopExpired())) {
                player.sendMessage(plugin.config.getLabelColor() + "[" + s.getShopName() + "] " + plugin.config.getMessageColor() + s.getAd());
            }
        }
    }

    public void listShops(Player player)
    {
        if (plugin.shopHandler.shopsEmpty())
        {
            player.sendMessage(plugin.prefix + "There are no shops available to teleport to.");
            return;
        }
        player.sendMessage(plugin.prefix + "These are all the current shops.");
        for (Shop s : plugin.shopHandler.getShops()) {
            if ((s.shopAdvertising()) && (!s.shopExpired()) && (s.advertisesIn(player.getWorld().getName()))) {
                if (s.getWorld() == player.getWorld()) {
                    player.sendMessage(plugin.config.getLabelColor() + s.getShopName());
                } else {
                    player.sendMessage(plugin.config.getLabelColor() + s.getShopName() + "*");
                }
            }
        }
    }

    public void disable(Player player)
    {
        player.sendMessage(plugin.prefix + "Plugin disabled.");
    }

    public void reload(Player player)
    {
        player.sendMessage(plugin.prefix + "Config, shops, and players reloaded.");
    }

    public void shopDeleted(Player player, Shop shop)
    {
        player.sendMessage(plugin.prefix + "You have deleted " + shop.getShopName() + ".");
    }

    public void shopNameTaken(Player player)
    {
        player.sendMessage(plugin.prefix + "A shop by that name already exists.");
    }

    public void configSaved(Player player)
    {
        player.sendMessage(plugin.prefix + "Config saved.");
    }

    public void willReceiveAds(Player player)
    {
        player.sendMessage(plugin.prefix + "You will now receive ads.");
    }

    public void willNotReceiveAds(Player player)
    {
        player.sendMessage(plugin.prefix + "You will no longer receive ads.");
    }

    public void wasntReceivingAds(Player player)
    {
        player.sendMessage(plugin.prefix + "You weren't receiving ads.");
    }

    public void wasAlreadyReceivingAds(Player player)
    {
        player.sendMessage(plugin.prefix + "You were already receiving ads.");
    }

    public void sendsToAll(Player player)
    {
        player.sendMessage(plugin.prefix + "The current setting send to all players.");
    }
}