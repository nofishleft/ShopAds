package nz.rishaan.shopads.Player;

import nz.rishaan.shopads.ShopAds;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;

public class TeleportedPlayer {
    private final Player player;
    private Date tpExpire;
    private final Location oldLoc;

    public TeleportedPlayer(Player player, Date endTime) {
        this.player = player;

        this.tpExpire = endTime;
        this.oldLoc = player.getLocation();
    }

    public boolean isExpired() {
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        if (dateNow.after(this.tpExpire)) {
            return true;
        }
        if ((this.tpExpire.getTime() - dateNow.getTime()) / 1000L == 5) {
            ShopAds.message.tpTimeoutInFiveSecond(this.player);
        } else if ((this.tpExpire.getTime() - dateNow.getTime()) / 1000L == 30) {
            ShopAds.message.tpTimeoutInThirtySecond(this.player);
        }
        return false;
    }

    public Location getOldLoc() {
        return this.oldLoc;
    }

    public Date getTpExpire() {
        return this.tpExpire;
    }

    public void setTpExpire(Date date) {
        this.tpExpire = date;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void returnPlayer() {
        this.player.teleport(this.oldLoc);
        ShopAds.message.tpTimeout(this.player);
    }
}
