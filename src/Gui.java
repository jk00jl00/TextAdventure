import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Gui extends JFrame{

    private static Gui frame;
    private static final int maxNumberOfCharacters = 30;
    private static final int numberOfRowsInEventLog = 20;
    private SimpleAttributeSet keySet;
    private SimpleAttributeSet forSpaces;
    private JTextPane charInfo;
    private JTextPane playerInput;
    private JTextPane eventLog;
    private JTextPane roomInteractebles;
    private JTextPane enemyStats;
    private JTextPane map;
    private JTextPane options;
    private static final String spacesFromTop = "\n \n \n \n \n \n";
    private String[] events = new String[numberOfRowsInEventLog];
    private ArrayList<String> waitingStrings;
    private ArrayList<String> inventoryDiplay = new ArrayList<>();
    private ArrayList<String> charStatsDisplay = new ArrayList<>();
    private ArrayList<String> equippedDisplay = new ArrayList<>();
     int currentDisplayInCharstats = 2; //0 Char stats 1 inventory 2 equipped (Cycles at start)


    boolean isWaiting;

    Gui(){
        super("Text Adventure");
        frame = this;

        for(int i = 0; i < numberOfRowsInEventLog; i++){
            events[i] = " \n";
        }

        keySet = new SimpleAttributeSet();
        forSpaces = new SimpleAttributeSet();

        StyleConstants.setAlignment(keySet, StyleConstants.ALIGN_CENTER);
        StyleConstants.setBackground(keySet , Color.DARK_GRAY);
        StyleConstants.setForeground(keySet, Color.LIGHT_GRAY);
        StyleConstants.setBold(keySet, true);
        StyleConstants.setFontSize(keySet, 15);

        StyleConstants.setFontSize(forSpaces, 5);

        frame.setSize(new Dimension(720, 480));
        frame.setFocusTraversalKeysEnabled(false);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        createUIComponents();

        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    private void createUIComponents() {
        charInfo = new JTextPane();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        charInfo.setPreferredSize(new Dimension(280,460));
        charInfo.setFocusable(false);
        charInfo.setBackground(Color.DARK_GRAY);
        charInfo.setForeground(Color.LIGHT_GRAY);
        charInfo.setBorder(BorderFactory.createBevelBorder(0));

        StyledDocument charStatsDoc = charInfo.getStyledDocument();
        charStatsDoc.setParagraphAttributes(0, charStatsDoc.getLength(), keySet, false);

        frame.add(charInfo, gbc);
        gbc = new GridBagConstraints();


        eventLog = new JTextPane();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;

        eventLog.setPreferredSize(new Dimension(460,460));
        eventLog.setFocusable(false);
        eventLog.setBackground(Color.DARK_GRAY);
        eventLog.setForeground(Color.LIGHT_GRAY);
        eventLog.setBorder(BorderFactory.createBevelBorder(0));

        StyledDocument eventDoc = eventLog.getStyledDocument();
        eventDoc.setParagraphAttributes(0, eventDoc.getLength(), keySet, false);

        frame.add(eventLog, gbc);
        gbc = new GridBagConstraints();

        roomInteractebles = new JTextPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        roomInteractebles.setPreferredSize(new Dimension(280,180));
        roomInteractebles.setFocusable(false);
        roomInteractebles.setBackground(Color.DARK_GRAY);
        roomInteractebles.setForeground(Color.LIGHT_GRAY);
        roomInteractebles.setBorder(BorderFactory.createBevelBorder(0));
        roomInteractebles.setAlignmentX(CENTER_ALIGNMENT);

        StyledDocument inventoryDoc = roomInteractebles.getStyledDocument();
        inventoryDoc.setParagraphAttributes(0, inventoryDoc.getLength(), keySet, false);

        frame.add(roomInteractebles, gbc);
        gbc = new GridBagConstraints();

        playerInput = new JTextPane(new DefaultStyledDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if ((playerInput.getStyledDocument().getLength() + str.length()) <= maxNumberOfCharacters) {
                    super.insertString(offs, str, a);
                }
        }});
        gbc.gridx = 1;
        gbc.gridy = 2;
        //gbc.insets = new Insets(75, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        playerInput.setPreferredSize(new Dimension(460,35));
        playerInput.setBorder(BorderFactory.createBevelBorder(0));
        playerInput.setCaretColor(Color.LIGHT_GRAY);
        playerInput.getCaret().setBlinkRate(0);
        playerInput.setBackground(Color.DARK_GRAY);
        playerInput.setForeground(Color.LIGHT_GRAY);

        StyledDocument inputDoc = playerInput.getStyledDocument();
        inputDoc.setParagraphAttributes(0, inputDoc.getLength(), keySet, false);

        frame.add(playerInput, gbc);
        gbc = new GridBagConstraints();

        options = new JTextPane();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        options.setPreferredSize(new Dimension(460, 145));
        options.setBorder(BorderFactory.createBevelBorder(0));
        options.setBackground(Color.DARK_GRAY);
        options.setForeground(Color.LIGHT_GRAY);
        options.setFocusable(false);

        StyledDocument optionsDoc = options.getStyledDocument();
        optionsDoc.setParagraphAttributes(0, optionsDoc.getLength(), keySet, false);

        frame.add(options, gbc);
        gbc = new GridBagConstraints();

        enemyStats = new JTextPane();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;

        enemyStats.setPreferredSize(new Dimension(280,460));
        enemyStats.setFocusable(false);
        enemyStats.setBackground(Color.DARK_GRAY);
        enemyStats.setForeground(Color.LIGHT_GRAY);
        enemyStats.setBorder(BorderFactory.createBevelBorder(0));
        enemyStats.setAlignmentX(CENTER_ALIGNMENT);

        StyledDocument enemyDoc = enemyStats.getStyledDocument();
        enemyDoc.setParagraphAttributes(0, enemyDoc.getLength(), keySet, false);

        frame.add(enemyStats, gbc);
        gbc = new GridBagConstraints();

        map = new JTextPane();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        map.setPreferredSize(new Dimension(180,180));
        map.setFocusable(false);
        map.setBackground(Color.DARK_GRAY);
        map.setForeground(Color.LIGHT_GRAY);
        map.setBorder(BorderFactory.createBevelBorder(0));
        map.setAlignmentX(CENTER_ALIGNMENT);

        StyledDocument mapDoc = map.getStyledDocument();
        mapDoc.setParagraphAttributes(0, mapDoc.getLength(), keySet, false);

        frame.add(map, gbc);

    }

    void setDefaultText() {
    }

    void changeStats(Player player) {
        inventoryDiplay.clear();
        equippedDisplay.clear();
        charStatsDisplay.clear();
        this.inventoryDiplay = Inventory.invToStringArray();
        for(String s: player.equipped.keySet()){
            this.equippedDisplay.add(s + ": " + player.equipped.get(s).description + "\n");
        }
        for(String s: player.stats.keySet()){
            charStatsDisplay.add(s + ": " + player.stats.get(s) + "\n");
        }
        charStatsDisplay.add("\nHp: " + player.currentHealth + "/" + player.maxHealth + "\n");
        charStatsDisplay.add("Ap: " + player.currentActionPoints + "/" + player.maxActionPoints);
        updateCharStats(false, true);
    }

    private void updateEventLog(String newLine){
        eventLog.setText("");
        try {
            eventLog.getStyledDocument().insertString(0, spacesFromTop, forSpaces);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfRowsInEventLog - 1; i++) {
            events[i] = events[i + 1];
        }
        events[numberOfRowsInEventLog -1] = newLine + "\n";

        for(String a : events){
            try {
                eventLog.getStyledDocument().insertString(eventLog.getStyledDocument().getLength(), a, keySet);

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEventLog(){
        eventLog.setText("");
        try {
            eventLog.getStyledDocument().insertString(0, spacesFromTop, forSpaces);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        try {
            for(String a : events){
                if(a.equals(spacesFromTop)) continue;
                try {
                    eventLog.getStyledDocument().insertString(eventLog.getStyledDocument().getLength(), a, keySet);

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
        }
    }

    String getInputText() {
        return playerInput.getText();
    }

    void clearEventLog() {
        eventLog.setText(events[0]);
        for(int i = 0; i < numberOfRowsInEventLog; i++){
            events[i] = " \n";
        }
    }

    void clearInput() {
        playerInput.setText("");
    }

    void changeEnterAction(Main.OnEnter onEnter, Main.OnShiftEnter onShiftEnter) {
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        KeyStroke sEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);
        String enterBinding = enter.toString();
        String sEnterBinding = sEnter.toString();

        InputMap inputMap = playerInput.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = playerInput.getActionMap();

        inputMap.put(enter, enterBinding);
        inputMap.put(sEnter, sEnterBinding);
        actionMap.put(sEnterBinding, onShiftEnter);
        actionMap.put(enterBinding, onEnter);
    }

    void clearEvents() {
        for(int i = 0; i < numberOfRowsInEventLog; i++){
            events[i] = " \n";
        }
    }

    void addToEvents(String s){
         ArrayList<String> stringsToAdd = new ArrayList<>();
        if((float)s.length() / 40.0f > 1.0f){
            stringsToAdd = splitString(s);
        }else{
            stringsToAdd.add(s);
        }
        if(stringsToAdd.size() > numberOfRowsInEventLog){
            for(int i = 0; i < numberOfRowsInEventLog; i++){
                updateEventLog(stringsToAdd.get(i));
                stringsToAdd.remove(i);
            }
            isWaiting = true;
            waitingStrings = stringsToAdd;
        } else{
            for(String i: stringsToAdd){
                updateEventLog(i);
            }
            stringsToAdd.clear();
        }
    }

    private ArrayList<String> splitString(String s) {
        String[] splitString = s.split("\\s");
        ArrayList<String> strings = new ArrayList<>();
        boolean firstIteration = true;
        int i = 0;

        for(String a: splitString){
            if(firstIteration){
                strings.add(a + " ");
                firstIteration = false;
            }
            else if(strings.get(i).length() + a.length() + 1<= 40){
                strings.set(i, strings.get(i) + a + " ");
            } else{
                i++;
                strings.add(a + " ");
            }
        }
        return strings;
    }

    void waitOver() {
        if(waitingStrings.size() > numberOfRowsInEventLog){
            for(int i = 0; i < numberOfRowsInEventLog; i++){
                updateEventLog(waitingStrings.get(0));
                waitingStrings.remove(0);
            }
            isWaiting = true;
        } else{
            for(int i = 0; i < waitingStrings.size(); i++){
                updateEventLog(waitingStrings.get(0));
                waitingStrings.remove(0);
            }
            waitingStrings.clear();
        }
    }

    void updateCharStats(boolean backwards){
        updateCharStats(backwards, false);
    }
    void updateCharStats(boolean backwards, boolean noCycle) {
        if (!backwards) {
            switch(currentDisplayInCharstats){
                case 0:
                    if(noCycle){
                        setCharStatsToArray(charStatsDisplay);
                        return;
                    }
                    setCharStatsToArray(inventoryDiplay);
                    currentDisplayInCharstats++;
                    return;
                case 1:
                    if(noCycle){
                        setCharStatsToArray(inventoryDiplay);
                        return;
                    }
                    setCharStatsToArray(equippedDisplay);
                    currentDisplayInCharstats++;
                    return;
                case 2:
                    if(noCycle){
                        setCharStatsToArray(equippedDisplay);
                        return;
                    }
                    setCharStatsToArray(charStatsDisplay);
                    currentDisplayInCharstats = 0;
            }
        } else{
            switch(currentDisplayInCharstats){
                case 0:
                    if(noCycle) {
                        setCharStatsToArray(charStatsDisplay);
                        return;
                    }
                    setCharStatsToArray(equippedDisplay);
                    currentDisplayInCharstats = 2;
                    return;
                case 1:
                    if(noCycle){
                        setCharStatsToArray(inventoryDiplay);
                        return;
                    }
                    setCharStatsToArray(charStatsDisplay);
                    currentDisplayInCharstats--;
                    return;
                case 2:
                    if(noCycle){
                        setCharStatsToArray(equippedDisplay);
                        return;
                    }
                    setCharStatsToArray(inventoryDiplay);
                    currentDisplayInCharstats--;
            }
        }
    }

    private void setCharStatsToArray(ArrayList<String> str) {
        charInfo.setText("");
        try {
            charInfo.getStyledDocument().insertString(0, spacesFromTop, forSpaces);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        try {
            for(String a : str){
                try {
                    charInfo.getStyledDocument().insertString(charInfo.getStyledDocument().getLength(), a, keySet);

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
        }
    }
}
