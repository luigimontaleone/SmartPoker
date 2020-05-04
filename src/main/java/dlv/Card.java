package dlv;

public class Card {
    public static String clubs = "clubs";
    public static String diamonds = "diamonds";
    public static String spades = "spades";
    public static String hearts = "hearts";

    protected int number; //ace -> 0, two -> 1 etc etc
    protected String seed;

    public Card(int number, String seed) {
        this.number = number;
        this.seed = seed;
    }

    public int getNumber() {
        return number;
    }

    public String getSeed() {
        return seed;
    }
}
