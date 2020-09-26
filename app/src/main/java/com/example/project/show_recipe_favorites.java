package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class show_recipe_favorites extends AppCompatActivity {
    private TextView txtrec;
    private String rec;
    private String name_rec;
    private FirebaseDatabase database;
    private DatabaseReference ref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);
        Button b = (Button) findViewById(R.id.b_from_favorite);
        b.setVisibility(View.VISIBLE);
        init();
        getIntentMain();
    }


    private void init() {
        txtrec = findViewById(R.id.text_rec);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Избранное").child("1");
    }

    private void getIntentMain () {
        Intent i = getIntent();
        if (i != null){
            rec = i.getStringExtra("rec");
            name_rec = i.getStringExtra("name");
            txtrec.setText(rec);
        }
    }

    public void fromFavorite(View view) {
        System.out.println(ref.child(name_rec));
        ref.child(name_rec).removeValue();
        Button b = (Button) findViewById(R.id.b_from_favorite);
        b.setVisibility(View.GONE);
    }

}
