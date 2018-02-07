import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BoardTools {

    public enum Rarity{
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY;

        public int getInt(Rarity a){
            switch (a){
                case COMMON:
                    return 0;
                case UNCOMMON:
                    return 1;
                case RARE:
                    return 2;
                case EPIC:
                    return 3;
                case LEGENDARY:
                    return 4;
                default:
                    return 0;
            }
        }
    }

    private static int dir;
    private static int lastRoom;
    private static int floor;
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Integer> takenX = new ArrayList<>();
    private static ArrayList<Integer> takenY = new ArrayList<>();

    public static Room[] generateRooms(int fl) {
        floor = fl;
        rooms.add(new Room(0,0,floor,0, 0));
        takenX.add(0);
        takenY.add(0);
        lastRoom = 0;

        int roomMod = ThreadLocalRandom.current().nextInt(-5, 10);
        dir = ThreadLocalRandom.current().nextInt(3);

        for(int i = 0; i < 15 + roomMod; i++){
            constructRoom();
        }

        //pick a direction *
        //check if fits *
        //make the doors *
        //add doors *
        //add rooms *
        //repeat until all rooms made *

        fillRoom(rooms.get(0));
        return rooms.toArray(new Room[rooms.size()]);
    }

    private static void constructRoom() {
        int rndChange = ThreadLocalRandom.current().nextInt(9);
        if(rndChange > 3){
            dir = ThreadLocalRandom.current().nextInt(4);
        }

        if(rndChange > 3 && rooms.size() > 1){
            lastRoom = ThreadLocalRandom.current().nextInt(rooms.size());
        }

        switch (dir){
            case 0:
                if (isEmptySpot(rooms.get(lastRoom).x ,rooms.get(lastRoom).y + 1) && !rooms.get(lastRoom).hasDoorInDir('N')) {
                    Room newRoom = new Room(rooms.get(lastRoom).x, rooms.get(lastRoom).y + 1, floor, rooms.get(lastRoom).distance + 1, rooms.size());
                    Door d = new Door(rooms.get(lastRoom), newRoom, 'N');
                    rooms.get(lastRoom).addDoor(d);
                    newRoom.addDoor(d);
                    rooms.add(newRoom);
                    lastRoom = rooms.indexOf(newRoom);
                    return;
                }
                else{
                    lastRoom = ThreadLocalRandom.current().nextInt(rooms.size());
                    dir = ThreadLocalRandom.current().nextInt(4);
                    constructRoom();
                    return;
                }
            case 1:
                if (isEmptySpot(rooms.get(lastRoom).x + 1,rooms.get(lastRoom).y) && !rooms.get(lastRoom).hasDoorInDir('E')) {
                    Room newRoom = new Room(rooms.get(lastRoom).x + 1, rooms.get(lastRoom).y, floor, rooms.get(lastRoom).distance + 1, rooms.size());
                    Door d = new Door(rooms.get(lastRoom), newRoom, 'E');
                    rooms.get(lastRoom).addDoor(d);
                    newRoom.addDoor(d);
                    rooms.add(newRoom);
                    lastRoom = rooms.indexOf(newRoom);
                    return;
                }
                else{
                    lastRoom = ThreadLocalRandom.current().nextInt(rooms.size());
                    dir = ThreadLocalRandom.current().nextInt(4);
                    constructRoom();
                    return;
                }
            case 2:
                if (isEmptySpot(rooms.get(lastRoom).x,rooms.get(lastRoom).y - 1) && !rooms.get(lastRoom).hasDoorInDir('S')) {
                    Room newRoom = new Room(rooms.get(lastRoom).x, rooms.get(lastRoom).y - 1, floor, rooms.get(lastRoom).distance + 1, rooms.size());
                    Door d = new Door(rooms.get(lastRoom), newRoom, 'S');
                    rooms.get(lastRoom).addDoor(d);
                    newRoom.addDoor(d);
                    rooms.add(newRoom);
                    lastRoom = rooms.indexOf(newRoom);
                    return;
                }
                else{
                    lastRoom = ThreadLocalRandom.current().nextInt(rooms.size());
                    dir = ThreadLocalRandom.current().nextInt(4);
                    constructRoom();
                    return;
                }
            case 3:
                if (isEmptySpot(rooms.get(lastRoom).x - 1,rooms.get(lastRoom).y) && !rooms.get(lastRoom).hasDoorInDir('W')) {
                    Room newRoom = new Room(rooms.get(lastRoom).x - 1, rooms.get(lastRoom).y, floor, rooms.get(lastRoom).distance + 1, rooms.size());
                    Door d = new Door(rooms.get(lastRoom), newRoom, 'W');
                    rooms.get(lastRoom).addDoor(d);
                    newRoom.addDoor(d);
                    rooms.add(newRoom);
                    lastRoom = rooms.indexOf(newRoom);
                    return;
                }
                else{
                    lastRoom = ThreadLocalRandom.current().nextInt(rooms.size());
                    dir = ThreadLocalRandom.current().nextInt(4);
                    constructRoom();
                    return;
                }
                
            default: return;

        }
    }

    private static boolean isEmptySpot(int x, int y) {
        for(Room r: rooms){
            if(r.x == x && r.y == y) return false;
        }
        return true;
    }

    public static void fillRoom(Room r) {
        r.isFilled = true;
        int rnd = ThreadLocalRandom.current().nextInt(101);
        String s = new String("");
        if (r.roomNumber == 0) {
            r.interactebles.add(new Chest(0, true));
            r.interactebles.add(new Table(0, 0));
            r.addDescription("First Room");
            if(r.doors.size() >= 1){
                s = "Door to the: ";
                for(Door d: r.doors){
                    if(!r.equals(d.connects[1])){
                        s += d.dirInRoomOne + " ";
                    }
                }
                r.addDescription(s);
            }
        } else{
            //fill with objects
            //set descriptions for objects and room
            r.addDescription("Room number: " + r.roomNumber + ", at distance: " + r.distance);
            if(rnd < 65) {
                r.interactebles.add(new Chest(floor, r.distance));
                r.addDescription("A chest sits in the middle.");
            }
            if(rnd < 85) {
                r.interactebles.add(new Table(floor, r.distance));
                r.addDescription("A table breaks up the incredible monotony of the room just to add more letters to the string.");
            }

            if(r.doors.size() > 2){
                for(Door d: r.doors){
                    if(!r.equals(d.connects[1])){
                        s += d.dirInRoomOne + " ";
                    }
                }
                r.addDescription("Doors to the: " + s);
            } else if(r.doors.size() == 2){
                r.addDescription("Door to the: " + r.doors.get(1).dirInRoomOne);
            }
            r.addDescription("You came from the " );
            r.addDescription(charToString(r.doors.get(0).dirInRoomTwo));
        }
    }

    private static String charToString(char c){
        switch (c){
            case 'N':
                return "North";
            case 'E':
                return "East";
            case 'S':
                return "South";
            case 'W':
                return "West";
            default:
                return "North";
        }
    }
}


/* The walls seem to be speaking to you*/
