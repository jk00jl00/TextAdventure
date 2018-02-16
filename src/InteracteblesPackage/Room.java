package InteracteblesPackage;

import InteracteblesPackage.Door;
import InteracteblesPackage.Interacteble;

import java.util.ArrayList;

public class Room{

    int distance;
    int roomNumber;
    public boolean isFilled = false;
    int x;
    int y;
    private int floor;

    public ArrayList<String> description = new ArrayList<>();

    public ArrayList<Door> doors = new ArrayList<>();

    public ArrayList<Interacteble> interactebles = new ArrayList<>();


    Room(int x, int y, int fl, int dis, int rn){
        this.x = x;
        this.y = y;
        this.distance = dis;
        this.floor = fl;
        this.roomNumber = rn;
    }
    public void onEnter() {

    }

    void addDoor(Door d){
        doors.add(d);
    }

    public void setDescription(String description, int index) {
        this.description.set(index, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Room ").append(this.roomNumber).append(" at ").append("x: ").append(this.x).append(",  y: ").append(this.y).append(", Distance: ").append(
                this.distance).append(".\n");
        for(int i = 0; i < doors.size(); i++){
            sb.append("Door ").append(i).append(": dir in 1: ").append(doors.get(i).dirInRoomOne).append("(").append(
                    doors.get(i).connects[0].roomNumber).append(")").append(", in 2: ").append(doors.get(i).dirInRoomTwo).append(
                            "(").append(doors.get(i).connects[1].roomNumber).append(")").append(".\n");
        }
        if(this.interactebles.size() > 0){
            for(Interacteble i: this.interactebles){
                sb.append(i.id);
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    boolean hasDoorInDir(char c){
        for(Door d : doors){
            if(d.connects[0].equals(this) && d.dirInRoomOne == c){
                return true;
            }else if(d.connects[1].equals(this) && d.dirInRoomTwo == c){
                return true;
            }
        }
        return false;
    }

    void addDescription(String s) {
        this.description.add(s);
    }
}
