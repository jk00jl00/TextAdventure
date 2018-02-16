import ItemsPackage.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Inventory {
    private static ArrayList<Item> inventory = new ArrayList<>();
    private static int maxSize = 10;

    static boolean addToInv(Item i){
        if(inventory.size() < maxSize){
            inventory.add(i);
            return true;
        } else{
            return false;
        }
    }

    static boolean removeFromInv(Item i){
        inventory.remove(i);
        return true;
    }

    static ArrayList<String> invToStringArray(){
        ArrayList<String> s = new ArrayList<>();
        if(inventory.size() == 0){
            s.add("Your Inventory is empty");

            return s;
        }
        s.add("Your inventory contains:\n");
        for(int i = 1; i < maxSize + 1; i++){
            if (i < inventory.size() + 1) {
                s.add("Slot "+ i + ": " + inventory.get(i - 1).description + "\n");
            } else{
                s.add("Slot " + i + ": Empty \n");
            }
        }

        return s;
    }

    static int getSize(){
        return inventory.size();
    }

    public static Item[] getItems() {
        return inventory.toArray(new Item[inventory.size()]);
    }

    static Item getItem(int i) {
        return inventory.get(i);
    }
}
