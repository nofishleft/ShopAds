package nz.rishaan.shopads.Util.Messaging.Command;


import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.entity.Player;

public class CommandUsageMessage
        extends ShopAdsMessage {
    public void fullCommandMenu(Player player) {
        ShopAdsMessage.console.debug("Full COmmand List");
        player.sendMessage(ShopAds.prefix + "Commands");
        if (!ShopAds.config.getSendToAll()) {
            if (ShopAds.permissions.hasCreatePermission(player)) {
                createCommand(player);
            }
            if (ShopAds.permissions.hasSetPermission(player)) {
                setCommand(player);
            }
            if (ShopAds.permissions.hasDeleteOwnPermission(player)) {
                deleteCommand(player);
            }
            if ((ShopAds.permissions.hasAdminPermission(player)) || (ShopAds.permissions.hasStatsOtherPermission(player))) {
                statsOtherCommand(player);
            } else if (ShopAds.permissions.hasStatsPermission(player)) {
                statsCommand(player);
            }
            ratesCommand(player);
            listCommand(player);
            onCommand(player);
            offCommand(player);
            if (ShopAds.permissions.hasAdminPermission(player)) {
                configCommand(player);
                reloadCommand(player);
                disableCommand(player);
            }
            shopsCommand(player);
        } else {
            if (ShopAds.permissions.hasCreatePermission(player)) {
                createCommand(player);
            }
            if (ShopAds.permissions.hasSetPermission(player)) {
                setCommand(player);
            }
            if (ShopAds.permissions.hasDeleteOwnPermission(player)) {
                deleteCommand(player);
            }
            if ((ShopAds.permissions.hasAdminPermission(player)) || (ShopAds.permissions.hasStatsOtherPermission(player))) {
                statsOtherCommand(player);
            } else if (ShopAds.permissions.hasStatsPermission(player)) {
                statsCommand(player);
            }
            ratesCommand(player);
            listCommand(player);
            if (ShopAds.permissions.hasAdminPermission(player)) {
                configCommand(player);
                reloadCommand(player);
                disableCommand(player);
            }
            shopsCommand(player);
        }
    }

    public void shopsCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/shops - List shops available to tp");
    }

    public void disableCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad disable - Disables the plugin instantly");
    }

    public void reloadCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad reload - Reload the config and ads");
    }

    public void offCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad off - Stop receiving ads");
    }

    public void onCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad on - Start receiving ads");
    }

    public void listCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad list - Lists all the current ads");
    }

    public void ratesCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad rates - Returns the current daily rate");
    }

    public void statsCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad stats - Display information about all your current ads");
    }

    public void statsOtherCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad stats (player) - Display information about someones ads");
    }

    public void deleteCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad del [shopname] - Stop your currently running ad");
    }

    public void setCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad set [shopname] [loc/aworld/rworld/name/ad] - Modifies one of your shops");
    }

    public void createCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad c [shopname] [number of hrs] [message]");
    }

    public void configCommand(Player player) {
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config [key] [value]");
    }

    public void configSettings(Player player) {
        ShopAdsMessage.console.debug("configCommand Message");
        player.sendMessage(ShopAds.prefix + "config options:");
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config [key] [value]");
        player.sendMessage(ShopAds.config.getMessageColor() + "announceInterval: " + ShopAds.config.getAnnounceInterval());
        player.sendMessage(ShopAds.config.getMessageColor() + "sendToAll: " + ShopAds.config.getSendToAll());
        player.sendMessage(ShopAds.config.getMessageColor() + "enableTp: " + ShopAds.config.getEnableTp());
        player.sendMessage(ShopAds.config.getMessageColor() + "randomOrder: " + ShopAds.config.getRandomOrder());
        player.sendMessage(ShopAds.config.getMessageColor() + "tpCost: " + ShopAds.config.getTpCost());
        player.sendMessage(ShopAds.config.getMessageColor() + "transWorldAddition: " + ShopAds.config.getTransWorldAddition());
        player.sendMessage(ShopAds.config.getMessageColor() + "adsOverWorlds: " + ShopAds.config.getAdsOverWorlds());
        player.sendMessage(ShopAds.config.getMessageColor() + "tpTimeout: " + ShopAds.config.getTpTimeout());
        player.sendMessage(ShopAds.config.getMessageColor() + "maxAdRunTime: " + ShopAds.config.getMaxAdRunTime());
        player.sendMessage(ShopAds.config.getMessageColor() + "shopsPerPlayer: " + ShopAds.config.getShopsPerPlayer());
        player.sendMessage(ShopAds.config.getMessageColor() + "adCost: " + ShopAds.config.getAdCost());
        player.sendMessage(ShopAds.config.getMessageColor() + "tpCostDestination: " + ShopAds.config.getTpCostDestination());
        player.sendMessage(ShopAds.config.getMessageColor() + "announceRadius: " + ShopAds.config.getAnnounceRadius());
    }

    public void announceIntervalUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config announceInterval [number in seconds]");
    }

    public void sendToAllUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config sendToAll [true/false]");
    }

    public void enableTpUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config enableTp [true/false]");
    }

    public void randomOrderUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config randomOrder [true/false]");
    }

    public void tpCostUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config tpCost [number]");
    }

    public void transWorldAdditionAllUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config transWorldAddition [number]");
    }

    public void maxAdRunTimeUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config maxAdRunTime [number in hours]");
    }

    public void shopsPerPlayerUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config shopsPerPlayer [number]");
    }

    public void adCostUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config adCost [number]");
    }

    public void tpCostDestinationUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config tpCostDestination [shop/server]");
    }

    public void announceRadiusUsage(Player player) {
        incorrectUsage(player);
        player.sendMessage(ShopAds.config.getMessageColor() + "/ad config announceRadius [number of blocks]");
    }

    public void incorrectUsage(Player player) {
        player.sendMessage(ShopAds.prefix + "Command Usage:");
    }
}