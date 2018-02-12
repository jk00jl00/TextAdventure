package ItemsPackage;

import ItemsPackage.Item;

import java.util.HashMap;
import java.util.Map;

public class Equipment extends Item {

    public Map<String, Integer> stats = new HashMap<>();
    public String type;

    public Equipment(){

    }

    public int getStat(String stat){
        return stats.get(stat);
    }

    void setStats() {
        stats.put("Strength", 0);
        stats.put("Stamina", 0);
        stats.put("Dexterity", 0);
        stats.put("Luck", 0);
        stats.put("Intelligence", 0);
        stats.put("Wisdom", 0);
    }
}

/*
* Strength
* Stamina
* Dexterity
* Luck
* Intelligence
* Wisdom
* */