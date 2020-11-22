package nz.rishaan.shopads.Util.Messaging.Command;


import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CommandMessage
        extends ShopAdsMessage {
    public void rates(Player player) {
        player.sendMessage(ShopAds.prefix + "The current rates are " + ShopAds.economy.getEconomy().format(ShopAds.config.getAdCost()) + " per hour.");
    }

    public void shopStats(Player player, Shop shop) {
        DecimalFormat df = new DecimalFormat("#0.00");
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        double difference = 0.0D;
        if (!shop.shopExpired()) {
            if (!shop.runsForever()) {
                ShopAdsMessage.console.debug(" Shop time: " + shop.getTimeToEnd().getTime());
                ShopAdsMessage.console.debug("  Time now: " + dateNow.getTime());
                ShopAdsMessage.console.debug("Difference: " + (shop.getTimeToEnd().getTime() - dateNow.getTime()));
                difference = shop.getTimeToEnd().getTime() - dateNow.getTime();
            }
            player.sendMessage(ShopAds.config.getLabelColor() + "[" + shop.getShopName() + "]");
            if (!shop.runsForever()) {
                player.sendMessage(ShopAds.config.getMessageColor() + "Time left: " + df.format(difference / 3600000.0D) + " hrs");
            } else {
                player.sendMessage(ShopAds.config.getMessageColor() + "Time left: Forever");
            }
            player.sendMessage(ShopAds.config.getMessageColor() + "Times teleported to: " + shop.getTimesTeleportedTo());
            if (ShopAds.config.getTpCostDestination().equalsIgnoreCase("shop")) {
                player.sendMessage(ShopAds.config.getMessageColor() + "Money earned: " + df.format(shop.getMoneyEarned()));
            }
        }
    }

    public void ownedShopsSelf(Player player) {
        if (ShopAds.permissions.hasStatsSelf(player)) {
            if (ShopAds.playerHandler.playerExists(player.getName())) {
                ShopAdsMessage.console.debug("ownedShopsSelf - Player exists");
                ArrayList<Shop> playersShops = ShopAds.shopHandler.getPlayersShops(player);
                switch (playersShops.size()) {
                    case 0:
                        player.sendMessage(ShopAds.prefix + "You are not currently advertising.");
                        break;
                    case 1:
                        player.sendMessage(ShopAds.prefix + "You currently are advertising one shop:");
                        for (Shop s : playersShops) {
                            ShopAdsMessage.console.debug("ownedShopsSelf - displaying stats for " + s.getShopName());
                            shopStats(player, s);
                        }
                        break;
                    default:
                        player.sendMessage(ShopAds.prefix + "You currently are advertising " + playersShops.size() + " shops:");
                        ShopAdsMessage.console.debug("ownedShopsSelf - size of playersShops: " + playersShops.size());
                        for (Shop s : playersShops) {
                            ShopAdsMessage.console.debug("ownedShopsSelf - displaying stats for " + s.getShopName());
                            shopStats(player, s);
                        }
                        break;
                }
            } else {
                ShopAdsMessage.console.debug("Player doesn't exist");
            }
        } else {
                ShopAdsMessage.error.noCommandPermission(player, "STATS_SELF");
        }
    }

    public void ownedShopsOther(Player requester, String player) {
        ShopAdsMessage.console.debug("ownedShopOther message");
        if (ShopAds.permissions.hasStatsOther(requester)) {
            if (ShopAds.playerHandler.playerExists(player)) {
                ArrayList<Shop> playersShops = ShopAds.shopHandler.getPlayersShops(player);
                switch (playersShops.size()) {
                    case 0:
                        requester.sendMessage(ShopAds.prefix + player + " is not currently advertising.");
                        break;
                    case 1:
                        requester.sendMessage(ShopAds.prefix + player + " currently is advertising one shop.");
                        for (Shop s : playersShops) {
                            ShopAdsMessage.console.debug("displaying stats for " + s.getShopName());
                            shopStats(requester, s);
                        }
                        break;
                    default:
                        requester.sendMessage(ShopAds.prefix + player + " currently is advertising " + playersShops.size() + " shops.");
                        for (Shop s : playersShops) {
                            ShopAdsMessage.console.debug("displaying stats for " + s.getShopName());
                            shopStats(requester, s);
                        }
                        break;
                }
            } else {
                requester.sendMessage(ShopAds.prefix + player + " does not have any shops or doesn't exist.");
            }
        } else {
            ShopAdsMessage.error.noCommandPermission(requester, "STATS_OTHERS");
        }

    }

    public void shopCreated(Player player, Shop newShop) {
        if (!newShop.runsForever()) {
            player.sendMessage(ShopAds.prefix + "You just created " + newShop.getShopName() + " which will run until " + newShop.getTimeToEnd().toString());
        } else {
            player.sendMessage(ShopAds.prefix + "You just created " + newShop.getShopName() + " which will run until deleted");
        }
    }

    public void worldAddedToShop(Shop shop, Player player, World world) {
        player.sendMessage(ShopAds.prefix + "You added " + world.getName() + " to " + shop.getShopName());
    }

    public void shopNameChanged(Player player, Shop shop, String string) {
        player.sendMessage(ShopAds.prefix + "You changed the name of " + shop.getShopName() + " to " + string + ".");
    }

    public void listAds(Player player) {
        if (ShopAds.shopHandler.shopsEmpty()) {
            player.sendMessage(ShopAds.prefix + "There are no current ads.");
            return;
        }
        player.sendMessage(ShopAds.prefix + "These are all the current ads.");
        for (Shop s : ShopAds.shopHandler.getShops()) {
            ShopAdsMessage.console.debug("listAds shop advertising: " + s.shopAdvertising() + " and is shop expired: " + s.shopExpired());
            if ((s.shopAdvertising()) && (!s.shopExpired())) {
                player.sendMessage(ShopAds.config.getLabelColor() + "[" + s.getShopName() + "] " + ShopAds.config.getMessageColor() + s.getAd());
            }
        }
    }

    public void listShops(Player player) {
        if (ShopAds.shopHandler.shopsEmpty()) {
            player.sendMessage(ShopAds.prefix + "There are no shops available to teleport to.");
            return;
        }
        player.sendMessage(ShopAds.prefix + "These are all the current shops.");
        for (Shop s : ShopAds.shopHandler.getShops()) {
            if ((s.shopAdvertising()) && (!s.shopExpired()) && (s.advertisesIn(player.getWorld().getName()))) {
                if (s.getWorld() == player.getWorld()) {
                    player.sendMessage(ShopAds.config.getLabelColor() + s.getShopName());
                } else {
                    player.sendMessage(ShopAds.config.getLabelColor() + s.getShopName() + "*");
                }
            }
        }
    }

    public void disable(Player player) {
        player.sendMessage(ShopAds.prefix + "Plugin disabled.");
    }

    public void reload(Player player) {
        player.sendMessage(ShopAds.prefix + "Config, shops, and players reloaded.");
    }

    public void shopDeleted(Player player, Shop shop) {
        player.sendMessage(ShopAds.prefix + "You have deleted " + shop.getShopName() + ".");
    }

    public void shopNameTaken(Player player) {
        player.sendMessage(ShopAds.prefix + "A shop by that name already exists.");
    }

    public void configSaved(Player player) {
        player.sendMessage(ShopAds.prefix + "Config saved.");
    }

    public void willReceiveAds(Player player) {
        player.sendMessage(ShopAds.prefix + "You will now receive ads.");
    }

    public void willNotReceiveAds(Player player) {
        player.sendMessage(ShopAds.prefix + "You will no longer receive ads.");
    }

    public void wasntReceivingAds(Player player) {
        player.sendMessage(ShopAds.prefix + "You weren't receiving ads.");
    }

    public void wasAlreadyReceivingAds(Player player) {
        player.sendMessage(ShopAds.prefix + "You were already receiving ads.");
    }

    public void sendsToAll(Player player) {
        player.sendMessage(ShopAds.prefix + "The current setting send to all players.");
    }
}