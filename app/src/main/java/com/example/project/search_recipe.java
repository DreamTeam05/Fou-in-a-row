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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class search_recipe extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private List<String> lst;
    private List<Recipe> rec;
    private ListView listRecipe;
    private ArrayAdapter<String> adapter;
    private String dishstr;

    private EditText dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        getDataFromDB("");
        setOnClickItem();
        dish = findViewById(R.id.dish);
        dish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dishstr = dish.getText().toString().toLowerCase();
                init();
                getDataFromDB(dishstr);
                setOnClickItem();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void init() {
        listRecipe = findViewById(R.id.rec);
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
                    if (ds.getKey().toLowerCase().contains(str)) {
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
                Intent i = new Intent(search_recipe.this, show_recipe.class);
                i.putExtra("rec",recipe.рецепт);
                i.putExtra("ing",recipe.ингредиенты);
                i.putExtra("name",lst.get(position));
                startActivity(i);
            }
        });
    }


//    public void searchRecipeInDataset (View v) {
//        dish = findViewById(R.id.dish);
//        dishstr = dish.getText().toString().toLowerCase();
//        init();
//        getDataFromDB(dishstr);
//        setOnClickItem();
//    }

//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    Fragment selectedFrag = null;
//
//                    switch (menuItem.getItemId()) {
//                        case R.id.nav_home:
//                            selectedFrag = new HomePage();
//                            break;
//                        case R.id.nav_favorites:
//                            selectedFrag = new FavoritesPage();
//                            break;
//                    }
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            selectedFrag).commit();
//
//                    return true;
//                }
//            };
}
