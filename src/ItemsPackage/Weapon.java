package ItemsPackage;

import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends Equipment {

    public static Weapon starter = new Weapon();
    public static Weapon fist = new Weapon();

    static{
        fist.wClass = "Dagger";
        fist.description = "Your trusty fist";
        fist.minDmg = 1;
        fist.maxDmg = 1;
        fist.canChange = true;
        fist.isFist = true;
        fist.twoHanded = false;
        fist.value = 0;
    }

    private String wClass;
    private int minDmg;
    private int maxDmg;
    private boolean mainHand = false;
    private boolean offHand = false;
    private boolean canChange = false;
    private boolean twoHanded = false;
    private boolean isShield = false;
    private boolean isFist = false;

    private String[] types = {"mainhand", "offhand"};
    private String[][] wClasses = {
            {"LSword", "SSword", "Dagger"},
            {"Dagger", "Shield", "SSword"}
    };

    private Weapon(){
        type = types[0];
        wClass = wClasses[0][0];
        this.minDmg = 1;
        this.maxDmg = 3;
        this.mainHand = true;
        this.twoHanded = true;
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
        getName();
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


    private void getName() {
        switch (this.wClass){
            case "Dagger":
                this.description = "a dagger";
                break;
            case "LSword":
                this.description = "a long sword";
                break;
            case "SSword":
                this.description = "a short sword";
                break;
            case "Shield":
                this.description = "a shield";
                break;
        }
    }

    private void getStats(int fl, int de) {
        switch(this.wClass){
            case "Dagger":
                this.stats.put("Dexterity", ThreadLocalRandom.current().nextInt(0, (1 + fl) * de));
                break;
            case "LSword":
                this.stats.put("Strength", ThreadLocalRandom.current().nextInt(0, (1 + fl) * de));
                break;
            case "SSword":
                this.stats.put("Luck", ThreadLocalRandom.current().nextInt(0, (1 + fl) * de));
                break;
            case "Shield":
                this.stats.put("Stamina", ThreadLocalRandom.current().nextInt(0, (1 + fl) * de));
                break;
        }
    }

    private void setDescription(String description) {
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
                this.minDmg = ++fl * de;
                this.maxDmg = ++fl * de + de * ++fl;
                break;
            case "SSword":
                this.minDmg = ++fl;
                this.maxDmg = ++fl + de;
                break;
            case "Dagger":
                this.minDmg = fl;
                this.maxDmg = (fl + 2) * de;
                break;
            case "Shield":
                this.minDmg = -1;
                this.maxDmg = -1;
        }
    }
}
