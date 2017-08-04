package com.tvs.Registration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.Activity.Login;
import com.tvs.R;
import com.tvs.Utility.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import APIInterface.CategoryAPI;
import Model.LoginRegistermodel.Result;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegister extends AppCompatActivity {
    EditText ed_utyp, ed_compnynm, ed_name, ed_code, ed_mobile, ed_Email, ed_pass;
    private String[] UserType = {"Citypredictive in charge", "Missionary sales officers", "Area manager", "Head office staff", "Home representative", "Missionary sales officers"};
    private int userTypeValue;
    private String[] Companyname = {"LTVS", "SM","LIS","MAS","TVS","IMPAL","MATRIXZ"};
    private int companyValue;
    AlertDialog.Builder builder;
    ImageView iv_image;
    NetworkConnection net;
    String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmap;
    String fileName;
    private String name, Email, usertype, password, companyname, Empcode, mobilenumber, image;
    BitmapFactory.Options bfo;
    ByteArrayOutputStream bao;
    BitmapDrawable drawable;
    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        net = new NetworkConnection(LoginRegister.this);
        builder = new AlertDialog.Builder(LoginRegister.this);
        ed_utyp = (EditText) findViewById(R.id.ed_usertype);
        ed_compnynm = (EditText) findViewById(R.id.ed_companyname);
        ed_name = (EditText) findViewById(R.id.ed_emp_name);
        ed_code = (EditText) findViewById(R.id.ed_empcode);
        ed_mobile = (EditText) findViewById(R.id.ed_usermob);
        ed_Email = (EditText) findViewById(R.id.ed_email);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        iv_image = (ImageView) findViewById(R.id.iv_Image);

        ed_utyp.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_compnynm .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_name .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_code .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ed_pass .setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        ed_utyp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                builder.setTitle("Select The Person");
//                builder.setItems(UserType, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_utyp.setText(UserType[which]);
//                        userTypeValue = which + 1;
//
//                    }
//                });
//                builder.show();
//            }
//        });
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
    }

    public void ivclick(View view) {
//        selectImage();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginRegister.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(LoginRegister.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);


        }
    }

    private void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv_image.setImageBitmap(bitmap);
    }

    private void onSelectFromGalleryResult(Intent data) {

        bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Uri path = data.getData();
                fileName = path.getLastPathSegment();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        iv_image.setImageBitmap(bitmap);
    }


    public void loginpage(View view) {

        startActivity(new Intent(this, Login.class));
    }

    public void register(View view) {


//        bfo = new BitmapFactory.Options();
//        bfo.inSampleSize = 2;
//
//        bao = new ByteArrayOutputStream();
//
//        drawable = (BitmapDrawable) iv_image.getDrawable();
//        bitmap = drawable.getBitmap();
//
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//        byte[] ba = bao.toByteArray();
//
//        image = Base64.encodeToString(ba, Base64.DEFAULT);
        name = ed_name.getText().toString().trim();
        Empcode = ed_code.getText().toString().trim();
        companyname = ed_compnynm.getText().toString().trim();
        Email = ed_Email.getText().toString().trim();
        mobilenumber = ed_mobile.getText().toString().trim();
        if (name.equals("")) {
            ed_name.setFocusable(true);
            ed_name.setError("Enter Employee Name");
        } else if (Empcode.equals("")) {
            ed_code.setFocusable(true);
            ed_code.setError("Enter Employee code");
        } else if (companyname.equals("")) {
            ed_compnynm.setFocusable(true);
            Toast.makeText(this, "Enter Company Name", Toast.LENGTH_SHORT).show();
        }
        else if (Email.equals("")) {
            ed_Email.setFocusable(true);
            ed_Email.setError("Enter Email Id");
        }
        else if (!Email.matches(emailPattern)) {
            ed_Email.setFocusable(true);
            ed_Email.setError("Enter Correct Email Id");
        }
        else if (mobilenumber.equals("")) {
            ed_mobile.setFocusable(true);
            ed_mobile.setError("Enter MobileNumber");
        }
          else if (mobilenumber.length()!=10) {
            ed_mobile.setFocusable(true);
            ed_mobile.setError("Enter Correct MobileNumber");
        }
        else {
//            userSignUp();
//            Toast.makeText(this, image + name, Toast.LENGTH_SHORT).show();
            checkInternet();
        }
    }

    private void checkInternet() {
        if (net.CheckInternet()) {
            userSignUp();
        } else {
            Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void userSignUp() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            // Calling JSON

            Call<Result> call = service.login_register(name, Empcode, mobilenumber, companyname, Email);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    progressDialog.dismiss();


                    if (response.body().getResult().equals("Success")) {

                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                LoginRegister.this).create();

                        LayoutInflater inflater = (LoginRegister.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert, null);
                        alertDialog.setView(dialogView);


                        Button Ok = (Button) dialogView.findViewById(R.id.ok);


                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        Message.setText("Thank you for your Registration. Your Account Will be activate soon");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(LoginRegister.this, Login.class));
                                alertDialog.dismiss();
                            }
                        });


                        alertDialog.show();
//                        Toast.makeText(LoginRegister.this, "Thank you your Registration, Your Account Will be activated soon", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getResult().equals("EmployeeCodeExist")) {
                        Toast.makeText(LoginRegister.this, " Sorry Employee Code Exist", Toast.LENGTH_SHORT).show();

                    }
                    else if (response.body().getResult().equals("EmailIdExist")) {
                        Toast.makeText(LoginRegister.this, "Sorry EmailIdExist", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.body().getResult().equals("MobileNumberExist")) {
                        Toast.makeText(LoginRegister.this, "Sorry MobileNumberExist", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginRegister.this, "Register not complete!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    progressDialog.dismiss();
//                    Alert box = new Alert(Login_Activity.this);
//                    box.showAlertbox("Internet Connection is slow! Please try again!");

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void cancel(View view) {
        startActivity(new Intent(LoginRegister.this, Login.class));
        Toast.makeText(this, "Register Canceled!!!", Toast.LENGTH_SHORT).show();
    }
}
