package nz.rishaan.shopads.Player;

import com.sun.istack.internal.NotNull;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class PlayerHandler {
    public ArrayList<ShopAdsPlayer> players = new ArrayList<>();
    public ArrayList<TeleportedPlayer> teleportedPlayers;

    public void initializeTeleportedPlayers() {
        this.teleportedPlayers = new ArrayList<>();
    }

    public ArrayList<ShopAdsPlayer> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<ShopAdsPlayer> newPlayers) {
        this.players = newPlayers;
    }

    public final Object tpLock = new Object();

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        forceReturnTeleportedPlayer(event.getPlayer());
    }

    @Warning(reason = "Uses username instead of uuid")
    public ShopAdsPlayer getPlayer(String name) {
        for (ShopAdsPlayer test : this.players) {
            if (test.getName().equalsIgnoreCase(name)) {
                return test;
            }
        }
        return null;
    }

    public ShopAdsPlayer getOnlinePlayer(String name) {
        Player p = Bukkit.getServer().getPlayer(name);

        if (p == null) return null;

        return getPlayer(p.getUniqueId());
    }

    public ShopAdsPlayer getPlayer(UUID id) {
        for (ShopAdsPlayer p : this.players) {
            if (p.getUUID() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * @deprecated
     * This method uses username instead of uuid to match players.
     * This also iterates through the player list twice needlessly
     */
    @Deprecated
    public boolean playerExists(String name) {
        try {
            ShopAdsPlayer p = getPlayer(name);
            return this.players.contains(p);
        } catch (NullPointerException ignored) {
        }
        return false;
    }

    public boolean addPlayer(ShopAdsPlayer player) {
        if (!this.players.isEmpty()) {
            ShopAdsMessage.console.debug("players is not empty when adding player");
            if (playerExists(player.getName())) {
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

    public boolean removeTeleportedPlayer(TeleportedPlayer p) {
        synchronized (tpLock) {
            try {
                this.teleportedPlayers.remove(p);
                return true;
            } catch (Exception ignored) {
            }
            return false;
        }
    }

    public boolean returnTeleportedPlayers() {
        synchronized (tpLock) {
            if (this.teleportedPlayers == null) {
                return true;
            }
            if (this.teleportedPlayers.isEmpty()) {
                return true;
            }
            for (TeleportedPlayer p : this.teleportedPlayers) {
                if ((p.isExpired()) &&
                        (removeTeleportedPlayer(p))) {
                    p.getPlayer().teleport(p.getOldLoc());
                    ShopAds.message.tpTimeout(p.getPlayer());
                    return true;
                }
            }
            return false;
        }
    }

    public void forceReturnTeleportedPlayers() {
        synchronized (tpLock) {
            if (this.teleportedPlayers == null) {
                return;
            }
            if (this.teleportedPlayers.isEmpty()) {
                return;
            }
            for (TeleportedPlayer p : this.teleportedPlayers) {
                if (removeTeleportedPlayer(p)) {
                    p.getPlayer().teleport(p.getOldLoc());
                    ShopAds.message.tpTimeout(p.getPlayer());
                }
            }
        }
    }

    public void forceReturnTeleportedPlayer(@NotNull Player p) {
        synchronized (tpLock) {
            TeleportedPlayer tp = getTeleportedPlayer(p);

            if (tp != null)
                forceReturnTeleportedPlayer(p, tp);
        }
    }

    public void forceReturnTeleportedPlayer(@NotNull TeleportedPlayer tp) {
        synchronized (tpLock) {
            Player p = tp.getPlayer();

            if (p != null)
                forceReturnTeleportedPlayer(p, tp);
        }
    }

    public void forceReturnTeleportedPlayer(@NotNull Player p, @NotNull TeleportedPlayer tp) {
        synchronized (tpLock) {
            if (removeTeleportedPlayer(tp)) {
                p.teleport(tp.getOldLoc());
            }
        }
    }

    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    public void updateOwnedShopsFromFile() {
        for (ShopAdsPlayer p : getPlayers()) {
            int count = 0;
            if (ShopAds.shopHandler.shopsEmpty()) {
                p.setOwnedShops(count);
            } else {
                for (Shop s : ShopAds.shopHandler.getShops()) {
                    if (s.getShopOwner().equalsIgnoreCase(p.getName())) {
                        count++;
                    }
                }
                p.setOwnedShops(count);
            }
        }
    }

    public boolean isNull() {
        return this.players == null;
    }

    public ShopAdsPlayer getPlayer(int i) {
        return this.players.get(i);
    }

    public void playerTeleported(Player player) {
        Calendar calNow = Calendar.getInstance();
        Date dateNow = calNow.getTime();
        Date endTime = calNow.getTime();
        endTime.setTime(dateNow.getTime() + ShopAds.config.getTpTimeout() * 1000);
        if (ShopAds.config.getTpTimeout() > 0) {
            synchronized (tpLock) {
                if (this.teleportedPlayers == null) {
                    initializeTeleportedPlayers();
                }
                if (!teleportedPlayersContains(player.getName())) {
                    this.teleportedPlayers.add(new TeleportedPlayer(player, endTime));
                    ShopAds.message.playerTeleported(player);
                } else {
                    addTeleportTime(getTeleportedPlayer(player));
                    ShopAds.message.playerTeleportExtended(getTeleportedPlayer(player));
                }
            }
        }
    }

    @Warning(reason = "Uses username instead of uuid")
    public boolean teleportedPlayersContains(String name) {
        synchronized (tpLock) {
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
    }

    private TeleportedPlayer getTeleportedPlayer(Player player) {
        synchronized (tpLock) {
            for (TeleportedPlayer p : this.teleportedPlayers) {
                if (p.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                    return p;
                }
            }
            return null;
        }
    }

    private void addTeleportTime(TeleportedPlayer teleportedPlayer) {
        synchronized (tpLock) {
            Date endTime = teleportedPlayer.getTpExpire();
            endTime.setTime(endTime.getTime() + ShopAds.config.getTpTimeout() * 1000);
            this.teleportedPlayers.get(this.teleportedPlayers.indexOf(teleportedPlayer)).setTpExpire(endTime);
        }
    }
}