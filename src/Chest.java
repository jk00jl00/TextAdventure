import java.util.concurrent.ThreadLocalRandom;

public class Chest extends Interacteble{
    public boolean isOpen = false;

    public Chest(int fl, int deapth){
        this.id = 'C';
        int rnd = ThreadLocalRandom.current().nextInt(101);
        if(rnd > 0) {
            this.items.add(new Weapon(fl, deapth));
            this.items.add(new Gold(50));
        }
    }

    public Chest(int fl, boolean isFirst){
        this.id = 'C';
    }

    @Override
    public void interaction(){
        for(InteractionListener i: listeners){
            i.chestOpened(this);
        }
    }

    @Override
    public void addListener(InteractionListener i){
        listeners.add(i);
    }
}
