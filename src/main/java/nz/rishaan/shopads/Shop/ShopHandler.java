package nz.rishaan.shopads.Shop;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Player.PlayerHandler;
import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ErrorMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsIO;

public class ShopHandler
{
    private ArrayList<Shop> shops = new ArrayList();

    public ArrayList<Shop> getPlayersShops(Player player)
    {
        ArrayList<Shop> playersShops = new ArrayList();
        for (Shop s : this.shops) {
            if (s.getShopOwner().equalsIgnoreCase(player.getName())) {
                playersShops.add(s);
            }
        }
        return playersShops;
    }

    public ArrayList<Shop> getPlayersShops(String player)
    {
        ArrayList<Shop> playersShops = new ArrayList();
        for (Shop s : this.shops) {
            if (s.getShopOwner().equalsIgnoreCase(player)) {
                playersShops.add(s);
            }
        }
        return playersShops;
    }

    public void addShop(Shop shop)
    {
        if (shopExists(shop.getShopName())) {
            return;
        }
        this.shops.add(shop);
        ShopAdsMessage.console.debug(String.valueOf(ShopAds.shopads.playerHandler.playerExists("Debug")));
        if ((!ShopAds.shopads.playerHandler.isEmpty()) &&
                (ShopAds.shopads.playerHandler.playerExists(shop.getShopOwner()))) {
            ShopAds.shopads.playerHandler.getPlayer(shop.getShopOwner()).addOwnedShop();
        }
    }

    public void setShopLocation(int index, Location loc, Player player)
    {
        ((Shop)this.shops.get(index)).setLocation(loc);
    }

    public void setShopAdvertisement(int index, String ad, Player player)
    {
        ((Shop)this.shops.get(index)).setAdvertisement(ad);
    }

    public void setShopName(int index, String name, Player player)
    {
        ((Shop)this.shops.get(index)).setShopName(name);
    }

    public void addShopWorld(int index, Location loc, Player player)
    {
        ((Shop)this.shops.get(index)).addWorldToAdvertiseIn(loc.getWorld());
    }

    public void removeShopWorld(int index, Location loc, Player player)
    {
        boolean found = false;
        String[] arrayOfString1;
        int j = (arrayOfString1 = ((Shop)this.shops.get(index)).getWorldsToAdvertiseInAsString()).length;
        for (int i = 0; i < j; i++)
        {
            String world = arrayOfString1[i];
            if (world.equalsIgnoreCase(loc.getWorld().getName())) {
                found = true;
            }
        }
        if (!found)
        {
            ShopAdsMessage.error.worldNotFoundInShopsList(player);
            return;
        }
        ArrayList<String> newWorlds = new ArrayList();
        String[] arrayOfString2;
        int k = (arrayOfString2 = ((Shop)this.shops.get(index)).getWorldsToAdvertiseInAsString()).length;
        for (j = 0; j < k; j++)
        {
            String world = arrayOfString2[j];
            if (!world.equalsIgnoreCase(loc.getWorld().getName())) {
                newWorlds.add(world);
            }
        }
        ((Shop)this.shops.get(index)).setWorldsToAdvertiseIn((String[])newWorlds.toArray());
    }

    public void removeShopWorld(int index, String w, Player player)
    {
        boolean found = false;
        String[] arrayOfString1;
        int j = (arrayOfString1 = ((Shop)this.shops.get(index)).getWorldsToAdvertiseInAsString()).length;
        for (int i = 0; i < j; i++)
        {
            String world = arrayOfString1[i];
            if (world.equalsIgnoreCase(w)) {
                found = true;
            }
        }
        if (!found)
        {
            ShopAdsMessage.error.worldNotFoundInShopsList(player);
            return;
        }
        ArrayList<String> newWorlds = new ArrayList();
        String[] arrayOfString2;
        int k = (arrayOfString2 = ((Shop)this.shops.get(index)).getWorldsToAdvertiseInAsString()).length;
        for (j = 0; j < k; j++)
        {
            String world = arrayOfString2[j];
            if (!world.equalsIgnoreCase(w)) {
                newWorlds.add(world);
            }
        }
        ((Shop)this.shops.get(index)).setWorldsToAdvertiseIn((String[])newWorlds.toArray());
    }

    public void setShopWorld(int index, String world)
    {
        ((Shop)this.shops.get(index)).addWorldToAdvertiseIn(ShopAds.shopads.server.getWorld(world));
    }

    public void removeShop(Shop shop)
    {
        this.shops.remove(shop);
        ShopAds.shopads.playerHandler.getPlayer(shop.getShopOwner()).subtractOwnedShop();
    }

    public Shop getShop(String name)
    {
        Shop[] temp = new Shop[this.shops.size()];
        this.shops.toArray(temp);
        if ((temp != null) && (temp.length > 0)) {
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].getShopName().equalsIgnoreCase(name)) {
                    return temp[i];
                }
            }
        }
        return null;
    }

    public boolean shopsEmpty()
    {
        return this.shops.isEmpty();
    }

    public boolean shopExists(Shop shop)
    {
        if (this.shops.contains(shop)) {
            return true;
        }
        return false;
    }

    public boolean shopExists(String shop)
    {
        Shop[] temp = new Shop[this.shops.size()];
        this.shops.toArray(temp);
        if ((temp != null) && (temp.length > 0))
        {
            Shop[] arrayOfShop1;
            int j = (arrayOfShop1 = temp).length;
            for (int i = 0; i < j; i++)
            {
                Shop test = arrayOfShop1[i];
                if (test.getShopName().equalsIgnoreCase(shop)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getShopIndex(Shop shop)
    {
        return this.shops.indexOf(shop);
    }

    public void addWorldToShop(Shop shop, String string)
    {
        try
        {
            shop.addWorldToAdvertiseIn(ShopAds.shopads.server.getWorld(string));
        }
        catch (Exception localException) {}
    }

    public boolean ownsShop(Shop shop, Player player)
    {
        if (player.getName().equalsIgnoreCase(shop.getShopOwner())) {
            return true;
        }
        return false;
    }

    public ArrayList<Shop> getShops()
    {
        return this.shops;
    }

    public int getSize()
    {
        return this.shops.size();
    }

    public Shop getShop(int index)
    {
        return (Shop)this.shops.get(index);
    }

    public int getShopIndex(String shopName)
    {
        return this.shops.indexOf(getShop(shopName));
    }

    public void setShopColor(int shopIndex, String color)
    {
        ((Shop)this.shops.get(shopIndex)).setShopColor(color);
    }

    public void setAdColor(int shopIndex, String color)
    {
        ((Shop)this.shops.get(shopIndex)).setAdColor(color);
    }

    public void updateShops()
    {
        if (this.shops.size() > 0) {
            for (Shop s : this.shops) {
                if (s.shopExpired())
                {
                    removeShop(s);
                    ShopAds.shopads.message.shopExpired(s);
                    ShopAds.shopads.iO.saveShops();
                    ShopAds.shopads.iO.loadShops();
                }
            }
        }
    }
}
