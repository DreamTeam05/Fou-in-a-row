package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private List<String> lst;
    private List<Recipe> rec;
    private ListView listRecipe;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        BottomNavigationView botNav = findViewById(R.id.bottom_navigation );
        botNav.setOnNavigationItemSelectedListener(navListener);
//        ImageView im = findViewById(R.id.favoriteP);
//        im.setVisibility(View.VISIBLE);
        init();
        getDataFromDB();
        setOnClickItem();
    }

    public void init() {
        listRecipe = findViewById(R.id.fav_rec);
        lst = new ArrayList<>();
        rec = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, lst);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Избранное").child("1");
    }

    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (lst.size()>0) lst.clear();
                if (rec.size()>0) rec.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Recipe recipe = ds.getValue(Recipe.class);
                    assert recipe!= null;
                    lst.add(ds.getKey());
                    rec.add(recipe);
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
                Intent i = new Intent(Favorites.this, show_recipe_favorites.class);
                i.putExtra("rec",recipe.рецепт);
                i.putExtra("pos", position);
                i.putExtra("name",lst.get(position));
                startActivity(i);
            }
        });
    }

//    public void toHome (View v) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFrag = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFrag = new HomePage();
                            break;
                        case R.id.nav_favorites:
                            selectedFrag = new FavoritesPage();
                            break;
                    }

                    TextView txt = (TextView) findViewById(R.id.text2);
                    ListView lv = (ListView) findViewById(R.id.fav_rec);
                    txt.setVisibility(View.GONE);
                    lv.setVisibility(View.GONE);

//                    ImageView im = findViewById(R.id.favoriteP);
//                    im.setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFrag).commit();

                    return true;
                }
            };

    public void search_recipe_by_ing (View v) {
        Intent intent = new Intent("android.intent.action.searchRecByIng");
        startActivity(intent);
    }


    public void search_recipe (View view) {
        Intent intent = new Intent("android.intent.action.search_recipe");
        startActivity(intent);
    }
}
