import java.util.concurrent.ThreadLocalRandom;

public class Chest extends Interacteble{
    public boolean isOpen = false;
    private String inspectionString;
    private int qual;
    private int firstIStringInt;

    public Chest(int fl, int deapth){
        this.id = 'C';
        int rnd = ThreadLocalRandom.current().nextInt(101);

        if(rnd < 15) qual = 0;
        else if(rnd < 45) qual = 1;
        else qual = 2;

        if(rnd > 0) {
            this.items.add(new Weapon(fl, deapth));
            this.items.add(new Gold(50));
        }
        buildInspectionString(fl, deapth,false);
    }

    private void buildInspectionString(int fl, int deapth, boolean isFirst) {
        if(!isFirst){
            firstIStringInt = ThreadLocalRandom.current().nextInt(0,InteractbleStrings.rIFCONQ.length);
            inspectionString += InteractbleStrings.rIFCONQ[firstIStringInt];
        }
    }

    public Chest(int fl, boolean isFirst){
        this.id = 'C';
        this.items.add(Weapon.starter);
    }

    @Override
    public String inspection(){
        return inspectionString;
    }

    @Override
    public void interaction(){
        replaceInspectionString();
        for(InteractionListener i: listeners){
            i.chestOpened(this);
        }
    }

    private void replaceInspectionString() {
        inspectionString = InteractbleStrings.rIFCONQWO[firstIStringInt][ThreadLocalRandom.current().nextInt(0,InteractbleStrings.rIFCONQWO[firstIStringInt].length)];
    }

    @Override
    public void addListener(InteractionListener i){
        listeners.add(i);
    }
}
