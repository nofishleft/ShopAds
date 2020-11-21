package nz.rishaan.shopads.Util;

public class ArrayFunctions {
    public Object[] addToArray(Object[] O, Object o) {
        if ((O != null) && (O.length > 0)) {
            Object[] transfer = new Object[O.length + 1];
            System.arraycopy(O, 0, transfer, 0, O.length);
            transfer[O.length] = o;
            O = new Object[transfer.length];
            O = transfer;
            return O;
        }
        O = new Object[1];
        O[0] = o;
        return O;
    }
}
