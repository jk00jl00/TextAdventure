package ItemsPackage;

import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends Equipment {

    public static Weapon starter = new Weapon();
    String wClass;
    int minDmg;
    int maxDmg;
    boolean mainHand = false;
    boolean offHand = false;
    boolean canChange = false;
    boolean twoHanded = false;
    boolean isShield = false;

    String[] types = {"MainHand", "OffHand"};
    String[][] wClasses = {
            {"LSword", "SSword", "Dagger"},
            {"Dagger", "Shield", "SSword"}
    };

    private Weapon(){
        type = types[0];
        wClass = wClasses[0][0];
        this.minDmg = 1;
        this.maxDmg = 1;
        this.mainHand = true;
        this. twoHanded = true;
        this.value = 1;
        this.setStats();
        for(String s: this.stats.keySet()){
            this.stats.replace(s, 0);
        }
        this.setDescription("a starter Sword");
    }

    public Weapon(int fl, int de) {
        this.type = types[ThreadLocalRandom.current().nextInt(0,types.length)];
        getWClass();
        getDmg(fl, de);
        getStats(fl, de);
        /*setMaxDmg(5);
        this.type = "MainHand";
        this.minDmg = 1;
        this.maxDmg = 1;
        this.mainHand = true;
        this.twoHanded = true;
        this.wClass = "Sword";
        this.setStats();
        for(String s: this.stats.keySet()){
            this.stats.replace(s, 5);
        }
        setDescription("A sword");
        */
    }

    private void getStats(int fl, int de) {

    }

    public void setMaxDmg(int dmg) {
        this.maxDmg = dmg;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void getWClass() {
        int rnd = ThreadLocalRandom.current().nextInt(wClasses.length);
        if(rnd == 0){
            mainHand = true;
            int newRnd = ThreadLocalRandom.current().nextInt(wClasses[rnd].length);
            switch(newRnd){
                case 0:
                    twoHanded = true;
                    break;
                case 1:
                case 2:
                    this.canChange = true;
                    break;
            }
            this.wClass = wClasses[rnd][newRnd];
        } else if(rnd == 1){
            offHand = true;
            int newRnd = ThreadLocalRandom.current().nextInt(wClasses[rnd].length);
            switch (newRnd){
                case 0:
                case 2:
                    this.canChange = true;
                    break;
                case 1:
                    this.isShield = true;
                    break;
            }
            this.wClass = wClasses[rnd][newRnd];
        }
    }

    private void getDmg(int fl, int de) {
        switch(this.wClass) {
            case "LSword":
                this.minDmg = 1 * (1 + fl * de);
                this.maxDmg = 1 * (1 + fl * de) + de * (fl + 1);
                break;
            case "SSword":
                this.minDmg = 1 * (1 + fl);
                this.maxDmg = 1 * (1 + fl) + de;
                break;
            case "Dagger":
                this.minDmg = 1 * (fl);
                this.maxDmg = 1 * (fl + 2) * de;
                break;
            case "Shield":
                this.minDmg = -1;
                this.maxDmg = -1;
        }
    }
}
