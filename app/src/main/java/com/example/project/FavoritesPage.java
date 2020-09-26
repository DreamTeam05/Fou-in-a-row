package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
public class FavoritesPage extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private List<String> lst;
    private List<Recipe> rec;
    private ListView listRecipe;
    private ArrayAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_favorites, container, false);
    }

    public void init() {
        Intent intent = new Intent("android.intent.action.Favorites");
        startActivity(intent);
    }
}
