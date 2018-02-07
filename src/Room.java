import java.lang.reflect.Array;
import java.util.ArrayList;

public class Room{

    public int distance;
    public int roomNumber;
    public boolean isFilled = false;
    public int x;
    public int y;
    private int floor;

    public ArrayList<String> description = new ArrayList<>();

    ArrayList<Door> doors = new ArrayList<>();

    ArrayList<Interacteble> interactebles = new ArrayList<>();


    public Room(int x, int y, int fl, int dis, int rn){
        this.x = x;
        this.y = y;
        this.distance = dis;
        this.floor = fl;
        this.roomNumber = rn;
    }
    public void onEnter() {

    }

    public void addDoor(Door d){
        doors.add(d);
    }

    public void setDescription(String description, int index) {
        this.description.set(index, description);
    }

    @Override
    public String toString() {
        String s = new String("");
        s += "Room " + this.roomNumber + " at " + "x: " + this.x + ",  y: " + this.y + ", Distance: " + this.distance + ".\n";
        for(int i = 0; i < doors.size(); i++){
            s += "Door " + i + ": dir in 1: " + doors.get(i).dirInRoomOne + "(" + doors.get(i).connects[0].roomNumber + ")" +", in 2: " + doors.get(i).dirInRoomTwo + "(" + doors.get(i).connects[1].roomNumber + ")" + ".\n";
        }
        s += "\n";
        return s;
    }

    public boolean hasDoorInDir(char c){
        for(Door d : doors){
            if(d.connects[0].equals(this)){
                if(d.dirInRoomOne == c){
                    return true;
                } else{
                    return true;
                }
            }else{
                if(d.dirInRoomTwo == c){
                    return true;
                } else{
                    return false;
                }
            }
        }
        return false;
    }

    public void addDescription(String s) {
        this.description.add(s);
    }
}
