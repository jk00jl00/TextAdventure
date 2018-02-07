public class Door extends Interacteble {
    Room[] connects = new Room[2];

    char dirInRoomOne;
    char dirInRoomTwo;

    public Door(Room prevRoom, Room r, char dirRomOne){
        this.connects[0] = prevRoom;
        this.connects[1] = r;

        this.dirInRoomOne = dirRomOne;
        this.dirInRoomTwo = getDir(dirInRoomOne);
    }

    private char getDir(char dirInRoomOne) {
        switch (dirInRoomOne){
            case 'N':
                return 'S';
            case 'E':
                return 'W';
            case 'S':
                return 'N';
            case 'W':
                return 'E';
            default:
                return 'A';
        }
    }

    @Override
    public void interaction(){
        //Describe door
        for (InteractionListener e: listeners) {
            e.doorInteracted(this);
        }
    }

    @Override
    public String inspection(){
        return this.description;
    }

    @Override
    public void addListener(InteractionListener listener) {
        if(listeners.size() == 0) {
            listeners.add(listener);
        }
    }
}
