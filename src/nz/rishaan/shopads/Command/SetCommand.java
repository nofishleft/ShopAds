package nz.rishaan.shopads.Command;

import java.util.ArrayList;

import nz.rishaan.shopads.ShopAds;
import org.bukkit.World;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.Util.Messaging.Command.SetCommandMessage;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ErrorMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsEconomy;

public class SetCommand
        extends ShopAdsCommand
{
    private Player player;
    private Shop shop;
    private String key;
    private ArrayList<String> values = new ArrayList();

    public SetCommand(Player player, Shop shop, String key, ArrayList<String> values)
    {
        this.key = key;
        this.values = values;
        this.player = player;
        this.shop = shop;

        executeCommand();
    }

    private void executeCommand()
    {
        if (this.values.isEmpty()) {
            setNoValue();
        } else {
            setValue();
        }
    }

    private void setNoValue()
    {
        int shopIndex = ShopAds.shopads.shopHandler.getShopIndex(this.shop);
        if ((this.key.equalsIgnoreCase("location")) || (this.key.equalsIgnoreCase("loc")) || (this.key.equalsIgnoreCase("l")))
        {
            if (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetLocationCost()))
            {
                ShopAds.shopads.economy.chargePlayer(this.player, ShopAds.shopads.config.getSetLocationCost());
                ShopAds.shopads.shopHandler.setShopLocation(shopIndex, this.player.getLocation(), this.player);
                ShopAdsMessage.setCommand.shopLocationChanged(this.player);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.shopads.config.getSetLocationCost());
        }
        if ((this.key.equalsIgnoreCase("name")) || (this.key.equalsIgnoreCase("n")))
        {
            ShopAdsMessage.error.noNameEntered(this.player);
            return;
        }
        if ((this.key.equalsIgnoreCase("advertisement")) || (this.key.equalsIgnoreCase("ad")) || (this.key.equalsIgnoreCase("a")))
        {
            ShopAdsMessage.error.noAdEntered(this.player);
            return;
        }
        if ((this.key.equalsIgnoreCase("aworld")) || (this.key.equalsIgnoreCase("aw")))
        {
            if (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetWorldCost()))
            {
                ShopAds.shopads.economy.chargePlayer(this.player, ShopAds.shopads.config.getSetWorldCost());
                ShopAds.shopads.shopHandler.addShopWorld(shopIndex, this.player.getLocation(), this.player);
                World[] arrayOfWorld;
                int j = (arrayOfWorld = ShopAds.shopads.shopHandler.getShop(shopIndex).getWorldsToAdvertiseIn()).length;
                for (int i = 0; i < j; i++)
                {
                    World w = arrayOfWorld[i];
                    ShopAdsMessage.console.debug(w.getName());
                }
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.shopads.config.getSetWorldCost());
        }
        if ((this.key.equalsIgnoreCase("rworld")) || (this.key.equalsIgnoreCase("rw")))
        {
            ShopAds.shopads.shopHandler.removeShopWorld(shopIndex, this.player.getLocation(), this.player);
            ShopAdsMessage.setCommand.shopWorldsChanged(this.player, null, this.player.getWorld().getName());
            return;
        }
        if ((this.key.equalsIgnoreCase("shopcolor")) || (this.key.equalsIgnoreCase("scolor")) || (this.key.equalsIgnoreCase("sc")))
        {
            ShopAdsMessage.error.noColorEntered(this.player);
            return;
        }
        if ((this.key.equalsIgnoreCase("adcolor")) || (this.key.equalsIgnoreCase("acolor")) || (this.key.equalsIgnoreCase("ac")))
        {
            ShopAdsMessage.error.noColorEntered(this.player);
            return;
        }
    }

    private void setValue()
    {
        int shopIndex = ShopAds.shopads.shopHandler.getShopIndex(this.shop);
        if ((this.key.equalsIgnoreCase("location")) || (this.key.equalsIgnoreCase("loc")) || (this.key.equalsIgnoreCase("l")))
        {
            if (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetLocationCost()))
            {
                ShopAds.shopads.economy.chargePlayer(this.player, ShopAds.shopads.config.getSetLocationCost());
                ShopAds.shopads.shopHandler.setShopLocation(shopIndex, this.player.getLocation(), this.player);
                ShopAdsMessage.error.inputIgnored(this.player, (String)this.values.get(0));
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.shopads.config.getSetLocationCost());
        }
        if ((this.key.equalsIgnoreCase("name")) || (this.key.equalsIgnoreCase("n")))
        {
            if (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetNameCost()))
            {
                ShopAds.shopads.economy.chargePlayer(this.player, ShopAds.shopads.config.getSetNameCost());
                ShopAds.shopads.shopHandler.setShopName(shopIndex, (String)this.values.get(0), this.player);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.shopads.config.getSetNameCost());
        }
        if ((this.key.equalsIgnoreCase("advertisement")) || (this.key.equalsIgnoreCase("ad")) || (this.key.equalsIgnoreCase("a")))
        {
            if (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetAdCost()))
            {
                ShopAds.shopads.economy.chargePlayer(this.player, ShopAds.shopads.config.getSetAdCost());
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < this.values.size() - 1; i++)
                {
                    sb.append((String)this.values.get(i));
                    sb.append(" ");
                }
                sb.append((String)this.values.get(this.values.size() - 1));
                ShopAds.shopads.shopHandler.setShopAdvertisement(shopIndex, sb.toString(), this.player);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.shopads.config.getSetAdCost());
        }
        if ((this.key.equalsIgnoreCase("aworld")) || (this.key.equalsIgnoreCase("aw")))
        {
            if (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetWorldCost()))
            {
                ShopAds.shopads.economy.chargePlayer(this.player, ShopAds.shopads.config.getSetWorldCost());
                ShopAds.shopads.shopHandler.setShopWorld(shopIndex, (String)this.values.get(0));
                ShopAdsMessage.setCommand.shopWorldsChanged(this.player, (String)this.values.get(0), null);
                return;
            }
            ShopAdsMessage.error.insufficientFunds(this.player, ShopAds.shopads.config.getSetWorldCost());
        }
        if ((this.key.equalsIgnoreCase("rworld")) || (this.key.equalsIgnoreCase("rw")))
        {
            ShopAds.shopads.shopHandler.removeShopWorld(shopIndex, (String)this.values.get(0), this.player);
            ShopAdsMessage.setCommand.shopWorldsChanged(this.player, null, (String)this.values.get(0));
            return;
        }
        if (((this.key.equalsIgnoreCase("shopcolor")) || (this.key.equalsIgnoreCase("scolor")) || (this.key.equalsIgnoreCase("sc"))) &&
                (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetShopColorCost())))
        {
            ShopAds.shopads. economy.chargePlayer(this.player, ShopAds.shopads.config.getSetShopColorCost());
            ShopAds.shopads.shopHandler.setShopColor(shopIndex, (String)this.values.get(0));
            return;
        }
        if (((this.key.equalsIgnoreCase("adcolor")) || (this.key.equalsIgnoreCase("acolor")) || (this.key.equalsIgnoreCase("ac"))) &&
                (ShopAds.shopads.economy.hasEnough(this.player, ShopAds.shopads.config.getSetAdColorCost())))
        {
            ShopAds.shopads.economy.chargePlayer(this.player, ShopAds.shopads.config.getSetAdColorCost());
            ShopAds.shopads.shopHandler.setAdColor(shopIndex, (String)this.values.get(0));
            return;
        }
    }
}
