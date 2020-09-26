package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView botNav = findViewById(R.id.bottom_navigation );
        botNav.setOnNavigationItemSelectedListener(navListener);
    }

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

                    ImageView im = (ImageView) findViewById(R.id.mainP);
                    im.setVisibility(View.GONE);

                    //TextView txt = (TextView)findViewById(R.id.welcome);
                    //txt.setVisibility(View.GONE);

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
