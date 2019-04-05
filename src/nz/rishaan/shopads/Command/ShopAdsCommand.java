package nz.rishaan.shopads.Command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Player.PlayerHandler;
import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.Shop.AdLocation;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.Command.CommandMessage;
import nz.rishaan.shopads.Util.Messaging.Command.CommandUsageMessage;
import nz.rishaan.shopads.Util.Messaging.Command.SetCommandMessage;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ErrorMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsEconomy;
import nz.rishaan.shopads.Util.ShopAdsIO;
import nz.rishaan.shopads.Util.ShopAdsPermissions;

public class ShopAdsCommand
        implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        ShopAdsMessage.console.debug("Command caught");
        if ((sender instanceof Player))
        {
            if (playerCommand((Player)sender, cmd, commandLabel, args)) {
                return true;
            }
        }
        else if (consoleCommand(cmd, commandLabel, args)) {
            return true;
        }
        return false;
    }

    private boolean consoleCommand(Command cmd, String commandLabel, String[] args)
    {
        ShopAdsMessage.console.customMessage("Console commands are not yet implemented");
        return false;
    }

    private boolean playerCommand(Player player, Command cmd, String commandLabel, String[] args)
    {
        ShopAdsMessage.console.debug("playerCommand");
        if ((commandLabel.equalsIgnoreCase("ad")) || (commandLabel.equalsIgnoreCase("ads")))
        {
            ShopAdsMessage.console.debug("ad command");
            if (args.length > 0)
            {
                if (args[0].equalsIgnoreCase("on"))
                {
                    on(player, args);
                    return true;
                }
                if (args[0].equalsIgnoreCase("off"))
                {
                    off(player, args);
                    return true;
                }
                if ((args[0].equalsIgnoreCase("stat")) || (args[0].equalsIgnoreCase("stats")))
                {
                    if (!ShopAds.shopads.permissions.hasStatsPermission(player))
                    {
                        ShopAdsMessage.error.noCommandPermission(player, "STATS");
                        return true;
                    }
                    stats(player, args);
                    return true;
                }
                if ((args[0].equalsIgnoreCase("del")) || (args[0].equalsIgnoreCase("delete")))
                {
                    if (!ShopAds.shopads.permissions.hasDeleteOwnPermission(player))
                    {
                        ShopAdsMessage.error.noCommandPermission(player, "DELETE");
                        return true;
                    }
                    delete(player, args);
                    return true;
                }
                if ((args[0].equalsIgnoreCase("c")) || (args[0].equalsIgnoreCase("create")))
                {
                    if (!ShopAds.shopads.permissions.hasCreatePermission(player))
                    {
                        ShopAdsMessage.error.noCommandPermission(player, "CREATE");
                        return true;
                    }
                    create(player, args);
                    return true;
                }
                if ((args[0].equalsIgnoreCase("rate")) || (args[0].equalsIgnoreCase("rates")))
                {
                    rates(player, args);
                    return true;
                }
                if (args[0].equalsIgnoreCase("list"))
                {
                    list(player, args);
                    return true;
                }
                if ((args[0].equalsIgnoreCase("rel")) || (args[0].equalsIgnoreCase("reload")))
                {
                    if (!ShopAds.shopads.permissions.hasAdminPermission(player))
                    {
                        ShopAdsMessage.error.noCommandPermission(player, "RELOAD");
                        return true;
                    }
                    reload(player, args);
                    return true;
                }
                if (args[0].equalsIgnoreCase("config"))
                {
                    if (!ShopAds.shopads.permissions.hasAdminPermission(player))
                    {
                        ShopAdsMessage.error.noCommandPermission(player, "CONFIG");
                        return true;
                    }
                    config(player, args);
                    return true;
                }
                if (args[0].equalsIgnoreCase("disable"))
                {
                    if (!ShopAds.shopads.permissions.hasAdminPermission(player))
                    {
                        ShopAdsMessage.error.noCommandPermission(player, "DISABLE");
                        return true;
                    }
                    disable(player, args);
                    return true;
                }
                if (args[0].equalsIgnoreCase("set"))
                {
                    if (!ShopAds.shopads.permissions.hasSetPermission(player))
                    {
                        ShopAdsMessage.error.noCommandPermission(player, "SET");
                        return true;
                    }
                    set(player, args);
                    return true;
                }
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.fullCommandMenu(player);
                return true;
            }
            ShopAdsMessage.commandUsage.fullCommandMenu(player);
        }
        if (commandLabel.equalsIgnoreCase("shops")) {
            if (args.length == 0) {
                ShopAdsMessage.command.listShops(player);
            } else if (args.length == 1) {
                teleportToShop(player, args[0]);
            }
        }
        return false;
    }

    private void on(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("on command");
        if (args.length == 1)
        {
            if (!ShopAds.shopads.playerHandler.playerExists(player.getName()))
            {
                ShopAds.shopads.playerHandler.addPlayer(new ShopAdsPlayer(player.getName(), true, 0));
                return;
            }
            boolean was = ShopAds.shopads.playerHandler.getPlayer(player.getName()).getWantsAds();
            if (!ShopAds.shopads.config.getSendToAll())
            {
                ShopAds.shopads.playerHandler.getPlayer(player.getName()).setWantsAds(true);
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

    private void off(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("off command");
        if (args.length == 1)
        {
            if (!ShopAds.shopads.playerHandler.playerExists(player.getName()))
            {
                ShopAds.shopads.playerHandler.addPlayer(new ShopAdsPlayer(player.getName(), false, 0));
                return;
            }
            if (!ShopAds.shopads.config.getSendToAll())
            {
                ShopAds.shopads.playerHandler.getPlayer(player.getName()).setWantsAds(false);
                return;
            }
            ShopAdsMessage.command.sendsToAll(player);
            return;
        }
        ShopAdsMessage.commandUsage.incorrectUsage(player);
        ShopAdsMessage.commandUsage.offCommand(player);
    }

    private void stats(Player player, String[] args)
    {
        if (args.length == 2)
        {
            ShopAdsMessage.command.ownedShopsOther(player, args[1]);
            return;
        }
        if (args.length == 1)
        {
            ShopAdsMessage.console.debug("stats command");
            ShopAdsMessage.command.ownedShopsSelf(player);
            return;
        }
        ShopAdsMessage.commandUsage.incorrectUsage(player);
        ShopAdsMessage.commandUsage.statsCommand(player);
    }

    private void delete(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("delete command");
        if (args.length == 2)
        {
            String shopToDelete = args[1];
            if (ShopAds.shopads.shopHandler.shopExists(shopToDelete))
            {
                Shop deleteMe = ShopAds.shopads.shopHandler.getShop(shopToDelete);
                if ((ShopAds.shopads.shopHandler.ownsShop(deleteMe, player)) || (ShopAds.shopads.permissions.hasAdminDeletePermission(player)))
                {
                    ShopAds.shopads.shopHandler.removeShop(deleteMe);
                    ShopAdsMessage.command.shopDeleted(player, deleteMe);
                    ShopAds.shopads.iO.saveShops();
                    ShopAds.shopads.iO.loadShops();
                    ShopAds.shopads.playerHandler.getPlayer(deleteMe.getShopOwner()).subtractOwnedShop();
                }
            }
            else
            {
                ShopAdsMessage.error.noShopFound(player, shopToDelete);
                return;
            }
        }
        ShopAdsMessage.error.noShopEntered(player);
        ShopAdsMessage.commandUsage.incorrectUsage(player);
        ShopAdsMessage.commandUsage.deleteCommand(player);
    }

    private void create(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("create command");
        ShopAdsMessage.console.debug("args.length = " + args.length);
        if (args.length >= 3)
        {
            ShopAdsMessage.console.debug("args is the correct size");
            ShopAdsMessage.console.debug("Character at args[2] = " + args[2].charAt(0));
            if (Character.isLetter(args[2].charAt(0)))
            {
                if (ShopAds.shopads.permissions.hasAdminPermission(player)) {
                    createShopUnlimited(player, args);
                }
            }
            else if (Character.isDigit(args[2].charAt(0)))
            {
                if ((ShopAds.shopads.playerHandler.getPlayer(player.getName()).getOwnedShops() < ShopAds.shopads.config.getShopsPerPlayer()) || (ShopAds.shopads.permissions.hasAdminPermission(player))) {
                    createShopWithTime(player, args);
                }
                return;
            }
        }
        ShopAdsMessage.commandUsage.incorrectUsage(player);
        ShopAdsMessage.commandUsage.createCommand(player);
    }

    private void rates(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("rates command");
        ShopAdsMessage.command.rates(player);
    }

    private void list(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("list command");
        ShopAdsMessage.command.listAds(player);
    }

    private void config(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("config command");
        if (args.length == 1)
        {
            ShopAdsMessage.commandUsage.incorrectUsage(player);
            ShopAdsMessage.commandUsage.configSettings(player);
            return;
        }
        if (args.length == 2)
        {
            if (args[1].equalsIgnoreCase("save"))
            {
                ShopAds.shopads.iO.saveConfig();
                ShopAdsMessage.command.configSaved(player);
            }
            noValue(player, args);
            return;
        }
        if (args.length == 3)
        {
            changeConfig(player, args);
            return;
        }
    }

    private void reload(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("reload command");
        ShopAdsMessage.command.reload(player);
        ShopAds.shopads.reload();
    }

    private void disable(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("disable command");
        ShopAdsMessage.command.disable(player);

        ShopAds.shopads.onDisable();
    }

    private void set(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("set command");
        if (args.length == 1)
        {
            ShopAdsMessage.error.noShopEntered(player);
            ShopAdsMessage.commandUsage.incorrectUsage(player);
            ShopAdsMessage.commandUsage.setCommand(player);
            return;
        }
        if (ShopAds.shopads.shopHandler.shopExists(args[1]))
        {
            Shop shop = ShopAds.shopads.shopHandler.getShop(args[1]);
            if ((!shop.getShopOwner().equalsIgnoreCase(player.getName())) &&
                    (!ShopAds.shopads.permissions.hasSetOtherPermission(player)))
            {
                ShopAdsMessage.error.notYourShop(player, shop);
                return;
            }
            if (args.length == 2)
            {
                ShopAdsMessage.console.debug("show shop settings");
                ShopAdsMessage.setCommand.displayShopSettings(player, shop);
                return;
            }
            ArrayList<String> values = new ArrayList();
            if (args.length >= 4) {
                for (int i = 0; i < args.length - 3; i++) {
                    values.add(args[(i + 3)]);
                }
            }
            SetCommand set = new SetCommand(player, shop, args[2], values);
            return;
        }
        ShopAdsMessage.error.noShopFound(player, args[1]);
    }

    private void createShopWithTime(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("createShopWithTime started");
        if (ShopAds.shopads.shopHandler.shopExists(args[1]))
        {
            ShopAdsMessage.command.shopNameTaken(player);
            return;
        }
        if (Integer.parseInt(args[2]) <= ShopAds.shopads.config.getMaxAdRunTime())
        {
            if (!ShopAds.shopads.economy.hasEnough(player, Double.parseDouble(args[2]) * ShopAds.shopads.config.getAdCost()))
            {
                ShopAdsMessage.error.insufficientFunds(player, Double.parseDouble(args[2]) * ShopAds.shopads.config.getAdCost());
                return;
            }
            String[] worlds = new String[1];
            worlds[0] = player.getWorld().getName();
            Calendar calNow = Calendar.getInstance();
            Date dateNow = calNow.getTime();
            Date timeToEnd = calNow.getTime();
            timeToEnd.setTime(dateNow.getTime() + Long.parseLong(args[2]) * 3600000L);
            StringBuilder ad = new StringBuilder(args[3]);
            if (args.length > 4) {
                for (int i = 4; i < args.length; i++)
                {
                    ad.append(" ");
                    ad.append(args[i]);
                }
            }
            Shop newShop = new Shop(args[1], player.getLocation(), player.getName(), timeToEnd, false, player.getWorld(), ad.toString(), worlds, true);
            ShopAds.shopads.shopHandler.addShop(newShop);
            ShopAds.shopads.economy.chargePlayer(player, Double.parseDouble(args[2]) * ShopAds.shopads.config.getAdCost());
            ShopAdsMessage.command.shopCreated(player, newShop);
        }
        else
        {
            ShopAdsMessage.error.overMaxRunTime(player, Integer.parseInt(args[2]));
        }
    }

    private void createShopUnlimited(Player player, String[] args)
    {
        String[] worlds = new String[1];
        worlds[0] = player.getWorld().getName();
        StringBuilder ad = new StringBuilder(args[2]);
        if (args.length > 3) {
            for (int i = 3; i < args.length; i++)
            {
                ad.append(" ");
                ad.append(args[i]);
            }
        }
        Shop newShop = new Shop(args[1], player.getLocation(), player.getName(), null, true, player.getWorld(), ad.toString(), worlds, true);
        ShopAds.shopads.shopHandler.addShop(newShop);
        ShopAdsMessage.command.shopCreated(player, newShop);
    }

    private void noValue(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("noValue");
        String key = args[1];
        if (key.equalsIgnoreCase("announceInterval")) {
            ShopAdsMessage.commandUsage.announceIntervalUsage(player);
        } else if (key.equalsIgnoreCase("sendToAll")) {
            ShopAdsMessage.commandUsage.sendToAllUsage(player);
        } else if (key.equalsIgnoreCase("enableTp")) {
            ShopAdsMessage.commandUsage.enableTpUsage(player);
        } else if (key.equalsIgnoreCase("randomOrder")) {
            ShopAdsMessage.commandUsage.randomOrderUsage(player);
        } else if (key.equalsIgnoreCase("tpCost")) {
            ShopAdsMessage.commandUsage.tpCostUsage(player);
        } else if (key.equalsIgnoreCase("transWorldAddition")) {
            ShopAdsMessage.commandUsage.transWorldAdditionAllUsage(player);
        } else if (key.equalsIgnoreCase("maxAdRunTime")) {
            ShopAdsMessage.commandUsage.maxAdRunTimeUsage(player);
        } else if (key.equalsIgnoreCase("shopsPerPlayer")) {
            ShopAdsMessage.commandUsage.shopsPerPlayerUsage(player);
        } else if (key.equalsIgnoreCase("adCost")) {
            ShopAdsMessage.commandUsage.adCostUsage(player);
        } else if (key.equalsIgnoreCase("tpCostDestination")) {
            ShopAdsMessage.commandUsage.tpCostDestinationUsage(player);
        } else if (key.equalsIgnoreCase("announceRadius")) {
            ShopAdsMessage.commandUsage.announceRadiusUsage(player);
        } else {
            ShopAdsMessage.commandUsage.configSettings(player);
        }
    }

    private void changeConfig(Player player, String[] args)
    {
        ShopAdsMessage.console.debug("changeConfig");
        String key = args[1];
        String value = args[2];
        if (key.equalsIgnoreCase("announceInterval"))
        {
            if (Character.isDigit(value.charAt(0)))
            {
                ShopAds.shopads.iO.setConfigOption("announceInterval", value, player);
                ShopAds.shopads.config.setAnnounceInterval(Integer.parseInt(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.announceIntervalUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("sendToAll"))
        {
            if ((value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("false")))
            {
                ShopAds.shopads.iO.setConfigOption("sendToAll", value, player);
                ShopAds.shopads.config.setSendToAll(Boolean.parseBoolean(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.sendToAllUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("enableTp"))
        {
            if ((value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("false")))
            {
                ShopAds.shopads.iO.setConfigOption("enableTp", value, player);
                ShopAds.shopads.config.setEnableTp(Boolean.parseBoolean(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.enableTpUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("randomOrder"))
        {
            if ((value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("false")))
            {
                ShopAds.shopads.iO.setConfigOption("randomOrder", value, player);
                ShopAds.shopads.config.setRandomOrder(Boolean.parseBoolean(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.randomOrderUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("tpCost"))
        {
            if (Character.isDigit(value.charAt(0)))
            {
                ShopAds.shopads.iO.setConfigOption("tpCost", value, player);
                ShopAds.shopads.config.setTpCost(Double.parseDouble(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.tpCostUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("transWorldAddition"))
        {
            if (Character.isDigit(value.charAt(0)))
            {
                ShopAds.shopads.iO.setConfigOption("transWorldAddition", value, player);
                ShopAds.shopads.config.setTransWorldAddition(Double.parseDouble(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.transWorldAdditionAllUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("maxAdRunTime"))
        {
            if (Character.isDigit(value.charAt(0)))
            {
                ShopAds.shopads.iO.setConfigOption("maxAdRunTime", value, player);
                ShopAds.shopads.config.setMaxAdRunTime(Integer.parseInt(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.maxAdRunTimeUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("shopsPerPlayer"))
        {
            if (Character.isDigit(value.charAt(0)))
            {
                ShopAds.shopads.iO.setConfigOption("shopsPerPlayer", value, player);
                ShopAds.shopads.config.setShopsPerPlayer(Integer.parseInt(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.shopsPerPlayerUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("adCost"))
        {
            if (Character.isDigit(value.charAt(0)))
            {
                ShopAds.shopads.iO.setConfigOption("adCost", value, player);
                ShopAds.shopads.config.setAdCost(Double.parseDouble(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.adCostUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("tpCostDestination"))
        {
            if ((value.equalsIgnoreCase("player")) || (value.equalsIgnoreCase("shop")) || (value.equalsIgnoreCase("owner")))
            {
                ShopAds.shopads.iO.setConfigOption("tpCostDestination", "shop", player);
                ShopAds.shopads.config.setTpCostDestination("shop");
            }
            else if ((value.equalsIgnoreCase("server")) || (value.equalsIgnoreCase("nobody")) || (value.equalsIgnoreCase("consume")))
            {
                ShopAds.shopads.iO.setConfigOption("tpCostDestination", "server", player);
                ShopAds.shopads.config.setTpCostDestination("server");
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.tpCostDestinationUsage(player);
            }
            return;
        }
        if (key.equalsIgnoreCase("announceRadius"))
        {
            if (Character.isDigit(value.charAt(0)))
            {
                ShopAds.shopads.iO.setConfigOption("announceRadius", value, player);
                ShopAds.shopads.config.setAnnounceRadius(Integer.parseInt(value));
            }
            else
            {
                ShopAdsMessage.commandUsage.incorrectUsage(player);
                ShopAdsMessage.commandUsage.announceRadiusUsage(player);
            }
            return;
        }
    }

    private char setCommandParser(String[] args)
    {
        ShopAdsMessage.console.debug("setCommandParser");
        char action = 'z';
        if ((args[2].equalsIgnoreCase("location")) || (args[2].equalsIgnoreCase("loc")) || (args[2].equalsIgnoreCase("l"))) {
            action = 'l';
        }
        if ((args[2].equalsIgnoreCase("name")) || (args[2].equalsIgnoreCase("n"))) {
            action = 'n';
        }
        if ((args[2].equalsIgnoreCase("advertisement")) || (args[2].equalsIgnoreCase("ad")) || (args[2].equalsIgnoreCase("a"))) {
            action = 'a';
        }
        if ((args[2].equalsIgnoreCase("world")) || (args[2].equalsIgnoreCase("w"))) {
            action = 'w';
        }
        return action;
    }

    private void teleportToShop(Player player, String shop)
    {
        if (ShopAds.shopads.shopHandler.shopExists(shop))
        {
            Shop tpShop = ShopAds.shopads.shopHandler.getShop(shop);
            if (ShopAds.shopads.economy.hasEnough(player, ShopAds.shopads.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation())))
            {
                if (!tpShop.getShopOwner().equalsIgnoreCase(player.getName()))
                {
                    ShopAds.shopads.economy.chargePlayer(player, ShopAds.shopads.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
                    ShopAds.shopads.shopHandler.getShop(shop).addMoneyEarned(ShopAds.shopads.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
                    try
                    {
                        if ((ShopAds.shopads.config.getTpCostDestination().equals("shop")) &&
                                (!tpShop.runsForever())) {
                            ShopAds.shopads.economy.payPlayer(tpShop.getShopOwner(), ShopAds.shopads.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
                        }
                    }
                    catch (NullPointerException e)
                    {
                        ShopAdsMessage.console.debug("There was an error with config.getTpCostDestination().equals('shop') " + e.getLocalizedMessage());
                    }
                }
                if (ShopAds.shopads.config.getTpTimeout() > 0) {
                    ShopAds.shopads.playerHandler.playerTeleported(player);
                }
                player.teleport(tpShop.getLocation().getLocation());
                Player[] arrayOfPlayer;
                int j = (arrayOfPlayer = (Player[]) ShopAds.shopads.server.getOnlinePlayers().toArray()).length;
                for (int i = 0; i < j; i++)
                {
                    Player p = arrayOfPlayer[i];
                    if (p.getName().equalsIgnoreCase(tpShop.getShopOwner())) {
                        ShopAds.shopads.message.playerTeleportedToYourShop(p, player, tpShop);
                    }
                }
                ShopAds.shopads.shopHandler.getShop(shop).shopTeleportedTo();
            }
            else
            {
                ShopAdsMessage.error.insufficientFunds(player, ShopAds.shopads.economy.getTpCharge(player, player.getLocation(), tpShop.getLocation().getLocation()));
            }
        }
        else
        {
            ShopAdsMessage.error.noShopFound(player, shop);
        }
    }
}