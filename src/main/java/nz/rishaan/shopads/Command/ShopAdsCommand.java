package nz.rishaan.shopads.Command;

import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.Warning;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShopAdsCommand
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ShopAdsMessage.console.debug("Command caught");
        if ((sender instanceof Player)) {
            return playerCommand((Player) sender, cmd, commandLabel, args);
        } else return consoleCommand(cmd, commandLabel, args);
    }

    private boolean consoleCommand(Command cmd, String commandLabel, String[] args) {
        ShopAdsMessage.console.customMessage("Console commands are not yet implemented");
        return false;
    }

    @Warning(reason = "Uses if/else instead of string switch")
    private boolean playerCommand(Player player, Command cmd, String commandLabel, String[] args) {
        ShopAdsMessage.console.debug("playerCommand");
        if ((commandLabel.equalsIgnoreCase("ad")) || (commandLabel.equalsIgnoreCase("ads"))) {
            ShopAdsMessage.console.debug("ad command");
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "on":
                        on(player, args);
                        break;
                    case "off":
                        off(player, args);
                        break;
                    case "stat":
                    case "stats":
                        if (ShopAds.permissions.hasStatsSelf(player) || ShopAds.permissions.hasStatsOther(player))
                            stats(player, args);
                        else
                            ShopAdsMessage.error.noCommandPermission(player, "STATS");
                        break;
                    case "del":
                    case "delete":
                        if (ShopAds.permissions.hasDeleteSelf(player) || ShopAds.permissions.hasDeleteOther(player))
                            delete(player, args);
                        else
                            ShopAdsMessage.error.noCommandPermission(player, "DELETE");
                        break;
                    case "c":
                    case "create":
                        if (ShopAds.permissions.hasCreate(player))
                            create(player, args);
                        else
                            ShopAdsMessage.error.noCommandPermission(player, "CREATE");
                        break;
                    case "rate":
                    case "rates":
                        rates(player, args);
                        break;
                    case "list":
                        list(player, args);
                        break;
                    case "rel":
                    case "reload":
                        if (ShopAds.permissions.hasReload(player))
                            reload(player, args);
                        else
                            ShopAdsMessage.error.noCommandPermission(player, "RELOAD");
                        break;
                    case "config":
                        if (ShopAds.permissions.hasConfig(player))
                            config(player, args);
                        else
                            ShopAdsMessage.error.noCommandPermission(player, "CONFIG");
                        break;
                    case "disable":
                        if (ShopAds.permissions.hasDisable(player))
                            disable(player, args);
                        else
                            ShopAdsMessage.error.noCommandPermission(player, "DISABLE");
                        break;
                    case "set":
                        if (ShopAds.permissions.hasSetSelf(player) || ShopAds.permissions.hasSetOther(player))
                            set(player, args);
                        else
                            ShopAdsMessage.error.noCommandPermission(player, "SET");
                        break;
                    default:
                        ShopAdsMessage.commandUsage.incorrectUsage(player);
                        ShopAdsMessage.commandUsage.fullCommandMenu(player);
                        break;
                }
                return true;
            }
            ShopAdsMessage.commandUsage.fullCommandMenu(player);
        }
        if (commandLabel.equalsIgnoreCase("shops")) {
            if (args.length == 0) {
                ShopAdsMessage.command.listShops(player);
                return true;
            } else if (args.length == 1) {
                teleportToShop(player, args[0]);
                return true;
            }
        }
        return false;
    }

    private void on(Player player, String[] args) {
        ShopAdsMessage.console.debug("on command");
        if (args.length == 1) {
            if (!ShopAds.playerHandler.playerExists(player.getName())) {
                ShopAds.playerHandler.addPlayer(new ShopAdsPlayer(player.getName(), true, 0));
                return;
            }
            boolean was = ShopAds.playerHandler.getPlayer(player.getName()).getWantsAds();
            if (!ShopAds.config.getSendToAll()) {
                ShopAds.playerHandler.getPlayer(player.getName()).setWantsAds(true);
                if (was) {
                    ShopAdsMessage.command.wasAlreadyReceivingAds(player);
                } else {
                    ShopAdsMessage.command.willReceiveAds(player);
                }
                return;
            }
            ShopAdsMessage.command.sendsToAll(player);
            if (!was) {
                ShopAdsMessage.command.wasntReceivingAds(player);
            } else {
                ShopAdsMessage.command.willNotReceiveAds(player);
            }
            return;
        }
        ShopAdsMessage.commandUsage.incorrectUsage(player);
        ShopAdsMessage.commandUsage.onCommand(player);
    }

    private void off(Player player, String[] args) {
        ShopAdsMessage.console.debug("off command");
        if (args.length == 1) {
            if (!ShopAds.playerHandler.playerExists(player.getName())) {
                ShopAds.playerHandler.addPlayer(new ShopAdsPlayer(player.getName(), false, 0));
                return;
            }
            if (!ShopAds.config.getSendToAll()) {
                ShopAds.playerHandler.getPlayer(player.getName()).setWantsAds(false);
                return;
            }
            ShopAdsMessage.command.sendsToAll(player);
            return;
        }
        ShopAdsMessage.commandUsage.incorrectUsage(player);
        ShopAdsMessage.commandUsage.offCommand(player);
    }

    private void stats(Player player, String[] args) {
        if (args.length == 2) {
            ShopAdsMessage.command.ownedShopsOther(player, args[1]);
        } else if (args.length == 1) {
            ShopAdsMessage.console.debug("stats command");
            ShopAdsMessage.command.ownedShopsSelf(player);
        } else {
            ShopAdsMessage.commandUsage.incorrectUsage(player);
            ShopAdsMessage.commandUsage.statsCommand(player);
        }
    }

    private void delete(Player player, String[] args) {
        ShopAdsMessage.console.debug("delete command");
        if (args.length == 2) {
            String shopToDelete = args[1];
            Shop deleteMe = ShopAds.shopHandler.getShop(shopToDelete);
            if (deleteMe != null) {
                if ((ShopAds.shopHandler.ownsShop(deleteMe, player) && ShopAds.permissions.hasDeleteSelf(player))
                        || (ShopAds.permissions.hasDeleteOther(player))) {
                    ShopAds.shopHandler.removeShop(deleteMe);
                    ShopAdsMessage.command.shopDeleted(player, deleteMe);
                    ShopAds.iO.saveShops();
                    ShopAds.iO.loadShops();
                    ShopAds.playerHandler.getPlayer(deleteMe.getShopOwner()).subtractOwnedShop();
                } else {
                    ShopAdsMessage.error.notYourShop(player, deleteMe);
                }
            } else {
                ShopAdsMessage.error.noShopFound(player, shopToDelete);
            }
        } else {
            ShopAdsMessage.error.noShopEntered(player);
            ShopAdsMessage.commandUsage.incorrectUsage(player);
            ShopAdsMessage.commandUsage.deleteCommand(player);
        }
    }

    private void create(Player player, String[] args) {
        ShopAdsMessage.console.debug("create command");
        ShopAdsMessage.console.debug("args.length = " + args.length);
        if (args.length >= 4) {
            ShopAdsMessage.console.debug("args is the correct size");
            ShopAdsMessage.console.debug("Character at args[2] = " + args[2].charAt(0));
            if (Character.isLetter(args[2].charAt(0))) {
                if (ShopAds.permissions.hasTimeUnlimited(player)) {
                    createShopUnlimited(player, args);
                    return;
                }
            } else if (Character.isDigit(args[2].charAt(0))) {
                if ((ShopAds.playerHandler.getPlayer(player.getName()).getOwnedShops() < ShopAds.config.getShopsPerPlayer())
                        || (ShopAds.permissions.hasCountUnlimited(player))) {
                    createShopWithTime(player, args);
                } else {
                    ShopAdsMessage.error.maxShopsReached(player);
                }
                return;
            }
        }
        ShopAdsMessage.commandUsage.incorrectUsage(player);
        ShopAdsMessage.commandUsage.createCommand(player);
    }

    private void rates(Player player, String[] args) {
        ShopAdsMessage.console.debug("rates command");
        ShopAdsMessage.command.rates(player);
    }

    private void list(Player player, String[] args) {
        ShopAdsMessage.console.debug("list command");
        ShopAdsMessage.command.listAds(player);
    }

    private void config(Player player, String[] args) {
        ShopAdsMessage.console.debug("config command");

        switch (args.length) {
            case 1:
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.configSettings(player);
                break;
            case 2:
                if (args[1].equalsIgnoreCase("save")) {
                    ShopAds.iO.saveConfig();
                    ShopAdsMessage.command.configSaved(player);
                }
                noValue(player, args);
                break;
            case 3:
                changeConfig(player, args);
                break;
        }
    }

    private void reload(Player player, String[] args) {
        ShopAdsMessage.console.debug("reload command");
        ShopAdsMessage.command.reload(player);
        ShopAds.shopads.reload();
    }

    private void disable(Player player, String[] args) {
        ShopAdsMessage.console.debug("disable command");
        ShopAdsMessage.command.disable(player);

        ShopAds.shopads.onDisable();
    }

    private void set(Player player, String[] args) {
        ShopAdsMessage.console.debug("set command");
        if (args.length == 1) {
            ShopAdsMessage.error.noShopEntered(player);
            ShopAdsMessage.commandUsage.incorrectUsage(player);
            ShopAdsMessage.commandUsage.setCommand(player);
        } else if (ShopAds.shopHandler.shopExists(args[1])) {
            Shop shop = ShopAds.shopHandler.getShop(args[1]);
            if ((!shop.getShopOwner().equalsIgnoreCase(player.getName())) &&
                    (!ShopAds.permissions.hasSetOther(player))) {
                ShopAdsMessage.error.notYourShop(player, shop);
            } else if (args.length == 2) {
                ShopAdsMessage.console.debug("show shop settings");
                ShopAdsMessage.setCommand.displayShopSettings(player, shop);
            } else if (args.length >= 4) {
                List<String> values = Arrays.asList(args).subList(3, args.length);
                SetCommand set = new SetCommand(player, shop, args[2], values);
            } else {
                SetCommand set = new SetCommand(player, shop, args[2], new ArrayList<>());
            }
        } else {
            ShopAdsMessage.error.noShopFound(player, args[1]);
        }

    }

    private void createShopWithTime(Player player, String[] args) {
        ShopAdsMessage.console.debug("createShopWithTime started");
        if (ShopAds.shopHandler.shopExists(args[1])) {
            ShopAdsMessage.command.shopNameTaken(player);
            return;
        }
        if (Integer.parseInt(args[2]) <= ShopAds.config.getMaxAdRunTime()) {
            if (!ShopAds.economy.hasEnough(player, Double.parseDouble(args[2]) * ShopAds.config.getAdCost())) {
                ShopAdsMessage.error.insufficientFunds(player, Double.parseDouble(args[2]) * ShopAds.config.getAdCost());
                return;
            }
            String[] worlds = new String[1];
            worlds[0] = player.getWorld().getName();
            Calendar calNow = Calendar.getInstance();
            Date dateNow = calNow.getTime();
            Date timeToEnd = calNow.getTime();
            timeToEnd.setTime(dateNow.getTime() + Long.parseLong(args[2]) * 3600000L);
            StringBuilder ad = new StringBuilder(args[3]);
            if (args.length >= 4) {
                for (int i = 4; i < args.length; i++) {
                    ad.append(" ");
                    ad.append(args[i]);
                }
            }
            Shop newShop = new Shop(args[1], player.getLocation(), player.getName(), timeToEnd, false, player.getWorld(), ad.toString(), worlds, true);
            ShopAds.shopHandler.addShop(newShop);
            ShopAds.economy.chargePlayer(player, Double.parseDouble(args[2]) * ShopAds.config.getAdCost());
            ShopAdsMessage.command.shopCreated(player, newShop);
        } else {
            ShopAdsMessage.error.overMaxRunTime(player, Integer.parseInt(args[2]));
        }
    }

    private void createShopUnlimited(Player player, String[] args) {
        String[] worlds = new String[1];
        worlds[0] = player.getWorld().getName();
        StringBuilder ad = new StringBuilder(args[2]);
        if (args.length > 3) {
            for (int i = 3; i < args.length; i++) {
                ad.append(" ");
                ad.append(args[i]);
            }
        }
        Shop newShop = new Shop(args[1], player.getLocation(), player.getName(), null, true, player.getWorld(), ad.toString(), worlds, true);
        ShopAds.shopHandler.addShop(newShop);
        ShopAdsMessage.command.shopCreated(player, newShop);
    }

    private void noValue(Player player, String[] args) {
        ShopAdsMessage.console.debug("noValue");

        switch (args[1].toLowerCase()) {
            case "announceinterval":
                ShopAdsMessage.commandUsage.announceIntervalUsage(player);
                break;
            case "sendtoall":
                ShopAdsMessage.commandUsage.sendToAllUsage(player);
                break;
            case "enabletp":
                ShopAdsMessage.commandUsage.enableTpUsage(player);
                break;
            case "randomorder":
                ShopAdsMessage.commandUsage.randomOrderUsage(player);
                break;
            case "tpcost":
                ShopAdsMessage.commandUsage.tpCostUsage(player);
                break;
            case "transworldaddition":
                ShopAdsMessage.commandUsage.transWorldAdditionAllUsage(player);
                break;
            case "maxadruntime":
                ShopAdsMessage.commandUsage.maxAdRunTimeUsage(player);
                break;
            case "shopsperplayer":
                ShopAdsMessage.commandUsage.shopsPerPlayerUsage(player);
                break;
            case "adcost":
                ShopAdsMessage.commandUsage.adCostUsage(player);
                break;
            case "tpcostdestination":
                ShopAdsMessage.commandUsage.tpCostDestinationUsage(player);
                break;
            case "announceradius":
                ShopAdsMessage.commandUsage.announceRadiusUsage(player);
                break;
            default:
                ShopAdsMessage.commandUsage.configSettings(player);
                break;
        }
    }

    private void changeConfig(Player player, String[] args) {
        ShopAdsMessage.console.debug("changeConfig");
        String key = args[1];
        String value = args[2];

        switch (key.toLowerCase()) {
            case "announceinterval":
                if (Character.isDigit(value.charAt(0))) {
                    ShopAds.config.setAnnounceInterval(Integer.parseInt(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.announceIntervalUsage(player);
                }
                break;
            case "sendtoall":
                if ((value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("false"))) {
                    ShopAds.config.setSendToAll(Boolean.parseBoolean(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.sendToAllUsage(player);
                }
                break;
            case "enabletp":
                if ((value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("false"))) {
                    ShopAds.config.setEnableTp(Boolean.parseBoolean(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.enableTpUsage(player);
                }
                break;
            case "randomorder":
                if ((value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("false"))) {
                    ShopAds.config.setRandomOrder(Boolean.parseBoolean(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.randomOrderUsage(player);
                }
                break;
            case "tpcost":
                if (Character.isDigit(value.charAt(0))) {
                    ShopAds.config.setTpCost(Double.parseDouble(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.tpCostUsage(player);
                }
                break;
            case "transworldaddition":
                if (Character.isDigit(value.charAt(0))) {
                    ShopAds.config.setTransWorldAddition(Double.parseDouble(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.transWorldAdditionAllUsage(player);
                }
                break;
            case "maxadruntime":
                if (Character.isDigit(value.charAt(0))) {
                    ShopAds.config.setMaxAdRunTime(Integer.parseInt(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.maxAdRunTimeUsage(player);
                }
                break;
            case "shopsperplayer":
                if (Character.isDigit(value.charAt(0))) {
                    ShopAds.config.setShopsPerPlayer(Integer.parseInt(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.shopsPerPlayerUsage(player);
                }
                break;
            case "adcost":
                if (Character.isDigit(value.charAt(0))) {
                    ShopAds.config.setAdCost(Double.parseDouble(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.adCostUsage(player);
                }
                break;
            case "tpcostdestination":
                switch (value.toLowerCase()) {
                    case "player":
                    case "shop":
                    case "owner":
                        ShopAds.config.setTpCostDestination("shop");
                        break;
                    case "server":
                    case "nobody":
                    case "consume":
                        ShopAds.config.setTpCostDestination("server");
                        break;
                    default:
                        ShopAdsMessage.commandUsage.incorrectUsage(player);
                        ShopAdsMessage.commandUsage.tpCostDestinationUsage(player);
                        break;
                }
                break;
            case "announceradius":
                if (Character.isDigit(value.charAt(0))) {
                    ShopAds.config.setAnnounceRadius(Integer.parseInt(value));
                } else {
                    ShopAdsMessage.commandUsage.incorrectUsage(player);
                    ShopAdsMessage.commandUsage.announceRadiusUsage(player);
                }
                break;
        }
    }

    private char setCommandParser(String[] args) {
        ShopAdsMessage.console.debug("setCommandParser");

        char action;

        switch (args[2].toLowerCase()) {
            case "location":
            case "loc":
            case "l":
                action = 'l';
                break;
            case "name":
            case "n":
                action = 'n';
                break;
            case "advertisement":
            case "ad":
            case "a":
                action = 'a';
                break;
            case "world":
            case "w":
                action = 'w';
                break;
            default:
                action = 'z';
                break;
        }

        return action;
    }

    private void teleportToShop(Player player, String shop) {
        if (ShopAds.shopHandler.shopExists(shop)) {
            Shop tpShop = ShopAds.shopHandler.getShop(shop);
            if (ShopAds.economy.hasEnough(player, ShopAds.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()))) {
                if (!tpShop.getShopOwner().equalsIgnoreCase(player.getName())) {
                    ShopAds.economy.chargePlayer(player, ShopAds.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
                    ShopAds.shopHandler.getShop(shop).addMoneyEarned(ShopAds.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
                    try {
                        if ((ShopAds.config.getTpCostDestination().equals("shop")) &&
                                (!tpShop.runsForever())) {
                            ShopAds.economy.payPlayer(tpShop.getShopOwner(), ShopAds.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
                        }
                    } catch (NullPointerException e) {
                        ShopAdsMessage.console.debug("There was an error with config.getTpCostDestination().equals('shop') " + e.getLocalizedMessage());
                    }
                }
                if (ShopAds.config.getTpTimeout() > 0) {
                    ShopAds.playerHandler.playerTeleported(player);
                }
                player.teleport(tpShop.getLocation().getLocation());

                for (Player p : ShopAds.server.getOnlinePlayers()) {
                    if (p.getName().equalsIgnoreCase(tpShop.getShopOwner())) {
                        ShopAds.message.playerTeleportedToYourShop(p, player, tpShop);
                    }
                }
                ShopAds.shopHandler.getShop(shop).shopTeleportedTo();
            } else {
                ShopAdsMessage.error.insufficientFunds(player, ShopAds.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
            }
        } else {
            ShopAdsMessage.error.noShopFound(player, shop);
        }
    }
}