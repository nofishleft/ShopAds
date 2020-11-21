package nz.rishaan.shopads.Util;

import net.milkbowl.vault.permission.Permission;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.entity.Player;

public class ShopAdsPermissions {
    public Permission permission = null;

    public boolean hasCreatorPermission(Player player) {
        if (this.permission.has(player, "ShopAds.Creator")) {
            ShopAdsMessage.console.debug(player.getDisplayName() + " has creator permission");
            return true;
        }
        return false;
    }

    public boolean hasCreatePermission(Player player) {
        if (hasCreatorPermission(player)) {
            ShopAdsMessage.console.debug(player.getDisplayName() + " has creator permission so has create");
            return true;
        }
        if (hasAdminPermission(player)) {
            ShopAdsMessage.console.debug(player.getDisplayName() + " has admin permission so has create");
            return true;
        }
        return this.permission.has(player, "ShopAds.Creator.create");
    }

    public boolean hasSetPermission(Player player) {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        return this.permission.has(player, "ShopAds.Creator.set.self");
    }

    public boolean hasStatsPermission(Player player) {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        return this.permission.has(player, "ShopAds.Creator.stats.self");
    }

    public boolean hasStatsOtherPermission(Player player) {
        if (hasAdminPermission(player)) {
            return true;
        }
        return this.permission.has(player, "ShopAds.Admin.stats.other");
    }

    public boolean hasAdminDeletePermission(Player player) {
        if (hasAdminPermission(player)) {
            return true;
        }
        return this.permission.has(player, "ShopAds.Admin.delete.other");
    }

    public boolean hasDeleteOwnPermission(Player player) {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        return this.permission.has(player, "ShopAds.Creator.delete.own");
    }

    public boolean hasAdminPermission(Player player) {
        return (this.permission.has(player, "ShopAds.Admin")) || (this.permission.has(player, "ShopAds.*")) || (player.isOp());
    }

    public boolean hasSetOtherPermission(Player player) {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        return this.permission.has(player, "ShopAds.Creator.set.other");
    }
}