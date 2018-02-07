public class Weapon extends Equipment{

    public static Weapon starter = new Weapon();
    String wClass;
    int minDmg;
    int maxDmg;
    boolean mainHand = false;
    boolean offHand = false;
    boolean canChange = false;
    boolean twoHanded = false;

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
        setMaxDmg(5);
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
    }


    public void setMaxDmg(int dmg) {
        this.maxDmg = dmg;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
