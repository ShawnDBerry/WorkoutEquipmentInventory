package com.example.exerciseinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ItemQuantitySelectionActivity extends AppCompatActivity {

    public static String EQUIPMENT_KEY = "equipment_key";

    private TextView equipTextView;
    private EditText equipEditView;
    private Button equipmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_quantity_selection);

        equipTextView = findViewById(R.id.equipment_selection_textview);
        equipEditView =findViewById(R.id.equipment_quantity_edit_text);
        equipmentButton = findViewById(R.id.equipment_quantity_select_button);

        String messageReceived = getIntent().getStringExtra(MainActivity.EQUIPMENT_KEY);

        equipTextView.setText(messageReceived);

        equipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equipEditView.getText().toString().length() > 0){
                    String tempMessage = equipTextView.getText().toString();
                    Intent replyIntent = new Intent(ItemQuantitySelectionActivity.this, MainActivity.class);
                    replyIntent.putExtra(MainActivity.EQUIPMENT_KEY, tempMessage + " " + equipEditView.getText().toString());
                    setResult(AppCompatActivity.RESULT_OK, replyIntent);

                    finish();
                }
                else{
                    Toast.makeText(ItemQuantitySelectionActivity.this, "Message must not be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
