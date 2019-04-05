package nz.rishaan.shopads.Player;

import java.util.Calendar;
import java.util.Date;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;

public class TeleportedPlayer
{
    private Player player;
    private Date tpExpire;
    private Location oldLoc;

    public TeleportedPlayer(Player player, Date endTime)
    {
        this.player = player;

        this.tpExpire = endTime;
        this.oldLoc = player.getLocation();
    }

    public boolean isExpired()
    {
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        if (dateNow.after(this.tpExpire)) {
            return true;
        }
        if (((this.tpExpire.getTime() - dateNow.getTime()) / 1000L < 6L) && ((this.tpExpire.getTime() - dateNow.getTime()) / 1000L > 4L)) {
            ShopAds.shopads.message.tpTimeoutInFiveSecond(this.player);
        } else if (((this.tpExpire.getTime() - dateNow.getTime()) / 1000L < 31L) && ((this.tpExpire.getTime() - dateNow.getTime()) / 1000L > 29L)) {
            ShopAds.shopads.message.tpTimeoutInThirtySecond(this.player);
        }
        return false;
    }

    public Location getOldLoc()
    {
        return this.oldLoc;
    }

    public Date getTpExpire()
    {
        return this.tpExpire;
    }

    public void setTpExpire(Date date)
    {
        this.tpExpire = date;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public void returnPlayer()
    {
        this.player.teleport(this.oldLoc);
        ShopAds.shopads.message.tpTimeout(this.player);
    }
}
