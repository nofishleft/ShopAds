package nz.rishaan.shopads.Util;


import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;
import org.bukkit.ChatColor;
import org.bukkit.Warning;

public class ShopAdsConfig {
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

    public void setDefaultAdColor(String defaultAdColor) {
        this.defaultAdColor = defaultAdColor;
        ShopAds.shopads.getConfig().set("defaultAdColor", defaultAdColor);
    }

    public void setDefaultShopColor(String defaultShopColor) {
        this.defaultShopColor = defaultShopColor;
        ShopAds.shopads.getConfig().set("defaultShopColor", defaultShopColor);
    }

    public void setLabelColor(String color) {
        ShopAdsMessage.console.debug("setting label color to :" + color);
        this.lColor = color;
        ShopAds.shopads.getConfig().set("labelColor", color);
        ShopAdsMessage.console.debug("after lcolor=color : " + this.lColor);
    }

    public void setMessageColor(String color) {
        ShopAdsMessage.console.debug("setting message color");
        this.mColor = color;
        ShopAds.shopads.getConfig().set("messageColor", color);
    }

    public int getAnnounceRadius() {
        return this.announceRadius;
    }

    public void setAnnounceRadius(int announceRadius) {
        this.announceRadius = announceRadius;
        ShopAds.shopads.getConfig().set("announceRadius", announceRadius);
    }

    public String getTpCostDestination() {
        return this.tpCostDestination;
    }

    @Warning(reason = "using if/else instead of string switch")
    public void setTpCostDestination(String tpCostDestination) {
        if ((tpCostDestination.equalsIgnoreCase("player")) || (tpCostDestination.equalsIgnoreCase("shop")) || (tpCostDestination.equalsIgnoreCase("owner"))) {
            this.tpCostDestination = "shop";
        } else if ((tpCostDestination.equalsIgnoreCase("server")) || (tpCostDestination.equalsIgnoreCase("nobody")) || (tpCostDestination.equalsIgnoreCase("consume"))) {
            this.tpCostDestination = "server";
        } else {
            this.tpCostDestination = "server";
        }
        ShopAds.shopads.getConfig().set("tpCostDestination", this.tpCostDestination);
        ShopAdsMessage.console.debug("tpCostDestination set to '" + this.tpCostDestination + "'.");
    }

    public boolean getDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        ShopAds.shopads.getConfig().set("debug", debug);
    }

    public double getAdCost() {
        return this.adCost;
    }

    public void setAdCost(double adCost) {
        this.adCost = adCost;
        ShopAds.shopads.getConfig().set("adCost", adCost);
    }

    public boolean getAdsOverWorlds() {
        return this.adsOverWorlds;
    }

    public void setAdsOverWorlds(boolean adsOverWorlds) {
        this.adsOverWorlds = adsOverWorlds;
        ShopAds.shopads.getConfig().set("adsOverWorlds", adsOverWorlds);
    }

    public int getAnnounceInterval() {
        return this.announceInterval;
    }

    public void setAnnounceInterval(int announceInterval) {
        this.announceInterval = announceInterval;
        ShopAds.shopads.getConfig().set("announceInterval", announceInterval);
    }

    public boolean getEnableTp() {
        return this.enableTp;
    }

    public void setEnableTp(boolean enableTp) {
        this.enableTp = enableTp;
        ShopAds.shopads.getConfig().set("enableTp", enableTp);
    }

    public int getMaxAdRunTime() {
        return this.maxAdRunTime;
    }

    public void setMaxAdRunTime(int maxAdRunTime) {
        this.maxAdRunTime = maxAdRunTime;
        ShopAds.shopads.getConfig().set("maxAdRunTie", maxAdRunTime);
    }

    public boolean getRandomOrder() {
        return this.randomOrder;
    }

    public void setRandomOrder(boolean randomOrder) {
        this.randomOrder = randomOrder;
        ShopAds.shopads.getConfig().set("randomOrder", randomOrder);
    }

    public boolean getSendToAll() {
        return this.sendToAll;
    }

    public void setSendToAll(boolean sendToAll) {
        this.sendToAll = sendToAll;
        ShopAds.shopads.getConfig().set("sendToAll", sendToAll);
    }

    public int getShopsPerPlayer() {
        return this.shopsPerPlayer;
    }

    public void setShopsPerPlayer(int shopsPerPlayer) {
        this.shopsPerPlayer = shopsPerPlayer;
        ShopAds.shopads.getConfig().set("shopsPerPlayer", shopsPerPlayer);
    }

    public double getTpCost() {
        return this.tpCost;
    }

    public void setTpCost(double tpCost) {
        this.tpCost = tpCost;
        ShopAds.shopads.getConfig().set("tpCost", tpCost);
    }

    public int getTpTimeout() {
        return this.tpTimeout;
    }

    public void setTpTimeout(int tpTimeout) {
        this.tpTimeout = tpTimeout;
        ShopAds.shopads.getConfig().set("tpTimeout", tpTimeout);
    }

    public double getTransWorldAddition() {
        return this.transWorldAddition;
    }

    public void setTransWorldAddition(double transWorldAddition) {
        this.transWorldAddition = transWorldAddition;
        ShopAds.shopads.getConfig().set("transWorldAddition", transWorldAddition);
    }

    public boolean getAnnounceDebug() {
        return this.announceDebug;
    }

    public void setAnnounceDebug(boolean announceDebug) {
        this.announceDebug = announceDebug;
        ShopAds.shopads.getConfig().set("announceDebug", announceDebug);
    }

    public ChatColor getLabelColor() {
        return ShopAds.message.getColor(this.lColor);
    }

    public ChatColor getMessageColor() {
        return ShopAds.message.getColor(this.mColor);
    }

    public String getDefaultShopColor() {
        return this.defaultShopColor;
    }

    public String getDefaultAdColor() {
        return this.defaultAdColor;
    }

    public double getSetLocationCost() {
        return this.setLocationCost;
    }

    public double getSetWorldCost() {
        return this.setWorldCost;
    }

    public double getSetNameCost() {
        return this.setNameCost;
    }

    public void setSetNameCost(double setNameCost) {
        this.setNameCost = setNameCost;
        ShopAds.shopads.getConfig().set("setNameCost", setNameCost);
    }

    public double getSetAdCost() {
        return this.setAdCost;
    }

    public void setSetAdCost(double setAdCost) {
        this.setAdCost = setAdCost;
        ShopAds.shopads.getConfig().set("setAdCost", setAdCost);
    }

    public void setSetLocationCost(double setLocationCost) {
        this.setLocationCost = setLocationCost;
        ShopAds.shopads.getConfig().set("setocationCOst", setLocationCost);
    }

    public void setSetWorldCost(double setWorldCost) {
        this.setWorldCost = setWorldCost;
        ShopAds.shopads.getConfig().set("setWorldCost", setWorldCost);
    }

    public double getSetShopColorCost() {
        return this.setShopColorCost;
    }

    public void setSetShopColorCost(double setShopColorCost) {
        this.setShopColorCost = setShopColorCost;
        ShopAds.shopads.getConfig().set("setShopColorCost", setShopColorCost);
    }

    public double getSetAdColorCost() {
        return this.setAdColorCost;
    }

    public void setSetAdColorCost(double setAdColorCost) {
        this.setAdColorCost = setAdColorCost;
        ShopAds.shopads.getConfig().set("setAdColorCost", setAdColorCost);
    }
}
