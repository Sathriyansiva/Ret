package com.tvs.OEDealer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.tvs.Activity.Home;
import com.tvs.Activity.ViewAll;
import com.tvs.R;
import com.tvs.Split.Capture;
import com.tvs.Split.Shop;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Model.AddOEdealer.RegOEdealer;
import Model.Predictive.Citypredictive;
import Model.Register.Register;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_OEDealer extends AppCompatActivity {
    private String[] OEMDEALER = {"TATA Motors – PCD", "ATA Motors – CV",
            "Maruti Suzuki", "Hyundai", "Volvo Eicher", "Ashok Leyland", "Bummins India", "Bajaj",
            "Greaves", "KOEL", "SML Izuzu", "Force Motors", "AL Nissan", "M&M Tractor",
            "M&M –UV", "M&M - LCV", "M&M – Buses & Trucks ", "TAFE ", "Punjab Tractors – Swaraj ", "International Tractors Ltd – Sonalika" };
    private int OEMDEALERValue;

    EditText et_oem,  et_cmpnyname, et_dealercode, et_adrs, et_loc, et_cntctname,
            et_cntctphone, et_contctdesin, et_cnctemail, et_custmcmnt, et_acblcmnt, et_networkcmnt;
    RadioGroup rg_custm, rg_accbl, rg_network;
    RadioButton rb_custm, rb_accbl, rb_network;
    String oem, city, cmpnyname, dealercode, adrs, loc, cntctname,
            cntctphone, contctdesin, cnctemail, custm, accbl, network,custmcmnt, acblcmnt, networkcmnt;
    int selectedId,selectedId2,selectedId3;
    AutoCompleteTextView et_city;
    AlertDialog.Builder builder;
    NetworkConnection net;

    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    int PLACE_PICKER_REQUEST = 1;
    String LAT, LONG,CaptureLocation;
    List<String> Cityarray;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__oedealer);

        net = new NetworkConnection(Add_OEDealer.this);
        builder = new AlertDialog.Builder(Add_OEDealer.this);
        et_oem = (EditText) findViewById(R.id.reg_oem_dealer);
        et_city = (AutoCompleteTextView) findViewById(R.id.reg_City);
        et_cmpnyname = (EditText) findViewById(R.id.reg_Company_Name);
        et_dealercode = (EditText) findViewById(R.id.reg_Dealer_Code);
        et_adrs = (EditText) findViewById(R.id.reg_Address);
        et_loc = (EditText) findViewById(R.id.reg_location);
        et_cntctname = (EditText) findViewById(R.id.reg_Name);
        et_cntctphone = (EditText) findViewById(R.id.reg_Phone);
        et_contctdesin = (EditText) findViewById(R.id.reg_Designation);
        et_cnctemail = (EditText) findViewById(R.id.reg_email);
        et_custmcmnt = (EditText) findViewById(R.id.reg_customer_service_cmd);
        et_acblcmnt = (EditText) findViewById(R.id.reg_accessible_cmd);
        et_networkcmnt = (EditText) findViewById(R.id.reg_network_cmd);
        rg_custm = (RadioGroup) findViewById(R.id.cs_select_mode);
        rg_accbl = (RadioGroup) findViewById(R.id.acc_select_mode);
        rg_network = (RadioGroup) findViewById(R.id.net_select_mode);

        et_oem .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_city .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_cmpnyname .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_dealercode .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_adrs .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_loc .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_cntctname .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_contctdesin .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_custmcmnt .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_acblcmnt .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        et_networkcmnt .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Cityarray = new ArrayList<String>();
        getcity();
        et_oem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(OEMDEALER, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_oem.setText(OEMDEALER[which]);
                        OEMDEALERValue = which + 1;
                        oem = et_oem.getText().toString();
                    }
                });
                builder.show();
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
                        ArrayAdapter adapter = new ArrayAdapter(Add_OEDealer.this, android.R.layout.simple_list_item_1, Cityarray);
                        et_city.setAdapter(adapter);
                        city = et_city.getText().toString();

                    } else {
                        Toast.makeText(Add_OEDealer.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Citypredictive> call, Throwable t) {
                    Toast.makeText(Add_OEDealer.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void locaioncap(View view) {

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(Add_OEDealer.this), PLACE_PICKER_REQUEST);
        }catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();
            Toast.makeText(Add_OEDealer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
            Toast.makeText(Add_OEDealer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == PLACE_PICKER_REQUEST) {
                if (resultCode == RESULT_OK) {
                    try {
                        Place place = PlacePicker.getPlace(data, this);
                        loc = String.format("Place: %s", place.getName());
                        LAT=String.valueOf(place.getLatLng().latitude);
                        LONG=String.valueOf(place.getLatLng().longitude);

                        Toast.makeText(this, loc+LAT+LONG, Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                        e.printStackTrace();
//                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Save(View view) {
        try {
            oem=et_oem.getText().toString().trim();
            city=et_city.getText().toString().trim();
            cmpnyname=et_cmpnyname.getText().toString().trim();
            dealercode=et_dealercode.getText().toString().trim();
            adrs=et_adrs.getText().toString().trim();
            cntctname=et_cntctname.getText().toString().trim();
            cntctphone=et_cntctphone.getText().toString().trim();
            contctdesin=et_contctdesin.getText().toString().trim();
            cnctemail=et_cnctemail.getText().toString().trim();
            custmcmnt=et_custmcmnt.getText().toString().trim();
            acblcmnt=et_acblcmnt.getText().toString().trim();
            networkcmnt=et_networkcmnt.getText().toString().trim();


            if (oem.equals("")) {
                Toast.makeText(Add_OEDealer.this, "Please Enter OEM Dealer", Toast.LENGTH_SHORT).show();

            }
            else if (city.equals("")) {
                et_city.setError("Please Enter City");
                Toast.makeText(Add_OEDealer.this, "Please Enter City", Toast.LENGTH_SHORT).show();
            }

            else if (cmpnyname.equals("")) {
                Toast.makeText(this, "Please Enter Company Name", Toast.LENGTH_SHORT).show();
                et_cmpnyname.setError("Please Enter Company Name");
                et_cmpnyname.setFocusable(true);

            } else if (dealercode.equals("")) {
                et_dealercode.setError("Please Enter Dealer code");
                Toast.makeText(this, "Please Enter Dealer code", Toast.LENGTH_SHORT).show();
                et_dealercode.setFocusable(true);

            } else if (adrs.equals("")) {
                et_adrs.setError("Please Enter Address");
                Toast.makeText(this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                et_adrs.setFocusable(true);

            }  else if (cntctname.equals("")) {
                et_cntctname.setError("Please Enter Name");
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                et_cntctname.setFocusable(true);
            }  else if (cntctphone.equals("") || cntctphone.length() != 10) {
                et_cntctphone.setError("Please Enter Phone Number");
                Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                et_cntctphone.setFocusable(true);

            }  else if (cnctemail.equals("") || !cnctemail.matches(emailPattern)) {
                et_cnctemail.setError("Please Enter Email");
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                et_cnctemail.setFocusable(true);

            }

            else {

                selectedId = rg_custm.getCheckedRadioButtonId();
                rb_custm = (RadioButton) findViewById(selectedId);
                custm = rb_custm.getText().toString().trim();
                selectedId2 = rg_accbl.getCheckedRadioButtonId();
                rb_accbl = (RadioButton) findViewById(selectedId2);
                accbl =  rb_accbl.getText().toString().trim();
                selectedId3 = rg_network.getCheckedRadioButtonId();
                rb_network = (RadioButton) findViewById(selectedId3);
                network = rb_network.getText().toString().trim();
                if (net.CheckInternet()) {

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            Add_OEDealer.this).create();

                    LayoutInflater inflater = (Add_OEDealer.this).getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.conform_oe_dealer, null);
                    alertDialog.setView(dialogView);
                    Button Register = (Button) dialogView.findViewById(R.id.con_register);
                    Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);

                    EditText con_et_oem = (EditText) dialogView.findViewById(R.id.con_OEM_Dealer);
                    EditText con_et_city = (EditText) dialogView.findViewById(R.id.con_City);
                    EditText con_et_cmpnyname = (EditText) dialogView.findViewById(R.id.con_Company_Name);
                    EditText con_et_dealercode = (EditText) dialogView.findViewById(R.id.con_Dealer_Code);
                    EditText con_et_adrs = (EditText) dialogView.findViewById(R.id.con_Address);
                    EditText con_et_loc = (EditText) dialogView.findViewById(R.id.con_location);
                    EditText con_et_cntctname = (EditText) dialogView.findViewById(R.id.con_Name);
                    EditText con_et_cntctphone = (EditText) dialogView.findViewById(R.id.con_Phone);
                    EditText con_et_contctdesin = (EditText) dialogView.findViewById(R.id.con_Designation);
                    EditText con_et_cnctemail = (EditText) dialogView.findViewById(R.id.con_Email);
                    EditText con_et_custmcmnt = (EditText) dialogView.findViewById(R.id.con_customer_Comment);
                    EditText con_et_acblcmnt = (EditText) dialogView.findViewById(R.id.con_accessible_Comment);
                    EditText con_et_networkcmnt = (EditText) dialogView.findViewById(R.id.con_network_Comment);
                    EditText con_custm = (EditText) dialogView.findViewById(R.id.con_customer);
                    EditText con_accbl = (EditText) dialogView.findViewById(R.id.con_accessible);
                    EditText con_network = (EditText) dialogView.findViewById(R.id.con_network);

                    con_et_oem.setText(oem);
                    con_et_city.setText(city);
                    con_et_cmpnyname.setText(cmpnyname);
                    con_et_dealercode.setText(dealercode);
                    con_et_adrs.setText(adrs);
                    con_et_loc.setText(loc);
                    con_et_cntctname.setText(cntctname);
                    con_et_cntctphone.setText(cntctphone);
                    con_et_contctdesin.setText(contctdesin);
                    con_et_cnctemail.setText(cnctemail);
                    con_et_custmcmnt.setText(custmcmnt);
                    con_et_acblcmnt.setText(acblcmnt);
                    con_et_networkcmnt.setText(networkcmnt);
                    con_custm.setText(custm);
                    con_accbl.setText(accbl);
                    con_network.setText(network);
                    Register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        reg_OEdealer();
                            alertDialog.dismiss();
                        }
                    });
                    Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Add_OEDealer.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();

                } else {

                    Toast.makeText(this, "Please Check Your Network and Try again", Toast.LENGTH_SHORT).show();


                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Please Fill Feedback questionnaires", Toast.LENGTH_SHORT).show();

        }
    }
    private void reg_OEdealer() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Add_OEDealer.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<RegOEdealer> call = service.addOE_dealer(oem, city, cmpnyname, dealercode, adrs, loc, cntctname,
                    cntctphone, contctdesin, cnctemail, custm, accbl, network,custmcmnt, acblcmnt, networkcmnt,LAT, LONG);
            call.enqueue(new Callback<RegOEdealer>() {
                @Override
                public void onResponse(Call<RegOEdealer> call, Response<RegOEdealer> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
//                        et_oem .setText("");
//                        et_city .setText("");
//                        et_cmpnyname .setText("");
//                        et_dealercode .setText("");
//                        et_adrs.setText("");
//                        et_loc .setText("");
//                        et_cntctname.setText("");
//                        et_cntctphone .setText("");
//                        et_contctdesin .setText("");
//                        et_cnctemail.setText("");
//                        et_custmcmnt .setText("");
//                        et_acblcmnt.setText("");
//                        et_networkcmnt .setText("");

                        Toast.makeText(Add_OEDealer.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Add_OEDealer.this,Home.class));
                    } else {
                        Toast.makeText(Add_OEDealer.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegOEdealer> call, Throwable t) {
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
