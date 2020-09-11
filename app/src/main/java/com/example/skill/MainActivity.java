package com.example.skill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText Etname, Etpass;
    Button button, button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Etname = findViewById(R.id.name);
        Etpass = findViewById(R.id.pass);
        Button button = findViewById(R.id.login);
        Button button2 = findViewById(R.id.create);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Etname.getText().toString();
                String password = Etpass.getText().toString();
                if (email.isEmpty() && password.isEmpty()) {
                    Etname.setError("can't be empty");
                    Etname.requestFocus();
                    Etpass.setError("can't be empty");
                    Etpass.requestFocus();
                } else if (email.isEmpty()) {
                    Etname.setError("can't be empty");
                    Etname.requestFocus();
                    //Toast.makeText(MainActivity.this, "please enter username and password", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Etpass.setError("can't be empty");
                    Etpass.requestFocus();
                    // Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's informatio
                                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                                        finish();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Register and Try Again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                // Check for a valid password.
                if (password.isEmpty()) {
                    Etpass.setError("please enter password");
                } else if (password.length() < 6) {
                    Etpass.setError("password is too weak");
                    Etpass.requestFocus();
                } else {
                }


            }
        });
    }
}

