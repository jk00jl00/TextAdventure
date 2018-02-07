public class Table extends Interacteble {


    public Table(int fl, int deapth){
        this.id = 'T';
    }

    public void interaction(){
        for(InteractionListener i: listeners){
            i.tableInteracted(this);
        }
    }
}
