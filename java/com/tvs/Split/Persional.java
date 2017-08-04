package com.tvs.Split;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.tvs.Activity.Home;
import com.tvs.R;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Model.Predictive.Citypredictive;
import Model.Predictive.StatePridictive;
import RetroClient.RetroClient;
import Shared.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Persional extends AppCompatActivity {
    private String[] state = {"Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh",
            "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
            "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
            "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",};
    private int stateValue;
    EditText ed_reg_Ownername, ed_reg_Shopname, ed_reg_Door, ed_reg_Street, ed_reg_Area,
            ed_reg_Landmark, ed_reg_National, ed_reg_Pincode,
            ed_reg_Mobile1, ed_reg_Mobile2, ed_reg_Email, ed_reg_Gst;
    AutoCompleteTextView ed_reg_State, ed_reg_City;
    String Ownername, Shopname, DoorNo, Street, Area, Landmark, City, State, National, Pincode, Mobile1, Mobile2, Email, GstNo;
    AlertDialog.Builder builder;
    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<String> Cityarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional);
        builder = new AlertDialog.Builder(Persional.this);
        Cityarray = new ArrayList<String>();
        ed_reg_Ownername = (EditText) findViewById(R.id.reg_ownname);
        ed_reg_Shopname = (EditText) findViewById(R.id.reg_shopname);
        ed_reg_Door = (EditText) findViewById(R.id.reg_door_no);
        ed_reg_Street = (EditText) findViewById(R.id.reg_street);
        ed_reg_Area = (EditText) findViewById(R.id.reg_area);
        ed_reg_Landmark = (EditText) findViewById(R.id.reg_landmark);
        ed_reg_City = (AutoCompleteTextView) findViewById(R.id.reg_city);
        ed_reg_State = (AutoCompleteTextView) findViewById(R.id.reg_satet);
        ed_reg_National = (EditText) findViewById(R.id.reg_national);
        ed_reg_Pincode = (EditText) findViewById(R.id.reg_pincode);
        ed_reg_Mobile1 = (EditText) findViewById(R.id.reg_mob1);
        ed_reg_Mobile2 = (EditText) findViewById(R.id.reg_mob2);
        ed_reg_Email = (EditText) findViewById(R.id.reg_email);
        ed_reg_Gst = (EditText) findViewById(R.id.reg_gst);

        ed_reg_Ownername.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_Shopname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_Door.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_Street.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_Area.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_Landmark.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_City.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_State.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_National.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_reg_Gst.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        getcity();
        ed_reg_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ed_reg_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ed_reg_City.setText("");
            }

        });
        ed_reg_City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Statedata();
            }
        });
    }



    public void getcity() {
        try {
            CategoryAPI service = RetroClient.getApiService();

            // Calling JSON

            Call<Citypredictive> call = service.getallCity();

            call.enqueue(new Callback<Citypredictive>() {
                @Override
                public void onResponse(Call<Citypredictive> call, Response<Citypredictive> response) {
                    //Dismiss Dialog
                    if (response.isSuccessful()) {
                        Cityarray = response.body().getData();
                        ArrayAdapter adapter = new ArrayAdapter(Persional.this, android.R.layout.simple_list_item_1, Cityarray);
                        ed_reg_City.setAdapter(adapter);
                        City = ed_reg_City.getText().toString();

                    } else {
                        Toast.makeText(Persional.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Citypredictive> call, Throwable t) {
                    Toast.makeText(Persional.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Statedata() {
        try {
            City = ed_reg_City.getText().toString();
            CategoryAPI service = RetroClient.getApiService();

            Call<StatePridictive> call = service.getstate(City);
            call.enqueue(new Callback<StatePridictive>() {
                @Override
                public void onResponse(Call<StatePridictive> call, Response<StatePridictive> response) {
                    if (response.body().getResult().equals("Success")) {
                        State = response.body().getData();
                        ed_reg_State.setText(State);
                    } else {


                        Toast.makeText(Persional.this, "Sorry No Data Found", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<StatePridictive> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void next(View view) {
        try {
            //pers
            National = ed_reg_National.getText().toString().trim();
            Ownername = ed_reg_Ownername.getText().toString().trim();
            Shopname = ed_reg_Shopname.getText().toString();
            DoorNo = ed_reg_Door.getText().toString();
            Street = ed_reg_Street.getText().toString();
            Area = ed_reg_Area.getText().toString();
            Landmark = ed_reg_Landmark.getText().toString();
            City = ed_reg_City.getText().toString();
            State = ed_reg_State.getText().toString();
            National = ed_reg_National.getText().toString();
            Pincode = ed_reg_Pincode.getText().toString();
            Mobile1 = ed_reg_Mobile1.getText().toString();
            Mobile2 = ed_reg_Mobile2.getText().toString();
            Email = ed_reg_Email.getText().toString();
            GstNo = ed_reg_Gst.getText().toString();
            if (Shopname.equals("")) {
                Toast.makeText(this, "Please Enter Shop name", Toast.LENGTH_SHORT).show();
                ed_reg_Shopname.setError("Please Enter Shop name");
                ed_reg_Shopname.setFocusable(true);
            } else if (Ownername.equals("")) {
                ed_reg_Ownername.setError("Please Enter Owner name");
                ed_reg_Ownername.setFocusable(true);
                Toast.makeText(this, "Please Enter Owner name", Toast.LENGTH_SHORT).show();

            } else if (Mobile1.equals("") || Mobile1.length() != 10) {
                ed_reg_Mobile1.setError("Please Enter Mobile Number");
                Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                ed_reg_Mobile1.setFocusable(true);

            } else if (DoorNo.equals("")) {
                Toast.makeText(this, "Please Enter DoorNo", Toast.LENGTH_SHORT).show();
                ed_reg_Door.setError("Please Enter DoorNo");
                ed_reg_Door.setFocusable(true);

            } else if (Street.equals("")) {
                ed_reg_Street.setError("Please Enter Street");
                Toast.makeText(this, "Please Enter Street", Toast.LENGTH_SHORT).show();
                ed_reg_Street.setFocusable(true);

            } else if (Area.equals("")) {
                ed_reg_Area.setError("Please Enter Area");
                Toast.makeText(this, "Please Enter Area", Toast.LENGTH_SHORT).show();
                ed_reg_Area.setFocusable(true);

            } else if (City.equals("")) {
                ed_reg_City.setError("Please Enter City");
                Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();
                ed_reg_City.setFocusable(true);

            } else if (State.equals("")) {
                Toast.makeText(this, "Please Enter Citypredictive", Toast.LENGTH_SHORT).show();
//            ed_reg_State.setError("Please Enter Citypredictive");
            } else if (Pincode.equals("") || Pincode.length() != 6) {
                ed_reg_Pincode.setError("Please Enter Pincode");
                Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();
                ed_reg_Pincode.setFocusable(true);

            } else if (Email.equals("") || !Email.matches(emailPattern)) {
                ed_reg_Email.setError("Please Enter Email");
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                ed_reg_Email.setFocusable(true);

            } else if (GstNo.equals("")) {
                ed_reg_Gst.setError("Please Enter GSt No.");
                Toast.makeText(this, "Please Enter GST No.", Toast.LENGTH_SHORT).show();
                ed_reg_Gst.setFocusable(true);

            } else {
                sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("KEY_Ownername", Ownername);
                editor.putString("KEY_Shopname", Shopname);
                editor.putString("KEY_DoorNo", DoorNo);
                editor.putString("KEY_Street", Street);
                editor.putString("KEY_Area", Area);
                editor.putString("KEY_Landmark", Landmark);
                editor.putString("KEY_City", City);
                editor.putString("KEY_State", State);
                editor.putString("KEY_Contry", National);
                editor.putString("KEY_Pincode", Pincode);
                editor.putString("KEY_Mobile1", Mobile1);
                editor.putString("KEY_Mobile2", Mobile2);
                editor.putString("KEY_Email", Email);
                editor.putString("KEY_GstNo", GstNo);
                editor.apply();
                startActivity(new Intent(Persional.this, Shop.class));
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
