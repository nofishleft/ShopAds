package nz.rishaan.shopads.Util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import nz.rishaan.shopads.Player.PlayerHandler;
import nz.rishaan.shopads.Player.ShopAdsPlayer;
import nz.rishaan.shopads.Shop.Shop;
import nz.rishaan.shopads.Shop.ShopHandler;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.Command.SetCommandMessage;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;

public class ShopAdsIO
{
    private File configFile = new File("plugins/ShopAds/config.yml");
    private File shopsFile = new File("plugins/ShopAds/shops.dat");
    private File playerFile = new File("plugins/ShopAds/player.dat");
    private File shopAdsDir = new File("plugins/ShopAds");
    private ArrayList<String> configArray = new ArrayList();

    public boolean loadConfig()
    {
        ShopAdsMessage.console.debug("loadingConfig");
        Properties pr = new Properties();
        if (this.configFile.exists())
        {
            try
            {
                FileInputStream in = new FileInputStream(this.configFile);
                pr.load(in);
                try
                {
                    ShopAds.shopads.config.setAnnounceInterval(Integer.parseInt(pr.getProperty("announceInterval")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("announceInterval");
                }
                try
                {
                    ShopAds.shopads.config.setSendToAll(Boolean.parseBoolean(pr.getProperty("sendToAll")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("sendToAll");
                }
                try
                {
                    ShopAds.shopads.config.setEnableTp(Boolean.parseBoolean(pr.getProperty("enableTp")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("enableTp");
                }
                try
                {
                    ShopAds.shopads.config.setAdsOverWorlds(Boolean.parseBoolean(pr.getProperty("adsOverWorlds")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("adsOverWorlds");
                }
                try
                {
                    ShopAds.shopads.config.setRandomOrder(Boolean.parseBoolean(pr.getProperty("enableTp")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("enableTp");
                }
                try
                {
                    ShopAds.shopads.config.setAdCost(Double.parseDouble(pr.getProperty("adCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("adCost");
                }
                try
                {
                    ShopAds.shopads.config.setMaxAdRunTime(Integer.parseInt(pr.getProperty("maxAdRunTime")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("maxAdRunTime");
                }
                try
                {
                    ShopAds.shopads.config.setShopsPerPlayer(Integer.parseInt(pr.getProperty("shopsPerPlayer")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("shopsPerPlayer");
                }
                try
                {
                    ShopAds.shopads.config.setTpCost(Double.parseDouble(pr.getProperty("tpCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("tpCost");
                }
                try
                {
                    ShopAds.shopads.config.setTpTimeout(Integer.parseInt(pr.getProperty("tpTimeout")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("tpTimeout");
                }
                try
                {
                    ShopAds.shopads.config.setTransWorldAddition(Integer.parseInt(pr.getProperty("transWorldAddition")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("transWorldAddition");
                }
                try
                {
                    ShopAds.shopads.config.setAnnounceRadius(Integer.parseInt(pr.getProperty("announceRadius")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("announceRadius");
                }
                try
                {
                    ShopAds.shopads.config.setLabelColor(pr.getProperty("labelColor").toUpperCase());
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("labelColor");
                }
                try
                {
                    ShopAds.shopads.config.setMessageColor(pr.getProperty("messageColor").toUpperCase());
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("messageColor");
                }
                try
                {
                    ShopAds.shopads.config.setTpCostDestination(pr.getProperty("tpCostDestination"));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("tpCostDestination");
                }
                try
                {
                    ShopAds.shopads.config.setRandomOrder(Boolean.parseBoolean(pr.getProperty("randomOrder")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("randomOrder");
                }
                try
                {
                    ShopAds.shopads.config.setDebug(Boolean.parseBoolean(pr.getProperty("debug")));
                }
                catch (Exception e)
                {
                    ShopAds.shopads.config.setDebug(false);
                }
                try
                {
                    ShopAds.shopads.config.setAnnounceDebug(Boolean.parseBoolean(pr.getProperty("announceDebug")));
                }
                catch (Exception e)
                {
                    ShopAds.shopads.config.setDebug(false);
                }
                try
                {
                    ShopAds.shopads.config.setDefaultAdColor(pr.getProperty("defaultAdColor"));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("defaultAdColor");
                }
                try
                {
                    ShopAds.shopads.config.setDefaultShopColor(pr.getProperty("defaultShopColor"));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("defaultShopColor");
                }
                try
                {
                    ShopAds.shopads.config.setSetShopColorCost(Double.parseDouble(pr.getProperty("setShopColorCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("setShopColorCost");
                }
                try
                {
                    ShopAds.shopads.config.setSetAdColorCost(Double.parseDouble(pr.getProperty("setAdColorCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("setAdColorCost");
                }
                try
                {
                    ShopAds.shopads.config.setSetAdColorCost(Double.parseDouble(pr.getProperty("setLocationCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("setLocationCost");
                }
                try
                {
                    ShopAds.shopads.config.setSetAdColorCost(Double.parseDouble(pr.getProperty("setWorldCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("setWorldCost");
                }
                try
                {
                    ShopAds.shopads.config.setSetAdColorCost(Double.parseDouble(pr.getProperty("setNameCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("setNameCost");
                }
                try
                {
                    ShopAds.shopads.config.setSetAdColorCost(Double.parseDouble(pr.getProperty("setAdCost")));
                }
                catch (Exception e)
                {
                    ShopAdsMessage.console.checkConfigOption("setAdCost");
                }
                ShopAds.shopads.log.info("[ShopAds2] Config loaded!");
            }
            catch (IOException e)
            {
                ShopAds.shopads.log.info("[ShopAds2] Config file exists but is corrupt, please delete it!");
            }
        }
        else
        {
            if (!this.shopAdsDir.exists()) {
                this.shopAdsDir.mkdir();
            }
            createDefaultConfig();
            loadConfig();
            return true;
        }
        return false;
    }

    public boolean loadPlayers()
    {
        ShopAdsMessage.console.debug("loadingPlayers");
        if (!this.playerFile.exists()) {
            try
            {
                this.playerFile.createNewFile();
                return true;
            }
            catch (IOException ex)
            {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        ObjectInputStream in = null;
        try
        {
            FileInputStream fis = new FileInputStream(this.playerFile);
            in = new ObjectInputStream(fis);
        }
        catch (IOException localIOException1) {}
        int playerObjects = getObjectCount(this.playerFile);
        ShopAdsPlayer player = null;
        boolean end = false;
        do
        {
            try
            {
                player = (ShopAdsPlayer)in.readObject();
            }
            catch (IOException ex)
            {
                end = true;
            }
            catch (ClassNotFoundException ex)
            {
                ShopAds.shopads.log.info("Something terribly important is missing (ShopAdsPlayer)");
                return false;
            }
            catch (NullPointerException ex)
            {
                end = true;
            }
            if (!end) {
                ShopAds.shopads.playerHandler.addPlayer(player);
            }
        } while (!end);
        return true;
    }

    public boolean loadShops()
    {
        ShopAdsMessage.console.debug("loadingShops");
        if (!this.shopsFile.exists()) {
            try
            {
                this.shopsFile.createNewFile();
                return true;
            }
            catch (IOException ex)
            {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        ObjectInputStream in = null;
        try
        {
            FileInputStream fis = new FileInputStream(this.shopsFile);
            in = new ObjectInputStream(fis);
        }
        catch (IOException localIOException1) {}
        int shopsObjects = getObjectCount(this.shopsFile);
        Shop shop = null;
        boolean end = false;
        do
        {
            try
            {
                shop = (Shop)in.readObject();
            }
            catch (IOException ex)
            {
                end = true;
            }
            catch (ClassNotFoundException ex)
            {
                ShopAds.shopads.log.info("Something terribly important is missing (Shop)");
                return false;
            }
            catch (NullPointerException ex)
            {
                end = true;
            }
            if (!end) {
                try
                {
                    ShopAds.shopads.shopHandler.addShop(shop);
                }
                catch (NullPointerException e)
                {
                    ShopAdsMessage.console.playersFileReset();
                    ShopAds.shopads.plugin.onDisable();
                }
            }
        } while (!end);
        return true;
    }

    public boolean saveShops()
    {
        if (ShopAds.shopads.shopHandler.shopsEmpty()) {
            return true;
        }
        ShopAdsMessage.console.debug("savingShops");

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try
        {
            fos = new FileOutputStream(this.shopsFile);
            out = new ObjectOutputStream(fos);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ShopAdsMessage.console.debug(ShopAds.shopads.shopHandler.getShop(0).getShopName());
        for (Shop p : ShopAds.shopads.shopHandler.getShops()) {
            try
            {
                ShopAdsMessage.console.debug("Saving Shop : " + p.getShopName());
                out.writeObject(p);
            }
            catch (IOException ex)
            {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                ShopAdsMessage.console.debug("There was an error on " + p.getShopName());
            }
        }
        try
        {
            out.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
            ShopAdsMessage.console.debug("There was an error closing the shop writer");
        }
        return false;
    }

    public int getObjectCount(File file)
    {
        ShopAdsMessage.console.debug("getObjectCount on " + file.getName());
        if (file == null)
        {
            ShopAdsMessage.console.debug(file.getName() + " is empty.");
            return 0;
        }
        int count = 0;

        ObjectInputStream in = null;
        try
        {
            FileInputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
        }
        catch (EOFException ex)
        {
            ShopAdsMessage.console.debug("Object Count : 0");
            return 0;
        }
        catch (IOException ex)
        {
            ShopAdsMessage.console.debug("Object Count : 0");

            return 0;
        }
        Object object = null;
        try
        {
            for (;;)
            {
                object = in.readObject();
                count++;
            }
        }
        catch (NullPointerException localNullPointerException) {}catch (IOException localIOException1) {}catch (ClassNotFoundException localClassNotFoundException) {}
        return count;
    }

    public void createDefaultConfig()
    {
        try
        {
            this.configFile.createNewFile();
            try
            {
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
                ShopAds.shopads.log.info("[ShopAds2] No config found, created default config");
            }
            catch (IOException e)
            {
                ShopAds.shopads.log.info("[ShopAds2] Error writing to config");
            }
            return;
        }
        catch (IOException ioe)
        {
            ShopAds.shopads.log.info("[ShopAds] Error creating config file");
        }
    }

    public boolean savePlayers()
    {
        if (ShopAds.shopads.playerHandler.isEmpty()) {
            return true;
        }
        ShopAdsMessage.console.debug("saving Players");

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try
        {
            fos = new FileOutputStream(this.playerFile);
            out = new ObjectOutputStream(fos);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ShopAdsMessage.console.debug(ShopAds.shopads.playerHandler.getPlayer(0).getName());
        for (ShopAdsPlayer p : ShopAds.shopads.playerHandler.getPlayers()) {
            try
            {
                ShopAdsMessage.console.debug("Saving Player : " + p.getName());
                out.writeObject(p);
            }
            catch (IOException ex)
            {
                Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
                ShopAdsMessage.console.debug("There was an error on " + p.getName());
            }
        }
        try
        {
            out.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
            ShopAdsMessage.console.debug("There was an error closing the player writer");
        }
        return false;
    }

    public void loadConfigArray()
    {
        this.configArray = new ArrayList();
        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader(this.configFile);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = null;
        try
        {
            while ((line = bufferedReader.readLine()) != null) {
                this.configArray.add(line);
            }
        }
        catch (IOException localIOException) {}
        try
        {
            bufferedReader.close();
        }
        catch (IOException localIOException1) {}
    }

    public void saveConfig()
    {
        try
        {
            FileWriter out = new FileWriter(this.configFile);
            BufferedWriter write = new BufferedWriter(out);
            for (String line : this.configArray)
            {
                write.write(line);
                write.newLine();
            }
            write.close();
            out.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ShopAdsIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean setConfigOption(String key, String value, Player player)
    {
        loadConfigArray();
        if (getKeyIndex(key) >= 0)
        {
            int index = getKeyIndex(key);
            ShopAdsMessage.setCommand.configOption(key, value, player);
            this.configArray.set(index, key + "=" + value);
        }
        else
        {
            this.configArray.add(key + "=" + value);
        }
        saveConfig();
        return true;
    }

    private int getKeyIndex(String key)
    {
        int i = -1;
        for (String line : this.configArray)
        {
            i++;
            if ((!line.startsWith("#")) &&
                    (line.startsWith(key + "="))) {
                return i;
            }
        }
        return i;
    }
}
