import InteracteblesPackage.Room;
import ItemsPackage.Equipment;
import ItemsPackage.Gold;
import ItemsPackage.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Player {

    Room inside;

    private ArrayList<Equipment> tempEquip = new ArrayList<>();
    Map<String, Equipment> equipped = new HashMap<>();
    Map<String, Integer> stats = new HashMap<>();

    int maxHealth;
    int currentHealth;
    int maxActionPoints;
    int currentActionPoints;

    Gold gold = Gold.playerGold;

    Player(){
        this.maxHealth = 10;
        this.currentHealth = 10;
        this.maxActionPoints = 2;
        this.currentActionPoints = 2;
        this.setStats();
        this.setEquipment();
    }

    private void setEquipment() {
        equipped.put("mainhand", Weapon.fist);
        equipped.put("offhand", Weapon.fist);

    }

    private void setStats() {
        stats.put("Strength", 10);
        stats.put("Stamina", 10);
        stats.put("Dexterity", 10);
        stats.put("Luck", 10);
        stats.put("Intelligence", 10);
        stats.put("Wisdom", 10);
    }


    boolean equip(Equipment e, boolean fromInv){
        if (this.equipped.get(e.type).equals(Weapon.fist)) {
            this.tempEquip.add(this.equipped.get(e.type));
        } else {
            this.tempEquip.clear();
        }
        this.equipped.replace(e.type, e);
        addStats(e);
        if (tempEquip.size() >  0) {
            removeStats(tempEquip.get(0));
        }
        if(fromInv){
            Inventory.removeFromInv(e);
        }
        if (this.tempEquip.size() > 0 && !tempEquip.get(0).equals(Weapon.fist)) {
            if(!Inventory.addToInv(tempEquip.get(0))){
                System.out.println("Full inventory on equip");
            }
        }
        tempEquip.clear();
        updateHealth();
        updateActionPoints();
        return true;
    }

    private void updateActionPoints() {
        this.maxActionPoints = 2 + (this.stats.get("Dexterity") - 10) / 2;
        this.currentActionPoints = maxActionPoints;
    }

    private void updateHealth() {
        this.maxActionPoints = 2 + (this.stats.get("Stamina") - 10) / 2;
    }

    private void addStats(Equipment equipment){
        for(String s: equipment.stats.keySet()){
            if (equipment.getStat(s) !=  0) {
                changeStat(s , equipment.stats.get(s));
            }
        }
    }

    void changeStat(String s, int c){
        this.stats.replace(s, this.stats.get(s) + c);
        updateHealth();
        updateActionPoints();
    }

    private void removeStats(Equipment equipment){
        for(String s: equipment.stats.keySet()){
            this.stats.replace(s, this.stats.get(s),this.stats.get(s) - equipment.getStat(s));
        }
    }

    boolean unEquip(String item) {
        if(equipped.get(item).equals(Weapon.fist)) return false;
        Inventory.addToInv(equipped.get(item));
        equipped.replace(item, Weapon.fist);
        return true;
    }
}
