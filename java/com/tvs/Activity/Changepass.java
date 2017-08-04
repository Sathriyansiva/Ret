package com.tvs.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.R;

import APIInterface.CategoryAPI;
import Model.ChangePass.Changepassword;
import RetroClient.RetroClient;
import Shared.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Changepass extends AppCompatActivity {
    TextView tv_name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name;
    String OLDPASS, NEWPASS, CONFIRMPASS;
    String User_Name, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_changepass);
           // tv_name = (TextView) findViewById(R.id.title_name);
            sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            name = sharedPreferences.getString("KEY_Name", " ");
           // tv_name.setText("WELCOME  " + name);

            User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
            Password = sharedPreferences.getString("KEY_log_Password", " ");
            pass();
        } catch (Exception ex) {
            ex.getMessage();


        }
    }
    public  void pass(){
        final AlertDialog alertDialog = new AlertDialog.Builder(
                Changepass.this).create();
        LayoutInflater inflater = (Changepass.this).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.changepassalert, null);
        alertDialog.setView(dialogView);
        Button Cancel = (Button) dialogView.findViewById(R.id.ch_cancel);
        Button save = (Button) dialogView.findViewById(R.id.ch_save);


//            final EditText old_passwd = (EditText) dialogView.findViewById(R.id.ed_oldPass);
        final EditText New_Password = (EditText) dialogView.findViewById(R.id.ed_newPass);
        final EditText Confirm_pass = (EditText) dialogView.findViewById(R.id.ed_confirmPass);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    OLDPASS = old_passwd.getText().toString().trim();
                NEWPASS = New_Password.getText().toString().trim();
                CONFIRMPASS = Confirm_pass.getText().toString().trim();

//                    if (OLDPASS.equals("")) {
//                        old_passwd.setError("Please Enter Old Password");
//                    } else
                if (NEWPASS.equals("")) {
                    New_Password.setError("Please Enter New Password");
                } else if (CONFIRMPASS.equals("")) {
                    Confirm_pass.setError("Please Enter Confirm New Password");
                } else if (!NEWPASS.equals(CONFIRMPASS)) {
                    Confirm_pass.setError("Please Check Confirm New Password");
                    Toast.makeText(Changepass.this, "Please Check NewPAssword", Toast.LENGTH_SHORT).show();
                } else {
                    CHPSW();
//                    Toast.makeText(Home.this, "Your Password Changed", Toast.LENGTH_SHORT).show();

                    alertDialog.dismiss();
                }
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Changepass.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    public void CHPSW() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(Changepass.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Changepassword> call = service.ChangePASS(User_Name, Password, NEWPASS);
            call.enqueue(new Callback<Changepassword>() {
                @Override
                public void onResponse(Call<Changepassword> call, Response<Changepassword> response) {
                    if (response.body().getResult().equals("Success")) {
                        progressDialog.dismiss();
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Changepass.this).create();

                        LayoutInflater inflater = (Changepass.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert, null);
                        alertDialog.setView(dialogView);


                        Button Ok = (Button) dialogView.findViewById(R.id.ok);


                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        Message.setText("Password has Changed Successfully");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Changepass.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                                alertDialog.dismiss();
                            }
                        });


                        alertDialog.show();
//                        Toast.makeText(Home.this, "Your Password Changed", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(Changepass.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Changepassword> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }


    public void exit(View view) {
        finish();
        System.exit(0);
    }
}
