package com.tvs.Edit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.Activity.Home;
import com.tvs.R;

import APIInterface.CategoryAPI;
import Model.EditProfileModel.Update;
import RetroClient.RetroClient;
import Shared.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    EditText ed_utyp, ed_compnynm, ed_name, ed_uname, ed_mobile, ed_place, ed_pass, ed_email;
    private String[] UserType = {"Citypredictive in charge", "Missionary sales officers", "Area manager", "Head office staff", "Home representative", "Missionary sales officers"};
    private int userTypeValue;
    private String[] Companyname = {"LTVS", "MAS", "SM", "TVS", "IMPAL", "MATRIXZ"};
    private int companyValue;
    AlertDialog.Builder builder;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String User_Type, User_Name, Mobile_Number, Company_Name, Place, Password, Name, Image, Emailid;
    String User_Typeed, Mobile_Numbered, Company_Nameed, Placeed, Nameed, Email, User_Named;
    TextView tv_name;
    String OLDPASS, NEWPASS, CONFIRMPASS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_edit_profile);
            builder = new AlertDialog.Builder(EditProfile.this);
            ed_utyp = (EditText) findViewById(R.id.ed_usertype);
            ed_compnynm = (EditText) findViewById(R.id.ed_companyname);
            ed_name = (EditText) findViewById(R.id.ed_name);
            ed_uname = (EditText) findViewById(R.id.ed_user);
            ed_mobile = (EditText) findViewById(R.id.ed_usermob);
            ed_place = (EditText) findViewById(R.id.ed_place);
            ed_pass = (EditText) findViewById(R.id.ed_pass);
            ed_email = (EditText) findViewById(R.id.ed_email);
            tv_name = (TextView) findViewById(R.id.user_name);


            sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
            Password = sharedPreferences.getString("KEY_log_Password", " ");
            filledit();
//        Name = sharedPreferences.getString("KEY_Name", " ");
//        User_Type = sharedPreferences.getString("KEY_User_Type", " ");
//        Mobile_Number = sharedPreferences.getString("KEY_Mobile_Number", " ");
//        Company_Name = sharedPreferences.getString("KEY_Company_Name", " ");
//        Place = sharedPreferences.getString("KEY_Place", " ");
//        Emailid = sharedPreferences.getString("KEY_Email", " ");

            ed_utyp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                builder.setTitle("Select The Person");
                    builder.setItems(UserType, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ed_utyp.setText(UserType[which]);
                            userTypeValue = which + 1;

                        }
                    });
                    builder.show();
                }
            });
            ed_compnynm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                builder.setTitle("Select The Person");
                    builder.setItems(Companyname, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ed_compnynm.setText(Companyname[which]);
                            companyValue = which + 1;

                        }
                    });
                    builder.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Home(View view) {
        startActivity(new Intent(this, Home.class));

    }

    public void update(View view) {
        try {
            Email = ed_email.getText().toString().trim();
            User_Typeed = ed_utyp.getText().toString().trim();
            Mobile_Numbered = ed_mobile.getText().toString().trim();
            Company_Nameed = ed_compnynm.getText().toString().trim();
            Placeed = ed_place.getText().toString().trim();
            Nameed = ed_name.getText().toString().trim();
            User_Named = ed_uname.getText().toString().trim();
            filledit();
            edit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Update> call = service.EditData(User_Named, Password, User_Typeed, Mobile_Numbered, Company_Nameed, Placeed, Nameed, Email);
            call.enqueue(new Callback<Update>() {
                @Override
                public void onResponse(Call<Update> call, Response<Update> response) {
                    if (response.body().getResult().equals("Success")) {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Updated Sucessfully!!!", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Update> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void filledit() {
        try {
            CategoryAPI service = RetroClient.getApiService();
            Call<Model.Login.Result> call = service.login(User_Name, Password);
            call.enqueue(new Callback<Model.Login.Result>() {
                @Override
                public void onResponse(Call<Model.Login.Result> call, Response<Model.Login.Result> response) {
                    if (response.body().getResult().equals("Success")) {
                        Name = response.body().getData().getName();
                        User_Name = response.body().getData().getUsername();
                        User_Type = response.body().getData().getUsertype();
                        Mobile_Number = response.body().getData().getMobile();
                        Company_Name = response.body().getData().getCompanyName();
                        Place = response.body().getData().getPlace();
                        Email = response.body().getData().getEmailId();
                        tv_name.setText(Name);

                        ed_utyp.setText(User_Type);
                        ed_compnynm.setText(Company_Name);
                        ed_name.setText(Name);
                        ed_uname.setText(User_Name);
                        ed_mobile.setText(Mobile_Number);
                        ed_place.setText(Place);
                        ed_email.setText(Email);
//                        Toast.makeText(Login.this, "Registered Sucessfully!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Model.Login.Result> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }


    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
