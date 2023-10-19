package Model;

import java.util.Date;

public class Player implements Comparable<Player> {

    private static Player player;
    private String name;
    private Sprite sprite;
    private int health;

    private int score;

    private Date date;

    private int playerX; //- Ashan
    private int playerY; //- Ashan

    static int MAX_SCORE = 999;

    private Player() {
        this.name = null;
        this.sprite = null;
        this.health = 0;
        this.score = MAX_SCORE;
        this.date = new Date();
    }

    public static Player getPlayer() {
        if (player == null) {
            player = new Player();
        }

        return player;
    }

    public void setPlayer(String name, String spriteName, int health) {
        this.name = name;
        this.sprite = new Sprite(spriteName);
        this.health = health;
        this.score = MAX_SCORE;
        this.date = new Date();
    }

    public Player copy() {
        Player returnPlayer = new Player();

        returnPlayer.name = this.name;
        returnPlayer.health = this.health;
        returnPlayer.sprite = this.sprite;
        returnPlayer.score = this.score;
        returnPlayer.date = this.date;

        return returnPlayer;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getScore() { return score;}

    public Date getDate() {return date;}

    public void subScore(int amount) {
        score -= amount;

        if (score < 0) {
            score = 0;
        }
    }

public void setPlayerX (int x) {
        playerX = x;
}

    public void setPlayerY (int y) {
        playerY = y;
    }

    public int getPlayerX () {
        return playerX;
    }

    public int getPlayerY () {
        return playerY;
    }



    @Override
    public int compareTo(Player compPlayer){
        return compPlayer.score - this.score;
    }
}


