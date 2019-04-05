package nz.rishaan.shopads.Threads;

import java.util.Random;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.AdvertisementMessage;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;

public class AnnounceThread
        extends Thread
{
    private final Random randomGenerator;
    private final ShopAds plugin;
    private int lastAnnouncement = 0;
    private int[] announcementOrder = null;
    private int randomIndex;
    private boolean checkedVersion = false;

    public AnnounceThread(ShopAds SA)
    {
        this.randomGenerator = new Random();
        this.plugin = SA;
    }

    public void run()
    {
        ShopAdsMessage.console.announceDebug("Announce thread invoked");
        if (!ShopAds.shopHandler.shopsEmpty())
        {
            ShopAdsMessage.console.announceDebug("Some shops exist");
            if (this.plugin.playersOnline())
            {
                ShopAdsMessage.console.announceDebug("Some players online");
                if (ShopAds.config.getRandomOrder())
                {
                    ShopAdsMessage.console.announceDebug("Random order is on");
                    if (this.announcementOrder == null)
                    {
                        ShopAdsMessage.console.announceDebug("Generating new announcement order");
                        this.announcementOrder = new int[ShopAds.shopHandler.getSize()];
                        for (int i = 0; i < this.announcementOrder.length; i++) {
                            this.announcementOrder[i] = i;
                        }
                        this.randomIndex = 0;
                        for (int i = 0; i < this.announcementOrder.length; i++)
                        {
                            int randomPosition = this.randomGenerator.nextInt(this.announcementOrder.length);
                            int temp = this.announcementOrder[i];
                            this.announcementOrder[i] = this.announcementOrder[randomPosition];
                            this.announcementOrder[randomPosition] = temp;
                        }
                        ShopAdsMessage.console.announceDebug("Announcement order: ");
                        int[] arrayOfInt;
                        int j = (arrayOfInt = this.announcementOrder).length;
                        for (int i = 0; i < j; i++)
                        {
                            int x = arrayOfInt[i];
                            ShopAdsMessage.console.announceDebug("" + x);
                        }
                    }
                    ShopAdsMessage.advertise.advertise(ShopAds.shopHandler.getShop(this.announcementOrder[this.randomIndex]));

                    this.randomIndex += 1;
                    if (this.randomIndex >= this.announcementOrder.length)
                    {
                        this.announcementOrder = null;
                        this.randomIndex = 0;
                    }
                }
                else
                {
                    ShopAdsMessage.console.announceDebug("Random is false");
                    if (this.lastAnnouncement >= ShopAds.shopHandler.getSize()) {
                        this.lastAnnouncement = 0;
                    }
                    if (this.lastAnnouncement < ShopAds.shopHandler.getSize())
                    {
                        ShopAdsMessage.advertise.advertise(ShopAds.shopHandler.getShop(this.lastAnnouncement));
                        this.lastAnnouncement += 1;
                    }
                }
            }
        }
    }
}