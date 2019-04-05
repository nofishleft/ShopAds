package nz.rishaan.shopads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import nz.rishaan.shopads.Command.ShopAdsCommand;
import nz.rishaan.shopads.Player.PlayerHandler;
import nz.rishaan.shopads.Player.ShopAdsPlayerListener;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.Threads.AnnounceThread;
import nz.rishaan.shopads.Threads.OneSecondThread;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import nz.rishaan.shopads.Util.ShopAdsConfig;
import nz.rishaan.shopads.Util.ShopAdsEconomy;
import nz.rishaan.shopads.Util.ShopAdsIO;
import nz.rishaan.shopads.Util.ShopAdsPermissions;

public class ShopAds
        extends JavaPlugin
{
    protected AnnounceThread thread;
    protected OneSecondThread thread2;
    public static Plugin plugin;
    private final ShopAdsPlayerListener playerListener = new ShopAdsPlayerListener(this);
    public static final Logger log = Logger.getLogger("Minecraft");
    public static final ShopAdsConfig config = new ShopAdsConfig();
    public static final ShopAdsMessage message = new ShopAdsMessage();
    public static String prefix;
    public static final ShopAdsEconomy economy = new ShopAdsEconomy();
    public static final ShopAdsPermissions permissions = new ShopAdsPermissions();
    public static final ShopAdsIO iO = new ShopAdsIO();
    public static final PlayerHandler playerHandler = new PlayerHandler();
    public static final ShopHandler shopHandler = new ShopHandler();
    public static BukkitScheduler scheduler;
    public static Server server;
    public static final ShopAdsCommand command = new ShopAdsCommand();
    public boolean pluginUpToDate = true;
    public String updateVersion = null;
    public static ShopAds shopads;

    public ShopAds()
    {
        this.plugin = this;
        this.thread = new AnnounceThread(this);
        this.thread2 = new OneSecondThread(this);
    }

    public void onEnable()
    {
        message.plugin = this;

        server = getServer();
        plugin = server.getPluginManager().getPlugin("ShopAds");
        PluginDescriptionFile pdfFile = getDescription();
        log.info("[" + pdfFile.getName() + "]" + " Version " + pdfFile.getVersion() + " loading.");

        server.getPluginManager().registerEvents(this.playerListener, this);
        getCommand("ad").setExecutor(command);
        getCommand("ads").setExecutor(command);
        getCommand("shops").setExecutor(command);
        scheduler = server.getScheduler();
        setupPermissions();

        if (!setupEconomy()) System.out.println("!!!Error Setting Up Economy!!!");

        reload();
    }

    public void onDisable()
    {
        ShopAdsMessage.console.disablePlugin();
        ShopAdsMessage.console.savingPlayers();
        iO.savePlayers();
        ShopAdsMessage.console.savingShops();
        iO.saveShops();
        scheduler.cancelTasks(plugin);
    }

    public void reload()
    {
        Plugin thisPlugin = server.getPluginManager().getPlugin("ShopAds");
        scheduler.cancelTasks(thisPlugin);
        iO.loadConfig();
        iO.loadPlayers();
        iO.loadShops();
        ShopAdsMessage.console.numberOfShopsLoaded();
        if (!playerHandler.isEmpty()) {
            playerHandler.updateOwnedShopsFromFile();
        }
        prefix = config.getLabelColor() + "[ShopAds] " + config.getMessageColor();
        Long interval = Long.valueOf(Long.parseLong(String.valueOf(config.getAnnounceInterval())) * 20L);
        Long oneSecond = Long.valueOf(20L);
        scheduler.scheduleSyncRepeatingTask(plugin, this.thread2, oneSecond.longValue(), oneSecond.longValue());
        scheduler.scheduleSyncRepeatingTask(plugin, this.thread, interval.longValue(), interval.longValue());
    }

    public boolean playersOnline()
    {
        if ((server.getOnlinePlayers() == null) || (server.getOnlinePlayers().size() < 1)) {
            return false;
        }
        return true;
    }

    public void invokeAnnounceThread()
    {
        this.thread.run();
    }

    public static BufferedReader read(String url)
            throws Exception
    {
        return new BufferedReader(
                new InputStreamReader(
                        new URL(url).openStream()));
    }

    public boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null)
        {
            permissions.permission = ((Permission)permissionProvider.getProvider());
            ShopAdsMessage.console.hookedPermissions(((Permission)permissionProvider.getProvider()).getName());
            return true;
        }
        return false;
    }

    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
        {
            economy.economy = economyProvider.getProvider();
            ShopAdsMessage.console.hookedEconomy(economyProvider.getProvider().getName());
            return true;
        }
        return false;
    }
}
