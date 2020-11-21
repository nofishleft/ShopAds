package nz.rishaan.shopads.Player;

import java.io.Serializable;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;

public class ShopAdsPlayer
        implements Serializable
{
    private String name;
    private boolean wantsAds;
    private int ownedShops;
    public static final ShopAdsMessage message = new ShopAdsMessage();

    public ShopAdsPlayer(String name, boolean wantsAds, int ownedShops)
    {
        this.name = name;
        this.wantsAds = wantsAds;
        this.ownedShops = ownedShops;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOwnedShops()
    {
        return this.ownedShops;
    }

    public void setOwnedShops(int ownedShops)
    {
        this.ownedShops = ownedShops;
    }

    public boolean getWantsAds()
    {
        return this.wantsAds;
    }

    public void setWantsAds(boolean wantsAds)
    {
        this.wantsAds = wantsAds;
    }

    public void addOwnedShop()
    {
        this.ownedShops += 1;
    }

    public void subtractOwnedShop()
    {
        if (this.ownedShops == 1)
        {
            this.ownedShops = 0;
        }
        else
        {
            if (this.ownedShops == 0) {
                return;
            }
            this.ownedShops -= 1;
        }
    }
}