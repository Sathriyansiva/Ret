package com.tvs.Split;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.tvs.Activity.Home;
import com.tvs.R;
import com.tvs.Tracker.GPSTracker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import APIInterface.CategoryAPI;
import Model.Register.Register;
import RetroClient.RetroClient;
import Shared.Config;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Capture extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    NetworkConnection net;
    String userChoosenTask;
    private int REQUEST_CAMERA = 1888, SELECT_FILE = 1;
    private Bitmap bitmap, bitmap2, bitmap3;
    String fileName;
    String image, image2, visitingcard;
    BitmapFactory.Options bfo, bfo2, bfo3;
    ByteArrayOutputStream bao, bao2, bao3;
    BitmapDrawable drawable, drawable2, drawable3;
    ImageView iv_image, iv_image2, iv_card;
    AlertDialog.Builder builder;
    GPSTracker gps;
    double latitude;
    double longitude;
    int iv_pos;
    String LAT, LONG,CaptureLocation;
    int PLACE_PICKER_REQUEST = 1;

    String type;
    private String id_pos, User_Name,Ownername, Shopname, DoorNo, Street, Area, Landmark, City, State, National,
            Pincode, Mobile1, Mobile2, Email, GstNo, lati, longi,hwold_shop,
            seg_detls, pros_dels, majr_branddetls, dels_oebrands, ltvs_pur, part, Monthly, Stater, Alter, Wiper,
            Noofstaff, Spec_in, Stock, Vehical, Market, major_branddetls_other, Ltvs_pur_other,
            partname,spec_in_other, captureloc,pros_dels_other,dels_oebrands_other;

    SQLiteDatabase SQLITEDATABASE;

    String SQLiteQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_capture);
        net = new NetworkConnection(Capture.this);
        builder = new AlertDialog.Builder(Capture.this);
        iv_image = (ImageView) findViewById(R.id.con_image);
        iv_image2 = (ImageView) findViewById(R.id.con_image2);
        iv_card = (ImageView) findViewById(R.id.con_image3);
        sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
        type = sharedPreferences.getString("KEY_type", "");
    }
    public void ivclick(View view) {
        selectImage(1);
    }

    public void ivclick2(View view) {
        selectImage(2);
    }

    public void iv_visiting(View view) {
        selectImage(3);
    }

    private void selectImage(final int x) {
        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Capture.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(Capture.this);

                    if (items[item].equals("Take Photo")) {
                        userChoosenTask = "Take Photo";
//                    if (result)
                        cameraIntent(x);

                    } else if (items[item].equals("Choose from Library")) {
                        userChoosenTask = "Choose from Library";
//                    if (result)
                        galleryIntent(x);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void galleryIntent(int i) {
        try {
            iv_pos = i;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cameraIntent(int i) {
        try {

            iv_pos = i;
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
            }
            if (requestCode == PLACE_PICKER_REQUEST) {
                if (resultCode == RESULT_OK) {
                    try {
                        Place place = PlacePicker.getPlace(data, this);
                        CaptureLocation = String.format("Place: %s", place.getName());
                        LAT=String.valueOf(place.getLatLng().latitude);
                        LONG=String.valueOf(place.getLatLng().longitude);
                        Toast.makeText(this, CaptureLocation+LAT+LONG, Toast.LENGTH_LONG).show();

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

    private void onCaptureImageResult(Intent data) {
        try {
            if (iv_pos == 1) {
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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                iv_image.setImageBitmap(bitmap);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable = (BitmapDrawable) iv_image.getDrawable();
                bitmap = drawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();
                image = Base64.encodeToString(ba, Base64.DEFAULT);


            }
            if (iv_pos == 2) {
                bitmap2 = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                iv_image2.setImageBitmap(bitmap2);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable2 = (BitmapDrawable) iv_image2.getDrawable();
                bitmap2 = drawable2.getBitmap();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba2 = bao.toByteArray();
                image2 = Base64.encodeToString(ba2, Base64.DEFAULT);

            }
            if (iv_pos == 3) {
                bitmap3 = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                iv_card.setImageBitmap(bitmap3);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable3 = (BitmapDrawable) iv_card.getDrawable();
                bitmap3 = drawable3.getBitmap();
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba3 = bao.toByteArray();
                visitingcard = Base64.encodeToString(ba3, Base64.DEFAULT);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            if (iv_pos == 1) {
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
                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable = (BitmapDrawable) iv_image.getDrawable();
                bitmap = drawable.getBitmap();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();


                image = Base64.encodeToString(ba, Base64.DEFAULT);


            }
            if (iv_pos == 2) {
                bitmap2 = null;
                if (data != null) {
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        Uri path = data.getData();
                        fileName = path.getLastPathSegment();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                iv_image2.setImageBitmap(bitmap2);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;

                bao = new ByteArrayOutputStream();
                drawable2 = (BitmapDrawable) iv_image2.getDrawable();
                bitmap2 = drawable2.getBitmap();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba2 = bao.toByteArray();
                image2 = Base64.encodeToString(ba2, Base64.DEFAULT);

            }
            if (iv_pos == 3) {
                bitmap3 = null;
                if (data != null) {
                    try {
                        bitmap3 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        Uri path = data.getData();
                        fileName = path.getLastPathSegment();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                iv_card.setImageBitmap(bitmap3);
                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable3 = (BitmapDrawable) iv_card.getDrawable();
                bitmap3 = drawable3.getBitmap();
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba3 = bao.toByteArray();
                visitingcard = Base64.encodeToString(ba3, Base64.DEFAULT);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void locaioncap(View view) {

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(Capture.this), PLACE_PICKER_REQUEST);
        }catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();
            Toast.makeText(Capture.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
            Toast.makeText(Capture.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void Save(View view) {

        try {
            sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
            type = sharedPreferences.getString("KEY_type", "");
            Ownername = sharedPreferences.getString("KEY_Ownername", "");
            Shopname = sharedPreferences.getString("KEY_Shopname", "");
            DoorNo = sharedPreferences.getString("KEY_DoorNo", "");
            Street = sharedPreferences.getString("KEY_Street", "");
            Area = sharedPreferences.getString("KEY_Area", "");
            Landmark = sharedPreferences.getString("KEY_Landmark", "");
            City = sharedPreferences.getString("KEY_City", "");
            State = sharedPreferences.getString("KEY_State", "");
            National = sharedPreferences.getString("KEY_Contry", "");
            Pincode = sharedPreferences.getString("KEY_Pincode", "");
            Mobile1 = sharedPreferences.getString("KEY_Mobile1", "");
            Mobile2 = sharedPreferences.getString("KEY_Mobile2", "");
            Email = sharedPreferences.getString("KEY_Email", "");
            GstNo = sharedPreferences.getString("KEY_GstNo", "");

            hwold_shop = sharedPreferences.getString("KEY_hwold_shop", "");
            part = sharedPreferences.getString("KEY_part", "");
            partname = sharedPreferences.getString("KEY_partname", "");
            Market = sharedPreferences.getString("KEY_market", "");
            seg_detls = sharedPreferences.getString("KEY_seg_detls", "");

            if (type.equals("RETAILER")) {

                pros_dels = sharedPreferences.getString("KEY_pros_dels", "");
                Monthly = sharedPreferences.getString("KEY_monthly", "");
                majr_branddetls = sharedPreferences.getString("KEY_majr_branddetls", "");
                major_branddetls_other = sharedPreferences.getString("KEY_majr_branddetls_other", "");
                dels_oebrands = sharedPreferences.getString("KEY_dels_oebrands", "");
                ltvs_pur = sharedPreferences.getString("KEY_ltvs_pur", "");
                Ltvs_pur_other = sharedPreferences.getString("KEY_ltvs_pur_othe", "");
                dels_oebrands_other  = sharedPreferences.getString("KEY_dels_oebrands_other", "");
                pros_dels_other= sharedPreferences.getString("KEY_pros_dels_other", "");

            } else if (type.equals("MECHANIC")) {


                Monthly = sharedPreferences.getString("KEY_monthly", "");
                Spec_in = sharedPreferences.getString("KEY_spec_in", "");
                Stock = sharedPreferences.getString("KEY_stock", "");
                spec_in_other = sharedPreferences.getString("KEY_spec_inother", "");
                Vehical = sharedPreferences.getString("KEY_vehical", "");

            } else if (type.equals("ELECTRICIAN")) {

                pros_dels = sharedPreferences.getString("KEY_pros_dels", "");
                majr_branddetls = sharedPreferences.getString("KEY_majr_branddetls", "");
                major_branddetls_other = sharedPreferences.getString("KEY_majr_branddetls_other", "");
                Stater = sharedPreferences.getString("KEY_stater", "");
                Alter = sharedPreferences.getString("KEY_alter", "");
                Wiper = sharedPreferences.getString("KEY_wiper", "");
                Noofstaff = sharedPreferences.getString("KEY_noofstaff", "");
                pros_dels_other= sharedPreferences.getString("KEY_pros_dels_other", "");
            } else if (type.equals("RETAILER,MECHANIC")) {

                pros_dels = sharedPreferences.getString("KEY_pros_dels", "");
                Monthly = sharedPreferences.getString("KEY_monthly", "");
                majr_branddetls = sharedPreferences.getString("KEY_majr_branddetls", "");
                major_branddetls_other = sharedPreferences.getString("KEY_majr_branddetls_other", "");
                dels_oebrands = sharedPreferences.getString("KEY_dels_oebrands", "");
                ltvs_pur = sharedPreferences.getString("KEY_ltvs_pur", "");
                Ltvs_pur_other = sharedPreferences.getString("KEY_ltvs_pur_othe", "");
                pros_dels_other = sharedPreferences.getString("KEY_dels_oebrands_other", "");
                dels_oebrands_other = sharedPreferences.getString("KEY_pros_dels_other", "");



                Spec_in = sharedPreferences.getString("KEY_spec_in", "");
                Stock = sharedPreferences.getString("KEY_stock", "");
                spec_in_other = sharedPreferences.getString("KEY_spec_inother", "");
                Vehical = sharedPreferences.getString("KEY_vehical", "");

            } else if (type.equals("MECHANIC,ELECTRICIAN")) {

                pros_dels = sharedPreferences.getString("KEY_pros_dels", "");
                Monthly = sharedPreferences.getString("KEY_monthly", "");
                majr_branddetls = sharedPreferences.getString("KEY_majr_branddetls", "");
                major_branddetls_other = sharedPreferences.getString("KEY_majr_branddetls_other", "");
                Spec_in = sharedPreferences.getString("KEY_spec_in", "");
                Stock = sharedPreferences.getString("KEY_stock", "");
                spec_in_other = sharedPreferences.getString("KEY_spec_inother", "");
                Vehical = sharedPreferences.getString("KEY_vehical", "");
                pros_dels_other= sharedPreferences.getString("KEY_pros_dels_other", "");

                Stater = sharedPreferences.getString("KEY_stater", "");
                Alter = sharedPreferences.getString("KEY_alter", "");
                Wiper = sharedPreferences.getString("KEY_wiper", "");
                Noofstaff = sharedPreferences.getString("KEY_noofstaff", "");


            } else if (type.equals("RETAILER,ELECTRICIAN")) {

                pros_dels = sharedPreferences.getString("KEY_pros_dels", "");
                Monthly = sharedPreferences.getString("KEY_monthly", "");
                majr_branddetls = sharedPreferences.getString("KEY_majr_branddetls", "");
                major_branddetls_other = sharedPreferences.getString("KEY_majr_branddetls_other", "");
                dels_oebrands = sharedPreferences.getString("KEY_dels_oebrands", "");
                ltvs_pur = sharedPreferences.getString("KEY_ltvs_pur", "");
                Ltvs_pur_other = sharedPreferences.getString("KEY_ltvs_pur_othe", "");
                dels_oebrands_other  = sharedPreferences.getString("KEY_dels_oebrands_other", "");
                pros_dels_other= sharedPreferences.getString("KEY_pros_dels_other", "");

                Stater = sharedPreferences.getString("KEY_stater", "");
                Alter = sharedPreferences.getString("KEY_alter", "");
                Wiper = sharedPreferences.getString("KEY_wiper", "");
                Noofstaff = sharedPreferences.getString("KEY_noofstaff", "");


            } else if (type.equals("RETAILER,MECHANIC,ELECTRICIAN")) {

                pros_dels = sharedPreferences.getString("KEY_pros_dels", "");
                Monthly = sharedPreferences.getString("KEY_monthly", "");
                majr_branddetls = sharedPreferences.getString("KEY_majr_branddetls", "");
                major_branddetls_other = sharedPreferences.getString("KEY_majr_branddetls_other", "");
                dels_oebrands = sharedPreferences.getString("KEY_dels_oebrands", "");
                ltvs_pur = sharedPreferences.getString("KEY_ltvs_pur", "");
                Ltvs_pur_other = sharedPreferences.getString("KEY_ltvs_pur_othe", "");

                Stater = sharedPreferences.getString("KEY_stater", "");
                Alter = sharedPreferences.getString("KEY_alter", "");
                Wiper = sharedPreferences.getString("KEY_wiper", "");
                Noofstaff = sharedPreferences.getString("KEY_noofstaff", "");

                Spec_in = sharedPreferences.getString("KEY_spec_in", "");
                Stock = sharedPreferences.getString("KEY_stock", "");
                spec_in_other = sharedPreferences.getString("KEY_spec_inother", "");
                Vehical = sharedPreferences.getString("KEY_vehical", "");
                dels_oebrands_other  = sharedPreferences.getString("KEY_dels_oebrands_other", "");
                pros_dels_other= sharedPreferences.getString("KEY_pros_dels_other", "");

            } else {
                Toast.makeText(Capture.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
            }
            iv_image.buildDrawingCache();
            Bitmap bmap = iv_image.getDrawingCache();
            bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 2;
            bao = new ByteArrayOutputStream();
//        drawable = (BitmapDrawable) iv_image.getDrawable();
//        bitmap = drawable.getBitmap();
            bmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            image = Base64.encodeToString(ba, Base64.DEFAULT);

            iv_image2.buildDrawingCache();
            Bitmap bmap2 = iv_image2.getDrawingCache();
            bao2 = new ByteArrayOutputStream();
//            drawable2 = (BitmapDrawable) iv_image2.getDrawable();
//            bitmap2 = drawable2.getBitmap();
            bmap2.compress(Bitmap.CompressFormat.JPEG, 90, bao2);
            byte[] ba2 = bao2.toByteArray();
            image2 = Base64.encodeToString(ba2, Base64.DEFAULT);

            iv_card.buildDrawingCache();
            Bitmap bmap3 = iv_card.getDrawingCache();
            bao3 = new ByteArrayOutputStream();
//            drawable3 = (BitmapDrawable) iv_card.getDrawable();
//            bitmap3 = drawable3.getBitmap();
            bmap3.compress(Bitmap.CompressFormat.JPEG, 90, bao3);
            byte[] ba3 = bao3.toByteArray();
            visitingcard = Base64.encodeToString(ba3, Base64.DEFAULT);


          if (type.equals("RETAILER")) {

                    if (net.CheckInternet()) {

                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Capture.this).create();

                        LayoutInflater inflater = (Capture.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_confirmation_form, null);
                        alertDialog.setView(dialogView);
                        Button Register = (Button) dialogView.findViewById(R.id.con_register);
                        Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);

                        EditText on_name = (EditText) dialogView.findViewById(R.id.con_ownname);
                        EditText shop_name = (EditText) dialogView.findViewById(R.id.con_shopname);
                        EditText door_no = (EditText) dialogView.findViewById(R.id.con_door_no);
                        EditText street = (EditText) dialogView.findViewById(R.id.con_street);
                        EditText area = (EditText) dialogView.findViewById(R.id.con_area);
                        EditText landmark = (EditText) dialogView.findViewById(R.id.con_landmark);
                        EditText city = (EditText) dialogView.findViewById(R.id.con_city);
                        EditText state = (EditText) dialogView.findViewById(R.id.con_satet);
                        EditText country = (EditText) dialogView.findViewById(R.id.con_national);
                        EditText pincode = (EditText) dialogView.findViewById(R.id.con_pincode);
                        EditText Mob1 = (EditText) dialogView.findViewById(R.id.con_mob1);
                        EditText Mob2 = (EditText) dialogView.findViewById(R.id.con_mob2);
                        EditText ed_Email = (EditText) dialogView.findViewById(R.id.con_email);
                        EditText Gst = (EditText) dialogView.findViewById(R.id.con_gst);
                        EditText hw_old = (EditText) dialogView.findViewById(R.id.con_hwold_shop);
                        EditText Organisation = (EditText) dialogView.findViewById(R.id.con_part);
                        EditText partnername = (EditText) dialogView.findViewById(R.id.con_partname);

                        EditText market = (EditText) dialogView.findViewById(R.id.con_market);
                        EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                        EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                        EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                        EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                        EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);

                        EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                        EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                        EditText ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                        EditText stater = (EditText) dialogView.findViewById(R.id.con_starter);
                        EditText alter = (EditText) dialogView.findViewById(R.id.con_alter);
                        EditText wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                        EditText noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                        EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                        EditText stock = (EditText) dialogView.findViewById(R.id.con_stock);
                        EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                        EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        EditText Latitude = (EditText) dialogView.findViewById(R.id.con_lat);
                        EditText Logitude = (EditText) dialogView.findViewById(R.id.con_long);
                        LinearLayout ll_product = (LinearLayout) dialogView.findViewById(R.id.product);
                        LinearLayout ll_monthly = (LinearLayout) dialogView.findViewById(R.id.ll_monthly);
                        LinearLayout ll_major = (LinearLayout) dialogView.findViewById(R.id.major);
                        LinearLayout ll_majorother = (LinearLayout) dialogView.findViewById(R.id.majr_branddetlsother);
                        LinearLayout ll_dealsoe = (LinearLayout) dialogView.findViewById(R.id.dealswithoe);
                        LinearLayout ll_ltvspur = (LinearLayout) dialogView.findViewById(R.id.ltvs_pur);
                        LinearLayout ll_ltvspurother = (LinearLayout) dialogView.findViewById(R.id.ltvs_purother);
                        LinearLayout ll_stater = (LinearLayout) dialogView.findViewById(R.id.statermotor);
                        LinearLayout ll_alter = (LinearLayout) dialogView.findViewById(R.id.alternator);
                        LinearLayout ll_wiper = (LinearLayout) dialogView.findViewById(R.id.wiper);
                        LinearLayout ll_staff = (LinearLayout) dialogView.findViewById(R.id.staff);
                        LinearLayout ll_special = (LinearLayout) dialogView.findViewById(R.id.spec_in);
                        LinearLayout ll_specialother = (LinearLayout) dialogView.findViewById(R.id.spec_inother);
                        LinearLayout ll_maintaining = (LinearLayout) dialogView.findViewById(R.id.stock);
                        LinearLayout ll_vehicle = (LinearLayout) dialogView.findViewById(R.id.vehicle_attend);


                        final ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);
                        LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                        LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                        EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                        EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);

                        if (!pros_dels.equals("OTHERS")) {
                            ll_productdeals_other.setVisibility(View.GONE);
                        }
                        if (!dels_oebrands.equals("OTHERS")) {
                            ll_deals_oe_other.setVisibility(View.GONE);
                        }
                        if (!part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ltvs_pur.equals("OTHERS")) {
                            ll_ltvspurother.setVisibility(View.GONE);
                        }
                        ll_stater.setVisibility(View.GONE);
                        ll_alter.setVisibility(View.GONE);
                        ll_wiper.setVisibility(View.GONE);
                        ll_staff.setVisibility(View.GONE);
                        ll_special.setVisibility(View.GONE);
                        ll_specialother.setVisibility(View.GONE);
                        ll_maintaining.setVisibility(View.GONE);
                        ll_vehicle.setVisibility(View.GONE);
                        if (!image.equals(null)) {
                            byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                            Glide.with(Capture.this)
                                    .load(imageAsBytes)
                                    .into(con_iv);
                        } else {
                            con_iv.setImageResource(R.drawable.soft);
                        }
                        if (!image2.equals("")) {
                            byte[] imageAsBytes = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                            Glide.with(Capture.this)
                                    .load(imageAsBytes)
                                    .into(con_iv2);
                        } else {
                            con_iv2.setImageResource(R.drawable.soft);
                        }
                        if (!visitingcard.equals("")) {
                            byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                            Glide.with(Capture.this)
                                    .load(imageAsBytes3)
                                    .into(con_iv3);
                        } else {
                            con_iv3.setImageResource(R.drawable.soft);
                        }

                        on_name.setText(Ownername);
                        shop_name.setText(Shopname);
                        door_no.setText(DoorNo);
                        street.setText(Street);
                        area.setText(Area);
                        landmark.setText(Landmark);
                        city.setText(City);
                        state.setText(State);
                        country.setText(National);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);


                        hw_old.setText(hwold_shop);
                        Organisation.setText(part);
                        partnername.setText(partname);
                        market.setText(Market);
                        segments.setText(seg_detls);
                        productdeals.setText(pros_dels);
                        monthly_turn.setText(Monthly);
                        MajorBrand.setText(majr_branddetls);
                        majorbrandother.setText(major_branddetls_other);
                        deals_oe.setText(dels_oebrands);
                        ltvs_purchase.setText(ltvs_pur);
                        ltvs_pur_other.setText(Ltvs_pur_other);
                        productdeals_other.setText(pros_dels_other);
                        deals_oe_other.setText(dels_oebrands_other);

                        Register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                user_reg_ret();
                                alertDialog.dismiss();
                            }
                        });
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Capture.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();

                    } else {
                        DBCreate();
                        ret_insert();
//                    Toast.makeText(this, "Saved Successfully in Locally", Toast.LENGTH_SHORT).show();


                    }

            } else if (type.equals("MECHANIC")) {
                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Capture.this).create();

                        LayoutInflater inflater = (Capture.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_confirmation_form, null);
                        alertDialog.setView(dialogView);
                        Button Register = (Button) dialogView.findViewById(R.id.con_register);
                        Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);
                        ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);

                        EditText on_name = (EditText) dialogView.findViewById(R.id.con_ownname);
                        EditText shop_name = (EditText) dialogView.findViewById(R.id.con_shopname);
                        EditText door_no = (EditText) dialogView.findViewById(R.id.con_door_no);
                        EditText street = (EditText) dialogView.findViewById(R.id.con_street);
                        EditText area = (EditText) dialogView.findViewById(R.id.con_area);
                        EditText landmark = (EditText) dialogView.findViewById(R.id.con_landmark);
                        EditText city = (EditText) dialogView.findViewById(R.id.con_city);
                        EditText state = (EditText) dialogView.findViewById(R.id.con_satet);
                        EditText country = (EditText) dialogView.findViewById(R.id.con_national);
                        EditText pincode = (EditText) dialogView.findViewById(R.id.con_pincode);
                        EditText Mob1 = (EditText) dialogView.findViewById(R.id.con_mob1);
                        EditText Mob2 = (EditText) dialogView.findViewById(R.id.con_mob2);
                        EditText ed_Email = (EditText) dialogView.findViewById(R.id.con_email);
                        EditText Gst = (EditText) dialogView.findViewById(R.id.con_gst);
                        EditText hw_old = (EditText) dialogView.findViewById(R.id.con_hwold_shop);
                        EditText Organisation = (EditText) dialogView.findViewById(R.id.con_part);
                        EditText partnername = (EditText) dialogView.findViewById(R.id.con_partname);

                        EditText market = (EditText) dialogView.findViewById(R.id.con_market);
                        EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                        EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                        EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                        EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                        EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);

                        EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                        EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                        EditText ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                        EditText stater = (EditText) dialogView.findViewById(R.id.con_starter);
                        EditText alter = (EditText) dialogView.findViewById(R.id.con_alter);
                        EditText wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                        EditText noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                        EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                        EditText specin_other = (EditText) dialogView.findViewById(R.id.con_spec_in_other);
                        EditText edt_stock = (EditText) dialogView.findViewById(R.id.con_stock);
                        EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                        EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        EditText Latitude = (EditText) dialogView.findViewById(R.id.con_lat);
                        EditText Logitude = (EditText) dialogView.findViewById(R.id.con_long);
                        LinearLayout ll_product = (LinearLayout) dialogView.findViewById(R.id.product);
                        LinearLayout ll_monthly = (LinearLayout) dialogView.findViewById(R.id.ll_monthly);
                        LinearLayout ll_major = (LinearLayout) dialogView.findViewById(R.id.major);
                        LinearLayout ll_majorother = (LinearLayout) dialogView.findViewById(R.id.majr_branddetlsother);
                        LinearLayout ll_dealsoe = (LinearLayout) dialogView.findViewById(R.id.dealswithoe);
                        LinearLayout ll_ltvspur = (LinearLayout) dialogView.findViewById(R.id.ltvs_pur);
                        LinearLayout ll_ltvspurother = (LinearLayout) dialogView.findViewById(R.id.ltvs_purother);
                        LinearLayout ll_stater = (LinearLayout) dialogView.findViewById(R.id.statermotor);
                        LinearLayout ll_alter = (LinearLayout) dialogView.findViewById(R.id.alternator);
                        LinearLayout ll_wiper = (LinearLayout) dialogView.findViewById(R.id.wiper);
                        LinearLayout ll_staff = (LinearLayout) dialogView.findViewById(R.id.staff);
                        LinearLayout ll_special = (LinearLayout) dialogView.findViewById(R.id.spec_in);
                        LinearLayout ll_specialother = (LinearLayout) dialogView.findViewById(R.id.spec_inother);
                        LinearLayout ll_maintaining = (LinearLayout) dialogView.findViewById(R.id.stock);
                        LinearLayout ll_vehicle = (LinearLayout) dialogView.findViewById(R.id.vehicle_attend);
                        LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                        LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                        EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                        EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);

                        if (!part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!Spec_in.equals("OTHERS")) {
                            ll_specialother.setVisibility(View.GONE);
                        }
                        ll_productdeals_other.setVisibility(View.GONE);
                        ll_deals_oe_other.setVisibility(View.GONE);
                        ll_stater.setVisibility(View.GONE);
                        ll_alter.setVisibility(View.GONE);
                        ll_wiper.setVisibility(View.GONE);
                        ll_staff.setVisibility(View.GONE);
                        ll_product.setVisibility(View.GONE);
                        ll_major.setVisibility(View.GONE);
                        ll_majorother.setVisibility(View.GONE);
                        ll_dealsoe.setVisibility(View.GONE);
                        ll_ltvspur.setVisibility(View.GONE);
                        ll_ltvspurother.setVisibility(View.GONE);

                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes3)
                                .into(con_iv3);
                        on_name.setText(Ownername);
                        shop_name.setText(Shopname);
                        door_no.setText(DoorNo);
                        street.setText(Street);
                        area.setText(Area);
                        landmark.setText(Landmark);
                        city.setText(City);
                        state.setText(State);
                        country.setText(National);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);

                        hw_old.setText(hwold_shop);
                        Organisation.setText(part);
                        partnername.setText(partname);
                        market.setText(Market);
                        segments.setText(seg_detls);
                        monthly_turn.setText(Monthly);
                        specin.setText(Spec_in);
                        edt_stock.setText(Stock);
                        vechicle.setText(Vehical);
                        specin_other.setText(spec_in_other);

                        Register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_reg_mech();
                                alertDialog.dismiss();
                            }
                        });
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Capture.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        mech_insert();
                    }


            } else if (type.equals("ELECTRICIAN")) {

                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Capture.this).create();

                        LayoutInflater inflater = (Capture.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_confirmation_form, null);
                        alertDialog.setView(dialogView);
                        Button Register = (Button) dialogView.findViewById(R.id.con_register);
                        Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);
                        ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);

                        EditText on_name = (EditText) dialogView.findViewById(R.id.con_ownname);
                        EditText shop_name = (EditText) dialogView.findViewById(R.id.con_shopname);
                        EditText door_no = (EditText) dialogView.findViewById(R.id.con_door_no);
                        EditText street = (EditText) dialogView.findViewById(R.id.con_street);
                        EditText area = (EditText) dialogView.findViewById(R.id.con_area);
                        EditText landmark = (EditText) dialogView.findViewById(R.id.con_landmark);
                        EditText city = (EditText) dialogView.findViewById(R.id.con_city);
                        EditText state = (EditText) dialogView.findViewById(R.id.con_satet);
                        EditText country = (EditText) dialogView.findViewById(R.id.con_national);
                        EditText pincode = (EditText) dialogView.findViewById(R.id.con_pincode);
                        EditText Mob1 = (EditText) dialogView.findViewById(R.id.con_mob1);
                        EditText Mob2 = (EditText) dialogView.findViewById(R.id.con_mob2);
                        EditText ed_Email = (EditText) dialogView.findViewById(R.id.con_email);
                        EditText Gst = (EditText) dialogView.findViewById(R.id.con_gst);
                        EditText hw_old = (EditText) dialogView.findViewById(R.id.con_hwold_shop);
                        EditText Organisation = (EditText) dialogView.findViewById(R.id.con_part);
                        EditText partnername = (EditText) dialogView.findViewById(R.id.con_partname);

                        EditText market = (EditText) dialogView.findViewById(R.id.con_market);
                        EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                        EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                        EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                        EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                        EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);

                        EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                        EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                        EditText ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                        EditText stater = (EditText) dialogView.findViewById(R.id.con_starter);
                        EditText alter = (EditText) dialogView.findViewById(R.id.con_alter);
                        EditText wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                        EditText noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                        EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                        EditText stock = (EditText) dialogView.findViewById(R.id.con_stock);
                        EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                        EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        EditText Latitude = (EditText) dialogView.findViewById(R.id.con_lat);
                        EditText Logitude = (EditText) dialogView.findViewById(R.id.con_long);
                        LinearLayout ll_product = (LinearLayout) dialogView.findViewById(R.id.product);
                        LinearLayout ll_monthly = (LinearLayout) dialogView.findViewById(R.id.ll_monthly);
                        LinearLayout ll_major = (LinearLayout) dialogView.findViewById(R.id.major);
                        LinearLayout ll_majorother = (LinearLayout) dialogView.findViewById(R.id.majr_branddetlsother);
                        LinearLayout ll_dealsoe = (LinearLayout) dialogView.findViewById(R.id.dealswithoe);
                        LinearLayout ll_ltvspur = (LinearLayout) dialogView.findViewById(R.id.ltvs_pur);
                        LinearLayout ll_ltvspurother = (LinearLayout) dialogView.findViewById(R.id.ltvs_purother);
                        LinearLayout ll_stater = (LinearLayout) dialogView.findViewById(R.id.statermotor);
                        LinearLayout ll_alter = (LinearLayout) dialogView.findViewById(R.id.alternator);
                        LinearLayout ll_wiper = (LinearLayout) dialogView.findViewById(R.id.wiper);
                        LinearLayout ll_staff = (LinearLayout) dialogView.findViewById(R.id.staff);
                        LinearLayout ll_special = (LinearLayout) dialogView.findViewById(R.id.spec_in);
                        LinearLayout ll_specialother = (LinearLayout) dialogView.findViewById(R.id.spec_inother);
                        LinearLayout ll_maintaining = (LinearLayout) dialogView.findViewById(R.id.stock);
                        LinearLayout ll_vehicle = (LinearLayout) dialogView.findViewById(R.id.vehicle_attend);



                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);
                        LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                        LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                        EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                        EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);
                        if (!pros_dels.equals("OTHERS")) {
                            ll_productdeals_other.setVisibility(View.GONE);
                        }

                        productdeals_other.setText(pros_dels_other);
                        if (!part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        ll_productdeals_other.setVisibility(View.GONE);
                        ll_monthly.setVisibility(View.GONE);
                        ll_dealsoe.setVisibility(View.GONE);
                        ll_ltvspur.setVisibility(View.GONE);
                        ll_ltvspurother.setVisibility(View.GONE);
                        ll_special.setVisibility(View.GONE);
                        ll_specialother.setVisibility(View.GONE);
                        ll_maintaining.setVisibility(View.GONE);
                        ll_vehicle.setVisibility(View.GONE);

                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes3)
                                .into(con_iv3);
                        on_name.setText(Ownername);
                        shop_name.setText(Shopname);
                        door_no.setText(DoorNo);
                        street.setText(Street);
                        area.setText(Area);
                        landmark.setText(Landmark);
                        city.setText(City);
                        state.setText(State);
                        country.setText(National);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);


                        hw_old.setText(hwold_shop);
                        Organisation.setText(part);
                        partnername.setText(partname);
                        market.setText(Market);
                        segments.setText(seg_detls);
                        productdeals.setText(pros_dels);
                        MajorBrand.setText(majr_branddetls);
                        majorbrandother.setText(major_branddetls_other);
                        stater.setText(Stater);
                        alter.setText(Alter);
                        wiper.setText(Wiper);
                        noofstaff.setText(Noofstaff);


                        Register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_reg_elect();
                                alertDialog.dismiss();
                            }
                        });
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Capture.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        elect_insert();
                    }



            } else if (type.equals("RETAILER,MECHANIC")) {

                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Capture.this).create();

                        LayoutInflater inflater = (Capture.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_confirmation_form, null);
                        alertDialog.setView(dialogView);
                        Button Register = (Button) dialogView.findViewById(R.id.con_register);
                        Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);

                        ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);

                        EditText on_name = (EditText) dialogView.findViewById(R.id.con_ownname);
                        EditText shop_name = (EditText) dialogView.findViewById(R.id.con_shopname);
                        EditText door_no = (EditText) dialogView.findViewById(R.id.con_door_no);
                        EditText street = (EditText) dialogView.findViewById(R.id.con_street);
                        EditText area = (EditText) dialogView.findViewById(R.id.con_area);
                        EditText landmark = (EditText) dialogView.findViewById(R.id.con_landmark);
                        EditText city = (EditText) dialogView.findViewById(R.id.con_city);
                        EditText state = (EditText) dialogView.findViewById(R.id.con_satet);
                        EditText country = (EditText) dialogView.findViewById(R.id.con_national);
                        EditText pincode = (EditText) dialogView.findViewById(R.id.con_pincode);
                        EditText Mob1 = (EditText) dialogView.findViewById(R.id.con_mob1);
                        EditText Mob2 = (EditText) dialogView.findViewById(R.id.con_mob2);
                        EditText ed_Email = (EditText) dialogView.findViewById(R.id.con_email);
                        EditText Gst = (EditText) dialogView.findViewById(R.id.con_gst);
                        EditText hw_old = (EditText) dialogView.findViewById(R.id.con_hwold_shop);
                        EditText Organisation = (EditText) dialogView.findViewById(R.id.con_part);
                        EditText partnername = (EditText) dialogView.findViewById(R.id.con_partname);

                        EditText market = (EditText) dialogView.findViewById(R.id.con_market);
                        EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                        EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                        EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                        EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                        EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);

                        EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                        EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                        EditText ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                        EditText stater = (EditText) dialogView.findViewById(R.id.con_starter);
                        EditText alter = (EditText) dialogView.findViewById(R.id.con_alter);
                        EditText wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                        EditText noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                        EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                        EditText ed_stock = (EditText) dialogView.findViewById(R.id.con_stock);
                        EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                        EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        EditText Latitude = (EditText) dialogView.findViewById(R.id.con_lat);
                        EditText Logitude = (EditText) dialogView.findViewById(R.id.con_long);
                        LinearLayout ll_product = (LinearLayout) dialogView.findViewById(R.id.product);
                        LinearLayout ll_monthly = (LinearLayout) dialogView.findViewById(R.id.ll_monthly);
                        LinearLayout ll_major = (LinearLayout) dialogView.findViewById(R.id.major);
                        LinearLayout ll_majorother = (LinearLayout) dialogView.findViewById(R.id.majr_branddetlsother);
                        LinearLayout ll_dealsoe = (LinearLayout) dialogView.findViewById(R.id.dealswithoe);
                        LinearLayout ll_ltvspur = (LinearLayout) dialogView.findViewById(R.id.ltvs_pur);
                        LinearLayout ll_ltvspurother = (LinearLayout) dialogView.findViewById(R.id.ltvs_purother);
                        LinearLayout ll_stater = (LinearLayout) dialogView.findViewById(R.id.statermotor);
                        LinearLayout ll_alter = (LinearLayout) dialogView.findViewById(R.id.alternator);
                        LinearLayout ll_wiper = (LinearLayout) dialogView.findViewById(R.id.wiper);
                        LinearLayout ll_staff = (LinearLayout) dialogView.findViewById(R.id.staff);
                        LinearLayout ll_special = (LinearLayout) dialogView.findViewById(R.id.spec_in);
                        LinearLayout ll_specialother = (LinearLayout) dialogView.findViewById(R.id.spec_inother);
                        LinearLayout ll_maintaining = (LinearLayout) dialogView.findViewById(R.id.stock);
                        LinearLayout ll_vehicle = (LinearLayout) dialogView.findViewById(R.id.vehicle_attend);

                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        EditText specin_other = (EditText) dialogView.findViewById(R.id.con_spec_in_other);

                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);
                        LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                        LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                        EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                        EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);
                        if (!pros_dels.equals("OTHERS")) {
                            ll_productdeals_other.setVisibility(View.GONE);
                        }
                        if (!dels_oebrands.equals("OTHERS")) {
                            ll_deals_oe_other.setVisibility(View.GONE);
                        }
                        productdeals_other.setText(pros_dels_other);
                        deals_oe_other.setText(dels_oebrands_other);
                        if (!part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ltvs_pur.equals("OTHERS")) {
                            ll_ltvspurother.setVisibility(View.GONE);
                        }
                        if (!Spec_in.equals("OTHERS")) {
                            ll_specialother.setVisibility(View.GONE);
                        }
                        ll_stater.setVisibility(View.GONE);
                        ll_alter.setVisibility(View.GONE);
                        ll_wiper.setVisibility(View.GONE);
                        ll_staff.setVisibility(View.GONE);

                        specin_other.setText(spec_in_other);
                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes3)
                                .into(con_iv3);
                        on_name.setText(Ownername);
                        shop_name.setText(Shopname);
                        door_no.setText(DoorNo);
                        street.setText(Street);
                        area.setText(Area);
                        landmark.setText(Landmark);
                        city.setText(City);
                        state.setText(State);
                        country.setText(National);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);


                        hw_old.setText(hwold_shop);
                        Organisation.setText(part);
                        partnername.setText(partname);
                        market.setText(Market);
                        segments.setText(seg_detls);
                        productdeals.setText(pros_dels);
                        monthly_turn.setText(Monthly);
                        MajorBrand.setText(majr_branddetls);
                        majorbrandother.setText(major_branddetls_other);
                        deals_oe.setText(dels_oebrands);
                        ltvs_purchase.setText(ltvs_pur);
                        ltvs_pur_other.setText(Ltvs_pur_other);
                        specin.setText(Spec_in);
                        ed_stock.setText(Stock);
                        vechicle.setText(Vehical);

                        Register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_reg_ret_mech();
                                alertDialog.dismiss();
                            }
                        });
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Capture.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        ret__mech_insert();
                    }



            } else if (type.equals("MECHANIC,ELECTRICIAN")) {

                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Capture.this).create();

                        LayoutInflater inflater = (Capture.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_confirmation_form, null);
                        alertDialog.setView(dialogView);
                        Button Register = (Button) dialogView.findViewById(R.id.con_register);
                        Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);
                        ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);

                        EditText on_name = (EditText) dialogView.findViewById(R.id.con_ownname);
                        EditText shop_name = (EditText) dialogView.findViewById(R.id.con_shopname);
                        EditText door_no = (EditText) dialogView.findViewById(R.id.con_door_no);
                        EditText street = (EditText) dialogView.findViewById(R.id.con_street);
                        EditText area = (EditText) dialogView.findViewById(R.id.con_area);
                        EditText landmark = (EditText) dialogView.findViewById(R.id.con_landmark);
                        EditText city = (EditText) dialogView.findViewById(R.id.con_city);
                        EditText state = (EditText) dialogView.findViewById(R.id.con_satet);
                        EditText country = (EditText) dialogView.findViewById(R.id.con_national);
                        EditText pincode = (EditText) dialogView.findViewById(R.id.con_pincode);
                        EditText Mob1 = (EditText) dialogView.findViewById(R.id.con_mob1);
                        EditText Mob2 = (EditText) dialogView.findViewById(R.id.con_mob2);
                        EditText ed_Email = (EditText) dialogView.findViewById(R.id.con_email);
                        EditText Gst = (EditText) dialogView.findViewById(R.id.con_gst);
                        EditText hw_old = (EditText) dialogView.findViewById(R.id.con_hwold_shop);
                        EditText Organisation = (EditText) dialogView.findViewById(R.id.con_part);
                        EditText partnername = (EditText) dialogView.findViewById(R.id.con_partname);

                        EditText market = (EditText) dialogView.findViewById(R.id.con_market);
                        EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                        EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                        EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                        EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                        EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);

                        EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                        EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                        EditText ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                        EditText stater = (EditText) dialogView.findViewById(R.id.con_starter);
                        EditText alter = (EditText) dialogView.findViewById(R.id.con_alter);
                        EditText wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                        EditText noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                        EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                        EditText ed_stock = (EditText) dialogView.findViewById(R.id.con_stock);
                        EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                        EditText Latitude = (EditText) dialogView.findViewById(R.id.con_lat);
                        EditText Logitude = (EditText) dialogView.findViewById(R.id.con_long);
                        EditText specin_other = (EditText) dialogView.findViewById(R.id.con_spec_in_other);
                        LinearLayout ll_product = (LinearLayout) dialogView.findViewById(R.id.product);
                        LinearLayout ll_monthly = (LinearLayout) dialogView.findViewById(R.id.ll_monthly);
                        LinearLayout ll_major = (LinearLayout) dialogView.findViewById(R.id.major);
                        LinearLayout ll_majorother = (LinearLayout) dialogView.findViewById(R.id.majr_branddetlsother);
                        LinearLayout ll_dealsoe = (LinearLayout) dialogView.findViewById(R.id.dealswithoe);
                        LinearLayout ll_ltvspur = (LinearLayout) dialogView.findViewById(R.id.ltvs_pur);
                        LinearLayout ll_ltvspurother = (LinearLayout) dialogView.findViewById(R.id.ltvs_purother);
                        LinearLayout ll_stater = (LinearLayout) dialogView.findViewById(R.id.statermotor);
                        LinearLayout ll_alter = (LinearLayout) dialogView.findViewById(R.id.alternator);
                        LinearLayout ll_wiper = (LinearLayout) dialogView.findViewById(R.id.wiper);
                        LinearLayout ll_staff = (LinearLayout) dialogView.findViewById(R.id.staff);
                        LinearLayout ll_special = (LinearLayout) dialogView.findViewById(R.id.spec_in);
                        LinearLayout ll_specialother = (LinearLayout) dialogView.findViewById(R.id.spec_inother);
                        LinearLayout ll_maintaining = (LinearLayout) dialogView.findViewById(R.id.stock);
                        LinearLayout ll_vehicle = (LinearLayout) dialogView.findViewById(R.id.vehicle_attend);
                        specin_other.setText(spec_in_other);

                        final EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);

                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);
                        LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                        LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                        EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                        EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);
                        if (!pros_dels.equals("OTHERS")) {
                            ll_productdeals_other.setVisibility(View.GONE);
                        }

                        productdeals_other.setText(pros_dels_other);
                        if (!part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!Spec_in.equals("OTHERS")) {
                            ll_specialother.setVisibility(View.GONE);
                        }
                        ll_productdeals_other.setVisibility(View.GONE);
                        ll_dealsoe.setVisibility(View.GONE);
                        ll_ltvspur.setVisibility(View.GONE);
                        ll_ltvspurother.setVisibility(View.GONE);

                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes3)
                                .into(con_iv3);
                        on_name.setText(Ownername);
                        shop_name.setText(Shopname);
                        door_no.setText(DoorNo);
                        street.setText(Street);
                        area.setText(Area);
                        landmark.setText(Landmark);
                        city.setText(City);
                        state.setText(State);
                        country.setText(National);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);


                        hw_old.setText(hwold_shop);
                        Organisation.setText(part);
                        partnername.setText(partname);
                        market.setText(Market);
                        segments.setText(seg_detls);
                        monthly_turn.setText(Monthly);
                        specin.setText(Spec_in);
                        ed_stock.setText(Stock);
                        vechicle.setText(Vehical);
                        monthly_turn.setText(Monthly);

                        MajorBrand.setText(majr_branddetls);
                        majorbrandother.setText(major_branddetls_other);
                        productdeals.setText(pros_dels);
                        stater.setText(Stater);
                        alter.setText(Alter);
                        wiper.setText(Wiper);
                        noofstaff.setText(Noofstaff);

                        Register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_reg_mech_elect();
                                alertDialog.dismiss();
                            }
                        });
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Capture.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        mech_elect_insert();
                    }
            } else if (type.equals("RETAILER,ELECTRICIAN")) {

                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Capture.this).create();

                        LayoutInflater inflater = (Capture.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_confirmation_form, null);
                        alertDialog.setView(dialogView);
                        Button Register = (Button) dialogView.findViewById(R.id.con_register);
                        Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);
                        ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);

                        EditText on_name = (EditText) dialogView.findViewById(R.id.con_ownname);
                        EditText shop_name = (EditText) dialogView.findViewById(R.id.con_shopname);
                        EditText door_no = (EditText) dialogView.findViewById(R.id.con_door_no);
                        EditText street = (EditText) dialogView.findViewById(R.id.con_street);
                        EditText area = (EditText) dialogView.findViewById(R.id.con_area);
                        EditText landmark = (EditText) dialogView.findViewById(R.id.con_landmark);
                        EditText city = (EditText) dialogView.findViewById(R.id.con_city);
                        EditText state = (EditText) dialogView.findViewById(R.id.con_satet);
                        EditText country = (EditText) dialogView.findViewById(R.id.con_national);
                        EditText pincode = (EditText) dialogView.findViewById(R.id.con_pincode);
                        EditText Mob1 = (EditText) dialogView.findViewById(R.id.con_mob1);
                        EditText Mob2 = (EditText) dialogView.findViewById(R.id.con_mob2);
                        EditText ed_Email = (EditText) dialogView.findViewById(R.id.con_email);
                        EditText Gst = (EditText) dialogView.findViewById(R.id.con_gst);
                        EditText hw_old = (EditText) dialogView.findViewById(R.id.con_hwold_shop);
                        EditText Organisation = (EditText) dialogView.findViewById(R.id.con_part);
                        EditText partnername = (EditText) dialogView.findViewById(R.id.con_partname);

                        EditText market = (EditText) dialogView.findViewById(R.id.con_market);
                        EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                        EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                        EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                        EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                        EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);

                        EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                        EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                        EditText ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                        EditText stater = (EditText) dialogView.findViewById(R.id.con_starter);
                        EditText alter = (EditText) dialogView.findViewById(R.id.con_alter);
                        EditText wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                        EditText noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                        EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                        EditText stock = (EditText) dialogView.findViewById(R.id.con_stock);
                        EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                        EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        EditText Latitude = (EditText) dialogView.findViewById(R.id.con_lat);
                        EditText Logitude = (EditText) dialogView.findViewById(R.id.con_long);
                        LinearLayout ll_product = (LinearLayout) dialogView.findViewById(R.id.product);
                        LinearLayout ll_monthly = (LinearLayout) dialogView.findViewById(R.id.ll_monthly);
                        LinearLayout ll_major = (LinearLayout) dialogView.findViewById(R.id.major);
                        LinearLayout ll_majorother = (LinearLayout) dialogView.findViewById(R.id.majr_branddetlsother);
                        LinearLayout ll_dealsoe = (LinearLayout) dialogView.findViewById(R.id.dealswithoe);
                        LinearLayout ll_ltvspur = (LinearLayout) dialogView.findViewById(R.id.ltvs_pur);
                        LinearLayout ll_ltvspurother = (LinearLayout) dialogView.findViewById(R.id.ltvs_purother);
                        LinearLayout ll_stater = (LinearLayout) dialogView.findViewById(R.id.statermotor);
                        LinearLayout ll_alter = (LinearLayout) dialogView.findViewById(R.id.alternator);
                        LinearLayout ll_wiper = (LinearLayout) dialogView.findViewById(R.id.wiper);
                        LinearLayout ll_staff = (LinearLayout) dialogView.findViewById(R.id.staff);
                        LinearLayout ll_special = (LinearLayout) dialogView.findViewById(R.id.spec_in);
                        LinearLayout ll_specialother = (LinearLayout) dialogView.findViewById(R.id.spec_inother);
                        LinearLayout ll_maintaining = (LinearLayout) dialogView.findViewById(R.id.stock);
                        LinearLayout ll_vehicle = (LinearLayout) dialogView.findViewById(R.id.vehicle_attend);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);
                        LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                        LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                        EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                        EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);
                        if (!pros_dels.equals("OTHERS")) {
                            ll_productdeals_other.setVisibility(View.GONE);
                        }
                        if (!dels_oebrands.equals("OTHERS")) {
                            ll_deals_oe_other.setVisibility(View.GONE);
                        }
                        productdeals_other.setText(pros_dels_other);
                        deals_oe_other.setText(dels_oebrands_other);
                        if (!part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ltvs_pur.equals("OTHERS")) {
                            ll_ltvspurother.setVisibility(View.GONE);
                        }
                        ll_special.setVisibility(View.GONE);
                        ll_specialother.setVisibility(View.GONE);
                        ll_vehicle.setVisibility(View.GONE);

                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes3)
                                .into(con_iv3);
                        on_name.setText(Ownername);
                        shop_name.setText(Shopname);
                        door_no.setText(DoorNo);
                        street.setText(Street);
                        area.setText(Area);
                        landmark.setText(Landmark);
                        city.setText(City);
                        state.setText(State);
                        country.setText(National);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);


                        hw_old.setText(hwold_shop);
                        Organisation.setText(part);
                        partnername.setText(partname);
                        market.setText(Market);
                        segments.setText(seg_detls);
                        productdeals.setText(pros_dels);
                        monthly_turn.setText(Monthly);
                        MajorBrand.setText(majr_branddetls);
                        majorbrandother.setText(major_branddetls_other);
                        deals_oe.setText(dels_oebrands);
                        ltvs_purchase.setText(ltvs_pur);
                        ltvs_pur_other.setText(Ltvs_pur_other);

                        stater.setText(Stater);
                        alter.setText(Alter);
                        wiper.setText(Wiper);
                        noofstaff.setText(Noofstaff);
                        Register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                user_reg_ret_elect();
                                alertDialog.dismiss();
                            }
                        });
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Capture.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        ret_elect_insert();
                    }

            } else if (type.equals("RETAILER,MECHANIC,ELECTRICIAN")) {

                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Capture.this).create();

                        LayoutInflater inflater = (Capture.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_confirmation_form, null);
                        alertDialog.setView(dialogView);
                        Button Register = (Button) dialogView.findViewById(R.id.con_register);
                        Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);
                        final ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);

                        EditText on_name = (EditText) dialogView.findViewById(R.id.con_ownname);
                        EditText shop_name = (EditText) dialogView.findViewById(R.id.con_shopname);
                        EditText door_no = (EditText) dialogView.findViewById(R.id.con_door_no);
                        EditText street = (EditText) dialogView.findViewById(R.id.con_street);
                        EditText area = (EditText) dialogView.findViewById(R.id.con_area);
                        EditText landmark = (EditText) dialogView.findViewById(R.id.con_landmark);
                        EditText city = (EditText) dialogView.findViewById(R.id.con_city);
                        EditText state = (EditText) dialogView.findViewById(R.id.con_satet);
                        EditText country = (EditText) dialogView.findViewById(R.id.con_national);
                        EditText pincode = (EditText) dialogView.findViewById(R.id.con_pincode);
                        EditText Mob1 = (EditText) dialogView.findViewById(R.id.con_mob1);
                        EditText Mob2 = (EditText) dialogView.findViewById(R.id.con_mob2);
                        EditText ed_Email = (EditText) dialogView.findViewById(R.id.con_email);
                        EditText Gst = (EditText) dialogView.findViewById(R.id.con_gst);
                        EditText hw_old = (EditText) dialogView.findViewById(R.id.con_hwold_shop);
                        EditText Organisation = (EditText) dialogView.findViewById(R.id.con_part);
                        EditText partnername = (EditText) dialogView.findViewById(R.id.con_partname);

                        EditText market = (EditText) dialogView.findViewById(R.id.con_market);
                        EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                        EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                        EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                        EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                        EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);

                        EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                        EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                        EditText ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                        EditText stater = (EditText) dialogView.findViewById(R.id.con_starter);
                        EditText alter = (EditText) dialogView.findViewById(R.id.con_alter);
                        EditText wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                        EditText noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                        EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                        EditText stock = (EditText) dialogView.findViewById(R.id.con_stock);
                        EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                        EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        EditText Latitude = (EditText) dialogView.findViewById(R.id.con_lat);
                        EditText Logitude = (EditText) dialogView.findViewById(R.id.con_long);
                        EditText specin_other = (EditText) dialogView.findViewById(R.id.con_spec_in_other);
                        LinearLayout ll_product = (LinearLayout) dialogView.findViewById(R.id.product);
                        LinearLayout ll_monthly = (LinearLayout) dialogView.findViewById(R.id.ll_monthly);
                        LinearLayout ll_major = (LinearLayout) dialogView.findViewById(R.id.major);
                        LinearLayout ll_majorother = (LinearLayout) dialogView.findViewById(R.id.majr_branddetlsother);
                        LinearLayout ll_dealsoe = (LinearLayout) dialogView.findViewById(R.id.dealswithoe);
                        LinearLayout ll_ltvspur = (LinearLayout) dialogView.findViewById(R.id.ltvs_pur);
                        LinearLayout ll_ltvspurother = (LinearLayout) dialogView.findViewById(R.id.ltvs_purother);
                        LinearLayout ll_stater = (LinearLayout) dialogView.findViewById(R.id.statermotor);
                        LinearLayout ll_alter = (LinearLayout) dialogView.findViewById(R.id.alternator);
                        LinearLayout ll_wiper = (LinearLayout) dialogView.findViewById(R.id.wiper);
                        LinearLayout ll_staff = (LinearLayout) dialogView.findViewById(R.id.staff);
                        LinearLayout ll_special = (LinearLayout) dialogView.findViewById(R.id.spec_in);
                        LinearLayout ll_specialother = (LinearLayout) dialogView.findViewById(R.id.spec_inother);
                        LinearLayout ll_maintaining = (LinearLayout) dialogView.findViewById(R.id.stock);
                        LinearLayout ll_vehicle = (LinearLayout) dialogView.findViewById(R.id.vehicle_attend);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);
                        LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                        LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                        EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                        EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);
                        if (!pros_dels.equals("OTHERS")) {
                            ll_productdeals_other.setVisibility(View.GONE);
                        }
                        if (!dels_oebrands.equals("OTHERS")) {
                            ll_deals_oe_other.setVisibility(View.GONE);
                        }
                        productdeals_other.setText(pros_dels_other);
                        deals_oe_other.setText(dels_oebrands_other);
                        if (!part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ltvs_pur.equals("OTHERS")) {
                            ll_ltvspurother.setVisibility(View.GONE);
                        }
                        if (!Spec_in.equals("OTHERS")) {
                            ll_specialother.setVisibility(View.GONE);
                        }
                        specin_other.setText(spec_in_other);

                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Capture.this)
                                .load(imageAsBytes3)
                                .into(con_iv3);
                        on_name.setText(Ownername);
                        shop_name.setText(Shopname);
                        door_no.setText(DoorNo);
                        street.setText(Street);
                        area.setText(Area);
                        landmark.setText(Landmark);
                        city.setText(City);
                        state.setText(State);
                        country.setText(National);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);



                        hw_old.setText(hwold_shop);
                        Organisation.setText(part);
                        partnername.setText(partname);
                        market.setText(Market);
                        segments.setText(seg_detls);
                        productdeals.setText(pros_dels);
                        monthly_turn.setText(Monthly);
                        MajorBrand.setText(majr_branddetls);
                        majorbrandother.setText(major_branddetls_other);
                        deals_oe.setText(dels_oebrands);
                        ltvs_purchase.setText(ltvs_pur);
                        ltvs_pur_other.setText(Ltvs_pur_other);

                        specin.setText(Spec_in);
                        stock.setText(Stock);
                        vechicle.setText(Vehical);

                        stater.setText(Stater);
                        alter.setText(Alter);
                        wiper.setText(Wiper);
                        noofstaff.setText(Noofstaff);

                        Register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                user_reg_ret_mech_elect();


                                alertDialog.dismiss();
                            }
                        });
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Capture.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        ret_mech_elect_insert();
                    }


            } else {
                Toast.makeText(Capture.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
            }

//        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //    public void cancel(View view) {
//        Toast.makeText(this, "Registered Canceled!!!", Toast.LENGTH_SHORT).show();
//    }


    private void user_reg_ret() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Capture.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();


            // Calling JSON

            Call<Register> call = service.register_ret(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City, State, National,
                    Pincode, Mobile1, Mobile2, Email, GstNo, hwold_shop, part, partname, Market, seg_detls, pros_dels,
                    Monthly, majr_branddetls, major_branddetls_other, dels_oebrands, ltvs_pur, Ltvs_pur_other, LAT, LONG, image, image2, visitingcard,CaptureLocation,pros_dels_other, dels_oebrands_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        Toast.makeText(Capture.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Capture.this,Home.class));

                    } else {
                        Toast.makeText(Capture.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void user_reg_mech() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Capture.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_mech(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City, State, National,
                    Pincode, Mobile1, Mobile2, Email, GstNo, hwold_shop,
                    part, partname, Market, seg_detls, Monthly, Spec_in,Stock,Vehical, LAT, LONG, image, image2, visitingcard,spec_in_other,CaptureLocation);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        Toast.makeText(Capture.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Capture.this,Home.class));
                    } else {
                        Toast.makeText(Capture.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void user_reg_elect() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Capture.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();


            Call<Register> call = service.register_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    hwold_shop, part, partname, Market, seg_detls, pros_dels,
                    majr_branddetls, major_branddetls_other, Stater,Alter,Wiper, Noofstaff, LAT, LONG, image, image2, visitingcard,CaptureLocation,pros_dels_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        Toast.makeText(Capture.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Capture.this,Home.class));
                    } else {
                        Toast.makeText(Capture.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void user_reg_ret_mech() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Capture.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_ret_mech(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    hwold_shop, part, partname, Market, seg_detls,
                    pros_dels, Monthly, majr_branddetls, major_branddetls_other,
                    dels_oebrands, ltvs_pur, Ltvs_pur_other,
                    Spec_in, Stock,Vehical, LAT, LONG, image, image2, visitingcard,spec_in_other,CaptureLocation,pros_dels_other, dels_oebrands_other
            );
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        Toast.makeText(Capture.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Capture.this,Home.class));
                    } else {
                        Toast.makeText(Capture.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void user_reg_mech_elect() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Capture.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_mech_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    hwold_shop, part, partname, Market, seg_detls,
                    Monthly, majr_branddetls, major_branddetls_other, pros_dels, Stater, Alter, Wiper,
                    Noofstaff, Spec_in, Stock, Vehical, LAT, LONG, image, image2, visitingcard,spec_in_other,CaptureLocation,pros_dels_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        Toast.makeText(Capture.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Capture.this,Home.class));
                    } else {
                        Toast.makeText(Capture.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void user_reg_ret_elect() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Capture.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_ret_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    hwold_shop, part, partname, Market,
                    seg_detls, pros_dels, Monthly, majr_branddetls, major_branddetls_other,
                    dels_oebrands, ltvs_pur, Ltvs_pur_other,
                    Stater,Alter,Wiper, Noofstaff, LAT, LONG, image, image2, visitingcard,CaptureLocation,pros_dels_other, dels_oebrands_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        Toast.makeText(Capture.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Capture.this,Home.class));
                    } else {
                        Toast.makeText(Capture.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void user_reg_ret_mech_elect() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Capture.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_ret_mech_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    hwold_shop, part, partname, Market, seg_detls, pros_dels, Monthly, majr_branddetls,
                    major_branddetls_other, dels_oebrands, ltvs_pur, Ltvs_pur_other,
                    Stater,Alter,Wiper, Noofstaff,Spec_in,Stock,Vehical, LAT, LONG, image, image2, visitingcard,spec_in_other,CaptureLocation,pros_dels_other, dels_oebrands_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        Toast.makeText(Capture.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Capture.this,Home.class));
                    } else {
                        Toast.makeText(Capture.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    public void DBCreate() {
        try {
            SQLITEDATABASE = openOrCreateDatabase("LucasTVS", Context.MODE_PRIVATE, null);

            SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS Register_Table1(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "User_Type VARCHAR,UserName VARCHAR, ShopName VARCHAR, DoorNo VARCHAR, Street VARCHAR, Area VARCHAR, LandMark VARCHAR, City VARCHAR" +
                    ", Citypredictive VARCHAR, Country VARCHAR, Pincode VARCHAR, Mobile VARCHAR, Phone VARCHAR, Email VARCHAR, GSTNumber VARCHAR" +
                    ", OwnerName VARCHAR, Dealsin VARCHAR, HowOldTheShopIs VARCHAR, TypeOfOrganisation VARCHAR, Market VARCHAR,OtherPartnersNames VARCHAR, " +
                    "SegmentDeals VARCHAR, ProductDealsWith VARCHAR, MonthyTurnOver VARCHAR, MajorBrandDealsWithElectrical VARCHAR," +
                    " DealsWithOEBrand VARCHAR, LucasTVSPurchaseDealsWith VARCHAR, LucasTVSPurchaseDealsWithOther VARCHAR, NoOfStarterMotorServicedInMonth VARCHAR" +
                    ", MajorBrandDealsWithElectricalother VARCHAR, NoOfAlternatorServicedInMonth VARCHAR, NoOfWiperMotorSevicedInMonth VARCHAR," +
                    " NoOfStaff VARCHAR, SpecialistIn VARCHAR, MaintainingStock VARCHAR," +
                    " VehicleAlterMonth VARCHAR, ShopImage VARCHAR,ShopImage2 VARCHAR,VisitingCard VARCHAR,Latitude VARCHAR," +
                    " Longitude VARCHAR, Specialist_other VARCHAR, Location VARCHAR,ProductDealsOther VARCHAR,DealsOEBrandsOther VARCHAR);");
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret_insert() {
        try {


            SQLiteQuery = "DELETE FROM Register_Table1";
            SQLITEDATABASE.execSQL(SQLiteQuery);
            SQLiteQuery = "INSERT INTO Register_Table1 (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,ProductDealsOther,DealsOEBrandsOther)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + hwold_shop + "','" + part + "','" + partname + "','" + Market + "','" + seg_detls + "', '" + pros_dels + "'," +
                    "'" + Monthly + "', '" + majr_branddetls + "','" + major_branddetls_other + "','" + dels_oebrands + "', " +
                    "'" + ltvs_pur + "','" + Ltvs_pur_other + "','" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + pros_dels_other + "','" + dels_oebrands_other + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Capture.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void mech_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table1";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table1 (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,MonthyTurnOver," +
                    "SpecialistIn,MaintainingStock,VehicleAlterMonth,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,Specialist_other)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + hwold_shop + "','" + part + "','" + partname + "','" + Market + "','" + seg_detls + "'," +

                    "'" + Monthly + "', '" + Spec_in + "','" + Stock + "','" + Vehical + "', " +
                    "'" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + spec_in_other + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Capture.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table1";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table1 (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,ProductDealsOther)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + hwold_shop + "','" + part + "','" + partname + "','" + Market + "','" + seg_detls + "', '" + pros_dels + "'," +
                    "'" + majr_branddetls + "', '" + major_branddetls_other + "', '" + Stater + "','" + Alter + "','" + Wiper + "', " +
                    "'" + Noofstaff + "', '" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + pros_dels_other + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Capture.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret__mech_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table1";
            SQLITEDATABASE.execSQL(SQLiteQuery);

            SQLiteQuery = "INSERT INTO Register_Table1 (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,SpecialistIn,MaintainingStock,VehicleAlterMonth," +
                    "Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,Specialist_other,ProductDealsOther,DealsOEBrandsOther)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + hwold_shop + "','" + part + "','" + partname + "','" + Market + "','" + seg_detls + "', '" + pros_dels + "'," +
                    "'" + Monthly + "', '" + majr_branddetls + "','" +major_branddetls_other  + "','" + dels_oebrands + "', " +
                    "'" + ltvs_pur + "','" + Ltvs_pur_other + "','" + Spec_in + "','" + Stock + "','" + Vehical + "'" +
                    ",'" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + spec_in_other + "','" + pros_dels_other + "','" + dels_oebrands_other + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Capture.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void mech_elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table1";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table1 (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,MonthyTurnOver," +
                    "SpecialistIn,MaintainingStock,VehicleAlterMonth," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,ProductDealsWith,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard" +
                    ",Specialist_other,ProductDealsOther)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + hwold_shop + "','" + part + "','" + partname + "','" + Market + "','" + seg_detls + "', '" + Monthly + "'," +
                    "'" + Spec_in + "', '" + Stock + "','" + Vehical + "','" + majr_branddetls + "', " +
                    "'" + major_branddetls_other + "','" + pros_dels + "','" + Stater + "','" + Alter + "','" + Wiper + "','" + Noofstaff + "'" +
                    ",'" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + spec_in_other + "','" + pros_dels_other + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Capture.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret_elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table1";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table1 (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,ProductDealsOther,DealsOEBrandsOther)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + hwold_shop + "','" + part + "','" + partname + "','" + Market + "','" + seg_detls + "', '" + pros_dels + "'," +
                    "'" + Monthly + "', '" + majr_branddetls + "','" + major_branddetls_other + "','" + dels_oebrands + "', " +
                    "'" + ltvs_pur + "','" + Ltvs_pur_other + "', '" + Stater + "', '" + Alter + "', '" + Wiper + "'" +
                    ", '" + Noofstaff + "', '" + LAT + "', '" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + pros_dels_other + "','" + dels_oebrands_other + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Capture.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret_mech_elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table1";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table1 (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,SpecialistIn,MaintainingStock,VehicleAlterMonth," +
                    "Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,Specialist_other,ProductDealsOther,DealsOEBrandsOther)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + hwold_shop + "','" + part + "','" + partname + "','" + Market + "','" + seg_detls + "', '" + pros_dels + "'," +
                    "'" + Monthly + "', '" + majr_branddetls + "','" + major_branddetls_other + "','" + dels_oebrands + "', " +
                    "'" + ltvs_pur + "','" + Ltvs_pur_other + "', '" + Stater + "', '" + Alter + "', " +
                    " '" + Wiper + "', '" + Noofstaff + "', '" + Spec_in + "', '" + Stock + "', '" + Vehical + "', '" + LAT + "', '" + LONG + "','" + image + "','" + image2 + "'" +
                    ",'" + pros_dels_other + "','" + dels_oebrands_other + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Capture.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
