package nz.rishaan.shopads.Util;


import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopAdsIO {
    private final File configFile = new File("plugins/ShopAds/config.yml");
    private final File shopsFile = new File("plugins/ShopAds/shops.dat");
    private final File playerFile = new File("plugins/ShopAds/player.dat");
    private final File shopAdsDir = new File("plugins/ShopAds");

    public void loadConfig() {
        if (!configFile.exists()) {
            if (!this.shopAdsDir.exists())
                this.shopAdsDir.mkdir();

            createDefaultConfig();
        }

        ShopAdsMessage.console.debug("loadingConfig");

        ShopAdsConfig config = ShopAds.config;
        FileConfiguration fc = ShopAds.shopads.getConfig();

        config.setAnnounceInterval(fc.getInt("announceInterval", 240));

        config.setSendToAll(fc.getBoolean("announceInterval", true));

        config.setEnableTp(fc.getBoolean("enableTP", true));

        config.setAdsOverWorlds(fc.getBoolean("adsOverWorlds", true));

        config.setRandomOrder(fc.getBoolean("randomOrder", true));

        config.setAdCost(fc.getDouble("adCost", 20));

        config.setMaxAdRunTime(fc.getInt("maxAdRunTime", 24));

        config.setShopsPerPlayer(fc.getInt("shopsPerPlayer", 1));

        config.setTpCost(fc.getDouble("tpCost", 0));

        config.setTpTimeout(fc.getInt("tpTimeout", 60));

        config.setTransWorldAddition(fc.getDouble("transWorldAddition", 0));

        config.setAnnounceRadius(fc.getInt("announceRadius", 0));

        config.setLabelColor(fc.getString("labelColor", "Gold").toUpperCase());

        config.setMessageColor(fc.getString("messageColor", "Gray").toUpperCase());

        config.setTpCostDestination(fc.getString("tpCostDestination", "shop"));

        config.setDebug(fc.getBoolean("debug", false));

        config.setAnnounceDebug(fc.getBoolean("announceDebug", false));

        config.setDefaultAdColor(fc.getString("defaultAdColor", "Gray"));

        config.setDefaultShopColor(fc.getString("defaultShopColor", "Gold"));

        config.setSetShopColorCost(fc.getDouble("setShopColorCost", 0));

        config.setSetAdColorCost(fc.getDouble("setAdColorCost", 0));

        config.setSetNameCost(fc.getDouble("setNameCost", 0));

        config.setSetAdCost(fc.getDouble("setAdCost", 0));

        config.setSetLocationCost(fc.getDouble("setLocationCost", 0));

        config.setSetWorldCost(fc.getDouble("setWorldCost", 0));
    }

    public boolean loadPlayers() {
        ShopAdsMessage.console.debug("loadingPlayers");
        if (!this.playerFile.exists()) {
            try {
                this.playerFile.createNewFile();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        ObjectInputStream in = null;
        try {
            FileInputStream fis = new FileInputStream(this.playerFile);
            in = new ObjectInputStream(fis);
        } catch (IOException ignored) {
        }
        int playerObjects = getObjectCount(this.playerFile);
        ShopAdsPlayer player = null;
        boolean end = false;
        do {
            try {
                player = (ShopAdsPlayer) in.readObject();
            } catch (IOException | NullPointerException ex) {
                end = true;
            } catch (ClassNotFoundException ex) {
                ShopAds.log.info("Something terribly important is missing (ShopAdsPlayer)");
                return false;
            }
            if (!end) {
                ShopAds.playerHandler.addPlayer(player);
            }
        } while (!end);
        return true;
    }

    public boolean loadShops() {
        ShopAdsMessage.console.debug("loadingShops");
        if (!this.shopsFile.exists()) {
            try {
                this.shopsFile.createNewFile();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        ObjectInputStream in = null;
        try {
            FileInputStream fis = new FileInputStream(this.shopsFile);
            in = new ObjectInputStream(fis);
        } catch (IOException ignored) {
        }
        int shopsObjects = getObjectCount(this.shopsFile);
        Shop shop = null;
        boolean end = false;
        do {
            try {
                shop = (Shop) in.readObject();
            } catch (IOException | NullPointerException ex) {
                end = true;
            } catch (ClassNotFoundException ex) {
                ShopAds.log.info("Something terribly important is missing (Shop)");
                return false;
            }
            if (!end) {
                try {
                    ShopAds.shopHandler.addShop(shop);
                } catch (NullPointerException e) {
                    ShopAdsMessage.console.playersFileReset();
                    ShopAds.shopads.onDisable();
                }
            }
        } while (!end);
        return true;
    }

    public boolean saveShops() {
        if (ShopAds.shopHandler.shopsEmpty()) {
            return true;
        }
        ShopAdsMessage.console.debug("savingShops");

        FileOutputStream fos;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(this.shopsFile);
            out = new ObjectOutputStream(fos);
        } catch (IOException ex) {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ShopAdsMessage.console.debug(ShopAds.shopHandler.getShop(0).getShopName());
        for (Shop p : ShopAds.shopHandler.getShops()) {
            try {
                ShopAdsMessage.console.debug("Saving Shop : " + p.getShopName());
                out.writeObject(p);
            } catch (IOException ex) {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                ShopAdsMessage.console.debug("There was an error on " + p.getShopName());
            }
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
            ShopAdsMessage.console.debug("There was an error closing the shop writer");
        }
        return false;
    }

    public int getObjectCount(File file) {
        ShopAdsMessage.console.debug("getObjectCount on " + file.getName());
        int count = 0;

        ObjectInputStream in;
        try {
            FileInputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
        } catch (IOException ex) {
            ShopAdsMessage.console.debug("Object Count : 0");
            return 0;
        }
        Object object = null;
        try {
            for (; ; ) {
                object = in.readObject();
                count++;
            }
        } catch (NullPointerException | IOException | ClassNotFoundException ignored) {
        }
        return count;
    }

    public void createDefaultConfig() {
        ShopAds.shopads.saveDefaultConfig();
    }

    @Deprecated
    public void createDefaultConfig_legacy() {
        try {
            this.configFile.createNewFile();
            try {
                PrintWriter out = new PrintWriter(new FileWriter(this.configFile));
                out.println("#'shopsPerPlayer' - The maximum number of ads allowed to each player");
                out.println("#'announceInterval' - The time in seconds between ad announcements [number(secs)]");
                out.println("#'randomOrder' - Should the ads be in a random order [true/false]");
                out.println("#'adcost' - The cost per hour of advertising [number(currency)]");
                out.println("#'maxAdRunTime' - The longest time you want an ad to run for [number(hours)]");
                out.println("#'sendToAll' - Whether to send to all players, disregarding their choice [true/false]");
                out.println("#'tpCost' - Price to charge the player to teleport (0 for free)[number(currency)]");
                out.println("#'tpCostDestination' - The destination of the money that is collected from tp [shop/server]");
                out.println("#'transWorldAddition' - How much more to charge when tp to a shop in a different world (0 to disable)([number(currency)]");
                out.println("#'announceRadius' - The distance in blocks that an advertisement will be heard (0 for unlimited)[number(blocks)]");
                out.println("#'adsOverWorlds' - Controls whether ads are broadcasted over to other worlds [true/false]");
                out.println("#'enableTp' - Allows or denies the tp of players to ad locations [true/false]");
                out.println("#'tpTimeout' - The time in seconds until returning player to previous location (0 to disable)[number(seconds)]");
                out.println("#'labelColor' - The color of both the label of ShopAds messages and shop messages. [color]");
                out.println("#'messageColor' - The color of both the ShopAds messages and shop messages. [color]");
                out.println("#'setLocationCost' - The cost to change the location of a shop [number(currency)] ");
                out.println("#'setWorldCost' - The cost to add a world to advertise a shop in [number(currency)]");
                out.println("#'setShopColorCost' - The cost to change the display color of the shop label [number(currency)]");
                out.println("#'setAdColorCost' - The cost to change the color of the ad [number(currency)]");
                out.println("#'setNameCost' - The cost to change a shops name [number(currency)]");
                out.println("#'setAdCost' - The cost to change an ad message of a shop [number(currency)]");
                out.println("");
                out.println("#Color:");
                out.println("#      Black");
                out.println("#      Dark Blue");
                out.println("#      Dark Green");
                out.println("#      Teal");
                out.println("#      Dark Red");
                out.println("#      Purple");
                out.println("#      Gold");
                out.println("#      Gray");
                out.println("#      Dark Gray");
                out.println("#      Blue");
                out.println("#      Bright Green");
                out.println("#      Aqua");
                out.println("#      Red");
                out.println("#      Light Purple");
                out.println("#      Yellow");
                out.println("#      White");
                out.println("");
                out.println("#Shop settings");
                out.println("shopsPerPlayer=1");
                out.println("adCost=20");
                out.println("maxAdRunTime=24");
                out.println("");
                out.println("#Announcement settings");
                out.println("announceRadius=0");
                out.println("announceInterval=240");
                out.println("sendToAll=true");
                out.println("randomOrder=false");
                out.println("adsOverWorlds=true");
                out.println("");
                out.println("#Teleport settings");
                out.println("enableTp=true");
                out.println("tpTimeout=60");
                out.println("");
                out.println("#Economy settings");
                out.println("tpCost=0");
                out.println("transWorldAddition=0");
                out.println("tpCostDestination=shop");
                out.println("setLocationCost=0");
                out.println("setWorldCost=0");
                out.println("setShopColorCost=0");
                out.println("setAdColorCost=0");
                out.println("setNameCost=0");
                out.println("setAdCost=0");
                out.println("");
                out.println("#Coloring");
                out.println("labelColor=Gold");
                out.println("messageColor=Gray");
                out.println("defaultShopColor=Gold");
                out.println("defaultAdColor=Gray");
                out.close();
                ShopAds.log.info("[ShopAds2] No config found, created default config");
            } catch (IOException e) {
                ShopAds.log.info("[ShopAds2] Error writing to config");
            }
        } catch (IOException ioe) {
            ShopAds.log.info("[ShopAds] Error creating config file");
        }
    }

    public boolean savePlayers() {
        if (ShopAds.playerHandler.isEmpty()) {
            return true;
        }
        ShopAdsMessage.console.debug("saving Players");

        FileOutputStream fos;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(this.playerFile);
            out = new ObjectOutputStream(fos);
        } catch (IOException ex) {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ShopAdsMessage.console.debug(ShopAds.playerHandler.getPlayer(0).getName());
        for (ShopAdsPlayer p : ShopAds.playerHandler.getPlayers()) {
            try {
                ShopAdsMessage.console.debug("Saving Player : " + p.getName());
                out.writeObject(p);
            } catch (IOException ex) {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                ShopAdsMessage.console.debug("There was an error on " + p.getName());
            }
        }
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
            ShopAdsMessage.console.debug("There was an error closing the player writer");
        }
        return false;
    }

    public void saveConfig() {
        ShopAds.shopads.saveConfig();
    }
}
