package com.example.exerciseinventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //current Equipment in the gym
    private int currentUserCount = 0;

    //SharedPreferences object we will use to read from sp
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    //currentMembers Key for SharedPreferences
    public static String EQUIPMENT_KEY = "equipment_key";

    final List equipmentlist = new ArrayList<String>();
    final List equipmentInventory = new ArrayList<String>();


    //Member key prefix
    private final String equipKeyPrefix = "equipment_id_";

    //equipment list views
    private ListView equipmentListView, avaliableExerciseListView;

    private TextView equipmentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equipmentlist.add("Dumb Bells");
        equipmentlist.add("Bar Bells");
        equipmentlist.add("Exercise Ball");
        equipmentlist.add("Exercise Rope");
        equipmentlist.add("BenchPress");
        equipmentlist.add("Squat Rack");

        //initializing sharedpreferences object
        sharedPreferences = getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);

        //initializing equipment list views
        equipmentListView = findViewById(R.id.exercise_equipment_menu_listview);
        avaliableExerciseListView = findViewById(R.id.exercise_equipment_available_listview);
        readGymItems();
        readPurchasedItems();

        final ArrayAdapter equipAdapter = new ArrayAdapter<String>(this,
                R.layout.exercise_equipment_item_layout,
                R.id.equipment_info_textview,
                equipmentlist);


        equipmentListView.setAdapter(equipAdapter);

        equipmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                equipmentItem = view.findViewById(R.id.equipment_info_textview);
                String message = equipmentItem.getText().toString().trim();

                if(message.length() > 0){
                    Intent secondActIntent = new Intent(MainActivity.this, ItemQuantitySelectionActivity.class);

                    secondActIntent.putExtra(ItemQuantitySelectionActivity.EQUIPMENT_KEY, message);

                    startActivityForResult(secondActIntent, 707);

                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 707 && resultCode == AppCompatActivity.RESULT_OK){
            String messageIn = data.getStringExtra(EQUIPMENT_KEY);

            equipmentInventory.add(messageIn);
            readPurchasedItems();
            readGymItems();
            saveToSharedPreferences(messageIn);
            Toast.makeText(this, messageIn, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToSharedPreferences(String message){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("my_string_key",  message);
        editor.apply();
    }
    public void readPurchasedItems(){
        final ArrayAdapter equipQuantityAdapter = new ArrayAdapter<String>(this,
                R.layout.exercise_equipment_item_layout,
                R.id.equipment_info_textview,
                equipmentInventory);
        avaliableExerciseListView.setAdapter(equipQuantityAdapter);

    }


    private void readGymItems(){
        currentUserCount = sharedPreferences.getInt("equipment_key", 0);

        for(int i = 0; i < currentUserCount; i++){
            equipmentInventory.add(sharedPreferences.getString(equipKeyPrefix+i, "default name"));
        }

        /*memberNameText.setText(stringBuilder.toString());*/

    }


}
