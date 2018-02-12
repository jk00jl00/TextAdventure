package InteracteblesPackage;

import InteracteblesPackage.Chest;
import InteracteblesPackage.Door;
import InteracteblesPackage.Table;

public interface InteractionListener {
    void doorInteracted(Door d);
    void tableInteracted(Table t);
    void chestOpened(Chest c);
}
