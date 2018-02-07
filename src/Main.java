import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements InteractionListener {
    Player player;
    Room[] rooms;
    Gui gui;
    private Interacteble inFocus;
    int floor;
    public String iObj = "door|table|chest";

    public Main() {
        floor = -1;
        int roomCount = 1;
        gui = new Gui();
        changeEnterAction();
        gui.setDefaultText();

        player = new Player();

        onStart();
        for(Room r: rooms){
            System.out.println(r.toString());
        }
        for(int i = 0; i < 3; i++){
            System.out.print(System.lineSeparator());
        }
        System.out.println("Rooms with many doors");
        int c = 0;
        for(Room r: rooms){
            if(r.doors.size() > 2){
                System.out.println(r.toString());
                c++;
            }
        }
        System.out.println("Total: " + c + " / " + rooms.length);
        //getRooms();
        //onEnterRoom();
    }

    private void onStart() {
        gui.changeStats(player.stats);
        onNewFloor();
        addListeners();
        onEnterRoom(rooms[0]);
        for(Interacteble i: rooms[0].interactebles){
            i.addListener(this);
        }
    }

    private void addListeners() {
        for(Room r: rooms){
            for(Door d: r.doors){
                d.addListener(this);
            }

        }
    }

    private void onNewFloor(){
        floor++;
        gui.addToEvents("Generating Rooms");
        rooms = BoardTools.generateRooms(floor);
        gui.addToEvents("Done");
    }

    private void onEnterRoom(Room r){
        //fill room
        if(!r.isFilled) {
            BoardTools.fillRoom(r);
            for(Interacteble i: r.interactebles){
                i.addListener(this);
            }
        }
        //move player
        player.inside = r;
        inFocus = null;
        //display everything
        for (String s : r.description) {
            gui.addToEvents(s);
        }
    }

    @Override
    public void doorInteracted(Door d) {
        if (d.connects[0].equals(player.inside)) {
            System.out.println("this");
            onEnterRoom(d.connects[1]);
        } else {
            System.out.println("that");
            onEnterRoom(d.connects[0]);
        }
    }



    @Override
    public void tableInteracted(Table t) {

    }

    @Override
    public void chestOpened(Chest c) {
        String s = "";
        if(!c.isOpen){
            c.isOpen = true;
            s ="You open the chest and find ";
        } else{
            s = "The chest contains";
        }
        if (c.items.size() > 0) {
            for(Item i : c.items){
                s += ", " + i.description;
            }
        } else{
            s = "The chest is empty.";
        }
        gui.addToEvents(s);
    }

    private void changeEnterAction(){
        gui.changeEnterAction(new OnEnter(), new OnShiftEnter());
    }

    public class OnEnter extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = gui.getInputText().toUpperCase();

            if (gui.isWaiting) {
                gui.waitOver();
                return;
            }
            //Interacting
            Matcher matcher = Pattern.compile("\\binter|\\buse|\\bopen|\\binspe|\\blook|\\bpick\\sup\\b|\\bequip|\\binven|\\bbag", Pattern.CASE_INSENSITIVE).matcher(text);
            matcher.find();

            switch (matcher.group()){
                case "INTER":
                case "USE":
                    System.out.println(1);
                    Matcher mO = Pattern.compile(iObj, Pattern.CASE_INSENSITIVE).matcher(text);
                    Matcher mD = Pattern.compile("\\bN\\b|\\bE\\b|\\bS\\b|\\bW\\b|NORTH|EAST|SOUTH|WEST", Pattern.CASE_INSENSITIVE).matcher(text);
                    mO.find();
                    mD.find();
                    switch (mO.group()) {
                        case "DOOR":
                            //When interacting with a door
                            char dir = 'N';
                            switch (mD.group()) {
                                case "NORTH":
                                    dir = 'N';
                                    break;
                                case "N":
                                    dir = 'N';
                                    break;
                                case "EAST":
                                    dir = 'E';
                                    break;
                                case "E":
                                    dir = 'E';
                                    break;
                                case "SOUTH":
                                    dir = 'S';
                                    break;
                                case "S":
                                    dir = 'S';
                                    break;
                                case "WEST":
                                    dir = 'W';
                                    break;
                                case "W":
                                    dir = 'W';
                                    break;
                                default:
                                    dir = 'N';
                            }
                            for (Door d : player.inside.doors) {
                                if (d.dirInRoomOne == dir && d.connects[0].equals(player.inside)) {
                                    System.out.println(0);
                                    d.interaction();
                                    break;
                                } else if (d.dirInRoomTwo == dir && d.connects[1].equals(player.inside)) {
                                    System.out.println(500);
                                    d.interaction();
                                    break;
                                }
                            }
                            break;
                        case "TABLE":
                            for(Interacteble i: player.inside.interactebles){
                                if(i.id == 'T') i.interaction();
                            }
                            break;
                        case "CHEST":
                            for(Interacteble i: player.inside.interactebles){
                                if(i.id == 'C') {
                                    i.interaction();
                                    inFocus = i;
                                }
                            }

                    }
                    gui.clearInput();
                    break;
                case "LOOK":
                case "INSPE":
                    break;
                case "PICK UP":
                    if(inFocus != null){
                        Matcher m = Pattern.compile("GOLD|SWORD", Pattern.CASE_INSENSITIVE).matcher(text);
                        m.find();
                        if(m.group().equals("GOLD") && inFocus.items.size() != 0){
                            Gold g = new Gold(0);
                            for(Item i : inFocus.items){
                                if(i.description.toUpperCase().contains("GOLD")){
                                    g = (Gold)i;
                                }
                            }
                            player.gold.amount += g.amount;
                            gui.addToEvents("You now have " + player.gold.amount + " Gold");
                            inFocus.items.remove(g);
                        } else if(inFocus.items.size() != 0){
                            for(Item i: inFocus.items){
                                if(i.description.toUpperCase().contains(m.group())){
                                    if(Inventory.addToInv(i)){
                                        gui.addToEvents("You picked up " + i.description);
                                    }
                                    inFocus.items.remove(i);
                                    inFocus.interaction(); // Temp för att kolla
                                }
                            }
                        }
                    }
                    break;
                case "EQUIP":
                    if (Inventory.getSize() > 0) {
                        Matcher iI = Pattern.compile("SWORD", Pattern.CASE_INSENSITIVE).matcher(text);
                        Matcher eS = Pattern.compile("MainHand", Pattern.CASE_INSENSITIVE).matcher(text);
                        if (!iI.find()) return;
                        eS.find();
                        for(Item i : Inventory.getItems()){
                            if(i.description.toUpperCase().contains(iI.group())){
                                if(player.equip((Equipment)i, true)){
                                    gui.changeStats(player.stats);
                                    gui.addToEvents("You equipped " + i.description);
                                }
                            }
                        }
                    }
                    break;
                case "INVE":
                case "BAG":
                    break;

            }
        }


    }

    public class OnShiftEnter extends  AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gui.getInputText().equals("CLog")){
                gui.clearEventLog();
                gui.clearEvents();
                gui.clearInput();
            } else if(gui.getInputText().equals("Exit")) System.exit(0);
            else if(gui.getInputText().contains("CStat")){
                Matcher sM = Pattern.compile("\\bstr\\b|\\bsta\\b|\\bdex\\b|\\bluc\\b|\\bint\\b|\\bwis\\b", Pattern.CASE_INSENSITIVE).matcher(gui.getInputText());
                Matcher nM = Pattern.compile("\\d").matcher(gui.getInputText());
                sM.find();
                int change = 0;
                String digits = "";
                while(nM.find()){
                    digits += nM.group();
                }
                change = Integer.parseInt(digits);

                switch(sM.group()){
                    case "str":
                        player.changeStat("Strength", change);
                        gui.changeStats(player.stats);
                        break;
                    case "sta":
                        player.changeStat("Stamina", change);
                        gui.changeStats(player.stats);
                        break;
                    case "dex":
                        player.changeStat("Dexterity", change);
                        gui.changeStats(player.stats);
                        break;
                    case "luc":
                        player.changeStat("Luck", change);
                        gui.changeStats(player.stats);
                        break;
                    case "int":
                        player.changeStat("Intelligence", change);
                        gui.changeStats(player.stats);
                        break;
                    case "wis":
                        player.changeStat("Wisdom", change);
                        gui.changeStats(player.stats);
                        break;

                }
            }
        }

    }


    public static void main(String[] args){
        new Main();
    }
    }

