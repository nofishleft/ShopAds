package nz.rishaan.shopads.Util.Messaging.Command;


import org.bukkit.entity.Player;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsPermissions;

public class CommandUsageMessage
        extends ShopAdsMessage
{
    public void fullCommandMenu(Player player)
    {
        ShopAdsMessage.console.debug("Full COmmand List");
        player.sendMessage(plugin.prefix + "Commands");
        if (!plugin.config.getSendToAll())
        {
            if (plugin.permissions.hasCreatePermission(player)) {
                createCommand(player);
            }
            if (plugin.permissions.hasSetPermission(player)) {
                setCommand(player);
            }
            if (plugin.permissions.hasDeleteOwnPermission(player)) {
                deleteCommand(player);
            }
            if ((plugin.permissions.hasAdminPermission(player)) || (plugin.permissions.hasStatsOtherPermission(player))) {
                statsOtherCommand(player);
            } else if (plugin.permissions.hasStatsPermission(player)) {
                statsCommand(player);
            }
            ratesCommand(player);
            listCommand(player);
            onCommand(player);
            offCommand(player);
            if (plugin.permissions.hasAdminPermission(player))
            {
                configCommand(player);
                reloadCommand(player);
                disableCommand(player);
            }
            shopsCommand(player);
        }
        else
        {
            if (plugin.permissions.hasCreatePermission(player)) {
                createCommand(player);
            }
            if (plugin.permissions.hasSetPermission(player)) {
                setCommand(player);
            }
            if (plugin.permissions.hasDeleteOwnPermission(player)) {
                deleteCommand(player);
            }
            if ((plugin.permissions.hasAdminPermission(player)) || (plugin.permissions.hasStatsOtherPermission(player))) {
                statsOtherCommand(player);
            } else if (plugin.permissions.hasStatsPermission(player)) {
                statsCommand(player);
            }
            ratesCommand(player);
            listCommand(player);
            if (plugin.permissions.hasAdminPermission(player))
            {
                configCommand(player);
                reloadCommand(player);
                disableCommand(player);
            }
            shopsCommand(player);
        }
    }

    public void shopsCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/shops - List shops available to tp");
    }

    public void disableCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad disable - Disables the plugin instantly");
    }

    public void reloadCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad reload - Reload the config and ads");
    }

    public void offCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad off - Stop receiving ads");
    }

    public void onCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad on - Start receiving ads");
    }

    public void listCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad list - Lists all the current ads");
    }

    public void ratesCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad rates - Returns the current daily rate");
    }

    public void statsCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad stats - Display information about all your current ads");
    }

    public void statsOtherCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad stats (player) - Display information about someones ads");
    }

    public void deleteCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad del [shopname] - Stop your currently running ad");
    }

    public void setCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad set [shopname] [loc/aworld/rworld/name/ad] - Modifies one of your shops");
    }

    public void createCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad c [shopname] [number of hrs] [message]");
    }

    public void configCommand(Player player)
    {
        player.sendMessage(plugin.config.getMessageColor() + "/ad config [key] [value]");
    }

    public void configSettings(Player player)
    {
        ShopAdsMessage.console.debug("configCommand Message");
        player.sendMessage(plugin.prefix + "config options:");
        player.sendMessage(plugin.config.getMessageColor() + "/ad config [key] [value]");
        player.sendMessage(plugin.config.getMessageColor() + "announceInterval: " +plugin. config.getAnnounceInterval());
        player.sendMessage(plugin.config.getMessageColor() + "sendToAll: " + plugin.config.getSendToAll());
        player.sendMessage(plugin.config.getMessageColor() + "enableTp: " + plugin.config.getEnableTp());
        player.sendMessage(plugin.config.getMessageColor() + "randomOrder: " + plugin.config.getRandomOrder());
        player.sendMessage(plugin.config.getMessageColor() + "tpCost: " + plugin.config.getTpCost());
        player.sendMessage(plugin.config.getMessageColor() + "transWorldAddition: " + plugin.config.getTransWorldAddition());
        player.sendMessage(plugin.config.getMessageColor() + "adsOverWorlds: " + plugin.config.getAdsOverWorlds());
        player.sendMessage(plugin.config.getMessageColor() + "tpTimeout: " + plugin.config.getTpTimeout());
        player.sendMessage(plugin.config.getMessageColor() + "maxAdRunTime: " + plugin.config.getMaxAdRunTime());
        player.sendMessage(plugin.config.getMessageColor() + "shopsPerPlayer: " + plugin.config.getShopsPerPlayer());
        player.sendMessage(plugin.config.getMessageColor() + "adCost: " + plugin.config.getAdCost());
        player.sendMessage(plugin.config.getMessageColor() + "tpCostDestination: " + plugin.config.getTpCostDestination());
        player.sendMessage(plugin.config.getMessageColor() + "announceRadius: " + plugin.config.getAnnounceRadius());
    }

    public void announceIntervalUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config announceInterval [number in seconds]");
    }

    public void sendToAllUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config sendToAll [true/false]");
    }

    public void enableTpUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config enableTp [true/false]");
    }

    public void randomOrderUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config randomOrder [true/false]");
    }

    public void tpCostUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config tpCost [number]");
    }

    public void transWorldAdditionAllUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config transWorldAddition [number]");
    }

    public void maxAdRunTimeUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config maxAdRunTime [number in hours]");
    }

    public void shopsPerPlayerUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config shopsPerPlayer [number]");
    }

    public void adCostUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config adCost [number]");
    }

    public void tpCostDestinationUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config tpCostDestination [shop/server]");
    }

    public void announceRadiusUsage(Player player)
    {
        incorrectUsage(player);
        player.sendMessage(plugin.config.getMessageColor() + "/ad config announceRadius [number of blocks]");
    }

    public void incorrectUsage(Player player)
    {
        player.sendMessage(plugin.prefix + "Command Usage:");
    }
}