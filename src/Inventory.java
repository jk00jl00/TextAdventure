import java.util.ArrayList;

public class Inventory {
    private static ArrayList<Item> inventory = new ArrayList<>();
    public static int maxSize = 10;

    public static boolean addToInv(Item i){
        if(inventory.size() < maxSize){
            inventory.add(i);
            return true;
        } else{
            return false;
        }
    }

    public static  boolean removeFromInv(Item i){
        inventory.remove(i);
        return true;
    }

    public static String[] invToStringArray(){
        String[] s = new String[maxSize + 1];
        if(inventory.size() == 0){
            s[0] = "Your Inventory is empty";

            return s;
        }
        s[0] = "Your inventory contains:\n";
        for(int i = 1; i < maxSize + 1; i++){
            if (i < inventory.size() + 1) {
                s[i] = "Slot "+ i + ": " + inventory.get(i - 1).description + "\n";
            } else{
                s[i] = "Slot " + i + ": Empty \n";
            }
        }

        return s;
    }

    public static int getSize(){
        return inventory.size();
    }

    public static Item[] getItems() {
        return inventory.toArray(new Item[inventory.size()]);
    }

    public static Item getItem(int i) {
        return inventory.get(i);
    }
}
