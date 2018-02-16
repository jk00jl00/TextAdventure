import InteracteblesPackage.*;
import ItemsPackage.Equipment;
import ItemsPackage.Gold;
import ItemsPackage.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements InteractionListener {
    private Player player;
    private Room[] rooms;
    private Gui gui;

    private boolean inInv = false;
    private Interacteble inFocus;
    private int floor;
    private String iObj = "door|table|chest";

    private Main() {
        floor = -1;
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
        gui.changeStats(player);
        onNewFloor();
        addListeners();
        gui.updateCharStats(false);
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
        gui.addToEvents("Floor " + floor);
        gui.addToEvents("--------------");
        gui.addToEvents("");
        rooms = BoardTools.generateRooms(floor);
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
        System.out.println(r.toString());
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
        StringBuilder s = new StringBuilder();
        if(!c.isOpen){
            c.isOpen = true;
            s.append("You open the chest and find");
        } else{
            s.append("The chest contains");
        }
        if (c.items.size() > 0) {
            for(Item i : c.items){
                s.append(", ").append(i.description);
            }
        } else{
            s.append("The chest is empty.");
        }
        gui.addToEvents(s.toString());
    }

    private void changeEnterAction(){
        gui.changeEnterAction(new OnEnter(), new OnShiftEnter());
    }

    public class OnEnter extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.isWaiting) {
                gui.waitOver();
                return;
            }

            String text = gui.getInputText();

            if(text.length() == 0){
                gui.updateCharStats(false);
                return;
            }
            //Interacting
            Matcher matcher = Pattern.compile(
                    "\\binter|\\buse\\b|\\bopen|\\binspe|\\blook|\\bpick\\sup\\b|\\bequip\\b|\\binven|\\bbag|\\bunequip|\\btake\\soff|" +
                    "\\bgrab\\b|\\benter\\b|\\bopen\\b", Pattern.CASE_INSENSITIVE).matcher(text);
            if(!matcher.find()){
                System.out.println("No match for base word");
                return;
            }

            switch (matcher.group().toUpperCase()){
                case "INTER":
                case "USE":
                    Matcher mO = Pattern.compile(iObj, Pattern.CASE_INSENSITIVE).matcher(text);
                    if(!mO.find()){
                        System.out.println("No match for object when interacting");
                        return;
                    }
                    switch (mO.group().toUpperCase()) {
                        case "DOOR":
                            //When interacting with a door
                            Matcher mD = Pattern.compile("\\bN\\b|\\bE\\b|\\bS\\b|\\bW\\b|NORTH|EAST|SOUTH|WEST", Pattern.CASE_INSENSITIVE).matcher(text);
                            if(!mD.find()){
                                System.out.println("No match for direction when interaction with door");
                                return;
                            }
                            char dir;
                            switch (mD.group().toUpperCase()) {
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
                    if(text.toLowerCase().matches(iObj)){

                    }
                    break;
                case "ENTER":
                    //When interacting with a door
                    Matcher mD = Pattern.compile("\\bN\\b|\\bE\\b|\\bS\\b|\\bW\\b|NORTH|EAST|SOUTH|WEST", Pattern.CASE_INSENSITIVE).matcher(text);
                    if (!mD.find()) {
                        System.out.println("No match for direction when interaction with door");
                        return;
                    }
                    char dir;
                    switch (mD.group().toUpperCase()) {
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
                case "OPEN":
                    Matcher mOpen = Pattern.compile("chest", Pattern.CASE_INSENSITIVE).matcher(text);
                    if(!mOpen.find()){
                        System.out.println("No match for object to open");
                        return;
                    }
                    switch (mOpen.group().toUpperCase()) {
                        case "CHEST":
                            for (Interacteble i : player.inside.interactebles) {
                                if (i.id == 'C') {
                                    i.interaction();
                                    inFocus = i;
                                }
                            }
                    }
                    break;
                case "GRAB":
                case "PICK UP":
                    if(inFocus != null){
                        Matcher m = Pattern.compile("GOLD|SWORD|DAGGER|SHIELD", Pattern.CASE_INSENSITIVE).matcher(text);
                        if(!m.find()){
                            System.out.println("No match when trying to Pick up");
                            return;
                        }
                        if(m.group().toUpperCase().equals("GOLD") && inFocus.items.size() != 0){
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
                            Iterator<Item> itr = inFocus.items.iterator();
                            while (itr.hasNext()){
                                Item i = itr.next();
                                if(i.description.toUpperCase().contains(m.group().toUpperCase())){
                                    if(Inventory.addToInv(i)){
                                        gui.changeStats(player);
                                        gui.addToEvents("You picked up " + i.description);
                                    }
                                    itr.remove();
                                    inFocus.interaction(); // Temp för att kolla
                                }
                            }
                        }
                    }
                    gui.clearInput();
                    break;
                case "EQUIP":
                    if (Inventory.getSize() > 0) {
                        Matcher iI = Pattern.compile("[1-9]", Pattern.CASE_INSENSITIVE).matcher(text);
                        Matcher sM = Pattern.compile("main\\s?hand|off\\s?hand",Pattern.CASE_INSENSITIVE).matcher(text);
                        if (!iI.find()) {
                            System.out.println("No match when trying to Equip");
                            return;
                        }
                        if(sM.find()){
                            System.out.println(sM.group());
                        }
                        try {
                            Item i = Inventory.getItem(Integer.parseInt(iI.group()) - 1);
                            if(player.equip((Equipment)Inventory.getItem(Integer.parseInt(iI.group()) - 1), true)){
                                gui.addToEvents("You equipped " + i.description);
                                gui.changeStats(player);
                            }
                        } catch (IndexOutOfBoundsException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case "INVE":
                case "BAG":
                    gui.currentDisplayInCharstats = 1;
                    gui.updateCharStats(false, true);
                    gui.clearInput();
                    return;
                case "UNEQUIP":
                case "TAKE OFF":
                    Matcher eS = Pattern.compile("main\\s?hand|off\\s?hand", Pattern.CASE_INSENSITIVE).matcher(text);
                    if(!eS.find()){
                        System.out.println("No match when trying to unequip");
                        return;
                    }
                    if(player.unEquip(eS.group().toLowerCase().replaceFirst("\\s", ""))){
                        gui.addToEvents("Unequipped " + eS.group() + ".");
                    }
                    gui.changeStats(player);

            }
            gui.clearInput();
        }


    }

    public class OnShiftEnter extends  AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gui.getInputText().length() == 0){
                gui.updateCharStats(true);
                return;
            } else if(gui.getInputText().equals("CLog")){
                gui.clearEventLog();
                gui.clearEvents();
                gui.clearInput();
            } else if(gui.getInputText().equals("Exit")){
                System.exit(0);
            } else if(gui.getInputText().contains("CStat")){
                Matcher sM = Pattern.compile("\\bstr\\b|\\bsta\\b|\\bdex\\b|\\bluc\\b|\\bint\\b|\\bwis\\b", Pattern.CASE_INSENSITIVE).matcher(gui.getInputText());
                Matcher nM = Pattern.compile("\\d").matcher(gui.getInputText());
                if(!sM.find()) return;
                int change;
                StringBuilder sb = new StringBuilder();
                String digits;
                while(nM.find()){
                    sb.append(nM.group());
                }
                digits = sb.toString();
                change = Integer.parseInt(digits);

                switch(sM.group()){
                    case "str":
                        player.changeStat("Strength", change);
                        gui.changeStats(player);
                        break;
                    case "sta":
                        player.changeStat("Stamina", change);
                        gui.changeStats(player);
                        break;
                    case "dex":
                        player.changeStat("Dexterity", change);
                        gui.changeStats(player);
                        break;
                    case "luc":
                        player.changeStat("Luck", change);
                        gui.changeStats(player);
                        break;
                    case "int":
                        player.changeStat("Intelligence", change);
                        gui.changeStats(player);
                        break;
                    case "wis":
                        player.changeStat("Wisdom", change);
                        gui.changeStats(player);
                        break;

                }
            }
        }

    }


    public static void main(String[] args){
        new Main();
    }
    }

