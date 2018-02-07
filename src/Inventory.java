import java.util.ArrayList;

public class Inventory {
    private static ArrayList<Item> inventory = new ArrayList<>();
    private static int maxSize = 10;

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

    public static String invToString(){
        String s = "";
        if(inventory.size() == 0){
            return "Your inventory is empty.";
        }
        int index = 0;
        s += "Your inventory contains:\n";
        for(Item i: inventory){
            s += index + ": " + i.description;
        }

        return s;
    }

    public static int getSize(){
        return inventory.size();
    }

    public static Item[] getItems() {
        return inventory.toArray(new Item[inventory.size()]);
    }
}
