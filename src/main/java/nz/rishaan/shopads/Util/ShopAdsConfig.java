package nz.rishaan.shopads.Util;


import org.bukkit.ChatColor;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;

public class ShopAdsConfig
{
    private int announceInterval;
    private boolean sendToAll;
    private boolean enableTp;
    private boolean randomOrder;
    private double tpCost;
    private double transWorldAddition;
    private boolean adsOverWorlds;
    private int tpTimeout;
    private int maxAdRunTime;
    private int shopsPerPlayer;
    private double adCost;
    private String tpCostDestination;
    private int announceRadius;
    private boolean debug = false;
    private boolean announceDebug = false;
    private String lColor;
    private String mColor;
    private String defaultShopColor;
    private String defaultAdColor;
    private double setLocationCost;
    private double setWorldCost;
    private double setNameCost;
    private double setAdCost;
    private double setShopColorCost;
    private double setAdColorCost;

    public void setDefaultAdColor(String defaultAdColor)
    {
        this.defaultAdColor = defaultAdColor;
    }

    public void setDefaultShopColor(String defaultShopColor)
    {
        this.defaultShopColor = defaultShopColor;
    }

    public void setLabelColor(String color)
    {
        ShopAdsMessage.console.debug("setting label color to :" + color);
        this.lColor = color;
        ShopAdsMessage.console.debug("after lcolor=color : " + this.lColor);
    }

    public void setMessageColor(String color)
    {
        ShopAdsMessage.console.debug("setting message color");
        this.mColor = color;
    }

    public int getAnnounceRadius()
    {
        return this.announceRadius;
    }

    public void setAnnounceRadius(int announceRadius)
    {
        this.announceRadius = announceRadius;
    }

    public String getTpCostDestination()
    {
        return this.tpCostDestination;
    }

    public void setTpCostDestination(String tpCostDestination)
    {
        if ((tpCostDestination.equalsIgnoreCase("player")) || (tpCostDestination.equalsIgnoreCase("shop")) || (tpCostDestination.equalsIgnoreCase("owner"))) {
            this.tpCostDestination = "shop";
        } else if ((tpCostDestination.equalsIgnoreCase("server")) || (tpCostDestination.equalsIgnoreCase("nobody")) || (tpCostDestination.equalsIgnoreCase("consume"))) {
            this.tpCostDestination = "server";
        } else {
            this.tpCostDestination = "server";
        }
        ShopAdsMessage.console.debug("tpCostDestination set to '" + this.tpCostDestination + "'.");
    }

    public boolean getDebug()
    {
        return this.debug;
    }

    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }

    public double getAdCost()
    {
        return this.adCost;
    }

    public void setAdCost(double adCost)
    {
        this.adCost = adCost;
    }

    public boolean getAdsOverWorlds()
    {
        return this.adsOverWorlds;
    }

    public void setAdsOverWorlds(boolean adsOverWorlds)
    {
        this.adsOverWorlds = adsOverWorlds;
    }

    public int getAnnounceInterval()
    {
        return this.announceInterval;
    }

    public void setAnnounceInterval(int announceInterval)
    {
        this.announceInterval = announceInterval;
    }

    public boolean getEnableTp()
    {
        return this.enableTp;
    }

    public void setEnableTp(boolean enableTp)
    {
        this.enableTp = enableTp;
    }

    public int getMaxAdRunTime()
    {
        return this.maxAdRunTime;
    }

    public void setMaxAdRunTime(int maxAdRunTime)
    {
        this.maxAdRunTime = maxAdRunTime;
    }

    public boolean getRandomOrder()
    {
        return this.randomOrder;
    }

    public void setRandomOrder(boolean randomOrder)
    {
        this.randomOrder = randomOrder;
    }

    public boolean getSendToAll()
    {
        return this.sendToAll;
    }

    public void setSendToAll(boolean sendToAll)
    {
        this.sendToAll = sendToAll;
    }

    public int getShopsPerPlayer()
    {
        return this.shopsPerPlayer;
    }

    public void setShopsPerPlayer(int shopsPerPlayer)
    {
        this.shopsPerPlayer = shopsPerPlayer;
    }

    public double getTpCost()
    {
        return this.tpCost;
    }

    public void setTpCost(double tpCost)
    {
        this.tpCost = tpCost;
    }

    public int getTpTimeout()
    {
        return this.tpTimeout;
    }

    public void setTpTimeout(int tpTimeout)
    {
        this.tpTimeout = tpTimeout;
    }

    public double getTransWorldAddition()
    {
        return this.transWorldAddition;
    }

    public void setTransWorldAddition(double transWorldAddition)
    {
        this.transWorldAddition = transWorldAddition;
    }

    public boolean getAnnounceDebug()
    {
        return this.announceDebug;
    }

    public void setAnnounceDebug(boolean announceDebug)
    {
        this.announceDebug = announceDebug;
    }

    public ChatColor getLabelColor()
    {
        return ShopAds.shopads.message.getColor(this.lColor);
    }

    public ChatColor getMessageColor()
    {
        return ShopAds.shopads.message.getColor(this.mColor);
    }

    public String getDefaultShopColor()
    {
        return this.defaultShopColor;
    }

    public String getDefaultAdColor()
    {
        return this.defaultAdColor;
    }

    public double getSetLocationCost()
    {
        return this.setLocationCost;
    }

    public double getSetWorldCost()
    {
        return this.setWorldCost;
    }

    public double getSetNameCost()
    {
        return this.setNameCost;
    }

    public void setSetNameCost(double setNameCost)
    {
        this.setNameCost = setNameCost;
    }

    public double getSetAdCost()
    {
        return this.setAdCost;
    }

    public void setSetAdCost(double setAdCost)
    {
        this.setAdCost = setAdCost;
    }

    public void setSetLocationCost(double setLocationCost)
    {
        this.setLocationCost = setLocationCost;
    }

    public void setSetWorldCost(double setWorldCost)
    {
        this.setWorldCost = setWorldCost;
    }

    public double getSetShopColorCost()
    {
        return this.setShopColorCost;
    }

    public void setSetShopColorCost(double setShopColorCost)
    {
        this.setShopColorCost = setShopColorCost;
    }

    public double getSetAdColorCost()
    {
        return this.setAdColorCost;
    }

    public void setSetAdColorCost(double setAdColorCost)
    {
        this.setAdColorCost = setAdColorCost;
    }
}
