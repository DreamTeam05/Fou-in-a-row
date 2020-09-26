package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class show_recipe extends AppCompatActivity {

    private TextView txtrec;
    private String rec;
    private String ing;
    private String name_rec;
    private FirebaseDatabase database;
    private DatabaseReference ref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);
        init();
        getIntentMain();
        checkFavorites();
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
            ing = i.getStringExtra("ing");
            txtrec.setText(rec);
            name_rec = i.getStringExtra("name");
        }
    }


    public void toFavorite(View view) {
        ref.child(name_rec).child("рецепт").setValue(rec);
        ref.child(name_rec).child("ингредиенты").setValue(ing);
        Button b = (Button) findViewById(R.id.b_to_favorite);
        b.setVisibility(View.GONE);
    }

    private void checkFavorites() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k=0;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Recipe recipe = ds.getValue(Recipe.class);
                    assert recipe!= null;
                    if (name_rec.equals(ds.getKey())){
                        k=1;
                    }
                }
                if (k==0) {
                    Button b = (Button) findViewById(R.id.b_to_favorite);
                    b.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(vListener);
    }
}
