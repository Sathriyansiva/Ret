package com.tvs.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.R;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.ViewSearch_Data;
import Adapter.Viewall_Data;
import Model.FilterData.Filter;
import Model.FilterData.SearchData;
import Model.Predictive.Citypredictive;
import Model.ViewAllData.Datum;
import RetroClient.RetroClient;
import Shared.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAll extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 100;
    AutoCompleteTextView ed_state, ed_city;
    EditText ed_type;
    private String[] state = {"Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh",
            "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
            "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
            "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",};
    private int stateValue;

    private String[] Type = {"RETAILER", "MECHANIC", "ELECTRICIAN"};
    private int TypeValue;
    AlertDialog.Builder builder;
    private List<Datum> viewall;
    List<SearchData> viewall1;
    private Viewall_Data adapter;
    private ViewSearch_Data adapter1;
    ListView list_view;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name;
    TextView tv_name;
    String type;
    String STATE, CITY, TYPE;
    RadioButton rb_all;
    List<String> Cityarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        builder = new AlertDialog.Builder(ViewAll.this);
        rb_all = (RadioButton) findViewById(R.id.all);
        ed_state = (AutoCompleteTextView) findViewById(R.id.state);
        ed_city = (AutoCompleteTextView) findViewById(R.id.city);
        ed_type = (EditText) findViewById(R.id.type);
        ed_city.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_state.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        viewall1 = new ArrayList<>();
        Cityarray = new ArrayList<String>();
        viewall = new ArrayList<>();
        tv_name = (TextView) findViewById(R.id.vw_username);
        sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("KEY_Name", null);
//        tv_name.setText(name);
        list_view = (ListView) findViewById(R.id.gridlist);

        getcity();
        ed_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(state, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_state.setText(state[which]);
                        stateValue = which + 1;
                    }
                });
                builder.show();
            }
        });

        ed_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewAll.this);
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
                                Toast.makeText(ViewAll.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        rb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View();
            }
        });
//        GetPermission();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void GetPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Snackbar.make(findViewById(R.id.viewall), "You need give permission", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View v) {
                            //your action here
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
                        }
                    }).show();

                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
                }
            } else {
                //TrackLocation();
            }
        } else

        {
            //TrackLocation();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Snackbar.make(findViewById(R.id.viewall), "Permission Granted",
                    Snackbar.LENGTH_LONG).show();
           // TrackLocation();

        } else {

            Snackbar.make(findViewById(R.id.viewall), "Permission denied",
                    Snackbar.LENGTH_LONG).show();

        }
    }



    private void View() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(ViewAll.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            // Calling JSON

            Call<Model.ViewAllData.View> call = service.getalldetails();

            call.enqueue(new Callback<Model.ViewAllData.View>() {
                @Override
                public void onResponse(Call<Model.ViewAllData.View> call, Response<Model.ViewAllData.View> response) {
                    //Dismiss Dialog
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {
                        list_view.setVisibility(View.VISIBLE);

                        viewall = response.body().getData();

                        adapter = new Viewall_Data(ViewAll.this, viewall);
                        list_view.setAdapter(adapter);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ViewAll.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Model.ViewAllData.View> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(ViewAll.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                        ArrayAdapter adapter = new ArrayAdapter(ViewAll.this, android.R.layout.simple_list_item_1, Cityarray);
                        ed_city.setAdapter(adapter);
                        CITY = ed_city.getText().toString();

                    } else {
                        Toast.makeText(ViewAll.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Citypredictive> call, Throwable t) {
                    Toast.makeText(ViewAll.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Search(View view) {
        try {
            STATE = ed_state.getText().toString();
            CITY = ed_city.getText().toString();
            TYPE = ed_type.getText().toString();
            rb_all.clearFocus();
            SearchDatas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SearchDatas() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(ViewAll.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Filter> call = service.Search(TYPE, STATE, CITY);
            call.enqueue(new Callback<Filter>() {
                @Override
                public void onResponse(Call<Filter> call, Response<Filter> response) {
                    if (response.body().getResult().equals("Success")) {
                        progressDialog.dismiss();
                        viewall1 = response.body().getData();

                        adapter1 = new ViewSearch_Data(ViewAll.this, viewall1);
                        list_view.setAdapter(adapter1);
                        ed_state.getText().clear();
                        ed_city.getText().clear();
                        ed_type.getText().clear();
                        list_view.setVisibility(View.VISIBLE);
                    } else {

                        progressDialog.dismiss();
                        ed_state.getText().clear();
                        ed_city.getText().clear();
                        ed_type.getText().clear();
                        list_view.setVisibility(View.GONE);
                        Toast.makeText(ViewAll.this, "Sorry No Data Found", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Filter> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
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
