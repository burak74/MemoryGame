package com.example.memoryyeni;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;

import androidx.gridlayout.widget.GridLayout;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {
    CountDownTimer timer = new CountDownTimer(100, 100) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {

        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelActivity.class));
        timer.cancel();
        finish();
    }

    TextView currentScoreLb2, remainingTimeLb2;
    boolean data = false;
    String topPoint = "";


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);


        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setRowCount(CardGame.getInstance().getRow());
        gridLayout.setColumnCount(CardGame.getInstance().getCoulmn());

        Random rand = new Random();
        int size = CardGame.getInstance().getRow() * CardGame.getInstance().getCoulmn();
        CardGame.getInstance().setImageList(new ArrayList<>());
        CardGame.getInstance().setImageIds(new ArrayList<>());

        for (int i = 0; i < size / 2; i++) {
            CardGame.getInstance().getImageIds().add(String.valueOf(i));
            CardGame.getInstance().getImageIds().add(String.valueOf(i));
        }

        for (int i = 0; i < size; i++) {
            int randominteger = rand.nextInt(CardGame.getInstance().getImageIds().size());
            ImageButton button = new ImageButton(this);
            button.setBackgroundColor(Color.parseColor("#948b72"));
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.height = 250;
            layoutParams.width = 250;
            button.setLayoutParams(layoutParams);
            button.setTag(R.id.flipstatus, "0");
            button.setTag(R.id.imagestatus, CardGame.getInstance().getImageIds().get(randominteger));
            Log.d("Image status", i + ". Buttons status -> " + button.getTag(R.id.imagestatus));
            CardGame.getInstance().getImageIds().remove(randominteger);
            gridLayout.addView(button, i);
            button.setId(i);
            CardGame.getInstance().getImageList().add(button);
            setImage(button);
            button.setEnabled(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (ImageButton btn3 : CardGame.getInstance().getImageList()) {
                    btn3.setImageResource(R.drawable.questionbrown);
                    btn3.setEnabled(true);
                }
                startTimer();
            }
        }, 2000);


        for (ImageButton btn : CardGame.getInstance().getImageList()) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btn.getTag(R.id.flipstatus).equals("0")) {
                        setImage(btn);
                        btn.setTag(R.id.flipstatus, "1");
                    } else {
                        btn.setImageResource(R.drawable.questionbrown);
                        btn.setTag(R.id.flipstatus, "0");
                    }

                    for (ImageButton btn2 : CardGame.getInstance().getImageList()) {
                        if (btn.getId() != btn2.getId() && btn2.getTag(R.id.flipstatus).equals("1")) {
                            for (ImageButton btn3 : CardGame.getInstance().getImageList()) {
                                btn3.setClickable(false);
                            }
                            if (btn.getTag(R.id.flipstatus).equals(btn2.getTag(R.id.flipstatus)) && btn.getTag(R.id.imagestatus).equals(btn2.getTag(R.id.imagestatus))) {
                                CardGame.getInstance().getImageList().remove(btn);
                                CardGame.getInstance().getImageList().remove(btn2);
                                btn.setClickable(false);
                                btn2.setClickable(false);
                                givePoint();

                                if (CardGame.getInstance().getImageList().isEmpty()) {
                                    levelComplete();
                                }

                                for (ImageButton btn3 : CardGame.getInstance().getImageList()) {
                                    btn3.setClickable(true);
                                }
                            } else {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        btn.setTag(R.id.flipstatus, "0");
                                        btn2.setTag(R.id.flipstatus, "0");
                                        btn.setImageResource(R.drawable.questionbrown);
                                        btn2.setImageResource(R.drawable.questionbrown);
                                        for (ImageButton btn3 : CardGame.getInstance().getImageList()) {
                                            btn3.setClickable(true);
                                        }
                                    }
                                }, 350);
                            }
                            break;
                        }
                    }
                }
            });
        }


        switch (CardGame.getInstance().getCurrentLevel()) {
            case 1:
                topPoint = CardGame.getInstance().getHighscores().get(0);
                break;
            case 2:
                topPoint = CardGame.getInstance().getHighscores().get(1);
                break;
            case 3:
                topPoint = CardGame.getInstance().getHighscores().get(2);
                break;
            case 4:
                topPoint = CardGame.getInstance().getHighscores().get(3);
                break;
            case 5:
                topPoint = CardGame.getInstance().getHighscores().get(4);
                break;
            case 6:
                topPoint = CardGame.getInstance().getHighscores().get(5);
                break;

        }

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView topScoreLb = new TextView(this);
        topScoreLb.setId(1);
        topScoreLb.setPadding(320, 0, 0, 50);
        topScoreLb.setText("En Yüksek Puan : " + topPoint);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.RIGHT_OF, topScoreLb.getId());
        TextView topScoreLb2 = new TextView(this);
        topScoreLb2.setId(2);
        topScoreLb2.setPadding(20, 0, 0, 0);
        topScoreLb2.setText("");

        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params4.addRule(RelativeLayout.BELOW, topScoreLb.getId());
        TextView currentScoreLb = new TextView(this);
        currentScoreLb.setId(5);
        currentScoreLb.setPadding(350, 0, 0, 50);
        currentScoreLb.setText("Puan : ");

        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params5.addRule(RelativeLayout.BELOW, topScoreLb.getId());
        params5.addRule(RelativeLayout.RIGHT_OF, currentScoreLb.getId());
        currentScoreLb2 = new TextView(this);
        currentScoreLb2.setId(6);
        currentScoreLb2.setPadding(20, 0, 0, 0);
        currentScoreLb2.setText("0");

        RelativeLayout.LayoutParams timeLabelParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        timeLabelParam.addRule(RelativeLayout.BELOW, currentScoreLb.getId());
        TextView remainingTimeLb = new TextView(this);
        remainingTimeLb.setId(3);
        remainingTimeLb.setPadding(350, 0, 0, 50);
        remainingTimeLb.setText("Kalan Süre : ");

        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params3.addRule(RelativeLayout.BELOW, currentScoreLb.getId());
        params3.addRule(RelativeLayout.RIGHT_OF, remainingTimeLb.getId());
        remainingTimeLb2 = new TextView(this);
        remainingTimeLb2.setId(4);
        remainingTimeLb2.setPadding(20, 0, 0, 0);
        remainingTimeLb2.setText("");


        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout outsideLayout = new RelativeLayout(this);

        if (CardGame.getInstance().isTimer()) {
            params6.addRule(RelativeLayout.BELOW, remainingTimeLb.getId());
            outsideLayout.addView(remainingTimeLb, timeLabelParam);
            outsideLayout.addView(remainingTimeLb2, params3);
        } else {
            params6.addRule(RelativeLayout.BELOW, currentScoreLb.getId());
        }

        outsideLayout.addView(topScoreLb);
        outsideLayout.addView(topScoreLb2, params2);
        outsideLayout.addView(currentScoreLb, params4);
        outsideLayout.addView(currentScoreLb2, params5);
        outsideLayout.addView(gridLayout, params6);
        outsideLayout.setGravity(Gravity.CENTER);
        setContentView(outsideLayout);


    }

    public void setImage(ImageButton button) {
        int drawable;
        if (button.getTag(R.id.imagestatus).equals("0")) {
            drawable = R.drawable.test1;
        } else if (button.getTag(R.id.imagestatus).equals("1")) {
            drawable = R.drawable.test2;
        } else if (button.getTag(R.id.imagestatus).equals("2")) {
            drawable = R.drawable.test3;
        } else if (button.getTag(R.id.imagestatus).equals("3")) {
            drawable = R.drawable.test4;
        } else if (button.getTag(R.id.imagestatus).equals("4")) {
            drawable = R.drawable.test5;
        } else if (button.getTag(R.id.imagestatus).equals("5")) {
            drawable = R.drawable.test6;
        } else if (button.getTag(R.id.imagestatus).equals("6")) {
            drawable = R.drawable.test7;
        } else {
            drawable = R.drawable.test8;
        }
        button.setImageResource(drawable);
    }

    public void levelComplete() {
        String currentLevelDbField = "";

        if (Integer.parseInt(CardGame.getInstance().getGameLevel()) <= CardGame.getInstance().getCurrentLevel()) {
            Map<String, Object> docData = new HashMap<>();
            switch (CardGame.getInstance().getCurrentLevel()) {
                case 1:
                    CardGame.getInstance().setGameLevel("2");
                    currentLevelDbField = "highscore_level_1";
                    break;
                case 2:
                    CardGame.getInstance().setGameLevel("3");
                    currentLevelDbField = "highscore_level_2";
                    break;
                case 3:
                    CardGame.getInstance().setGameLevel("4");
                    currentLevelDbField = "highscore_level_3";
                    break;
                case 4:
                    CardGame.getInstance().setGameLevel("5");
                    currentLevelDbField = "highscore_level_4";
                    break;
                case 5:
                    CardGame.getInstance().setGameLevel("6");
                    currentLevelDbField = "highscore_level_5";
                    break;
                case 6:
                    CardGame.getInstance().setGameLevel("6");
                    currentLevelDbField = "highscore_level_6";
                    break;
                default:
            }
            docData.put(currentLevelDbField, Integer.parseInt(currentScoreLb2.getText().toString()));
            docData.put("current_level", CardGame.getInstance().getGameLevel());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("userlist")
                    .document(CardGame.getInstance().getUsername())
                    .update(docData);
            //TODO : LEVEL UP
        } else {
            Map<String, Object> docData = new HashMap<>();
            switch (CardGame.getInstance().getCurrentLevel()) {
                case 1:
                    currentLevelDbField = "highscore_level_1";
                    break;
                case 2:
                    currentLevelDbField = "highscore_level_2";
                    break;
                case 3:
                    currentLevelDbField = "highscore_level_3";
                    break;
                case 4:
                    currentLevelDbField = "highscore_level_4";
                    break;
                case 5:
                    currentLevelDbField = "highscore_level_5";
                    break;
                case 6:
                    currentLevelDbField = "highscore_level_6";
                    break;
                default:
            }
            if(Integer.parseInt(topPoint) < Integer.parseInt(currentScoreLb2.getText().toString())){
                docData.put(currentLevelDbField, Integer.parseInt(currentScoreLb2.getText().toString()));
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("userlist")
                        .document(CardGame.getInstance().getUsername())
                        .update(docData);
            }
            //TODO : UPDATE HIGH SCORE
        }




        AlertDialog.Builder builder = new AlertDialog.Builder(GamePlayActivity.this);
        builder.setTitle("Bölüm tamamlandı.");
        builder.setMessage("Kazandığınız puan : " + currentScoreLb2.getText().toString());
        builder.setCancelable(false);
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), LevelActivity.class));
                finish();
            }
        }).show();

        if (CardGame.getInstance().isTimer()) {
            timer.cancel();
        }
    }


    public void levelLose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GamePlayActivity.this);
        builder.setTitle("Kaybettin");
        builder.setMessage("Kazandığınız puan : " + currentScoreLb2.getText().toString());
        builder.setCancelable(false);
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), LevelActivity.class));
                finish();
            }
        }).show();

        if (CardGame.getInstance().isTimer()) {
            timer.cancel();
        }
    }

    public void givePoint() {
        switch (CardGame.getInstance().getCurrentLevel()) {
            case 1:
                currentScoreLb2.setText(String.valueOf(Integer.parseInt(currentScoreLb2.getText().toString()) + 10));
                break;
            case 2:
                currentScoreLb2.setText(String.valueOf(Integer.parseInt(currentScoreLb2.getText().toString()) + 20));
                break;
            case 3:
                currentScoreLb2.setText(String.valueOf(Integer.parseInt(currentScoreLb2.getText().toString()) + 30));
                break;
            case 4:
                currentScoreLb2.setText(String.valueOf(Integer.parseInt(currentScoreLb2.getText().toString()) + (Integer.parseInt(remainingTimeLb2.getText().toString()) * 4)));
                break;
            case 5:
                currentScoreLb2.setText(String.valueOf(Integer.parseInt(currentScoreLb2.getText().toString()) + (Integer.parseInt(remainingTimeLb2.getText().toString()) * 5)));
                break;
            case 6:
                currentScoreLb2.setText(String.valueOf(Integer.parseInt(currentScoreLb2.getText().toString()) + (Integer.parseInt(remainingTimeLb2.getText().toString()) * 6)));
                break;
            default:

        }
    }

    public void startTimer(){
        if (CardGame.getInstance().isTimer()) {
            timer = new CountDownTimer(CardGame.getInstance().getTimeRemaining() * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    remainingTimeLb2.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    levelLose();
                }
            }.start();
        }
    }

}