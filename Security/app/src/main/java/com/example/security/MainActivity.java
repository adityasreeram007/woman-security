package com.example.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class MainActivity  extends AppCompatActivity implements View.OnClickListener {
    private EditText edi;
    private EditText pass;
    private Button bt;
    private FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edi=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        bt=(Button)findViewById(R.id.button);
        bt.setOnClickListener(this);
        fauth=FirebaseAuth.getInstance();






    }
    @Override
    public void onClick(View view)
    {
        try {
            String x = edi.getText().toString();
            String p = pass.getText().toString();
            fauth.signInWithEmailAndPassword(x, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        catch(Exception e)
        {
            Toast ts = Toast.makeText(MainActivity.this, "login Failed!!", Toast.LENGTH_SHORT);
            ts.show();
        }


    }

}
