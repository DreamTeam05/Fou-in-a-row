package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class searchRecByIng extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rec_by_ing);
        init();
        getDataFromDB("");
        setOnClickItem();
        ing = findViewById(R.id.ing);
        ing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingstr = ing.getText().toString().toLowerCase();
                init();
                getDataFromDB(ingstr);
                setOnClickItem();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private List<String> lst;
    private List<Recipe> rec;
    private ListView listRecipe;
    private ArrayAdapter<String> adapter;
    private String str;
    private ArrayList ingl;
    private String ingstr;
    private int k;

    private EditText ing;

//    public void searchRecipeInDataset(View v) {
//        ing = findViewById(R.id.ing);
//        System.out.println(ing.getText().toString());
//        ingstr = ing.getText().toString().toLowerCase();
//        init();
//        getDataFromDB(ingstr);
//        setOnClickItem();
//    }

    private void init() {
        listRecipe = findViewById(R.id.recIng);
        lst = new ArrayList<>();
        rec = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, lst);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Рецепты");
    }

    private void getDataFromDB (final String str ){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (lst.size()>0) lst.clear();
                if (rec.size()>0) rec.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Recipe recipe = ds.getValue(Recipe.class);
                    assert recipe!= null;
                    if (recipe.getИнгредиенты().toLowerCase().contains(str)) {
                        lst.add(ds.getKey());
                        rec.add(recipe);
                    }
                }
                //if (lst.size()==0) { lst.add ("Нет такого рецепта");}
                listRecipe.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(vListener);
    }

    private void setOnClickItem() {
        listRecipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = rec.get(position);
                Intent i = new Intent(searchRecByIng.this, show_recipe.class);
                i.putExtra("rec",recipe.рецепт);
                i.putExtra("ing",recipe.ингредиенты);
                i.putExtra("name",lst.get(position));
                startActivity(i);
            }
        });
    }
}
