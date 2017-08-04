package com.tvs.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.R;
import com.tvs.Registration.LoginRegister;
import com.tvs.Tracker.GPSTracker;

import APIInterface.CategoryAPI;
import Model.Forgot.Forgot;
import RetroClient.RetroClient;
import Shared.Config;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    protected LocationManager locationManager;
    private static final int PERMISSION_REQUEST = 100;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email;
    NetworkConnection net;
    EditText ed_username, ed_password;
    String username, password;
    String User_Type, User_Name, Mobile_Number, Company_Name, Place, Password, Name, Image, Email;
    Boolean isLoggedin=true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    GPSTracker gps;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(R.style.AppTheme);
        net = new NetworkConnection(Login.this);
        ed_username = (EditText) findViewById(R.id.ed_log_user);
        ed_password = (EditText) findViewById(R.id.ed_log_pass);
            ed_username.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        gps = new GPSTracker(Login.this);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void login(View view) {
        try{
        username = ed_username.getText().toString().trim();
        password = ed_password.getText().toString().trim();
        if (username.equals("")) {
            ed_username.setError("Please Enter UserName");
        } else if (password.equals("")) {
            ed_password.setError("Please Enter Password");
        } else {
            checkInternet();

        }
//        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        editor.putString("KEY_Username", username);
//        editor.putString("KEY_password", password);
//        editor.apply();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void checkInternet() {
        if (net.CheckInternet()) {
            userlogin();
//            checkGps();
        } else {
            Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkGps() {
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

          /*  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
                    Toast.LENGTH_LONG).show();*/
//            if (!String.valueOf(latitude).equals("0.0") || !String.valueOf(longitude).equals("0.0")) {
//                userlogin();
//            }

        } else {

            gps.showSettingsAlert();
        }
    }

    private void userlogin() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();


            // Calling JSON

            Call<Model.Login.Result> call = service.login(username, password);
            call.enqueue(new Callback<Model.Login.Result>() {
                @Override
                public void onResponse(Call<Model.Login.Result> call, Response<Model.Login.Result> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        Name = response.body().getData().getName();
                        User_Name = response.body().getData().getUsername();
                        User_Type = response.body().getData().getUsertype();
                        Mobile_Number = response.body().getData().getMobile();
                        Company_Name = response.body().getData().getCompanyName();
                        Place = response.body().getData().getPlace();
                        Email = response.body().getData().getEmailId();
                        Password = response.body().getData().getPassword();
                        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("KEY_Name", Name);
                        editor.putString("KEY_log_User_Name", User_Name);
                        editor.putString("KEY_User_Type", User_Type);
                        editor.putString("KEY_Mobile_Number", Mobile_Number);
                        editor.putString("KEY_Company_Name", Company_Name);
                        editor.putString("KEY_Place", Place);
                        editor.putString("KEY_log_Password", Password);
                        editor.putString("KEY_Email", Email);
                        editor.putBoolean("KEY_isLoggedin",true);
                        editor.apply();
                        ed_username.getText().clear();
                        ed_password.getText().clear();
                        startActivity(new Intent(Login.this, Changepass.class));
                        finish();
//                        Toast.makeText(Login.this, "Registered Sucessfully!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "UserName or Password incorrect!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Model.Login.Result> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void loginreg(View view) {
        startActivity(new Intent(this, LoginRegister.class));
    }

    public void forgot(View view) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                Login.this).create();

        LayoutInflater inflater = (Login.this).getLayoutInflater();
        View dialog = inflater.inflate(R.layout.forgot, null);
        alertDialog.setView(dialog);

        TextView tologin = (TextView) dialog.findViewById(R.id.tologin);
        Button submit = (Button) dialog.findViewById(R.id.submit);
        final EditText ed_email = (EditText) dialog.findViewById(R.id.email);
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = ed_email.getText().toString().trim();
                if (email.length() == 0 || !email.matches(emailPattern)) {
                    ed_email.setError("Invalid email address");
                } else {
                    ed_email.setText("");
                    Forgot();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    private void Forgot() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();
            // Calling JSON
            Call<Forgot> call = service.forgot(email);
            call.enqueue(new Callback<Forgot>() {
                @Override
                public void onResponse(Call<Forgot> call, Response<Forgot> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Login.this).create();
                        LayoutInflater inflater = (Login.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert, null);
                        alertDialog.setView(dialogView);
                        Button Ok = (Button) dialogView.findViewById(R.id.ok);
                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        Message.setText("Please Check your Email, We Have Sent Your Username&Password to Your Email Id");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Login.this, Login.class));
                                alertDialog.dismiss();
                            }
                        });


                        alertDialog.show();
//                        Toast.makeText(Login.this, "Please Check your Email, We Have Sent Your Username&Password to Your Email Id", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Login.this, "Your Email Id is Wrong!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Forgot> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
