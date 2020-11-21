package nz.rishaan.shopads.Util;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;

public class ShopAdsEconomy
{
    public Economy economy = null;

    public boolean playerPayPlayer(Player sender, Player receiver, double amount)
    {
        if (this.economy == null) {
            return true;
        }
        if (this.economy.has(sender.getName(), amount))
        {
            this.economy.withdrawPlayer(sender.getName(), amount);
            this.economy.depositPlayer(receiver.getName(), amount);
            return true;
        }
        return false;
    }

    public boolean payPlayer(Player reciever, double amount)
    {
        if (this.economy == null) {
            return true;
        }
        this.economy.depositPlayer(reciever.getName(), amount);
        return true;
    }

    public boolean chargePlayer(Player player, double amount)
    {
        if (this.economy == null) {
            return true;
        }
        if (amount > 0)
        {
            if (this.economy.has(player.getName(), amount))
            {
                this.economy.withdrawPlayer(player.getName(), amount);
                ShopAds.shopads.message.chargePlayer(player, amount);
                return true;
            }
        }
        else {
            return true;
        }
        return false;
    }

    public boolean hasEnough(Player player, double amount)
    {
        if (amount <= 0 || this.economy == null) {
            return true;
        }
        ShopAdsMessage.console.debug("checking if " + player.getName() + " has " + amount);
        ShopAdsMessage.console.debug(this.economy.getName());
        if (this.economy.has(player.getName(), amount)) {
            return true;
        }
        return false;
    }

    public Economy getEconomy()
    {
        return this.economy;
    }

    public String format(double d)
    {
        return this.economy.format(d);
    }

    public double getTpCharge(Player p, Location from, Location to)
    {
        if (this.economy == null) {
            return 0;
        }
        double total = ShopAds.shopads.config.getTpCost();
        if ((ShopAds.shopads.config.getTransWorldAddition() > 0.0D) &&
                (from.getWorld() != to.getWorld())) {
            total = ShopAds.shopads.config.getTransWorldAddition();
        }
        return total;
    }

    public boolean payPlayer(String receiver, double amount)
    {
        if (amount <= 0 || this.economy == null) return true;

        this.economy.depositPlayer(receiver, amount);
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = (Player[]) ShopAds.shopads.server.getOnlinePlayers().toArray()).length;
        for (int i = 0; i < j; i++)
        {
            Player p = arrayOfPlayer[i];
            if (p.getName().equalsIgnoreCase(receiver)) {
                ShopAds.shopads.message.payPlayer(p, amount);
            }
        }
        return true;
    }
}
