package com.tvs.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.Edit.EditProfile;
import com.tvs.OEDealer.Add_OEDealer;
import com.tvs.OEDealer.View_OEDealer;
import com.tvs.R;
import com.tvs.Split.User_Type;

import APIInterface.CategoryAPI;
import Model.ChangePass.Changepassword;
import RetroClient.RetroClient;
import Shared.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    TextView tv_name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name;
    String OLDPASS, NEWPASS, CONFIRMPASS;
    String User_Name, Password;
    Boolean isLoggedin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            tv_name = (TextView) findViewById(R.id.title_name);
            sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            name = sharedPreferences.getString("KEY_Name", " ");
            tv_name.setText("WELCOME  " + name);
            sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
            Password = sharedPreferences.getString("KEY_log_Password", " ");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void logout(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle(name);
        alertDialog.setMessage("Are you sure want to Logout?");
        alertDialog.setIcon(R.drawable.close);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putBoolean("KEY_isLoggedin", false);

                            editor.apply();
                            startActivity(new Intent(Home.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }

    public void popup(View view) {

    }

    public void Changepswd(View view) {
        try {
            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                    Home.this).create();

            LayoutInflater inflater = (Home.this).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.changepassword, null);
            alertDialog.setView(dialogView);

            Button Cancel = (Button) dialogView.findViewById(R.id.ch_cancel);
            Button save = (Button) dialogView.findViewById(R.id.ch_save);

            final EditText old_passwd = (EditText) dialogView.findViewById(R.id.ed_oldPass);
            final EditText New_Password = (EditText) dialogView.findViewById(R.id.ed_newPass);
            final EditText Confirm_pass = (EditText) dialogView.findViewById(R.id.ed_confirmPass);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OLDPASS = old_passwd.getText().toString().trim();
                    NEWPASS = New_Password.getText().toString().trim();
                    CONFIRMPASS = Confirm_pass.getText().toString().trim();

                    if (OLDPASS.equals("")) {
                        old_passwd.setError("Please Enter Old Password");
                    } else if (NEWPASS.equals("")) {
                        New_Password.setError("Please Enter New Password");
                    } else if (CONFIRMPASS.equals("")) {
                        Confirm_pass.setError("Please Enter Confirm New Password");
                    } else if (!NEWPASS.equals(CONFIRMPASS)) {
                        Confirm_pass.setError("Please Check Confirm New Password");
                        Toast.makeText(Home.this, "Please Check NewPAssword", Toast.LENGTH_SHORT).show();
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
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void CHPSW() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(Home.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Changepassword> call = service.ChangePASS(User_Name, OLDPASS, NEWPASS);
            call.enqueue(new Callback<Changepassword>() {
                @Override
                public void onResponse(Call<Changepassword> call, Response<Changepassword> response) {
                    if (response.body().getResult().equals("Success")) {
                        progressDialog.dismiss();
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Home.this).create();

                        LayoutInflater inflater = (Home.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert, null);
                        alertDialog.setView(dialogView);


                        Button Ok = (Button) dialogView.findViewById(R.id.ok);


                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        Message.setText("Password has Changed Successfully");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Home.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                                alertDialog.dismiss();
                            }
                        });


                        alertDialog.show();
//                        Toast.makeText(Home.this, "Your Password Changed", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
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

    public void add(View view) {
        startActivity(new Intent(this, User_Type.class));
    }

    public void all(View view) {
        startActivity(new Intent(this, ViewAll.class));
    }

    public void Edit(View view) {
        startActivity(new Intent(this, EditProfile.class));
    }

    public void localview(View view) {
        startActivity(new Intent(this, LocalView.class));
    }

    public void addOE(View view) {
        startActivity(new Intent(this, Add_OEDealer.class));
    }

    public void allOE(View view) {
        startActivity(new Intent(this, View_OEDealer.class));
    }

    public void Exit(View view) {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

//        startActivity(new Intent(this, Splash.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle(name);
        alertDialog.setMessage("Are you sure want to Exit?");
        alertDialog.setIcon(R.drawable.close);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
