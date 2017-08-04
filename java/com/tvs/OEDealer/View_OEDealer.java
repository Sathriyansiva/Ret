package com.tvs.OEDealer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tvs.Activity.Home;
import com.tvs.Activity.ViewAll;
import com.tvs.R;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.ViewSearchOEDealer_Data;
import Adapter.ViewSearch_Data;
import Adapter.ViewallOEDealer_Data;
import Model.FilterData.Filter;
import Model.Predictive.Citypredictive;
import Model.ViewOEdealer.ViewOE;
import Model.ViewOEdealer.ViewOEdata;
import Model.ViewSearchOEdealer.FilterOEdata;
import Model.ViewSearchOEdealer.FilterOe;
import RetroClient.RetroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_OEDealer extends AppCompatActivity {
    ListView list_view;
    private List<ViewOEdata> viewalloe;
    ViewallOEDealer_Data adapter;
    RadioButton rb_all;
    AutoCompleteTextView et_city;
    List<String> Cityarray;
    String city;
    List<FilterOEdata> viewall1;
    private ViewSearchOEDealer_Data adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__oedealer);
        et_city = (AutoCompleteTextView) findViewById(R.id.city);
        et_city.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        viewall1 = new ArrayList<>();

        Cityarray = new ArrayList<String>();
        getcity();
        rb_all = (RadioButton) findViewById(R.id.all);
        list_view = (ListView) findViewById(R.id.gridlist);
        viewalloe = new ArrayList<>();
        rb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View();
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
                        ArrayAdapter adapter = new ArrayAdapter(View_OEDealer.this, android.R.layout.simple_list_item_1, Cityarray);
                        et_city.setAdapter(adapter);
                        city = et_city.getText().toString();

                    } else {
                        Toast.makeText(View_OEDealer.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Citypredictive> call, Throwable t) {
                    Toast.makeText(View_OEDealer.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void View() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(View_OEDealer.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            // Calling JSON

            Call<ViewOE> call = service.getallOEdetails();

            call.enqueue(new Callback<ViewOE>() {
                @Override
                public void onResponse(Call<ViewOE> call, Response<ViewOE> response) {
                    //Dismiss Dialog
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {
                        list_view.setVisibility(View.VISIBLE);
                        viewalloe = response.body().getData();

                        adapter = new ViewallOEDealer_Data(View_OEDealer.this, viewalloe);
                        list_view.setAdapter(adapter);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(View_OEDealer.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewOE> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(View_OEDealer.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
public void Search(View view){
    city = et_city.getText().toString();
    SearchView();
}
    private void SearchView() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(View_OEDealer.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<FilterOe> call = service.SearchOE(city);
            call.enqueue(new Callback<FilterOe>() {
                @Override
                public void onResponse(Call<FilterOe> call, Response<FilterOe> response) {
                    if (response.body().getResult().equals("Success")) {
                        progressDialog.dismiss();
                        viewall1 = response.body().getData();

                        adapter1 = new ViewSearchOEDealer_Data(View_OEDealer.this, viewall1);
                        list_view.setAdapter(adapter1);
                        et_city.getText().clear();
                        list_view.setVisibility(View.VISIBLE);
                    } else {

                        progressDialog.dismiss();
                        et_city.getText().clear();
                        list_view.setVisibility(View.GONE);
                        Toast.makeText(View_OEDealer.this, "Sorry No Data Found", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<FilterOe> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
