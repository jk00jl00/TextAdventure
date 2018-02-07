import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {

    Room inside;

    ArrayList<Equipment> tempEquip = new ArrayList<>();
    Map<String, Equipment> equipped = new HashMap<>();
    public Map<String, Integer> stats = new HashMap<>();

    public Gold gold = Gold.playerGold;

    private static int invMaxSize = 10;


    public Player(){
        this.setStats();
        this.setEquipment();
    }

    private void setEquipment() {
        equipped.put("MainHand", Weapon.starter);
    }

    private void setStats() {
        stats.put("Strength", 10);
        stats.put("Stamina", 10);
        stats.put("Dexterity", 10);
        stats.put("Luck", 10);
        stats.put("Intelligence", 10);
        stats.put("Wisdom", 10);
    }


    public boolean equip(Equipment e, boolean fromInv){
        this.tempEquip.add(this.equipped.get(e.type));
        this.equipped.replace(e.type, e);
        addStats(e);
        removeStats(tempEquip.get(0));
        if(fromInv){
            Inventory.removeFromInv(e);
        }
        if(!Inventory.addToInv(tempEquip.get(0))){
            System.out.println("Full inventory on equip");
        }
        tempEquip.clear();
        return true;
    }

    private void addStats(Equipment equipment){
        for(String s: equipment.stats.keySet()){
            changeStat(s , equipment.stats.get(s));
        }
    }

    public void changeStat(String s, int c){
        this.stats.replace(s, this.stats.get(s) + c);
    }

    private void removeStats(Equipment equipment){
        for(String s: equipment.stats.keySet()){
            this.stats.replace(s, this.stats.get(s),this.stats.get(s) - equipment.getStat(s));
        }
    }
}