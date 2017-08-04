package com.tvs.Split;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tvs.Activity.Home;
import com.tvs.R;

import Shared.Config;

public class User_Type extends AppCompatActivity {
    private String[] Type = {"RETAILER", "MECHANIC", "ELECTRICIAN"};
    private int TypeValue;
    EditText ed_type;
    String type;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
        setContentView(R.layout.activity_type);
        ed_type = (EditText) findViewById(R.id.ed_type);
        ed_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(User_Type.this);
                int count = Type.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(Type, is_checked,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton, boolean isChecked) {
                            }
                        });

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ListView list = ((AlertDialog) dialog).getListView();
                                // make selected item in the comma seprated string
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < list.getCount(); i++) {
                                    boolean checked = list.isItemChecked(i);
                                    if (checked) {
                                        if (stringBuilder.length() > 0) stringBuilder.append(",");
                                        stringBuilder.append(list.getItemAtPosition(i));
                                    }
                                }
                                if (stringBuilder.toString().trim().equals("")) {

                                    ed_type.setText("");
                                    stringBuilder.setLength(0);

                                } else {

                                    ed_type.setText(stringBuilder);
                                    type = ed_type.getText().toString().trim();


                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_type.setText("");
                                type = ed_type.getText().toString().trim();
                                Toast.makeText(User_Type.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void next(View view) {
        try {
            type = ed_type.getText().toString().trim();
            if (type.equals("")) {
//            ed_type.setError("Please Enter type");
                Toast.makeText(this, "Please Select User_Type", Toast.LENGTH_SHORT).show();

            } else {
                sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("KEY_type", type);
                editor.apply();
                startActivity(new Intent(User_Type.this, Persional.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Home(View view) {
        startActivity(new Intent(this, Home.class));
    }

    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
