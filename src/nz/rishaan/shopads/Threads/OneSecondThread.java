package nz.rishaan.shopads.Threads;

import nz.rishaan.shopads.Player.PlayerHandler;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.ShopAdsConfig;

public class OneSecondThread
        extends Thread
{
    private final ShopAds plugin;
    private int count = 0;

    public OneSecondThread(ShopAds plugin)
    {
        this.plugin = plugin;
    }

    public void run()
    {
        if (ShopAds.config.getTpTimeout() > 0) {
            ShopAds.playerHandler.returnTeleportedPlayers();
        }
    }
}
