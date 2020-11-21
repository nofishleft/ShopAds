package nz.rishaan.shopads.Command;

import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class SetCommand
        extends ShopAdsCommand {
    private final Player player;
    private final Shop shop;
    private final String key;
    private final List<String> values;

    public SetCommand(Player player, Shop shop, String key, List<String> values) {
        this.key = key;
        this.values = values;
        this.player = player;
        this.shop = shop;

        executeCommand();
    }

    private void executeCommand() {
        if (this.values.isEmpty()) {
            setNoValue();
        } else {
            setValue();
        }
    }

    @Warning(reason = "Uses if/else instead of string switch")
    private void setNoValue() {
        int shopIndex = ShopAds.shopHandler.getShopIndex(this.shop);
        if ((this.key.equalsIgnoreCase("location")) || (this.key.equalsIgnoreCase("loc")) || (this.key.equalsIgnoreCase("l"))) {
            if (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetLocationCost())) {
                ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetLocationCost());
                ShopAds.shopHandler.setShopLocation(shopIndex, this.player.getLocation(), this.player);
                ShopAdsMessage.setCommand.shopLocationChanged(this.player);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.config.getSetLocationCost());
        }
        if ((this.key.equalsIgnoreCase("name")) || (this.key.equalsIgnoreCase("n"))) {
            ShopAdsMessage.error.noNameEntered(this.player);
            return;
        }
        if ((this.key.equalsIgnoreCase("advertisement")) || (this.key.equalsIgnoreCase("ad")) || (this.key.equalsIgnoreCase("a"))) {
            ShopAdsMessage.error.noAdEntered(this.player);
            return;
        }
        if ((this.key.equalsIgnoreCase("aworld")) || (this.key.equalsIgnoreCase("aw"))) {
            if (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetWorldCost())) {
                ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetWorldCost());
                ShopAds.shopHandler.addShopWorld(shopIndex, this.player.getLocation(), this.player);
                World[] arrayOfWorld;
                int j = (arrayOfWorld = ShopAds.shopHandler.getShop(shopIndex).getWorldsToAdvertiseIn()).length;
                for (int i = 0; i < j; i++) {
                    World w = arrayOfWorld[i];
                    ShopAdsMessage.console.debug(w.getName());
                }
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.config.getSetWorldCost());
        }
        if ((this.key.equalsIgnoreCase("rworld")) || (this.key.equalsIgnoreCase("rw"))) {
            ShopAds.shopHandler.removeShopWorld(shopIndex, this.player.getLocation(), this.player);
            ShopAdsMessage.setCommand.shopWorldsChanged(this.player, null, this.player.getWorld().getName());
            return;
        }
        if ((this.key.equalsIgnoreCase("shopcolor")) || (this.key.equalsIgnoreCase("scolor")) || (this.key.equalsIgnoreCase("sc"))) {
            ShopAdsMessage.error.noColorEntered(this.player);
            return;
        }
        if ((this.key.equalsIgnoreCase("adcolor")) || (this.key.equalsIgnoreCase("acolor")) || (this.key.equalsIgnoreCase("ac"))) {
            ShopAdsMessage.error.noColorEntered(this.player);
            return;
        }
    }

    @Warning(reason = "Uses if/else instead of string switch")
    private void setValue() {
        int shopIndex = ShopAds.shopHandler.getShopIndex(this.shop);
        if ((this.key.equalsIgnoreCase("location")) || (this.key.equalsIgnoreCase("loc")) || (this.key.equalsIgnoreCase("l"))) {
            if (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetLocationCost())) {
                ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetLocationCost());
                ShopAds.shopHandler.setShopLocation(shopIndex, this.player.getLocation(), this.player);
                ShopAdsMessage.error.inputIgnored(this.player, this.values.get(0));
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.config.getSetLocationCost());
        }
        if ((this.key.equalsIgnoreCase("name")) || (this.key.equalsIgnoreCase("n"))) {
            if (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetNameCost())) {
                ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetNameCost());
                ShopAds.shopHandler.setShopName(shopIndex, this.values.get(0), this.player);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.config.getSetNameCost());
        }
        if ((this.key.equalsIgnoreCase("advertisement")) || (this.key.equalsIgnoreCase("ad")) || (this.key.equalsIgnoreCase("a"))) {
            if (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetAdCost())) {
                ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetAdCost());
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < this.values.size() - 1; i++) {
                    sb.append(this.values.get(i));
                    sb.append(" ");
                }
                sb.append(this.values.get(this.values.size() - 1));
                ShopAds.shopHandler.setShopAdvertisement(shopIndex, sb.toString(), this.player);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.config.getSetAdCost());
        }
        if ((this.key.equalsIgnoreCase("aworld")) || (this.key.equalsIgnoreCase("aw"))) {
            if (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetWorldCost())) {
                ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetWorldCost());
                ShopAds.shopHandler.setShopWorld(shopIndex, this.values.get(0));
                ShopAdsMessage.setCommand.shopWorldsChanged(this.player, this.values.get(0), null);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.config.getSetWorldCost());
        }
        if ((this.key.equalsIgnoreCase("rworld")) || (this.key.equalsIgnoreCase("rw"))) {
            ShopAds.shopHandler.removeShopWorld(shopIndex, this.values.get(0), this.player);
            ShopAdsMessage.setCommand.shopWorldsChanged(this.player, null, this.values.get(0));
            return;
        }
        if (((this.key.equalsIgnoreCase("shopcolor")) || (this.key.equalsIgnoreCase("scolor")) || (this.key.equalsIgnoreCase("sc"))) &&
                (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetShopColorCost()))) {
            ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetShopColorCost());
            ShopAds.shopHandler.setShopColor(shopIndex, this.values.get(0));
            return;
        }
        if (((this.key.equalsIgnoreCase("adcolor")) || (this.key.equalsIgnoreCase("acolor")) || (this.key.equalsIgnoreCase("ac"))) &&
                (ShopAds.economy.hasEnough(this.player, ShopAds.config.getSetAdColorCost()))) {
            ShopAds.economy.chargePlayer(this.player, ShopAds.config.getSetAdColorCost());
            ShopAds.shopHandler.setAdColor(shopIndex, this.values.get(0));
            return;
        }
    }
}
