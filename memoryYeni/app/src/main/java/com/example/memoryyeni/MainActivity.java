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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText username, pass;
    Button btnKayit, btnGiris;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txtName);
        pass = findViewById(R.id.txtPass);
        btnGiris = findViewById(R.id.giris);
        btnKayit = findViewById(R.id.kayitOl);


        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String password = pass.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Tüm alanlar doldurulmalıdır.",Toast.LENGTH_SHORT).show();

                }
                else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("userlist")
                            .document(user)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        if (doc.exists()) {
                                            if(doc.get("password").equals(password)){
                                                Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
                                                startActivity(intent);
                                                CardGame.getInstance().setUsername(user);
                                                CardGame.getInstance().setGameLevel((String) doc.get("current_level"));
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Şifreler uyuşmamaktadır.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Bu isimle kayıtlı kullanıcı bulunmamaktadır.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }


            }
        });
        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });
    }
}