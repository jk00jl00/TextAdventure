import java.util.ArrayList;

public class Interacteble {
    public String description;
    public char id;
    ArrayList<InteractionListener> listeners = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();

    public void interaction(){

    }

    public String inspection(){
        return "";
    }

    public void addListener(InteractionListener listener){
        this.listeners.add(listener);
    }
}
