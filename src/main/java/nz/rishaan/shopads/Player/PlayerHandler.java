package nz.rishaan.shopads.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;

public class PlayerHandler
{
    public ArrayList<ShopAdsPlayer> players = new ArrayList();
    public ArrayList<TeleportedPlayer> teleportedPlayers;

    public void initializeTeleportedPlayers()
    {
        this.teleportedPlayers = new ArrayList();
    }

    public ArrayList<ShopAdsPlayer> getPlayers()
    {
        return this.players;
    }

    public void setPlayers(ArrayList<ShopAdsPlayer> newPlayers)
    {
        this.players = newPlayers;
    }

    public ShopAdsPlayer getPlayer(String name)
    {
        for (ShopAdsPlayer test : this.players) {
            if (test.getName().equalsIgnoreCase(name)) {
                return test;
            }
        }
        return null;
    }

    public boolean playerExists(String name)
    {
        try
        {
            ShopAdsPlayer p = getPlayer(name);
            if (this.players.contains(p)) {
                return true;
            }
            return false;
        }
        catch (NullPointerException localNullPointerException) {}
        return false;
    }

    public boolean addPlayer(ShopAdsPlayer player)
    {
        if (!this.players.isEmpty())
        {
            ShopAdsMessage.console.debug("players is not empty when adding player");
            if (playerExists(player.getName()))
            {
                ShopAdsMessage.console.debug(player.getName() + " was not added to players.");
                return false;
            }
            this.players.add(player);
            ShopAdsMessage.console.debug(player.getName() + " was added to players.");
            return true;
        }
        ShopAdsMessage.console.debug("players is empty when adding player");
        this.players.add(player);
        ShopAdsMessage.console.debug(player.getName() + " was added to players.");
        return true;
    }

    public boolean removeTeleportedPlayer(TeleportedPlayer p)
    {
        try
        {
            this.teleportedPlayers.remove(p);
            return true;
        }
        catch (Exception localException) {}
        return false;
    }

    public boolean returnTeleportedPlayers()
    {
        if (this.teleportedPlayers == null) {
            return true;
        }
        if (this.teleportedPlayers.isEmpty()) {
            return true;
        }
        for (TeleportedPlayer p : this.teleportedPlayers) {
            if ((p.isExpired()) &&
                    (removeTeleportedPlayer(p)))
            {
                p.getPlayer().teleport(p.getOldLoc());
                ShopAds.shopads.message.tpTimeout(p.getPlayer());
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty()
    {
        if (this.players.isEmpty()) {
            return true;
        }
        return false;
    }

    public void updateOwnedShopsFromFile()
    {
        for (ShopAdsPlayer p : getPlayers())
        {
            int count = 0;
            if (ShopAds.shopads.shopHandler.shopsEmpty())
            {
                p.setOwnedShops(count);
            }
            else
            {
                for (Shop s : ShopAds.shopads.shopHandler.getShops()) {
                    if (s.getShopOwner().equalsIgnoreCase(p.getName())) {
                        count++;
                    }
                }
                p.setOwnedShops(count);
            }
        }
    }

    public boolean isNull()
    {
        if (this.players == null) {
            return true;
        }
        return false;
    }

    public ShopAdsPlayer getPlayer(int i)
    {
        return (ShopAdsPlayer)this.players.get(i);
    }

    public void playerTeleported(Player player)
    {
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        Date endTime = calNow.getTime();
        endTime.setTime(dateNow.getTime() + ShopAds.shopads.config.getTpTimeout() * 1000);
        if (ShopAds.shopads.config.getTpTimeout() > 0)
        {
            if (this.teleportedPlayers == null) {
                initializeTeleportedPlayers();
            }
            if (!teleportedPlayersContains(player.getName()))
            {
                this.teleportedPlayers.add(new TeleportedPlayer(player, endTime));
                ShopAds.shopads.message.playerTeleported(player);
            }
            else
            {
                addTeleportTime(getTeleportedPlayer(player));
                ShopAds.shopads.message.playerTeleportExtended(getTeleportedPlayer(player));
            }
        }
    }

    public boolean teleportedPlayersContains(String name)
    {
        if (this.teleportedPlayers == null) {
            return false;
        }
        if (this.teleportedPlayers.isEmpty()) {
            return false;
        }
        for (TeleportedPlayer p : this.teleportedPlayers) {
            if (p.getPlayer().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private TeleportedPlayer getTeleportedPlayer(Player player)
    {
        for (TeleportedPlayer p : this.teleportedPlayers) {
            if (p.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                return p;
            }
        }
        return null;
    }

    private void addTeleportTime(TeleportedPlayer teleportedPlayer)
    {
        Date endTime = teleportedPlayer.getTpExpire();
        endTime.setTime(endTime.getTime() + ShopAds.shopads.config.getTpTimeout() * 1000);
        ((TeleportedPlayer)this.teleportedPlayers.get(this.teleportedPlayers.indexOf(teleportedPlayer))).setTpExpire(endTime);
    }
}