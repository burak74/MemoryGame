package com.example.memoryyeni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username_kayit, password_kayit, repassword_kayit;
    Button btnKayit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_kayit = findViewById(R.id.txtUsername);
        password_kayit = findViewById(R.id.txtPassword);
        repassword_kayit = findViewById(R.id.txtRePassword);
        btnKayit = findViewById(R.id.btnKayit);




        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username_kayit.getText().toString();
                String pass = password_kayit.getText().toString();
                String repass = repassword_kayit.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
                    Toast.makeText(getApplicationContext(), "Hiçbir alan boş bırakılmamalıdır.", Toast.LENGTH_SHORT).show();

                } else if (!pass.equals(repass)) {
                    Toast.makeText(getApplicationContext(), "Şifreler uyuşmamaktadır.", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("userlist")
                            .document(user)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        if (doc.exists()) {
                                            Toast.makeText(RegisterActivity.this, "Bu isimle kayıtlı kullanıcı bulunmaktadır.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            HashMap<String, Object> rUser = new HashMap<>();
                                            rUser.put("username", user);
                                            rUser.put("password", pass);
                                            rUser.put("current_level", "1");
                                            rUser.put("highscore_level_1", 0);
                                            rUser.put("highscore_level_2", 0);
                                            rUser.put("highscore_level_3", 0);
                                            rUser.put("highscore_level_4", 0);
                                            rUser.put("highscore_level_5", 0);
                                            rUser.put("highscore_level_6", 0);
                                            db.collection("userlist").document(user).set(rUser);
                                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            //finish();
                                            onBackPressed();
                                        }
                                    }
                                }
                            });
                }
            }
        });


    }
}