package nz.rishaan.shopads.Command;


import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;

public class CreateCommand
        extends ShopAdsCommand {
    private final double price;
    private final String shopName;
    private final String advertisement;
    private final int time;
    private final Player player;

    public CreateCommand(double price, String shopName, String advertisement, int time, Player player) {
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

    private void executeCommand() {
        if (this.time == -1) {
            Shop newShop = createShopWithoutTime();
            ShopAds.shopHandler.addShop(newShop);
            ShopAdsMessage.command.shopCreated(this.player, newShop);
        }
        ShopAdsMessage.console.debug("create command time was not -1 or 0");
    }

    private Shop createShopWithoutTime() {
        String[] worlds = new String[1];
        worlds[0] = this.player.getWorld().getName();
        ShopAds.economy.chargePlayer(this.player, this.time * ShopAds.config.getAdCost());
        return new Shop(this.shopName, this.player.getLocation(), this.player.getName(), getEndTime(), false, this.player.getWorld(), this.advertisement, worlds, true);
    }

    private boolean preCreationChecks() {
        if (ShopAds.shopHandler.shopExists(this.shopName)) {
            ShopAdsMessage.command.shopNameTaken(this.player);
            return false;
        }
        if (this.time <= ShopAds.config.getMaxAdRunTime()) {
            if (!ShopAds.economy.hasEnough(this.player, this.time * ShopAds.config.getAdCost())) {
                ShopAdsMessage.error.insufficientFunds(this.player, this.time * ShopAds.config.getAdCost());
                return false;
            }
        } else {
            ShopAdsMessage.error.overMaxRunTime(this.player, this.time);
            return false;
        }
        return true;
    }

    private Date getEndTime() {
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        Date timeToEnd = calNow.getTime();
        timeToEnd.setTime(dateNow.getTime() + this.time * 3600000L);
        return timeToEnd;
    }
}
