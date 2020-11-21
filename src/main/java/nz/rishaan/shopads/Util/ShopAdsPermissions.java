package nz.rishaan.shopads.Util;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;

public class ShopAdsPermissions
{
    public Permission permission = null;

    public boolean hasCreatorPermission(Player player)
    {
        if (this.permission.has(player, "ShopAds.Creator"))
        {
            ShopAdsMessage.console.debug(player.getDisplayName() + " has creator permission");
            return true;
        }
        return false;
    }

    public boolean hasCreatePermission(Player player)
    {
        if (hasCreatorPermission(player))
        {
            ShopAdsMessage.console.debug(player.getDisplayName() + " has creator permission so has create");
            return true;
        }
        if (hasAdminPermission(player))
        {
            ShopAdsMessage.console.debug(player.getDisplayName() + " has admin permission so has create");
            return true;
        }
        if (this.permission.has(player, "ShopAds.Creator.create")) {
            return true;
        }
        return false;
    }

    public boolean hasSetPermission(Player player)
    {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        if (this.permission.has(player, "ShopAds.Creator.set.self")) {
            return true;
        }
        return false;
    }

    public boolean hasStatsPermission(Player player)
    {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        if (this.permission.has(player, "ShopAds.Creator.stats.self")) {
            return true;
        }
        return false;
    }

    public boolean hasStatsOtherPermission(Player player)
    {
        if (hasAdminPermission(player)) {
            return true;
        }
        if (this.permission.has(player, "ShopAds.Admin.stats.other")) {
            return true;
        }
        return false;
    }

    public boolean hasAdminDeletePermission(Player player)
    {
        if (hasAdminPermission(player)) {
            return true;
        }
        if (this.permission.has(player, "ShopAds.Admin.delete.other")) {
            return true;
        }
        return false;
    }

    public boolean hasDeleteOwnPermission(Player player)
    {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        if (this.permission.has(player, "ShopAds.Creator.delete.own")) {
            return true;
        }
        return false;
    }

    public boolean hasAdminPermission(Player player)
    {
        if ((this.permission.has(player, "ShopAds.Admin")) || (this.permission.has(player, "ShopAds.*")) || (player.isOp())) {
            return true;
        }
        return false;
    }

    public boolean hasSetOtherPermission(Player player)
    {
        if (hasCreatorPermission(player)) {
            return true;
        }
        if (hasAdminPermission(player)) {
            return true;
        }
        if (this.permission.has(player, "ShopAds.Creator.set.other")) {
            return true;
        }
        return false;
    }
}