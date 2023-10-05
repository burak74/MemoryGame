package com.example.memoryyeni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity {

    Button  lvl1, lvl2, lvl3, lvl4, lvl5, lvl6, scores;
    TextView username;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        getHighScores();


        username = findViewById(R.id.textView);
        lvl1 = findViewById(R.id.btnLevel1);
        lvl2 = findViewById(R.id.btnLvl2);
        lvl3 = findViewById(R.id.btnLvl3);
        lvl4 = findViewById(R.id.btnLvl4);
        lvl5 = findViewById(R.id.btnLvl5);
        lvl6 = findViewById(R.id.btnLvl6);
        scores = findViewById(R.id.scrBoard);


        switch (CardGame.getInstance().getGameLevel()) {
            case "1":
                setLevels(true, false, false, false, false, false);
                break;
            case "2":
                setLevels(true, true, false, false, false, false);
                break;
            case "3":
                setLevels(true, true, true, false, false, false);
                break;
            case "4":
                setLevels(true, true, true, true, false, false);
                break;
            case "5":
                setLevels(true, true, true, true, true, false);
                break;
            case "6":
                setLevels(true, true, true, true, true, true);
                break;
            default:
                break;
        }


        username.setText("Hoşgeldin " + CardGame.getInstance().getUsername() + "!");

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LevelActivity.this, ScoreBoard.class);
                startActivity(intent);

            }
        });

        lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardGame.getInstance().setCurrentLevel(1);
                CardGame.getInstance().setRow(2);
                CardGame.getInstance().setCoulmn(4);
                CardGame.getInstance().setTimer(false);
                intent = new Intent(LevelActivity.this, GamePlayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardGame.getInstance().setCurrentLevel(2);
                CardGame.getInstance().setRow(3);
                CardGame.getInstance().setCoulmn(4);
                CardGame.getInstance().setTimer(false);
                intent = new Intent(LevelActivity.this, GamePlayActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardGame.getInstance().setCurrentLevel(3);
                CardGame.getInstance().setRow(4);
                CardGame.getInstance().setCoulmn(4);
                CardGame.getInstance().setTimer(false);
                intent = new Intent(LevelActivity.this, GamePlayActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardGame.getInstance().setCurrentLevel(4);
                CardGame.getInstance().setRow(2);
                CardGame.getInstance().setCoulmn(4);
                CardGame.getInstance().setTimeRemaining(10);
                CardGame.getInstance().setTimer(true);
                intent = new Intent(LevelActivity.this, GamePlayActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardGame.getInstance().setCurrentLevel(5);
                CardGame.getInstance().setRow(3);
                CardGame.getInstance().setCoulmn(4);
                CardGame.getInstance().setTimeRemaining(15);
                CardGame.getInstance().setTimer(true);
                intent = new Intent(LevelActivity.this, GamePlayActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lvl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardGame.getInstance().setCurrentLevel(6);
                CardGame.getInstance().setRow(4);
                CardGame.getInstance().setCoulmn(4);
                CardGame.getInstance().setTimeRemaining(25);
                CardGame.getInstance().setTimer(true);
                intent = new Intent(LevelActivity.this, GamePlayActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void setLevels(boolean lv1, boolean lv2, boolean lv3, boolean lv4, boolean lv5, boolean lv6) {
        lvl1.setEnabled(lv1);
        lvl2.setEnabled(lv2);
        lvl3.setEnabled(lv3);
        lvl4.setEnabled(lv4);
        lvl5.setEnabled(lv5);
        lvl6.setEnabled(lv6);
    }

    public void getHighScores() {
        CardGame.getInstance().setHighscores(new ArrayList<>());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("userlist")
                .document(CardGame.getInstance().getUsername())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            CardGame.getInstance().getHighscores().add(String.valueOf(doc.get("highscore_level_1")));
                            CardGame.getInstance().getHighscores().add(String.valueOf(doc.get("highscore_level_2")));
                            CardGame.getInstance().getHighscores().add(String.valueOf(doc.get("highscore_level_3")));
                            CardGame.getInstance().getHighscores().add(String.valueOf(doc.get("highscore_level_4")));
                            CardGame.getInstance().getHighscores().add(String.valueOf(doc.get("highscore_level_5")));
                            CardGame.getInstance().getHighscores().add(String.valueOf(doc.get("highscore_level_6")));
                        }
                    }
                });
    }
}