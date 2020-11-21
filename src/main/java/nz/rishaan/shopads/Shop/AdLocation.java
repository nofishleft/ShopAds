package nz.rishaan.shopads.Shop;

import java.io.Serializable;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.util.Vector;
import nz.rishaan.shopads.ShopAds;
import nz.rishaan.shopads.Util.Messaging.ConsoleMessage;
import nz.rishaan.shopads.Util.Messaging.ShopAdsMessage;

public class AdLocation
        implements Serializable
{
    private String world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public AdLocation(Location loc)
    {
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.pitch = loc.getPitch();
        this.yaw = loc.getYaw();
        this.world = loc.getWorld().getName();
    }

    public float getPitch()
    {
        return this.pitch;
    }

    public void setPitch(float pitch)
    {
        this.pitch = pitch;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public float getYaw()
    {
        return this.yaw;
    }

    public void setYaw(float yaw)
    {
        this.yaw = yaw;
    }

    public double getZ()
    {
        return this.z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public Vector getDirection()
    {
        return new Location(ShopAds.shopads.server.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch).getDirection();
    }

    public Location getLocation()
    {
        return new Location(ShopAds.shopads.server.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public double distance(Location o)
    {
        return new Location(ShopAds.shopads.server.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch).distance(o);
    }

    public World getWorld()
    {
        ShopAdsMessage.console.debug("World in shop: " + this.world);

        World realWorld = ShopAds.shopads.server.getWorld(this.world);
        return realWorld;
    }
}
