package com.example.memoryyeni;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ScoreBoard extends AppCompatActivity {

    TextView firstlv4name, firstlv4score, secondlv4name, secondlv4score, thirdlv4name, thirdlv4score;
    TextView firstlv5name, firstlv5score, secondlv5name, secondlv5score, thirdlv5name, thirdlv5score;
    TextView firstlv6name, firstlv6score, secondlv6name, secondlv6score, thirdlv6name, thirdlv6score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        setTexts();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query nextQuery = db.collection("userlist")
                .orderBy("highscore_level_4", Query.Direction.DESCENDING)
                .limit(3);

        nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                firstlv4name.setText(value.getDocuments().get(value.size() - 3).get("username").toString());
                firstlv4score.setText(value.getDocuments().get(value.size() - 3).get("highscore_level_4").toString());
                secondlv4name.setText(value.getDocuments().get(value.size() - 2).get("username").toString());
                secondlv4score.setText(value.getDocuments().get(value.size() - 2).get("highscore_level_4").toString());
                thirdlv4name.setText(value.getDocuments().get(value.size() - 1).get("username").toString());
                thirdlv4score.setText(value.getDocuments().get(value.size() - 1).get("highscore_level_4").toString());
            }
        });

        nextQuery = db.collection("userlist")
                .orderBy("highscore_level_5", Query.Direction.DESCENDING)
                .limit(3);

        nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                firstlv5name.setText(value.getDocuments().get(value.size() - 3).get("username").toString());
                firstlv5score.setText(value.getDocuments().get(value.size() - 3).get("highscore_level_5").toString());
                secondlv5name.setText(value.getDocuments().get(value.size() - 2).get("username").toString());
                secondlv5score.setText(value.getDocuments().get(value.size() - 2).get("highscore_level_5").toString());
                thirdlv5name.setText(value.getDocuments().get(value.size() - 1).get("username").toString());
                thirdlv5score.setText(value.getDocuments().get(value.size() - 1).get("highscore_level_5").toString());
            }
        });

        nextQuery = db.collection("userlist")
                .orderBy("highscore_level_6", Query.Direction.DESCENDING)
                .limit(3);

        nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                firstlv6name.setText(value.getDocuments().get(value.size() - 3).get("username").toString());
                firstlv6score.setText(value.getDocuments().get(value.size() - 3).get("highscore_level_6").toString());
                secondlv6name.setText(value.getDocuments().get(value.size() - 2).get("username").toString());
                secondlv6score.setText(value.getDocuments().get(value.size() - 2).get("highscore_level_6").toString());
                thirdlv6name.setText(value.getDocuments().get(value.size() - 1).get("username").toString());
                thirdlv6score.setText(value.getDocuments().get(value.size() - 1).get("highscore_level_6").toString());
            }
        });

    }

    public void setTexts() {
        firstlv4name = findViewById(R.id.namelvl4first);
        firstlv4score = findViewById(R.id.scorelvl4first);
        secondlv4name = findViewById(R.id.namelvl4second);
        secondlv4score = findViewById(R.id.scorelvl4second);
        thirdlv4name = findViewById(R.id.namelvl4third);
        thirdlv4score = findViewById(R.id.scorelvl4third);

        firstlv5name = findViewById(R.id.namelvl5first);
        firstlv5score = findViewById(R.id.scorelvl5first);
        secondlv5name = findViewById(R.id.namelvl5second);
        secondlv5score = findViewById(R.id.scorelvl5second);
        thirdlv5name = findViewById(R.id.namelvl5third);
        thirdlv5score = findViewById(R.id.scorelvl5third);

        firstlv6name = findViewById(R.id.namelvl6first);
        firstlv6score = findViewById(R.id.scorelvl6first);
        secondlv6name = findViewById(R.id.namelvl6second);
        secondlv6score = findViewById(R.id.scorelvl6second);
        thirdlv6name = findViewById(R.id.namelvl6third);
        thirdlv6score = findViewById(R.id.scorelvl6third);

    }
}