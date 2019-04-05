package nz.rishaan.shopads.Command;


import java.util.Calendar;
import java.util.Date;

import nz.rishaan.shopads.ShopAds;
import org.bukkit.World;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.Util.Messaging.Command.CommandMessage;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ErrorMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsEconomy;

public class CreateCommand
        extends ShopAdsCommand
{
    private double price;
    private String shopName;
    private String advertisement;
    private int time;
    private Player player;

    public CreateCommand(double price, String shopName, String advertisement, int time, Player player)
    {
        this.price = price;
        this.shopName = shopName;
        this.advertisement = advertisement;
        this.time = time;
        this.player = player;
        if (!preCreationChecks()) {
            return;
        }
        executeCommand();
    }

    private void executeCommand()
    {
        if (this.time == -1)
        {
            Shop newShop = createShopWithoutTime();
            ShopAds.shopads.shopHandler.addShop(newShop);
            ShopAdsMessage.command.shopCreated(this.player, newShop);
        }
        ShopAdsMessage.console.debug("create command time was not -1 or 0");
    }

    private Shop createShopWithoutTime()
    {
        String[] worlds = new String[1];
        worlds[0] = this.player.getWorld().getName();
        ShopAds.shopads.economy.chargePlayer(this.player, this.time * ShopAds.shopads.config.getAdCost());
        return new Shop(this.shopName, this.player.getLocation(), this.player.getName(), getEndTime(), false, this.player.getWorld(), this.advertisement, worlds, true);
    }

    private boolean preCreationChecks()
    {
        if (ShopAds.shopads.shopHandler.shopExists(this.shopName))
        {
            ShopAdsMessage.command.shopNameTaken(this.player);
            return false;
        }
        if (this.time <= ShopAds.shopads.config.getMaxAdRunTime())
        {
            if (!ShopAds.shopads.economy.hasEnough(this.player, this.time * ShopAds.shopads.config.getAdCost()))
            {
                ShopAdsMessage.error.insufficientFunds(this.player, this.time * ShopAds.shopads.config.getAdCost());
                return false;
            }
        }
        else
        {
            ShopAdsMessage.error.overMaxRunTime(this.player, this.time);
            return false;
        }
        return true;
    }

    private Date getEndTime()
    {
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        Date timeToEnd = calNow.getTime();
        timeToEnd.setTime(dateNow.getTime() + this.time * 3600000L);
        return timeToEnd;
    }
}
