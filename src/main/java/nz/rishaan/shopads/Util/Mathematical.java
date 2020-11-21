package nz.rishaan.shopads.Util;


import nz.rishaan.shopads.Shop.Shop;
import org.bukkit.entity.Player;

public class Mathematical {
    public boolean isNumber(String string) {
        char[] arrayOfChar;
        int j = (arrayOfChar = string.toCharArray()).length;
        for (int i = 0; i < j; i++) {
            char c = arrayOfChar[i];
            if ((!Character.isDigit(c)) && (!Character.isSpaceChar(c)) && (!Character.isWhitespace(c)) && (c != '.')) {
                return false;
            }
        }
        return true;
    }

    public double playerDistanceFromShop(Player p, Shop shop) {
        return shop.getLocation().distance(p.getLocation());
    }
}
