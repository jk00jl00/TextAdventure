package ItemsPackage;

public class Gold extends Item {


    public static Gold playerGold = new Gold(10);
    public int amount;

    public Gold(int amount){
        this.value = 1;
        this.amount = amount;
        this.description = "Gold";
    }

    public static void incrementGold(int amount){
        playerGold.value += amount;
    }

    public static Gold getPlayerGold() {
        return playerGold;
    }

    public static void setPlayerGold(Gold playerGold) {
        Gold.playerGold = playerGold;
    }

}
