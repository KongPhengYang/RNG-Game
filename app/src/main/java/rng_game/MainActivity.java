package rng_game;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Scene;
import androidx.transition.TransitionManager;

import com.example.rng_game.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Scene activityMainScene;
    private Scene instructionScene;
    private Scene rng_gameScene;
    private Scene listScene;
    private Scene endScene;
    private TextView numberDisplay;
    private TextView pointCounter;
    private Button spinButton;
    private TextView rarityDescription;
    private int points = 0;
    private final int[] numbers = {1, 2, 3, 5, 10, 50, 777, 1000, 123}; // Available numbers for spin
    private final int[] pointsForNumbers = {1, 1, 1, 2, 2, 3, 10, 10, 10}; // Points for each number
    private final String[] rarityLabels = {"Common", "Uncommon", "Rare", "Epic", "Legendary"};
    private final int[] rarityColors = {Color.GREEN, Color.BLUE, Color.parseColor("#800080"), Color.parseColor("#FFA500"), Color.RED};
    private final double[] rarityChances = {73.9, 20.0, 5.0, 1.0, 0.1}; // Percentage chances

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup mainView = findViewById(R.id.main);

        activityMainScene = Scene.getSceneForLayout(mainView, R.layout.activity_main, this);
        instructionScene = Scene.getSceneForLayout(mainView, R.layout.instruction, this);
        rng_gameScene = Scene.getSceneForLayout(mainView, R.layout.rng_game, this);
        listScene = Scene.getSceneForLayout(mainView, R.layout.list, this);
        endScene = Scene.getSceneForLayout(mainView, R.layout.end, this);
    }

    public void onStartClick(View view) {
        TransitionManager.go(instructionScene);
    }

    public void onExitClick(View view) {
        finish();
    }

    public void onBackClick(View view) {
        TransitionManager.go(rng_gameScene);

        reinitializeRngGameScene();
    }

    public void onListClick(View view) {
        TransitionManager.go(listScene);
    }

    public void onPlayClick(View view) {
        TransitionManager.go(rng_gameScene);

        reinitializeRngGameScene();
    }

    private void reinitializeRngGameScene() {
        numberDisplay = findViewById(R.id.randomNumberDisplay);
        pointCounter = findViewById(R.id.pointsCounter);
        spinButton = findViewById(R.id.spinButton);
        rarityDescription = findViewById(R.id.rarityDescription);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin();
            }
        });
        pointCounter.setText("Points: " + points);
    }

    private void spin() {
        final Random random = new Random();
        final Handler handler = new Handler();
        final int[] spinCount = {0};
        final int maxSpins = 20; // Amount of animation spins

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (spinCount[0] < maxSpins) {
                    int randomIndex = random.nextInt(numbers.length);
                    numberDisplay.setText(String.valueOf(numbers[randomIndex]));
                    spinCount[0]++;
                    handler.postDelayed(this, 100); // Adjust delay for faster or slower spinning
                } else {
                    int finalNumber = getRandomNumberBasedOnRarity();
                    numberDisplay.setText(String.valueOf(finalNumber));
                    int finalIndex = getIndexOfNumber(finalNumber);
                    points += pointsForNumbers[finalIndex];
                    pointCounter.setText("Points: " + points);

                    if (points >= 10) { //points to end scene
                        TransitionManager.go(endScene);
                    }
                    setRarityColor(finalNumber);
                }
            }
        }, 100);
    }

    private int getRandomNumberBasedOnRarity() {
        Random random = new Random();
        double chance = random.nextDouble() * 100; // Random number between 0 and 100
        if (chance < rarityChances[0]) { // Common
            return getNumberFromRarity(0);
        } else if (chance < rarityChances[0] + rarityChances[1]) { // Uncommon
            return getNumberFromRarity(1);
        } else if (chance < rarityChances[0] + rarityChances[1] + rarityChances[2]) { // Rare
            return getNumberFromRarity(2);
        } else if (chance < rarityChances[0] + rarityChances[1] + rarityChances[2] + rarityChances[3]) { // Epic
            return getNumberFromRarity(3);
        } else { // Legendary
            return getNumberFromRarity(4);
        }
    }

    private int getNumberFromRarity(int rarityIndex) {
        switch (rarityIndex) {
            case 0: // Common
                return numbers[0];
            case 1: // Uncommon
                return numbers[3];
            case 2: // Rare
                return numbers[6];
            case 3: // Epic
                return numbers[7];
            case 4: // Legendary
                return numbers[8];
            default:
                return numbers[0];
        }
    }

    private int getIndexOfNumber(int number) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == number) {
                return i;
            }
        }
        return -1;
    }

    private void setRarityColor(int number) {
        String rarity = "";
        int color = Color.BLACK;

        if (number == 1 || number == 2 || number == 3) { // Common
            rarity = "Common 73.9% chance of obtaining";
            color = Color.GREEN;
        } else if (number == 5 || number == 10 || number == 50) { // Uncommon
            rarity = "Uncommon 20% chance of obtaining";
            color = Color.BLUE;
        } else if (number == 777 || number == 1000 || number == 123) { // Rare
            rarity = "Rare 5% chance of obtaining";
            color = Color.parseColor("#800080");
        } else if (number == 23 || number == 1000000) { // Epic
            rarity = "Epic 1% chance of obtaining";
            color = Color.parseColor("#FFA500");
        } else if (number == 0) { // Legendary
            rarity = "Legendary 0.1% chance of obtaining";
            color = Color.RED;
        }

        numberDisplay.setTextColor(color);
        rarityDescription.setText(rarity);
    }
}
