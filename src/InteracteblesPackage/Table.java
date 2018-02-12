package InteracteblesPackage;

import InteracteblesPackage.Interacteble;

public class Table extends Interacteble {


    Table(int fl, int deapth){
        this.id = 'T';
    }

    public void interaction(){
        for(InteractionListener i: listeners){
            i.tableInteracted(this);
        }
    }
}
