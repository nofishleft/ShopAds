package nz.rishaan.shopads.Threads;

import nz.rishaan.shopads.ShopAds;

public class OneSecondThread
        extends Thread {
    private final ShopAds plugin;
    private final int count = 0;

    public OneSecondThread(ShopAds plugin) {
        this.plugin = plugin;
    }

    public void run() {
        if (ShopAds.config.getTpTimeout() > 0) {
            ShopAds.playerHandler.returnTeleportedPlayers();
        }
    }
}
