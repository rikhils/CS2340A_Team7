package viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import model.GameObject;
import model.Player;

public class GameScreen2 extends AppCompatActivity implements ScoreObserver, HealthObserver {

    private TextView playerScoreText;

    private TextView playerHealthText;

    private PlayerView playerView;


    private int playerY = 1320;
    private int playerX = 280;  //Ashan

    private final int moveSpeed = 40;

    private RelativeLayout gameLayout; //Ashan

    private Point screenSize;

    private EnemyView ghostView;
    private EnemyView batView;
    private int ghostX;
    private int ghostY;
    private int batX;
    private int batY;

    private PowerupView heartPowerupView;

    private int heartPowerupX;
    private int heartPowerupY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen2);

        GameObject gameObject = GameObject.getGameObject();
        Player player = gameObject.getPlayer();
        Player.getPlayer().addScoreObserver(this);
        Player.getPlayer().addHealthObserver(this);

        // Display difficulty
        TextView difficultyText = (TextView) findViewById(R.id.difficulty);
        difficultyText.setText(gameObject.getDifficulty());

        // Display player name
        TextView playerNameText = (TextView) findViewById(R.id.gamePlayerName);
        playerNameText.setText(player.getName());

        // Display player Health
        playerHealthText = (TextView) findViewById(R.id.playerHealth);
        playerHealthText.setText("Health: " + Integer.toString(player.getHealth()));

        // Display player sprite
        String spriteName = player.getSprite().getImageName();


        // Get the screen size
        Display display = getWindowManager().getDefaultDisplay();
        screenSize = new Point();
        display.getSize(screenSize);

        playerX = 250;
        playerY = 500;

        //AShan
        // Create character
        gameLayout = findViewById(R.id.gameLayout2);
        playerView = new PlayerView(this, playerX, playerY, spriteName);
        gameLayout.addView(playerView);


        // Display score
        playerScoreText = (TextView) findViewById(R.id.playerScore);
        playerScoreText.setText("Score: " + Integer.toString(player.getScore()));

        // Enemy 1: ghost
        ghostX = 370;
        ghostY = 740;

        // Enemy 2: bat
        batX = 690;
        batY = 1520;

        // Create enemy
        ghostView = new EnemyView(this, ghostX, ghostY, "Ghost");
        ghostView.startMoving();
        gameLayout.addView(ghostView);
        batView = new EnemyView(this, batX, batY, "Bat");
        batView.startMoving();
        gameLayout.addView(batView);

        // create heart powerup
        heartPowerupX = 410;
        heartPowerupY = 1140;

        // create heart view
        heartPowerupView = new PowerupView(this, heartPowerupX, heartPowerupY, "HeartPowerup");
        gameLayout.addView(heartPowerupView);

    }


    @Override
    public void onScoreChanged(int newScore) {
        playerScoreText = (TextView) findViewById(R.id.playerScore);
        playerScoreText.setText("Score: " + Integer.toString(newScore));

        if (newScore == 0) {
            Intent game = new Intent(GameScreen2.this, EndScreen.class);
            startActivity(game);
        }
    }

    public void onHealthChanged(int newHealth) {
        playerHealthText = (TextView) findViewById(R.id.playerHealth);
        playerHealthText.setText("Health: " + Integer.toString(newHealth));

        if (newHealth <= 0) {
            Intent game = new Intent(GameScreen2.this, EndScreen.class);
            startActivity(game);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Player player = Player.getPlayer();

        int newX = player.getPlayerX();
        int newY = player.getPlayerY();
        switch (keyCode) {
        case KeyEvent.KEYCODE_DPAD_DOWN:
            newX = playerX;
            newY = playerY + moveSpeed;
            break;
        case KeyEvent.KEYCODE_DPAD_UP:
            newX = playerX;
            newY = playerY - moveSpeed;
            break;
        case KeyEvent.KEYCODE_DPAD_LEFT:
            newX = playerX - moveSpeed;
            newY = playerY;
            break;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            newX = playerX + moveSpeed;
            newY = playerY;
            break;
        //Ashan's change - key to remove bat
        case KeyEvent.KEYCODE_1:
            // Check for collision with the enemy
            if (Math.abs(playerX - batX) < 400 && Math.abs(playerY - batY) < 400) {
                // Remove the bat from the screen
                Log.d("Enemy moving", "It works");
                batView.stopMovingAndRemove(this);
                batX = 0;
                batY = 0;
            }
            if (Math.abs(playerX - ghostX) < 400 && Math.abs(playerY - ghostY) < 400) {
                // Remove the ghost from the screen
                Log.d("Enemy moving", "It works");
                ghostView.stopMovingAndRemove(this);
                ghostX = 0;
                ghostY = 0;
            }
            break;
        default:
            break;
        }

        boolean legalMove = false;
        // top square, vertical passage, horizontal passage, vertical passage, horizontal passage
        if (newX >= 130 && newX <= 610 && newY >= 220 && newY <= 740) {
            legalMove = true;
        } else if (newX >= 410 && newX <= 530 && newY >= 700 && newY <= 1580) {
            legalMove = true;
        } else if (newX >= 410 && newX <= 930 && newY >= 1500 && newY <= 1580) {
            legalMove = true;
        } else if (newX >= 850 && newX <= 930 && newY >= 260 && newY <= 1580) {
            legalMove = true;
        } else if (newX >= 850 && newY >= 260 && newY <= 340) {
            legalMove = true;
        }

        if (legalMove) {
            player.playerMovement(playerX, playerY, screenSize); // frontend position updated here
            player.onKeyDown(keyCode, moveSpeed, event); // backend position is updated here
        }

        playerX = player.getPlayerX();
        playerY = player.getPlayerY();

        // ghost collision check
        if (Math.abs(playerX - ghostX) < 100 && Math.abs(playerY - ghostY) < 60) {
            player.takeDamage();
        }

        // bat collision check
        if (Math.abs(playerX - batX) < 100 && Math.abs(playerY - batY) < 60) {
            player.takeDamage();
        }

        // heart powerup collision check
        if (Math.abs(playerX - heartPowerupX) < 100 && Math.abs(playerY - heartPowerupY) < 60) {
            player.activateHeartPowerup();
            heartPowerupView.remove();
            heartPowerupX = 0;
            heartPowerupY = 0;
        }

        if (playerX + moveSpeed >= screenSize.x - screenSize.x / 8) {
            if (playerY <= 340) {
                Intent game = new Intent(this, GameScreen3.class);
                startActivity(game);
            }
        }
        playerView.updatePosition(playerX, playerY);
        //Log.d("position", "x:" + playerX + "y:" + playerY);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // Logic to stop player movement when a key is released
        // You may need to add logic here to stop the player's movement.
        return true;
    }

}
