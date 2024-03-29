package viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import model.Player;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn = findViewById(R.id.startButton);
        Button exitBtn =  findViewById(R.id.exitButton);

        Player.resetPlayer();

        startBtn.setOnClickListener(v -> {
            Intent game = new Intent(MainActivity.this, ConfigScreen.class);
            startActivity(game);
        });

        exitBtn.setOnClickListener(v -> {
            finishAffinity();  // Close all activities and exit the app
            System.exit(0);
        });

    }
}