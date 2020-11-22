package nz.rishaan.shopads.Util;

import net.milkbowl.vault.permission.Permission;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.entity.Player;

public class ShopAdsPermissions {
    public Permission permission = null;

    public boolean hasCreate(Player player)
    {
        return this.permission.has(player, "shopads.create");
    }

    public boolean hasDeleteSelf(Player player)
    {
        return this.permission.has(player, "shopads.delete.self");
    }

    public boolean hasDeleteOther(Player player)
    {
        return this.permission.has(player, "shopads.delete.other");
    }

    public boolean hasSetSelf(Player player)
    {
        return this.permission.has(player, "shopads.set.self");
    }

    public boolean hasSetOther(Player player)
    {
        return this.permission.has(player, "shopads.set.other");
    }

    public boolean hasStatsSelf(Player player)
    {
        return this.permission.has(player, "shopads.stats.self");
    }

    public boolean hasStatsOther(Player player)
    {
        return this.permission.has(player, "shopads.stats.other");
    }

    public boolean hasTimeUnlimited(Player player)
    {
        return this.permission.has(player, "shopads.time.unlimited");
    }

    public boolean hasCountUnlimited(Player player)
    {
        return this.permission.has(player, "shopads.count.unlimited");
    }

    public boolean hasDisable(Player player)
    {
        return this.permission.has(player, "shopads.disable");
    }

    public boolean hasReload(Player player)
    {
        return this.permission.has(player, "shopads.reload");
    }

    public boolean hasConfig(Player player)
    {
        return this.permission.has(player, "shopads.config");
    }

    public boolean hasAdmin(Player player) {
        return this.permission.has(player, "shopads.admin");
    }
}