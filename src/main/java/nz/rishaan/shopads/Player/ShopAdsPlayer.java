package nz.rishaan.shopads.Player;

import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.Warning;

import java.io.Serializable;
import java.util.UUID;

public class ShopAdsPlayer
        implements Serializable {
    private String name;
    private UUID id;
    private boolean wantsAds;
    private int ownedShops;
    public static final ShopAdsMessage message = new ShopAdsMessage();

    @Warning(reason = "Uses username instead of uuid")
    public ShopAdsPlayer(String name, boolean wantsAds, int ownedShops) {
        this.name = name;
        this.wantsAds = wantsAds;
        this.ownedShops = ownedShops;
    }

    public ShopAdsPlayer(UUID id, boolean wantsAds, int ownedShops) {
        this.id = id;
        this.wantsAds = wantsAds;
        this.ownedShops = ownedShops;
    }

    public UUID getUUID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnedShops() {
        return this.ownedShops;
    }

    public void setOwnedShops(int ownedShops) {
        this.ownedShops = ownedShops;
    }

    public boolean getWantsAds() {
        return this.wantsAds;
    }

    public void setWantsAds(boolean wantsAds) {
        this.wantsAds = wantsAds;
    }

    public void addOwnedShop() {
        ++ownedShops;
    }

    public void subtractOwnedShop() {
        if (ownedShops > 0)
            --ownedShops;
    }
}