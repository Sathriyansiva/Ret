package com.tvs.Registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class Registration extends AppCompatActivity {


    private String[] state = {"Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh",
            "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
            "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
            "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",};
    private int stateValue;
    private String[] Type = {"RETAILER", "MECHANIC", "ELECTRICIAN"};
    private int TypeValue;
    private String[] Hw_Oldshop = {"0-2 YEAR", "2-5 YEAR", "5 -10 YEAR", "10 & ABOVE"};
    private int Hw_OldshopValue;
    //    private String[] Deals_In = {"TWO WHEELER", "THREE WHEELER"
//            , "TRACTOR", "PCV", "LCV", "UV", "CV", "ENGINES", "EARTH MOVERS", "MINING", "MARINE", "GENSETS"};
//    private int Deals_InValue;
    private String[] SegmentDeals = {"TWO WHEELER", "THREE WHEELER"
            , "CAR", "UV", "LCV", "HCV", "TRACTOR", "ENGINES", "EARTH MOVERS", "MINING", "MARINE", "GENSETS"};
    private int SegmentDealsValue;
    private String[] ProductDeals = {"AUTO ELECTRICAL", "BATTERY", "FILTER", "RADIATOR FAN", "ELECTRICAL ACCESRIES", "ENGINE PARTS", "TYRE"
            , "FUEL INJECTION", "TURBO", "BRAKES"};
    private int ProductDealsValue;
    private String[] MajorBrands = {"LUCAS TVS", "BOSCH", "VALEO", "COMSTAR", "AUTO-LEK", "VARROC", "DENSO", "MAGNETON",
            "MITSUBISHI", "NIKKO", "LOCAL MAKES", "OTHER MAKES"};
    private int MajorBrandsValue;
    private String[] DealsWithOE = {"TATA", "AL", "M&M", "MARUTI", "HYUNDAI", "EICHER", "DAIMLER", "BAJAJ"};
    private int DealsWithOEValue;
    private String[] LTVSPur = {"LIS", "TVS OESL", "MAS", "SM", "IMPAL", "OTHERS"};
    private int LTVSPurValue;
    private String[] MonthlyTurnOver = {"Rs.0-50,000", "Rs.50,000-1,00,000", "Rs.1,00,000-2,00,000", "Rs.2,00,000-5,00,000", "Rs.5,00,000-10,00,000", "Rs.10,00,000 &above"};
    private int MonthlyTurnOverValue;

    private String[] Partner = {"PROPRIETOR", "PARTNER"};
    private int PartnerValue;
    private String[] Market = {"DOMESTIC", "EXPORT"};
    private int MarketValue;
    private String[] Spec_In = {"ENGINE", "CHASIS", "GEAR BOX", "BARAKE", "BODAY PARTS", "OTHERS"};
    private int Spec_InValue;
    private String[] Stock = {"YES", "NO"};
    private int StockValue;
    private String[] VehicalAttend = {"0-50 VEHICLE", "50-100 VEHICLE", "100-200 VEHICLE", "200 & above VEHICLE"};
    private int VehicalAttendValue;
    private String[] No_OffStaff = {"0-5 STAFF", "5-10 STAFF", "10 & above STAFF"};
    private int No_OffStaffValue;
    ImageView iv_image, iv_image2, iv_card;
    private Uri mImageCaptureUri;
    NetworkConnection net;
    String userChoosenTask;
    private int REQUEST_CAMERA = 1888, SELECT_FILE = 1;
    private Bitmap bitmap, bitmap2, bitmap3;
    String fileName;
    String image, image2, visitingcard;
    BitmapFactory.Options bfo, bfo2, bfo3;
    ByteArrayOutputStream bao, bao2, bao3;
    BitmapDrawable drawable, drawable2, drawable3;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SQLiteDatabase SQLITEDATABASE;

    String SQLiteQuery;
    String User_Name;
    //type

    String Contry;
    EditText ed_type;
    String type;

    //pers
    EditText ed_reg_Ownername, ed_reg_Shopname, ed_reg_Door, ed_reg_Street, ed_reg_Area,
            ed_reg_Landmark, ed_reg_City, ed_reg_State, ed_reg_National, ed_reg_Pincode,
            ed_reg_Mobile1, ed_reg_Mobile2, ed_reg_Email, ed_reg_Gst;
    String Ownername, Shopname, DoorNo, Street, Area, Landmark, City, State, National, Pincode, Mobile1, Mobile2, Email, GstNo;
    //ret
    LinearLayout ll_ret, ll_mech, ll_elect, ll_ret_mech, ll_mech_elect, ll_elect_ret, ll_ret_mech_elect,
            ll_partner, ll_majr_branddetls_other, ll_ltvs_pur_other, ll_ret_seg_detls_other;
    EditText ed_hwold_shop, ed_seg_detls, ed_pros_dels, ed_majr_branddetls, ed_dels_oebrands,
            ed_ltvs_pur, ed_part, ed_monthly, ed_market, ed_ret_majr_branddetls_other,
            ed_ret_lat, ed_ret_long, ed_ret_ltvs_pur_other, ed_ret_partname, ed_ret_seg_detls_other;

    String ret_hwold_shop, ret_seg_detls, ret_pros_dels, ret_majr_branddetls, ret_dels_oebrands,
            ret_ltvs_pur, ret_part, ret_monthly, ret_market, ret_majr_branddetls_other, ret_ltvs_pur_other, ret_partname, ret_long, ret_lat;

    //mech
    LinearLayout ll_mech_partner, ll_mech_seg_detls_other;

    EditText ed_mechhwold_shop, ed_detls_in, ed_spec_in, ed_stock, ed_mech_vehical, ed_mech_market, ed_mechpartner, ed_mech_monthly,
            ed_mech_lat, ed_mech_long, ed_mech_partname, ed_mech_seg_detls_other;

    String mechhwold_shop, detls_in, spec_in, stock, mech_vehical, mech_market, mechpartner, mech_monthly, mech_partname, mech_long, mech_lat, spec_inother;

    //elect
    EditText ed_elect_hwold_shop, ed_elect_seg_detls, ed_elect_pros_dels, ed_majr_elect_branddetls,
            ed_elect_market, ed_electpartner, ed_elect_stater, ed_elect_alter, ed_elect_wiper, ed_elect_noofstaff,
            ed_elect_majr_branddetls_other, ed_elect_lat, ed_elect_long, ed_elect_partname, ed_elect_seg_detls_other;

    String elect_hwold_shop, elect_seg_detls, elect_pros_dels, majr_elect_branddetls,
            elect_market, electpartner, elect_stater, elect_alter, elect_wiper, elect_noofstaff,
            elect_majr_branddetls_other, elect_partname, elect_long, elect_lat;

    LinearLayout ll_elect_majr_branddetls_others, ll_elect_partner, ll_elect_seg_detls_other;

    //ret_mech
    LinearLayout ll_ret_mech_partner, ll_ret_mech_majr_branddetls_other, ll_ret_mech_ltvs_pur_other, ll_ret_mech_seg_detls_other;
    EditText ed_ret_mech_hwold_shop, ed_ret_mech_seg_detls, ed_ret_mech_pros_dels, ed_ret_mech_majr_branddetls,
            ed_ret_mech_dels_oebrands, ed_ret_mech_ltvs_pur, ed_ret_mech_part, ed_ret_mech_monthly,
            ed_ret_mech_spec_in, ed_ret_mech_stock, ed_ret_mech_vehical,
            ed_ret_mech_market, ed_ret_mech_majr_branddetls_other, ed_ret_mech_ltvs_pur_other, ed_ret_mech_partname,
            ed_ret_mech_lat, ed_ret_mech_long, ed_ret_mech_seg_detls_other;

    String ret_mech_hwold_shop, ret_mech_seg_detls, ret_mech_pros_dels, ret_mech_majr_branddetls,
            ret_mech_dels_oebrands, ret_mech_ltvs_pur, ret_mech_part, ret_mech_monthly,
            ret_mech_spec_in, ret_mech_stock, ret_mech_vehical, ret_mech_spec_inother,
            ret_mech_market, ret_mech_majr_branddetls_other, ret_mech_ltvs_pur_other, ret_mech_partname, ret_mech_long, ret_mech_lat;

    //mech_elect
    EditText ed_mech_elect_hwold_shop, ed_mech_elect_detls_in, ed_mech_elect_spec_in, ed_mech_elect_stock, ed_mech_elect_vehical,
            ed_mech_elect_market, ed_mech_elect_partner, ed_mech_elect_monthly, ed_mech_elect_majr_branddetls, ed_mech_elect_stater,
            ed_mech_elect_alter, ed_mech_elect_wiper,
            ed_mech_elect_noofstaff, ed_mech_elec_pros_dels, ed_mech_elect_majr_branddetls_other, ed_mech_elect_lat, ed_mech_elect_long, ed_mech_elect_partname, ed_mech_elect_seg_detls_other;

    String mech_elect_hwold_shop, mech_elect_detls_in, mech_elect_spec_in, mech_elect_stock, mech_elect_vehical, mech_elect_spec_inother,
            mech_elect_market, mech_elect_partner, mech_elect_monthly, mech_elect_majr_branddetls, mech_elect_stater, mech_elect_alter,
            mech_elect_wiper, mech_elect_noofstaff, mech_elec_pros_dels, mech_elect_majr_branddetls_other, mech_elect_partname, mech_elect_long, mech_elect_lat;

    LinearLayout ll_mech_elect_partner, ll_mech_elect_majr_branddetls_other, ll_mech_elect_seg_detls_other;

    //elect_ret
    LinearLayout ll_ret_elect_partner, ll_ret_elect_majr_branddetls_other, ll_ret_elect_ltvs_pur_other, ll_ret_elect_seg_detls_other;
    EditText ed_ret_elect_hwold_shop, ed_ret_elect_seg_detls, ed_ret_elect_pros_dels, ed_ret_elect_majr_branddetls,
            ed_ret_elect_dels_oebrands, ed_ret_elect_ltvs_pur, ed_ret_elect_part, ed_ret_elect_monthly,
            ed_ret_elect_stater, ed_ret_elect_alter, ed_ret_elect_wiper, ed_ret_elect_noofstaff,
            ed_ret_elect_market, ed_ret_majr_elect_branddetls_other, ed_ret_elect_ltvs_pur_other, ed_ret_elect_partname, ed_ret_elect_lat, ed_ret_elect_long, ed_ret_elect_seg_detls_other;

    String ret_elect_hwold_shop, ret_elect_seg_detls, ret_elect_pros_dels, ret_elect_majr_branddetls,
            ret_elect_dels_oebrands, ret_elect_ltvs_pur, ret_elect_part, ret_elect_monthly,
            ret_elect_stater, ret_elect_alter, ret_elect_wiper, ret_elect_noofstaff,
            ret_elect_market, ret_majr_elect_branddetls_other, ret_elect_ltvs_pur_other, ret_elect_partname, ret_elect_long, ret_elect_lat;


    //ret_mech_elect_
    LinearLayout ll_ret_mech_elect_partner, ll_ret_mech_elect_majr_branddetls_other, ll_ret_mech_elect_ltvs_pur_other, ll_ret_mech_elect_seg_detls_other;
    EditText ed_ret_mech_elect_hwold_shop, ed_ret_mech_elect_seg_detls, ed_ret_mech_elect_pros_dels, ed_ret_mech_elect_majr_branddetls,
            ed_ret_mech_elect_dels_oebrands, ed_ret_mech_elect_ltvs_pur, ed_ret_mech_elect_part, ed_ret_mech_elect_monthly,
            ed_ret_mech_elect_stater, ed_ret_mech_elect_alter, ed_ret_mech_elect_wiper, ed_ret_mech_elect_noofstaff,
            ed_ret_mech_elect_spec_in, ed_ret_mech_elect_stock, ed_ret_mech_elect_vehical,
            ed_ret_mech_elect_market, ed_ret_mech_majr_elect_branddetls_other, ed_ret_mech_elect_lat, ed_ret_mech_elect_long, ed_ret_mech_elect_ltvs_pur_other, ed_ret_mech_elect_partname, ed_ret_mech_elect_seg_detls_other;

    String ret_mech_elect_hwold_shop, ret_mech_elect_seg_detls, ret_mech_elect_pros_dels, ret_mech_elect_majr_branddetls,
            ret_mech_elect_dels_oebrands, ret_mech_elect_ltvs_pur, ret_mech_elect_part, ret_mech_elect_monthly,
            ret_mech_elect_stater, ret_mech_elect_alter, ret_mech_elect_wiper, ret_mech_elect_noofstaff,
            ret_mech_elect_spec_in, ret_mech_elect_stock, ret_mech_elect_spec_inother, ret_mech_elect_vehical,
            ret_mech_elect_market, ret_mech_majr_elect_branddetls_other, ret_mech_elect_ltvs_pur_other, ret_mech_elect_partname, ret_mech_elect_long, ret_mech_elect_lat;
    String pros_dels_other, dels_oebrands_other;
    AlertDialog.Builder builder;
    GPSTracker gps;
    double latitude;
    double longitude;
    int iv_pos;
    String LAT, LONG, CaptureLocation;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        net = new NetworkConnection(Registration.this);
        builder = new AlertDialog.Builder(Registration.this);
        gps = new GPSTracker(Registration.this);
//        checkGps();

        ll_ret = (LinearLayout) findViewById(R.id.ll_ret);
        ll_mech = (LinearLayout) findViewById(R.id.ll_mech);
        ll_elect = (LinearLayout) findViewById(R.id.ll_elect);
        ll_ret_mech = (LinearLayout) findViewById(R.id.ll_ret_mech);
        ll_mech_elect = (LinearLayout) findViewById(R.id.ll_mech_elect);
        ll_elect_ret = (LinearLayout) findViewById(R.id.ll_elect_ret);
        ll_ret_mech_elect = (LinearLayout) findViewById(R.id.ll_ret_mech_elect);
        iv_image = (ImageView) findViewById(R.id.ivImage);
        iv_image2 = (ImageView) findViewById(R.id.ivImage2);
        iv_card = (ImageView) findViewById(R.id.visit_card);
        ed_type = (EditText) findViewById(R.id.ed_type);
        sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
        ed_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
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
                                    if (type.equals("RETAILER")) {
                                        ll_ret.setVisibility(View.VISIBLE);
                                        ll_mech.setVisibility(View.GONE);
                                        ll_elect.setVisibility(View.GONE);
                                        ll_ret_mech.setVisibility(View.GONE);
                                        ll_mech_elect.setVisibility(View.GONE);
                                        ll_elect_ret.setVisibility(View.GONE);
                                        ll_ret_mech_elect.setVisibility(View.GONE);

                                    } else if (type.equals("MECHANIC")) {
                                        ll_mech.setVisibility(View.VISIBLE);

                                        ll_ret.setVisibility(View.GONE);
                                        ll_elect.setVisibility(View.GONE);
                                        ll_ret_mech.setVisibility(View.GONE);
                                        ll_mech_elect.setVisibility(View.GONE);
                                        ll_elect_ret.setVisibility(View.GONE);
                                        ll_ret_mech_elect.setVisibility(View.GONE);
                                    } else if (type.equals("ELECTRICIAN")) {
                                        ll_elect.setVisibility(View.VISIBLE);

                                        ll_ret.setVisibility(View.GONE);
                                        ll_mech.setVisibility(View.GONE);
                                        ll_ret_mech.setVisibility(View.GONE);
                                        ll_mech_elect.setVisibility(View.GONE);
                                        ll_elect_ret.setVisibility(View.GONE);
                                        ll_ret_mech_elect.setVisibility(View.GONE);
                                    } else if (type.equals("RETAILER,MECHANIC")) {
                                        ll_ret_mech.setVisibility(View.VISIBLE);

                                        ll_ret.setVisibility(View.GONE);
                                        ll_mech.setVisibility(View.GONE);
                                        ll_elect.setVisibility(View.GONE);
                                        ll_mech_elect.setVisibility(View.GONE);
                                        ll_elect_ret.setVisibility(View.GONE);
                                        ll_ret_mech_elect.setVisibility(View.GONE);
                                    } else if (type.equals("MECHANIC,ELECTRICIAN")) {
                                        ll_mech_elect.setVisibility(View.VISIBLE);

                                        ll_ret.setVisibility(View.GONE);
                                        ll_mech.setVisibility(View.GONE);
                                        ll_elect.setVisibility(View.GONE);
                                        ll_ret_mech.setVisibility(View.GONE);
                                        ll_elect_ret.setVisibility(View.GONE);
                                        ll_ret_mech_elect.setVisibility(View.GONE);
                                    } else if (type.equals("RETAILER,ELECTRICIAN")) {
                                        ll_elect_ret.setVisibility(View.VISIBLE);

                                        ll_ret.setVisibility(View.GONE);
                                        ll_mech.setVisibility(View.GONE);
                                        ll_elect.setVisibility(View.GONE);
                                        ll_ret_mech.setVisibility(View.GONE);
                                        ll_mech_elect.setVisibility(View.GONE);
                                        ll_ret_mech_elect.setVisibility(View.GONE);
                                    } else if (type.equals("RETAILER,MECHANIC,ELECTRICIAN")) {
                                        ll_ret_mech_elect.setVisibility(View.VISIBLE);

                                        ll_ret.setVisibility(View.GONE);
                                        ll_mech.setVisibility(View.GONE);
                                        ll_elect.setVisibility(View.GONE);
                                        ll_ret_mech.setVisibility(View.GONE);
                                        ll_mech_elect.setVisibility(View.GONE);
                                        ll_elect_ret.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(Registration.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_type.setText("");
                                type = ed_type.getText().toString().trim();
                                Toast.makeText(Registration.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //pers

        ed_reg_Ownername = (EditText) findViewById(R.id.reg_ownname);
        ed_reg_Shopname = (EditText) findViewById(R.id.reg_shopname);
        ed_reg_Door = (EditText) findViewById(R.id.reg_door_no);
        ed_reg_Street = (EditText) findViewById(R.id.reg_street);
        ed_reg_Area = (EditText) findViewById(R.id.reg_area);
        ed_reg_Landmark = (EditText) findViewById(R.id.reg_landmark);
        ed_reg_City = (EditText) findViewById(R.id.reg_city);
        ed_reg_State = (EditText) findViewById(R.id.reg_satet);
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
        ed_reg_State.setOnClickListener(new View.OnClickListener() {
//        ed_reg_Pincode.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        ed_reg_Mobile1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        ed_reg_Mobile2.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        ed_reg_Email.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

            @Override
            public void onClick(View v) {
                builder.setItems(state, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_reg_State.setText(state[which]);
                        stateValue = which + 1;
                        State = ed_reg_State.getText().toString();

                    }
                });
                builder.show();
            }
        });
        //ret
        ll_partner = (LinearLayout) findViewById(R.id.ll_ret_partner);
        ll_majr_branddetls_other = (LinearLayout) findViewById(R.id.ret_ll_othersmakes);
        ll_ltvs_pur_other = (LinearLayout) findViewById(R.id.ret_ll_ltvs_pur_others);
        ll_ret_seg_detls_other = (LinearLayout) findViewById(R.id.ll_seg_detls_other);
        ed_hwold_shop = (EditText) findViewById(R.id.hwold_shop);
        ed_seg_detls = (EditText) findViewById(R.id.seg_detls);
        ed_pros_dels = (EditText) findViewById(R.id.pros_dels);
        ed_majr_branddetls = (EditText) findViewById(R.id.majr_branddetls);
        ed_dels_oebrands = (EditText) findViewById(R.id.dels_oebrands);
        ed_ltvs_pur = (EditText) findViewById(R.id.ltvs_pur);
        ed_part = (EditText) findViewById(R.id.ret_part);
        ed_monthly = (EditText) findViewById(R.id.ret_monthly);
        ed_market = (EditText) findViewById(R.id.ret_market);
        ed_ret_majr_branddetls_other = (EditText) findViewById(R.id.ret_othersmakes);
        ed_ret_ltvs_pur_other = (EditText) findViewById(R.id.ret_ltvs_pur_others);
        ed_ret_partname = (EditText) findViewById(R.id.ret_partname);
        ed_ret_lat = (EditText) findViewById(R.id.ret_lat);
        ed_ret_long = (EditText) findViewById(R.id.ret_long);
        ed_ret_seg_detls_other = (EditText) findViewById(R.id.seg_detls_other);

        ed_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Market, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_market.setText(Market[which]);
                        MarketValue = which + 1;
                        ret_market = ed_market.getText().toString();

                    }
                });
                builder.show();
            }
        });
        ed_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_part.setText(Partner[which]);
                        if (ed_part.getText().toString().trim().equals("PARTNER")) {

                            ll_partner.setVisibility(View.VISIBLE);
                        } else {
                            ll_partner.setVisibility(View.GONE);
                        }
                        PartnerValue = which + 1;
                        ret_part = ed_part.getText().toString();

                    }
                });
                builder.show();
            }
        });

        ed_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MonthlyTurnOver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_monthly.setText(MonthlyTurnOver[which]);
                        MonthlyTurnOverValue = which + 1;
                        ret_monthly = ed_monthly.getText().toString();
                    }
                });
                builder.show();
            }
        });
        ed_hwold_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_hwold_shop.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        ret_hwold_shop = ed_hwold_shop.getText().toString();

                    }
                });
                builder.show();

            }
        });

        ed_seg_detls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(SegmentDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_seg_detls.setText(SegmentDeals[which]);
//                        SegmentDealsValue = which + 1;
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = SegmentDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(SegmentDeals, is_checked,
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

                                    ed_seg_detls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_seg_detls = ed_seg_detls.getText().toString();
                                } else {

                                    ed_seg_detls.setText(stringBuilder);
                                    if (ed_seg_detls.getText().toString().trim().equals("ALL")) {
                                        ed_seg_detls.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    ret_seg_detls = ed_seg_detls.getText().toString();
                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_seg_detls.setText("");
                                ret_seg_detls = ed_seg_detls.getText().toString();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_pros_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(ProductDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_pros_dels.setText(ProductDeals[which]);
//                        ProductDealsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = ProductDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(ProductDeals, is_checked,
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

                                    ed_pros_dels.setText("");
                                    stringBuilder.setLength(0);
                                    ret_pros_dels = ed_pros_dels.getText().toString();

                                } else {

                                    ed_pros_dels.setText(stringBuilder);
                                    ret_pros_dels = ed_pros_dels.getText().toString();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_pros_dels.setText("");
                                ret_pros_dels = ed_pros_dels.getText().toString();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_majr_branddetls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(MajorBrands, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_majr_branddetls.setText(MajorBrands[which]);
//                        MajorBrandsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = MajorBrands.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(MajorBrands, is_checked,
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

                                    ed_majr_branddetls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_majr_branddetls = ed_majr_branddetls.getText().toString();


                                } else {

                                    ed_majr_branddetls.setText(stringBuilder);
                                    if (ed_majr_branddetls.getText().toString().trim().equals("OTHER MAKES")) {
                                        ll_majr_branddetls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_majr_branddetls_other.setVisibility(View.GONE);
                                    }
                                    ret_majr_branddetls = ed_majr_branddetls.getText().toString();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_majr_branddetls.setText("");
                                ret_majr_branddetls = ed_majr_branddetls.getText().toString();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_dels_oebrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(DealsWithOE, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_dels_oebrands.setText(DealsWithOE[which]);
//                        DealsWithOEValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = DealsWithOE.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(DealsWithOE, is_checked,
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

                                    ed_dels_oebrands.setText("");
                                    stringBuilder.setLength(0);
                                    ret_dels_oebrands = ed_dels_oebrands.getText().toString();
                                } else {

                                    ed_dels_oebrands.setText(stringBuilder);
                                    ret_dels_oebrands = ed_dels_oebrands.getText().toString();
                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_dels_oebrands.setText("");
                                ret_dels_oebrands = ed_dels_oebrands.getText().toString();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ltvs_pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(LTVSPur, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_ltvs_pur.setText(LTVSPur[which]);
//                        LTVSPurValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = LTVSPur.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(LTVSPur, is_checked,
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

                                    ed_ltvs_pur.setText("");
                                    stringBuilder.setLength(0);
                                    ret_ltvs_pur = ed_ltvs_pur.getText().toString();


                                } else {

                                    ed_ltvs_pur.setText(stringBuilder);
                                    if (ed_ltvs_pur.getText().toString().trim().equals("OTHERS")) {
                                        ll_ltvs_pur_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ltvs_pur_other.setVisibility(View.GONE);
                                    }
                                    ret_ltvs_pur = ed_ltvs_pur.getText().toString();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ltvs_pur.setText("");
                                ret_ltvs_pur = ed_ltvs_pur.getText().toString();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        ed_ret_lat.setText(String.valueOf(latitude));
        ed_ret_long.setText(String.valueOf(longitude));

        //mech
        ed_mechhwold_shop = (EditText) findViewById(R.id.mech_hwold_shop);
        ed_detls_in = (EditText) findViewById(R.id.mech_detls_in);

        ed_mech_market = (EditText) findViewById(R.id.mech_market);
        ed_mechpartner = (EditText) findViewById(R.id.mech_part);
        ed_mech_monthly = (EditText) findViewById(R.id.mech_monthly);
        ll_mech_partner = (LinearLayout) findViewById(R.id.ll_mech_partner);
        ll_mech_seg_detls_other = (LinearLayout) findViewById(R.id.ll_mech_seg_detls_other);

        ed_spec_in = (EditText) findViewById(R.id.mech_spec_in);
        ed_stock = (EditText) findViewById(R.id.stock);
        ed_mech_vehical = (EditText) findViewById(R.id.mech_vehical);

        ed_mech_partname = (EditText) findViewById(R.id.mech_partname);
        ed_mech_lat = (EditText) findViewById(R.id.mech_lat);
        ed_mech_long = (EditText) findViewById(R.id.mech_long);
        ed_mech_seg_detls_other = (EditText) findViewById(R.id.seg_mech_detls_other);
        ed_mech_vehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_vehical.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        mech_vehical = ed_mech_vehical.getText().toString();

                    }
                });
                builder.show();
            }
        });
        ed_spec_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(Spec_In, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_spec_in.setText(Spec_In[which]);
//                        Spec_InValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = Spec_In.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(Spec_In, is_checked,
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

                                    ed_spec_in.setText("");
                                    stringBuilder.setLength(0);
                                    spec_in = ed_spec_in.getText().toString();

                                } else {

                                    ed_spec_in.setText(stringBuilder);
                                    spec_in = ed_spec_in.getText().toString();
                                    if (ed_spec_in.getText().toString().trim().equals("OTHERS")) {
                                        ll_mech_seg_detls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_mech_seg_detls_other.setVisibility(View.GONE);
                                    }

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_spec_in.setText("");
                                spec_in = ed_spec_in.getText().toString();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Stock, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_stock.setText(Stock[which]);
                        StockValue = which + 1;
                        stock = ed_stock.getText().toString();

                    }
                });
                builder.show();
            }
        });
        ed_mech_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Market, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_market.setText(Market[which]);
                        MarketValue = which + 1;
                        mech_market = ed_mech_market.getText().toString();

                    }
                });
                builder.show();
            }
        });
        ed_mech_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MonthlyTurnOver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_monthly.setText(MonthlyTurnOver[which]);
                        MonthlyTurnOverValue = which + 1;
                        mech_monthly = ed_mech_monthly.getText().toString();

                    }
                });
                builder.show();
            }
        });
        ed_mechpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mechpartner.setText(Partner[which]);
                        if (ed_mechpartner.getText().toString().trim().equals("PARTNER")) {

                            ll_mech_partner.setVisibility(View.VISIBLE);
                        } else {
                            ll_mech_partner.setVisibility(View.GONE);
                        }
                        PartnerValue = which + 1;
                        mechpartner = ed_mechpartner.getText().toString();

                    }
                });
                builder.show();
            }
        });
        ed_mechhwold_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mechhwold_shop.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        mechhwold_shop = ed_mechhwold_shop.getText().toString();

                    }
                });
                builder.show();
            }
        });
        ed_detls_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(Deals_In, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_detls_in.setText(Deals_In[which]);
//                        Deals_InValue = which + 1;
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = SegmentDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(SegmentDeals, is_checked,
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

                                    ed_detls_in.setText("");
                                    stringBuilder.setLength(0);
                                    detls_in = ed_detls_in.getText().toString();

                                } else {

                                    ed_detls_in.setText(stringBuilder);
                                    if (ed_detls_in.getText().toString().trim().equals("ALL")) {
                                        ed_detls_in.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    detls_in = ed_detls_in.getText().toString();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_detls_in.setText("");
                                detls_in = ed_detls_in.getText().toString();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });


        ed_mech_lat.setText(String.valueOf(latitude));
        ed_mech_long.setText(String.valueOf(longitude));
        //elect
        ed_elect_hwold_shop = (EditText) findViewById(R.id.elect_hwold_shop);
        ed_elect_seg_detls = (EditText) findViewById(R.id.elect_seg_detls);
        ed_elect_pros_dels = (EditText) findViewById(R.id.elect_pros_dels);
        ed_majr_elect_branddetls = (EditText) findViewById(R.id.elect_majr_branddetls);
        ed_elect_market = (EditText) findViewById(R.id.elect_market);
        ed_electpartner = (EditText) findViewById(R.id.elect_part);
        ed_elect_stater = (EditText) findViewById(R.id.elect_starter);
        ed_elect_alter = (EditText) findViewById(R.id.elect_alter);
        ed_elect_wiper = (EditText) findViewById(R.id.elect_wiper);
        ed_elect_noofstaff = (EditText) findViewById(R.id.elect_noofstaf);
        ed_elect_majr_branddetls_other = (EditText) findViewById(R.id.elect_majr_branddetls_others);
        ed_elect_partname = (EditText) findViewById(R.id.elect_partname);
        ed_elect_lat = (EditText) findViewById(R.id.elect_lat);
        ed_elect_long = (EditText) findViewById(R.id.elect_long);
        ll_elect_partner = (LinearLayout) findViewById(R.id.ll_elect_partner);

        ll_elect_majr_branddetls_others = (LinearLayout) findViewById(R.id.ll_elect_majr_branddetls_others);
        ll_elect_seg_detls_other = (LinearLayout) findViewById(R.id.ll_elect_seg_detls_other);
        ed_elect_seg_detls_other = (EditText) findViewById(R.id.seg_elect_detls_other);

        ed_elect_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Market, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_elect_market.setText(Market[which]);
                        MarketValue = which + 1;
                        elect_market = ed_elect_market.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_electpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_electpartner.setText(Partner[which]);
                        if (ed_electpartner.getText().toString().trim().equals("PARTNER")) {

                            ll_elect_partner.setVisibility(View.VISIBLE);
                        } else {
                            ll_elect_partner.setVisibility(View.GONE);
                        }
                        PartnerValue = which + 1;
                        electpartner = ed_electpartner.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_elect_hwold_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_elect_hwold_shop.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        elect_hwold_shop = ed_elect_hwold_shop.getText().toString().trim();
                    }
                });
                builder.show();
            }
        });
        ed_elect_seg_detls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(SegmentDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_seg_detls.setText(SegmentDeals[which]);
//                        SegmentDealsValue = which + 1;
//                    }
//                });
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = SegmentDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(SegmentDeals, is_checked,
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

                                    ed_elect_seg_detls.setText("");
                                    stringBuilder.setLength(0);
                                    elect_seg_detls = ed_elect_seg_detls.getText().toString().trim();

                                } else {

                                    ed_elect_seg_detls.setText(stringBuilder);
                                    if (ed_elect_seg_detls.getText().toString().trim().equals("ALL")) {
                                        ed_elect_seg_detls.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    elect_seg_detls = ed_elect_seg_detls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_elect_seg_detls.setText("");
                                elect_seg_detls = ed_elect_seg_detls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        ed_elect_pros_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(ProductDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_pros_dels.setText(ProductDeals[which]);
//                        ProductDealsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = ProductDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(ProductDeals, is_checked,
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
                                if (stringBuilder.toString().trim().equals(" ")) {

                                    ed_elect_pros_dels.setText("");
                                    stringBuilder.setLength(0);
                                    elect_pros_dels = ed_elect_pros_dels.getText().toString().trim();


                                } else {

                                    ed_elect_pros_dels.setText(stringBuilder);
                                    elect_pros_dels = ed_elect_pros_dels.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_elect_pros_dels.setText("");
                                elect_pros_dels = ed_elect_pros_dels.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_majr_elect_branddetls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(MajorBrands, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_majr_branddetls.setText(MajorBrands[which]);
//                        MajorBrandsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = MajorBrands.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(MajorBrands, is_checked,
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

                                    ed_majr_elect_branddetls.setText("");
                                    stringBuilder.setLength(0);
                                    majr_elect_branddetls = ed_majr_elect_branddetls.getText().toString().trim();

                                } else {

                                    ed_majr_elect_branddetls.setText(stringBuilder);
                                    if (ed_majr_elect_branddetls.getText().toString().trim().equals("OTHER MAKES")) {
                                        ll_elect_majr_branddetls_others.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_elect_majr_branddetls_others.setVisibility(View.GONE);
                                    }
                                    majr_elect_branddetls = ed_majr_elect_branddetls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_majr_elect_branddetls.setText("");
                                majr_elect_branddetls = ed_majr_elect_branddetls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        ed_elect_stater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_elect_stater.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        elect_stater = ed_elect_stater.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_elect_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_elect_alter.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        elect_alter = ed_elect_alter.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_elect_wiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_elect_wiper.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        elect_wiper = ed_elect_wiper.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_elect_noofstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(No_OffStaff, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_elect_noofstaff.setText(No_OffStaff[which]);
                        No_OffStaffValue = which + 1;
                        elect_noofstaff = ed_elect_noofstaff.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });


        ed_elect_lat.setText(String.valueOf(latitude));
        ed_elect_long.setText(String.valueOf(longitude));
//ret_mech
        ll_ret_mech_partner = (LinearLayout) findViewById(R.id.ll_ret_mech_partner);
        ll_ret_mech_majr_branddetls_other = (LinearLayout) findViewById(R.id.ll_ret_mech_majr_branddetlsother);
        ll_ret_mech_ltvs_pur_other = (LinearLayout) findViewById(R.id.ll_ret_mechltvs_pur_other);
        ll_ret_mech_seg_detls_other = (LinearLayout) findViewById(R.id.ll_ret_mech_seg_detls_other);

        ed_ret_mech_hwold_shop = (EditText) findViewById(R.id.ret_mech_hwold_shop);
        ed_ret_mech_seg_detls = (EditText) findViewById(R.id.ret_mech_seg_detls);
        ed_ret_mech_pros_dels = (EditText) findViewById(R.id.ret_mech_pros_dels);
        ed_ret_mech_majr_branddetls = (EditText) findViewById(R.id.ret_mech_majr_branddetls);
        ed_ret_mech_dels_oebrands = (EditText) findViewById(R.id.ret_mech_dels_oebrands);
        ed_ret_mech_ltvs_pur = (EditText) findViewById(R.id.ret_mech_ltvs_pur);
        ed_ret_mech_part = (EditText) findViewById(R.id.ret_mech_part);
        ed_ret_mech_monthly = (EditText) findViewById(R.id.ret_mech_monthly);
        ed_ret_mech_market = (EditText) findViewById(R.id.ret_mech_market);
        ed_ret_mech_spec_in = (EditText) findViewById(R.id.ret_mech_spec_in);
        ed_ret_mech_stock = (EditText) findViewById(R.id.ret_mech_stock);
        ed_ret_mech_vehical = (EditText) findViewById(R.id.ret_mech_vehical);
        ed_ret_mech_majr_branddetls_other = (EditText) findViewById(R.id.ret_mech_majr_branddetlsother);
        ed_ret_mech_ltvs_pur_other = (EditText) findViewById(R.id.ret_mechltvs_pur_other);
        ed_ret_mech_partname = (EditText) findViewById(R.id.ret_mech_partname);
        ed_ret_mech_lat = (EditText) findViewById(R.id.ret_mech_lat);
        ed_ret_mech_long = (EditText) findViewById(R.id.ret_mech_long);
        ed_ret_mech_seg_detls_other = (EditText) findViewById(R.id.seg_ret_mech_detls_other);

        ed_ret_mech_vehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_vehical.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_vehical = ed_ret_mech_vehical.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_spec_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(Spec_In, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_spec_in.setText(Spec_In[which]);
//                        Spec_InValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = Spec_In.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(Spec_In, is_checked,
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

                                    ed_ret_mech_spec_in.setText(" ");
                                    stringBuilder.setLength(0);
                                    ret_mech_spec_in = ed_ret_mech_spec_in.getText().toString().trim();

                                } else {

                                    ed_ret_mech_spec_in.setText(stringBuilder);
                                    ret_mech_spec_in = ed_ret_mech_spec_in.getText().toString().trim();
                                    if (ed_ret_mech_spec_in.getText().toString().trim().equals("OTHERS")) {
                                        ll_ret_mech_seg_detls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_mech_seg_detls_other.setVisibility(View.GONE);
                                    }

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_spec_in.setText(" ");
                                ret_mech_spec_in = ed_ret_mech_spec_in.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Stock, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_stock.setText(Stock[which]);
                        StockValue = which + 1;
                        ret_mech_stock = ed_ret_mech_stock.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Market, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_market.setText(Market[which]);
                        MarketValue = which + 1;
                        ret_mech_market = ed_ret_mech_market.getText().toString().trim();


                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_part.setText(Partner[which]);
                        if (ed_ret_mech_part.getText().toString().trim().equals("PARTNER")) {

                            ll_ret_mech_partner.setVisibility(View.VISIBLE);
                        } else {
                            ll_ret_mech_partner.setVisibility(View.GONE);
                        }
                        PartnerValue = which + 1;
                        ret_mech_part = ed_ret_mech_part.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });

        ed_ret_mech_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MonthlyTurnOver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_monthly.setText(MonthlyTurnOver[which]);
                        MonthlyTurnOverValue = which + 1;
                        ret_mech_monthly = ed_ret_mech_monthly.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_hwold_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_hwold_shop.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        ret_mech_hwold_shop = ed_ret_mech_hwold_shop.getText().toString().trim();

                    }
                });
                builder.show();

            }
        });

        ed_ret_mech_seg_detls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(SegmentDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_seg_detls.setText(SegmentDeals[which]);
//                        SegmentDealsValue = which + 1;
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = SegmentDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(SegmentDeals, is_checked,
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

                                    ed_ret_mech_seg_detls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_seg_detls = ed_ret_mech_seg_detls.getText().toString().trim();

                                } else {

                                    ed_ret_mech_seg_detls.setText(stringBuilder);
                                    if (ed_ret_mech_seg_detls.getText().toString().trim().equals("ALL")) {
                                        ed_ret_mech_seg_detls.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    ret_mech_seg_detls = ed_ret_mech_seg_detls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_seg_detls.setText("");
                                ret_mech_seg_detls = ed_ret_mech_seg_detls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_pros_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(ProductDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_pros_dels.setText(ProductDeals[which]);
//                        ProductDealsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = ProductDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(ProductDeals, is_checked,
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

                                    ed_ret_mech_pros_dels.setText(" ");
                                    stringBuilder.setLength(0);
                                    ret_mech_pros_dels = ed_ret_mech_pros_dels.getText().toString().trim();

                                } else {

                                    ed_ret_mech_pros_dels.setText(stringBuilder);
                                    ret_mech_pros_dels = ed_ret_mech_pros_dels.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_pros_dels.setText("");
                                ret_mech_pros_dels = ed_ret_mech_pros_dels.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_majr_branddetls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(MajorBrands, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_majr_branddetls.setText(MajorBrands[which]);
//                        MajorBrandsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = MajorBrands.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(MajorBrands, is_checked,
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

                                    ed_ret_mech_majr_branddetls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_majr_branddetls = ed_ret_mech_majr_branddetls.getText().toString().trim();

                                } else {

                                    ed_ret_mech_majr_branddetls.setText(stringBuilder);
                                    if (ed_ret_mech_majr_branddetls.getText().toString().trim().equals("OTHER MAKES")) {
                                        ll_ret_mech_majr_branddetls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_mech_majr_branddetls_other.setVisibility(View.GONE);
                                    }
                                    ret_mech_majr_branddetls = ed_ret_mech_majr_branddetls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_majr_branddetls.setText("");
                                ret_mech_majr_branddetls = ed_ret_mech_majr_branddetls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_dels_oebrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(DealsWithOE, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_dels_oebrands.setText(DealsWithOE[which]);
//                        DealsWithOEValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = DealsWithOE.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(DealsWithOE, is_checked,
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

                                    ed_ret_mech_dels_oebrands.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_dels_oebrands = ed_ret_mech_dels_oebrands.getText().toString().trim();

                                } else {

                                    ed_ret_mech_dels_oebrands.setText(stringBuilder);
                                    ret_mech_dels_oebrands = ed_ret_mech_dels_oebrands.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_dels_oebrands.setText("");
                                ret_mech_dels_oebrands = ed_ret_mech_dels_oebrands.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_ltvs_pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(LTVSPur, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_ltvs_pur.setText(LTVSPur[which]);
//                        LTVSPurValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = LTVSPur.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(LTVSPur, is_checked,
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

                                    ed_ret_mech_ltvs_pur.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_ltvs_pur = ed_ret_mech_ltvs_pur.getText().toString().trim();


                                } else {

                                    ed_ret_mech_ltvs_pur.setText(stringBuilder);
                                    if (ed_ret_mech_ltvs_pur.getText().toString().trim().equals("OTHERS")) {
                                        ll_ret_mech_ltvs_pur_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_mech_ltvs_pur_other.setVisibility(View.GONE);
                                    }
                                    ret_mech_ltvs_pur = ed_ret_mech_ltvs_pur.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_ltvs_pur.setText("");
                                ret_mech_ltvs_pur = ed_ret_mech_ltvs_pur.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        ed_ret_mech_lat.setText(String.valueOf(latitude));
        ed_ret_mech_long.setText(String.valueOf(longitude));
        //mech_elect
        ll_mech_elect_partner = (LinearLayout) findViewById(R.id.ll_mech_elect_partner);
        ll_mech_elect_majr_branddetls_other = (LinearLayout) findViewById(R.id.ll_mech_elect_majr_branddetls_others);
        ll_mech_elect_seg_detls_other = (LinearLayout) findViewById(R.id.ll_mech_elect_seg_detls_other);

        ed_mech_elect_hwold_shop = (EditText) findViewById(R.id.mech_elect_hwold_shop);
        ed_mech_elect_detls_in = (EditText) findViewById(R.id.mech_elect_detls_in);
        ed_mech_elect_spec_in = (EditText) findViewById(R.id.mech_elect_spec_in);
        ed_mech_elect_stock = (EditText) findViewById(R.id.mech_elect_stock);
        ed_mech_elect_vehical = (EditText) findViewById(R.id.mech_elect_vehical);
        ed_mech_elect_market = (EditText) findViewById(R.id.mech_elect_market);
        ed_mech_elect_partner = (EditText) findViewById(R.id.mech_elect_part);
        ed_mech_elect_monthly = (EditText) findViewById(R.id.mech_elect_monthly);
        ed_mech_elect_stater = (EditText) findViewById(R.id.mech_elect_starter);
        ed_mech_elect_alter = (EditText) findViewById(R.id.mech_elect_alter);
        ed_mech_elect_wiper = (EditText) findViewById(R.id.mech_elect_wiper);
        ed_mech_elect_noofstaff = (EditText) findViewById(R.id.mech_elect_noofstaf);
        ed_mech_elect_majr_branddetls = (EditText) findViewById(R.id.mech_elect_majr_branddetls);
        ed_mech_elect_majr_branddetls_other = (EditText) findViewById(R.id.mech_elect_majr_branddetls_others);
        ed_mech_elect_partname = (EditText) findViewById(R.id.mech_elect_partname);
        ed_mech_elec_pros_dels = (EditText) findViewById(R.id.mech_elect_pros_dels);
        ed_mech_elect_lat = (EditText) findViewById(R.id.elect_mech_lat);
        ed_mech_elect_long = (EditText) findViewById(R.id.elect_mech_long);
        ed_mech_elect_seg_detls_other = (EditText) findViewById(R.id.seg_mech_elect_detls_other);

        ed_mech_elec_pros_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(ProductDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_pros_dels.setText(ProductDeals[which]);
//                        ProductDealsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = ProductDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(ProductDeals, is_checked,
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

                                    ed_mech_elec_pros_dels.setText("");
                                    stringBuilder.setLength(0);
                                    mech_elec_pros_dels = ed_mech_elec_pros_dels.getText().toString().trim();

                                } else {

                                    ed_mech_elec_pros_dels.setText(stringBuilder);
                                    mech_elec_pros_dels = ed_mech_elec_pros_dels.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_mech_elec_pros_dels.setText("");
                                mech_elec_pros_dels = ed_mech_elec_pros_dels.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_mech_elect_majr_branddetls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(MajorBrands, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_majr_branddetls.setText(MajorBrands[which]);
//                        MajorBrandsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = MajorBrands.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(MajorBrands, is_checked,
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

                                    ed_mech_elect_majr_branddetls.setText("");
                                    stringBuilder.setLength(0);
                                    mech_elect_majr_branddetls = ed_mech_elect_majr_branddetls.getText().toString().trim();

                                } else {

                                    ed_mech_elect_majr_branddetls.setText(stringBuilder);
                                    if (ed_mech_elect_majr_branddetls.getText().toString().trim().equals("OTHER MAKES")) {
                                        ll_mech_elect_majr_branddetls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_mech_elect_majr_branddetls_other.setVisibility(View.GONE);
                                    }
                                    mech_elect_majr_branddetls = ed_mech_elect_majr_branddetls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_mech_elect_majr_branddetls.setText("");
                                mech_elect_majr_branddetls = ed_mech_elect_majr_branddetls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_mech_elect_vehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_vehical.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        mech_elect_vehical = ed_mech_elect_vehical.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_spec_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(Spec_In, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_spec_in.setText(Spec_In[which]);
//                        Spec_InValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = Spec_In.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(Spec_In, is_checked,
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

                                    ed_mech_elect_spec_in.setText("");
                                    stringBuilder.setLength(0);
                                    mech_elect_spec_in = ed_mech_elect_spec_in.getText().toString().trim();

                                } else {

                                    ed_mech_elect_spec_in.setText(stringBuilder);
                                    mech_elect_spec_in = ed_mech_elect_spec_in.getText().toString().trim();
                                    if (ed_mech_elect_spec_in.getText().toString().trim().equals("OTHERS")) {
                                        ll_mech_elect_seg_detls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_mech_elect_seg_detls_other.setVisibility(View.GONE);
                                    }


                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_mech_elect_spec_in.setText("");
                                mech_elect_spec_in = ed_mech_elect_spec_in.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_mech_elect_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Stock, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_stock.setText(Stock[which]);
                        StockValue = which + 1;
                        mech_elect_stock = ed_mech_elect_stock.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_stater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_stater.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        mech_elect_stater = ed_mech_elect_stater.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_alter.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        mech_elect_alter = ed_mech_elect_alter.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_wiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_wiper.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        mech_elect_wiper = ed_mech_elect_wiper.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_noofstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(No_OffStaff, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_noofstaff.setText(No_OffStaff[which]);
                        No_OffStaffValue = which + 1;
                        mech_elect_noofstaff = ed_mech_elect_noofstaff.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });

        ed_mech_elect_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Market, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_market.setText(Market[which]);
                        MarketValue = which + 1;
                        mech_elect_market = ed_mech_elect_market.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MonthlyTurnOver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_monthly.setText(MonthlyTurnOver[which]);
                        MonthlyTurnOverValue = which + 1;
                        mech_elect_monthly = ed_mech_elect_monthly.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_partner.setText(Partner[which]);
                        if (ed_mech_elect_partner.getText().toString().trim().equals("PARTNER")) {

                            ll_mech_elect_partner.setVisibility(View.VISIBLE);
                        } else {
                            ll_mech_elect_partner.setVisibility(View.GONE);
                        }
                        PartnerValue = which + 1;
                        mech_elect_partner = ed_mech_elect_partner.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_hwold_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_mech_elect_hwold_shop.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        mech_elect_hwold_shop = ed_mech_elect_hwold_shop.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_mech_elect_detls_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(Deals_In, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_detls_in.setText(Deals_In[which]);
//                        Deals_InValue = which + 1;
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = SegmentDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(SegmentDeals, is_checked,
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

                                    ed_mech_elect_detls_in.setText("");
                                    stringBuilder.setLength(0);
                                    mech_elect_detls_in = ed_mech_elect_detls_in.getText().toString().trim();

                                } else {

                                    ed_mech_elect_detls_in.setText(stringBuilder);
                                    if (ed_mech_elect_detls_in.getText().toString().trim().equals("ALL")) {
                                        ed_mech_elect_detls_in.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    mech_elect_detls_in = ed_mech_elect_detls_in.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_mech_elect_detls_in.setText("");
                                mech_elect_detls_in = ed_mech_elect_detls_in.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        ed_mech_elect_lat.setText(String.valueOf(latitude));
        ed_mech_elect_long.setText(String.valueOf(longitude));
//ret_elect
        ll_ret_elect_partner = (LinearLayout) findViewById(R.id.ll_ret_elect_partner);
        ll_ret_elect_majr_branddetls_other = (LinearLayout) findViewById(R.id.ll_ret_elect_majr_branddetlsother);
        ll_ret_elect_ltvs_pur_other = (LinearLayout) findViewById(R.id.ll_ret_electltvs_pur_other);
        ll_ret_elect_seg_detls_other = (LinearLayout) findViewById(R.id.ll_ret_elect_seg_detls_other);

        ed_ret_elect_hwold_shop = (EditText) findViewById(R.id.ret_elect_hwold_shop);
        ed_ret_elect_seg_detls = (EditText) findViewById(R.id.ret_elect_seg_detls);
        ed_ret_elect_pros_dels = (EditText) findViewById(R.id.ret_elect_pros_dels);
        ed_ret_elect_majr_branddetls = (EditText) findViewById(R.id.ret_elect_majr_branddetls);
        ed_ret_elect_dels_oebrands = (EditText) findViewById(R.id.ret_elect_dels_oebrands);
        ed_ret_elect_ltvs_pur = (EditText) findViewById(R.id.ret_elect_ltvs_pur);
        ed_ret_elect_part = (EditText) findViewById(R.id.ret_elect_part);
        ed_ret_elect_monthly = (EditText) findViewById(R.id.ret_elect_monthly);
        ed_ret_elect_market = (EditText) findViewById(R.id.ret_elect_market);
        ed_ret_elect_stater = (EditText) findViewById(R.id.ret_elect_starter);
        ed_ret_elect_alter = (EditText) findViewById(R.id.ret_elect_alter);
        ed_ret_elect_wiper = (EditText) findViewById(R.id.ret_elect_wiper);
        ed_ret_elect_noofstaff = (EditText) findViewById(R.id.ret_elect_noofstaf);
        ed_ret_majr_elect_branddetls_other = (EditText) findViewById(R.id.ret_elect_majr_branddetlsother);
        ed_ret_elect_ltvs_pur_other = (EditText) findViewById(R.id.ret_electltvs_pur_other);
        ed_ret_elect_partname = (EditText) findViewById(R.id.ret_elect_partname);
        ed_ret_elect_lat = (EditText) findViewById(R.id.ret_elect_lat);
        ed_ret_elect_long = (EditText) findViewById(R.id.ret_elect_long);
        ed_ret_elect_seg_detls_other = (EditText) findViewById(R.id.seg_ret_elect_detls_other);

        ed_ret_elect_stater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_stater.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_elect_stater = ed_ret_elect_stater.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_elect_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_alter.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_elect_alter = ed_ret_elect_alter.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_elect_wiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_wiper.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_elect_wiper = ed_ret_elect_wiper.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_elect_noofstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(No_OffStaff, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_noofstaff.setText(No_OffStaff[which]);
                        No_OffStaffValue = which + 1;
                        ret_elect_noofstaff = ed_ret_elect_noofstaff.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_elect_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Market, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_market.setText(Market[which]);
                        MarketValue = which + 1;
                        ret_elect_market = ed_ret_elect_market.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_elect_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_part.setText(Partner[which]);
                        if (ed_ret_elect_part.getText().toString().trim().equals("PARTNER")) {

                            ll_ret_elect_partner.setVisibility(View.VISIBLE);
                        } else {
                            ll_ret_elect_partner.setVisibility(View.GONE);
                        }
                        PartnerValue = which + 1;
                        ret_elect_part = ed_ret_elect_part.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });

        ed_ret_elect_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MonthlyTurnOver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_monthly.setText(MonthlyTurnOver[which]);
                        MonthlyTurnOverValue = which + 1;
                        ret_elect_monthly = ed_ret_elect_monthly.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_elect_hwold_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_elect_hwold_shop.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        ret_elect_hwold_shop = ed_ret_elect_hwold_shop.getText().toString().trim();

                    }
                });
                builder.show();

            }
        });

        ed_ret_elect_seg_detls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(SegmentDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_seg_detls.setText(SegmentDeals[which]);
//                        SegmentDealsValue = which + 1;
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = SegmentDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(SegmentDeals, is_checked,
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

                                    ed_ret_elect_seg_detls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_elect_seg_detls = ed_ret_elect_seg_detls.getText().toString().trim();

                                } else {

                                    ed_ret_elect_seg_detls.setText(stringBuilder);
                                    if (ed_ret_elect_seg_detls.getText().toString().trim().equals("ALL")) {
                                        ed_ret_elect_seg_detls.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    ret_elect_seg_detls = ed_ret_elect_seg_detls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_elect_seg_detls.setText("");
                                ret_elect_seg_detls = ed_ret_elect_seg_detls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_elect_pros_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(ProductDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_pros_dels.setText(ProductDeals[which]);
//                        ProductDealsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = ProductDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(ProductDeals, is_checked,
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

                                    ed_ret_elect_pros_dels.setText(" ");
                                    stringBuilder.setLength(0);
                                    ret_elect_pros_dels = ed_ret_elect_pros_dels.getText().toString().trim();

                                } else {

                                    ed_ret_elect_pros_dels.setText(stringBuilder);
                                    ret_elect_pros_dels = ed_ret_elect_pros_dels.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_elect_pros_dels.setText("");
                                ret_elect_pros_dels = ed_ret_elect_pros_dels.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_elect_majr_branddetls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(MajorBrands, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_majr_branddetls.setText(MajorBrands[which]);
//                        MajorBrandsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = MajorBrands.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(MajorBrands, is_checked,
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

                                    ed_ret_elect_majr_branddetls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_elect_majr_branddetls = ed_ret_elect_majr_branddetls.getText().toString().trim();

                                } else {

                                    ed_ret_elect_majr_branddetls.setText(stringBuilder);
                                    if (ed_ret_elect_majr_branddetls.getText().toString().trim().equals("OTHER MAKES")) {
                                        ll_ret_elect_majr_branddetls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_elect_majr_branddetls_other.setVisibility(View.GONE);
                                    }
                                    ret_elect_majr_branddetls = ed_ret_elect_majr_branddetls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_elect_majr_branddetls.setText("");
                                ret_elect_majr_branddetls = ed_ret_elect_majr_branddetls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_elect_dels_oebrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(DealsWithOE, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_dels_oebrands.setText(DealsWithOE[which]);
//                        DealsWithOEValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = DealsWithOE.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(DealsWithOE, is_checked,
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

                                    ed_ret_elect_dels_oebrands.setText("");
                                    stringBuilder.setLength(0);
                                    ret_elect_dels_oebrands = ed_ret_elect_dels_oebrands.getText().toString().trim();

                                } else {

                                    ed_ret_elect_dels_oebrands.setText(stringBuilder);
                                    ret_elect_dels_oebrands = ed_ret_elect_dels_oebrands.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_elect_dels_oebrands.setText("");
                                ret_elect_dels_oebrands = ed_ret_elect_dels_oebrands.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_elect_ltvs_pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(LTVSPur, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_ltvs_pur.setText(LTVSPur[which]);
//                        LTVSPurValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = LTVSPur.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(LTVSPur, is_checked,
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

                                    ed_ret_elect_ltvs_pur.setText("");
                                    stringBuilder.setLength(0);
                                    ret_elect_ltvs_pur = ed_ret_elect_ltvs_pur.getText().toString().trim();

                                } else {

                                    ed_ret_elect_ltvs_pur.setText(stringBuilder);
                                    if (ed_ret_elect_ltvs_pur.getText().toString().trim().equals("OTHERS")) {
                                        ll_ret_elect_ltvs_pur_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_elect_ltvs_pur_other.setVisibility(View.GONE);
                                    }
                                    ret_elect_ltvs_pur = ed_ret_elect_ltvs_pur.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_elect_ltvs_pur.setText("");
                                ret_elect_ltvs_pur = ed_ret_elect_ltvs_pur.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        ed_ret_elect_lat.setText(String.valueOf(latitude));
        ed_ret_elect_long.setText(String.valueOf(longitude));

        //ret_mech_elect


        ll_ret_mech_elect_partner = (LinearLayout) findViewById(R.id.ll_ret_mech_elect_partner);
        ll_ret_mech_elect_majr_branddetls_other = (LinearLayout) findViewById(R.id.ll_ret_mech_elect_majr_branddetlsother);
        ll_ret_mech_elect_ltvs_pur_other = (LinearLayout) findViewById(R.id.ll_ret_mech_elect_ltvs_pur_other);
        ll_ret_mech_elect_seg_detls_other = (LinearLayout) findViewById(R.id.ll_ret_mech_elect_seg_detls_other);

        ed_ret_mech_elect_hwold_shop = (EditText) findViewById(R.id.ret_mech_elect_hwold_shop);
        ed_ret_mech_elect_seg_detls = (EditText) findViewById(R.id.ret_mech_elect_seg_detls);
        ed_ret_mech_elect_pros_dels = (EditText) findViewById(R.id.ret_mech_elect_pros_dels);
        ed_ret_mech_elect_majr_branddetls = (EditText) findViewById(R.id.ret_mech_elect_majr_branddetls);
        ed_ret_mech_elect_dels_oebrands = (EditText) findViewById(R.id.ret_mech_elect_dels_oebrands);
        ed_ret_mech_elect_ltvs_pur = (EditText) findViewById(R.id.ret_mech_elect_ltvs_pur);
        ed_ret_mech_elect_part = (EditText) findViewById(R.id.ret_mech_elect_part);
        ed_ret_mech_elect_monthly = (EditText) findViewById(R.id.ret_mech_elect_monthly);
        ed_ret_mech_elect_market = (EditText) findViewById(R.id.ret_mech_elect_market);
        ed_ret_mech_elect_stater = (EditText) findViewById(R.id.ret_mech_elect_starter);
        ed_ret_mech_elect_alter = (EditText) findViewById(R.id.ret_mech_elect_alter);
        ed_ret_mech_elect_wiper = (EditText) findViewById(R.id.ret_mech_elect_wiper);
        ed_ret_mech_elect_noofstaff = (EditText) findViewById(R.id.ret_mech_elect_noofstaf);
        ed_ret_mech_elect_spec_in = (EditText) findViewById(R.id.ret_mech_elect_spec_in);
        ed_ret_mech_elect_stock = (EditText) findViewById(R.id.ret_mech_elect_stock);
        ed_ret_mech_elect_vehical = (EditText) findViewById(R.id.ret_mech_elect_vehical);
        ed_ret_mech_majr_elect_branddetls_other = (EditText) findViewById(R.id.ret_mech_elect_majr_branddetlsother);
        ed_ret_mech_elect_ltvs_pur_other = (EditText) findViewById(R.id.ret_mech_elect_ltvs_pur_other);
        ed_ret_mech_elect_partname = (EditText) findViewById(R.id.ret_mech_elect_partname);
        ed_ret_mech_elect_lat = (EditText) findViewById(R.id.ret_mech_elect_lat);
        ed_ret_mech_elect_long = (EditText) findViewById(R.id.ret_mech_elect_long);
        ed_ret_mech_elect_seg_detls_other = (EditText) findViewById(R.id.seg_ret_mech_elect_detls_other);

        ed_ret_mech_elect_vehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_vehical.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_vehical = ed_ret_mech_elect_vehical.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_spec_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(Spec_In, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_spec_in.setText(Spec_In[which]);
//                        Spec_InValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = Spec_In.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(Spec_In, is_checked,
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

                                    ed_ret_mech_elect_spec_in.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_spec_in = ed_ret_mech_elect_spec_in.getText().toString().trim();

                                } else {

                                    ed_ret_mech_elect_spec_in.setText(stringBuilder);
                                    ret_mech_elect_spec_in = ed_ret_mech_elect_spec_in.getText().toString().trim();
                                    if (ed_ret_mech_elect_spec_in.getText().toString().trim().equals("OTHERS")) {
                                        ll_ret_mech_elect_seg_detls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_mech_elect_seg_detls_other.setVisibility(View.GONE);
                                    }
                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_elect_spec_in.setText("");
                                ret_mech_elect_spec_in = ed_ret_mech_elect_spec_in.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_elect_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Stock, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_stock.setText(Stock[which]);
                        StockValue = which + 1;
                        ret_mech_elect_stock = ed_ret_mech_elect_stock.getText().toString().trim();


                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_stater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_stater.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_stater = ed_ret_mech_elect_stater.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_alter.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_alter = ed_ret_mech_elect_alter.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_wiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_wiper.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_wiper = ed_ret_mech_elect_wiper.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_noofstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(No_OffStaff, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_noofstaff.setText(No_OffStaff[which]);
                        No_OffStaffValue = which + 1;
                        ret_mech_elect_noofstaff = ed_ret_mech_elect_noofstaff.getText().toString().trim();
                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Market, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_market.setText(Market[which]);
                        MarketValue = which + 1;
                        ret_mech_elect_market = ed_ret_mech_elect_market.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_part.setText(Partner[which]);
                        if (ed_ret_mech_elect_part.getText().toString().trim().equals("PARTNER")) {

                            ll_ret_mech_elect_partner.setVisibility(View.VISIBLE);
                        } else {
                            ll_ret_mech_elect_partner.setVisibility(View.GONE);
                        }
                        PartnerValue = which + 1;
                        ret_mech_elect_part = ed_ret_mech_elect_part.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });

        ed_ret_mech_elect_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MonthlyTurnOver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_monthly.setText(MonthlyTurnOver[which]);
                        MonthlyTurnOverValue = which + 1;
                        ret_mech_elect_monthly = ed_ret_mech_elect_monthly.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        ed_ret_mech_elect_hwold_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_ret_mech_elect_hwold_shop.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        ret_mech_elect_hwold_shop = ed_ret_mech_elect_hwold_shop.getText().toString().trim();

                    }
                });
                builder.show();

            }
        });

        ed_ret_mech_elect_seg_detls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(SegmentDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_seg_detls.setText(SegmentDeals[which]);
//                        SegmentDealsValue = which + 1;
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = SegmentDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(SegmentDeals, is_checked,
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

                                    ed_ret_mech_elect_seg_detls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_seg_detls = ed_ret_mech_elect_seg_detls.getText().toString().trim();

                                } else {

                                    ed_ret_mech_elect_seg_detls.setText(stringBuilder);
                                    if (ed_ret_mech_elect_seg_detls.getText().toString().trim().equals("ALL")) {
                                        ed_ret_mech_elect_seg_detls.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    ret_mech_elect_seg_detls = ed_ret_mech_elect_seg_detls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_elect_seg_detls.setText("");
                                ret_mech_elect_seg_detls = ed_ret_mech_elect_seg_detls.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_elect_pros_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(ProductDeals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_pros_dels.setText(ProductDeals[which]);
//                        ProductDealsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = ProductDeals.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(ProductDeals, is_checked,
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

                                    ed_ret_mech_elect_pros_dels.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_pros_dels = ed_ret_mech_elect_pros_dels.getText().toString().trim();

                                } else {

                                    ed_ret_mech_elect_pros_dels.setText(stringBuilder);
                                    ret_mech_elect_pros_dels = ed_ret_mech_elect_pros_dels.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_elect_pros_dels.setText("");
                                ret_mech_elect_pros_dels = ed_ret_mech_elect_pros_dels.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_elect_majr_branddetls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(MajorBrands, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_majr_branddetls.setText(MajorBrands[which]);
//                        MajorBrandsValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = MajorBrands.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(MajorBrands, is_checked,
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

                                    ed_ret_mech_elect_majr_branddetls.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_majr_branddetls = ed_ret_mech_elect_majr_branddetls.getText().toString().trim();

                                } else {

                                    ed_ret_mech_elect_majr_branddetls.setText(stringBuilder);
                                    if (ed_ret_mech_elect_majr_branddetls.getText().toString().trim().equals("OTHER MAKES")) {
                                        ll_ret_mech_elect_majr_branddetls_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_mech_elect_majr_branddetls_other.setVisibility(View.GONE);
                                    }
                                    ret_mech_elect_majr_branddetls = ed_ret_mech_elect_majr_branddetls.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_elect_majr_branddetls.setText("");
                                ret_mech_elect_majr_branddetls = ed_ret_mech_elect_majr_branddetls.getText().toString().trim();


                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_elect_dels_oebrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(DealsWithOE, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_dels_oebrands.setText(DealsWithOE[which]);
//                        DealsWithOEValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = DealsWithOE.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(DealsWithOE, is_checked,
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

                                    ed_ret_mech_elect_dels_oebrands.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_dels_oebrands = ed_ret_mech_elect_dels_oebrands.getText().toString().trim();

                                } else {

                                    ed_ret_mech_elect_dels_oebrands.setText(stringBuilder);
                                    ret_mech_elect_dels_oebrands = ed_ret_mech_elect_dels_oebrands.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_elect_dels_oebrands.setText("");
                                ret_mech_elect_dels_oebrands = ed_ret_mech_elect_dels_oebrands.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_elect_ltvs_pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
//                builder.setItems(LTVSPur, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ed_ltvs_pur.setText(LTVSPur[which]);
//                        LTVSPurValue = which + 1;
//
//                    }
//                });
//                builder.show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                int count = LTVSPur.length;
                boolean[] is_checked = new boolean[count];
                builder.setMultiChoiceItems(LTVSPur, is_checked,
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

                                    ed_ret_mech_elect_ltvs_pur.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_ltvs_pur = ed_ret_mech_elect_ltvs_pur.getText().toString().trim();

                                } else {

                                    ed_ret_mech_elect_ltvs_pur.setText(stringBuilder);
                                    if (ed_ret_mech_elect_ltvs_pur.getText().toString().trim().equals("OTHERS")) {
                                        ll_ret_mech_elect_ltvs_pur_other.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_ret_mech_elect_ltvs_pur_other.setVisibility(View.GONE);
                                    }
                                    ret_mech_elect_ltvs_pur = ed_ret_mech_elect_ltvs_pur.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_ret_mech_elect_ltvs_pur.setText("");
                                ret_mech_elect_ltvs_pur = ed_ret_mech_elect_ltvs_pur.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ed_ret_mech_elect_lat.setText(String.valueOf(latitude));
        ed_ret_mech_elect_long.setText(String.valueOf(longitude));
        //ret

        ret_hwold_shop = ed_hwold_shop.getText().toString();
        ret_seg_detls = ed_seg_detls.getText().toString();
        ret_pros_dels = ed_pros_dels.getText().toString();
        ret_majr_branddetls = ed_majr_branddetls.getText().toString();
        ret_ltvs_pur = ed_ltvs_pur.getText().toString();
        ret_part = ed_part.getText().toString();
        ret_monthly = ed_monthly.getText().toString();
        ret_market = ed_market.getText().toString();
        ret_dels_oebrands = ed_dels_oebrands.getText().toString();
        ret_lat = ed_ret_lat.getText().toString();
        ret_long = ed_ret_long.getText().toString();

        //mech

        mechhwold_shop = ed_mechhwold_shop.getText().toString();
        detls_in = ed_detls_in.getText().toString();
        spec_in = ed_spec_in.getText().toString();
        stock = ed_stock.getText().toString();
        mech_vehical = ed_mech_vehical.getText().toString();
        mech_market = ed_mech_market.getText().toString();
        mechpartner = ed_mechpartner.getText().toString();
        mech_monthly = ed_mech_monthly.getText().toString();
        ret_lat = ed_ret_lat.getText().toString();
        ret_long = ed_ret_long.getText().toString();

        //elect

        elect_hwold_shop = ed_elect_hwold_shop.getText().toString().trim();
        elect_seg_detls = ed_elect_seg_detls.getText().toString().trim();
        elect_pros_dels = ed_elect_pros_dels.getText().toString().trim();
        majr_elect_branddetls = ed_majr_elect_branddetls.getText().toString().trim();
        electpartner = ed_electpartner.getText().toString().trim();
        elect_stater = ed_elect_stater.getText().toString().trim();
        elect_alter = ed_elect_alter.getText().toString().trim();
        elect_wiper = ed_elect_wiper.getText().toString().trim();
        elect_noofstaff = ed_elect_noofstaff.getText().toString().trim();
        elect_market = ed_elect_market.getText().toString().trim();
        elect_lat = ed_elect_lat.getText().toString();
        elect_long = ed_elect_long.getText().toString();
        //ret_mech

        ret_mech_hwold_shop = ed_ret_mech_hwold_shop.getText().toString().trim();
        ret_mech_seg_detls = ed_ret_mech_seg_detls.getText().toString().trim();
        ret_mech_pros_dels = ed_ret_mech_pros_dels.getText().toString().trim();
        ret_mech_majr_branddetls = ed_ret_mech_majr_branddetls.getText().toString().trim();
        ret_mech_dels_oebrands = ed_ret_mech_dels_oebrands.getText().toString().trim();
        ret_mech_part = ed_ret_mech_part.getText().toString().trim();
        ret_mech_monthly = ed_ret_mech_monthly.getText().toString().trim();
        ret_mech_spec_in = ed_ret_mech_spec_in.getText().toString().trim();
        ret_mech_stock = ed_ret_mech_stock.getText().toString().trim();
        ret_mech_vehical = ed_ret_mech_vehical.getText().toString().trim();
        ret_mech_market = ed_ret_mech_market.getText().toString().trim();
        ret_mech_ltvs_pur = ed_ret_mech_ltvs_pur.getText().toString().trim();
        ret_mech_lat = ed_ret_mech_lat.getText().toString();
        ret_mech_long = ed_ret_mech_long.getText().toString();

        //mech_elect


        mech_elect_hwold_shop = ed_mech_elect_hwold_shop.getText().toString().trim();
        mech_elect_detls_in = ed_mech_elect_detls_in.getText().toString().trim();
        mech_elect_stock = ed_mech_elect_stock.getText().toString().trim();
        mech_elect_vehical = ed_mech_elect_vehical.getText().toString().trim();
        mech_elect_market = ed_mech_elect_market.getText().toString().trim();
        mech_elect_partner = ed_mech_elect_partner.getText().toString().trim();
        mech_elect_monthly = ed_mech_elect_monthly.getText().toString().trim();
        mech_elect_majr_branddetls = ed_mech_elect_majr_branddetls.getText().toString().trim();
        mech_elect_stater = ed_mech_elect_stater.getText().toString().trim();
        mech_elect_alter = ed_mech_elect_alter.getText().toString().trim();
        mech_elect_wiper = ed_mech_elect_wiper.getText().toString().trim();
        mech_elect_noofstaff = ed_mech_elect_noofstaff.getText().toString().trim();
        mech_elect_spec_in = ed_mech_elect_spec_in.getText().toString().trim();
        mech_elect_lat = ed_mech_elect_lat.getText().toString();
        mech_elect_long = ed_mech_elect_long.getText().toString();
        mech_elec_pros_dels = ed_mech_elec_pros_dels.getText().toString();
        //elect_ret

        ret_elect_hwold_shop = ed_ret_elect_hwold_shop.getText().toString().trim();
        ret_elect_seg_detls = ed_ret_elect_seg_detls.getText().toString().trim();
        ret_elect_pros_dels = ed_ret_elect_pros_dels.getText().toString().trim();
        ret_elect_majr_branddetls = ed_ret_elect_majr_branddetls.getText().toString().trim();
        ret_elect_dels_oebrands = ed_ret_elect_dels_oebrands.getText().toString().trim();
        ret_elect_ltvs_pur = ed_ret_elect_ltvs_pur.getText().toString().trim();
        ret_elect_part = ed_ret_elect_part.getText().toString().trim();
        ret_elect_monthly = ed_ret_elect_monthly.getText().toString().trim();
        ret_elect_stater = ed_ret_elect_stater.getText().toString().trim();
        ret_elect_alter = ed_ret_elect_alter.getText().toString().trim();
        ret_elect_wiper = ed_ret_elect_wiper.getText().toString().trim();
        ret_elect_noofstaff = ed_ret_elect_noofstaff.getText().toString().trim();
        ret_elect_market = ed_ret_elect_market.getText().toString().trim();

        ret_elect_lat = ed_ret_elect_lat.getText().toString();
        ret_elect_long = ed_ret_elect_long.getText().toString();
        //ret_mech_elect

        ret_mech_elect_hwold_shop = ed_ret_mech_elect_hwold_shop.getText().toString().trim();
        ret_mech_elect_seg_detls = ed_ret_mech_elect_seg_detls.getText().toString().trim();
        ret_mech_elect_pros_dels = ed_ret_mech_elect_pros_dels.getText().toString().trim();
        ret_mech_elect_majr_branddetls = ed_ret_mech_elect_majr_branddetls.getText().toString().trim();
        ret_mech_elect_dels_oebrands = ed_ret_mech_elect_dels_oebrands.getText().toString().trim();
        ret_mech_elect_ltvs_pur = ed_ret_mech_elect_ltvs_pur.getText().toString().trim();
        ret_mech_elect_part = ed_ret_mech_elect_part.getText().toString().trim();
        ret_mech_elect_monthly = ed_ret_mech_elect_monthly.getText().toString().trim();
        ret_mech_elect_stater = ed_ret_mech_elect_stater.getText().toString().trim();
        ret_mech_elect_alter = ed_ret_mech_elect_alter.getText().toString().trim();
        ret_mech_elect_wiper = ed_ret_mech_elect_wiper.getText().toString().trim();
        ret_mech_elect_noofstaff = ed_ret_mech_elect_noofstaff.getText().toString().trim();
        ret_mech_elect_spec_in = ed_ret_mech_elect_spec_in.getText().toString().trim();
        ret_mech_elect_stock = ed_ret_mech_elect_stock.getText().toString().trim();
        ret_mech_elect_vehical = ed_ret_mech_elect_vehical.getText().toString().trim();
        ret_mech_elect_market = ed_ret_mech_elect_market.getText().toString().trim();
        ret_mech_elect_lat = ed_ret_mech_elect_lat.getText().toString();
        ret_mech_elect_long = ed_ret_mech_elect_long.getText().toString();
    }

    public void Home(View view) {
        startActivity(new Intent(this, Home.class));
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

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registration.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(Registration.this);

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
                        LAT = String.valueOf(place.getLatLng().latitude);
                        LONG = String.valueOf(place.getLatLng().longitude);
                        Toast.makeText(this, CaptureLocation + LAT + LONG, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
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

    private void checkGps() {
        try {
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                LAT = String.valueOf(latitude);
                LONG = String.valueOf(longitude);

          /*  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
                    Toast.LENGTH_LONG).show();*/
//            if (!String.valueOf(latitude).equals("0.0") || !String.valueOf(longitude).equals("0.0")) {
//                userlogin();
//            }

            } else {
                Toast.makeText(this, "Please Enable Gps Location", Toast.LENGTH_SHORT).show();
                gps.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Save(View view) {
//        final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
//                R.style.Progress);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();

//        bfo = new BitmapFactory.Options();
//        bfo.inSampleSize = 2;
//
//        bao = new ByteArrayOutputStream();
//
//        drawable = (BitmapDrawable) iv_image.getDrawable();
//        bitmap = drawable.getBitmap();
//        drawable2 = (BitmapDrawable) iv_image2.getDrawable();
//        bitmap2 = drawable2.getBitmap();
//        drawable3 = (BitmapDrawable) iv_card.getDrawable();
//        bitmap3 = drawable3.getBitmap();
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//        byte[] ba = bao.toByteArray();
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//        byte[] ba2 = bao.toByteArray();
//        bitmap3.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//        byte[] ba3 = bao.toByteArray();
//
//        image = Base64.encodeToString(ba, Base64.DEFAULT);
//        image2 = Base64.encodeToString(ba2, Base64.DEFAULT);
//        visitingcard = Base64.encodeToString(ba3, Base64.DEFAULT);
        try {
            Contry = ed_reg_National.getText().toString().trim();
            type = ed_type.getText().toString().trim();

            //pers
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

            //ret
            ret_majr_branddetls_other = ed_ret_majr_branddetls_other.getText().toString();
            ret_ltvs_pur_other = ed_ret_ltvs_pur_other.getText().toString();
            ret_partname = ed_ret_partname.getText().toString();
            ret_lat = ed_ret_lat.getText().toString();
            ret_long = ed_ret_long.getText().toString();
            //mech
            mech_partname = ed_mech_partname.getText().toString();
            mech_lat = ed_ret_lat.getText().toString();
            mech_long = ed_ret_long.getText().toString();
            spec_inother = ed_mech_seg_detls_other.getText().toString();

            //elect
            elect_majr_branddetls_other = ed_elect_majr_branddetls_other.getText().toString().trim();
            elect_partname = ed_elect_partname.getText().toString().trim();
            elect_lat = ed_elect_lat.getText().toString();
            elect_long = ed_elect_long.getText().toString();
            elect_lat = ed_elect_lat.getText().toString();
            elect_long = ed_elect_long.getText().toString();
            //ret_mech
            ret_mech_majr_branddetls_other = ed_ret_mech_majr_branddetls_other.getText().toString().trim();
            ret_mech_ltvs_pur_other = ed_ret_mech_ltvs_pur_other.getText().toString().trim();
            ret_mech_partname = ed_ret_mech_partname.getText().toString().trim();
            ret_mech_lat = ed_ret_mech_lat.getText().toString();
            ret_mech_long = ed_ret_mech_long.getText().toString();
            ret_mech_spec_inother = ed_ret_mech_seg_detls_other.getText().toString();

            //mech_elect
            mech_elect_majr_branddetls_other = ed_mech_elect_majr_branddetls_other.getText().toString().trim();
            mech_elect_partname = ed_mech_elect_partname.getText().toString().trim();
            mech_elect_lat = ed_mech_elect_lat.getText().toString();
            mech_elect_long = ed_mech_elect_long.getText().toString();
            mech_elect_spec_inother = ed_mech_elect_seg_detls_other.getText().toString();
            //elect_ret
            ret_majr_elect_branddetls_other = ed_ret_majr_elect_branddetls_other.getText().toString().trim();
            ret_elect_ltvs_pur_other = ed_ret_elect_ltvs_pur_other.getText().toString().trim();
            ret_elect_partname = ed_ret_elect_partname.getText().toString().trim();
            ret_elect_lat = ed_ret_elect_lat.getText().toString();
            ret_elect_long = ed_ret_elect_long.getText().toString();
            //ret_mech_elect
            ret_mech_majr_elect_branddetls_other = ed_ret_mech_majr_elect_branddetls_other.getText().toString().trim();
            ret_mech_elect_ltvs_pur_other = ed_ret_mech_elect_ltvs_pur_other.getText().toString().trim();
            ret_mech_elect_partname = ed_ret_mech_elect_partname.getText().toString().trim();
            ret_mech_elect_lat = ed_ret_mech_elect_lat.getText().toString();
            ret_mech_elect_long = ed_ret_mech_elect_long.getText().toString();
            ret_mech_elect_spec_inother = ed_ret_mech_elect_seg_detls_other.getText().toString();

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
            if (Shopname.equals("")) {
                Toast.makeText(this, "Please Enter Shop name", Toast.LENGTH_SHORT).show();
                ed_reg_Shopname.setError("Please Enter Shop name");
            } else if (Ownername.equals("")) {
                ed_reg_Ownername.setError("Please Enter Owner name");
                ed_reg_Ownername.setFocusable(true);
                Toast.makeText(this, "Please Enter Owner name", Toast.LENGTH_SHORT).show();

            } else if (DoorNo.equals("")) {
                Toast.makeText(this, "Please Enter DoorNo", Toast.LENGTH_SHORT).show();
                ed_reg_Door.setError("Please Enter DoorNo");
            } else if (Street.equals("")) {
                ed_reg_Street.setError("Please Enter Street");
                Toast.makeText(this, "Please Enter Street", Toast.LENGTH_SHORT).show();

            } else if (Area.equals("")) {
                ed_reg_Area.setError("Please Enter Area");
                Toast.makeText(this, "Please Enter Area", Toast.LENGTH_SHORT).show();

            } else if (City.equals("")) {
                ed_reg_City.setError("Please Enter City");
                Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();

            } else if (State.equals("")) {
                Toast.makeText(this, "Please Enter Citypredictive", Toast.LENGTH_SHORT).show();
//            ed_reg_State.setError("Please Enter Citypredictive");
            } else if (Pincode.equals("") || Pincode.length() != 6) {
                ed_reg_Pincode.setError("Please Enter Pincode");
                Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();

            } else if (Mobile1.equals("") || Mobile1.length() != 10) {
                ed_reg_Mobile1.setError("Please Enter Mobile Number");
                Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();

            } else if (Email.equals("") || !Email.matches(emailPattern)) {
                ed_reg_Email.setError("Please Enter Email");
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

            } else if (GstNo.equals("")) {
                ed_reg_Gst.setError("Please Enter GSt No.");
                Toast.makeText(this, "Please Enter GST No.", Toast.LENGTH_SHORT).show();

            } else if (type.equals("")) {
//            ed_type.setError("Please Enter type");
                Toast.makeText(this, "Please Select User_Type", Toast.LENGTH_SHORT).show();

            } else if (type.equals("RETAILER")) {

                if (ret_hwold_shop.equals("")) {
//                ed_hwold_shop.setError("Please Enter ret_hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_part.equals("")) {
//                ed_part.setError("Please Enter ret_part");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }

//            else if (ret_part.equals("PARTNER")) {
//                if (ret_partname.equals("")) {
//                    ed_ret_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_market.equals("")) {
//                ed_market.setError("Please Enter ret_market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_seg_detls.equals("")) {
//                ed_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (ret_pros_dels.equals("")) {
//                ed_pros_dels.setError("Please Enter ret_pros_dels");
                    Toast.makeText(this, "Please Select Product Deals", Toast.LENGTH_SHORT).show();

                } else if (ret_monthly.equals("")) {
//                ed_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();

                } else if (ret_majr_branddetls.equals("")) {
//                ed_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With Electrical", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_majr_branddetls.equals("OTHER MAKES")) {
//                if (ret_majr_branddetls_other.equals("")) {
//                    ed_ret_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_dels_oebrands.equals("")) {
//                ed_dels_oebrands.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Deals With Oe Brands", Toast.LENGTH_SHORT).show();

                } else if (ret_ltvs_pur.equals("")) {
//                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select LTVS Purchase Deals With", Toast.LENGTH_SHORT).show();

                }
//                else if (ret_lat.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Latitude", Toast.LENGTH_SHORT).show();
//
//                } else if (ret_long.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Longitude", Toast.LENGTH_SHORT).show();
//
//                }
//            else if (ret_ltvs_pur.equals("OTHERS")) {
//                if (ret_ltvs_pur_other.equals("")) {
//                    ed_ret_ltvs_pur_other.setError("Please Enter ret_ltvs_pur_other");
//                }
//            }
                else {
                    if (net.CheckInternet()) {

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
                        editor.putString("KEY_Contry", Contry);
                        editor.putString("KEY_Pincode", Pincode);
                        editor.putString("KEY_Mobile1", Mobile1);
                        editor.putString("KEY_Mobile2", Mobile2);
                        editor.putString("KEY_Email", Email);
                        editor.putString("KEY_GstNo", GstNo);
                        editor.putString("KEY_type", type);

                        editor.putString("KEY_ret_hwold_shop", ret_hwold_shop);
                        editor.putString("KEY_ret_part", ret_part);
                        editor.putString("KEY_ret_partname", ret_partname);
                        editor.putString("KEY_ret_market", ret_market);
                        editor.putString("KEY_ret_seg_detls", ret_seg_detls);
                        editor.putString("KEY_ret_pros_dels", ret_pros_dels);
                        editor.putString("KEY_ret_monthly", ret_monthly);
                        editor.putString("KEY_ret_majr_branddetls", ret_majr_branddetls);
                        editor.putString("KEY_ret_majr_branddetls_other", ret_majr_branddetls_other);
                        editor.putString("KEY_ret_dels_oebrands", ret_dels_oebrands);
                        editor.putString("KEY_ret_ltvs_pur", ret_ltvs_pur);
                        editor.putString("KEY_ret_ltvs_pur_othe", ret_ltvs_pur_other);

                        editor.apply();

                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
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
                        Latitude.setText(ret_lat);
                        Logitude.setText(ret_long);

                        final ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);

                        if (!ret_part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!ret_majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ret_ltvs_pur.equals("OTHERS")) {
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
                            Glide.with(Registration.this)
                                    .load(imageAsBytes)
                                    .into(con_iv);
                        } else {
                            con_iv.setImageResource(R.drawable.soft);
                        }
                        if (!image2.equals("")) {
                            byte[] imageAsBytes = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                            Glide.with(Registration.this)
                                    .load(imageAsBytes)
                                    .into(con_iv2);
                        } else {
                            con_iv2.setImageResource(R.drawable.soft);
                        }
                        if (!visitingcard.equals("")) {
                            byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                            Glide.with(Registration.this)
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
                        country.setText(Contry);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);

                        hw_old.setText(ret_hwold_shop);
                        Organisation.setText(ret_part);
                        partnername.setText(ret_partname);
                        market.setText(ret_market);
                        segments.setText(ret_seg_detls);
                        productdeals.setText(ret_pros_dels);
                        monthly_turn.setText(ret_monthly);
                        MajorBrand.setText(ret_majr_branddetls);
                        majorbrandother.setText(ret_majr_branddetls_other);
                        deals_oe.setText(ret_dels_oebrands);
                        ltvs_purchase.setText(ret_ltvs_pur);
                        ltvs_pur_other.setText(ret_ltvs_pur_other);


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
                                Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                  /*  final AlertDialog dialog = new AlertDialog(Registration.this);
//                    dialog.setTitle("Confirmation");

                    dialog.setContentView(R.layout.activity_confirmation_form);
                    EditText on_name=(EditText) dialog.findViewById(R.id.con_ownname);
                    on_name.setText(Ownername);
                    Button Register = (Button) dialog.findViewById(R.id.con_register);
                    Button Cancel = (Button) dialog.findViewById(R.id.con_cancel);
                    Register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Registration.this, "Saved Successfully into Database", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();*/


                    } else {
                        DBCreate();
                        ret_insert();
//                    Toast.makeText(this, "Saved Successfully in Locally", Toast.LENGTH_SHORT).show();


                    }

//                ed_hwold_shop.getText().clear();
//                ed_part.getText().clear();
//                ed_ret_partname.getText().clear();
                }
            } else if (type.equals("MECHANIC")) {
                if (mechhwold_shop.equals("")) {
//                ed_mechhwold_shop.setError("Please Enter hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (mechpartner.equals("")) {
//                ed_mechpartner.setError("Please Enter partnername");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (mechpartner.equals("PARTNER")) {
//                if (mech_partname.equals("")) {
//                    ed_mech_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (mech_market.equals("")) {
//                ed_mech_market.setError("Please Enter market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (detls_in.equals("")) {
                    Toast.makeText(this, "Please Select Segment ", Toast.LENGTH_SHORT).show();
//                ed_detls_in.setError("Please Enter ret_seg_detls");
                } else if (spec_in.equals("")) {
//                ed_spec_in.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Specialist", Toast.LENGTH_SHORT).show();

                } else if (mech_monthly.equals("")) {
//                ed_mech_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();

                } else if (stock.equals("")) {
//                ed_stock.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Maintaining Stock", Toast.LENGTH_SHORT).show();

                } else if (mech_vehical.equals("")) {
//                ed_mech_vehical.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Vehicle Attend in a Month   ", Toast.LENGTH_SHORT).show();

                }
//                else if (mech_lat.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Latitude", Toast.LENGTH_SHORT).show();
//
//                } else if (mech_long.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Longitude", Toast.LENGTH_SHORT).show();
//
//                }
                else {

                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
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
                        Latitude.setText(mech_lat);
                        Logitude.setText(mech_long);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);

                        if (!mechpartner.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }

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
                        Glide.with(Registration.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
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
                        country.setText(Contry);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);
                        hw_old.setText(mechhwold_shop);
                        Organisation.setText(mechpartner);
                        partnername.setText(mech_partname);
                        market.setText(mech_market);
                        segments.setText(detls_in);
                        monthly_turn.setText(mech_monthly);
                        specin.setText(spec_in);
                        edt_stock.setText(stock);
                        vechicle.setText(mech_vehical);
                        specin_other.setText(spec_inother);

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
                                Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        mech_insert();
                    }

                }
            } else if (type.equals("ELECTRICIAN")) {
                if (elect_hwold_shop.equals("")) {
//                ed_elect_hwold_shop.setError("Please Enter hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (electpartner.equals("")) {
//                ed_electpartner.setError("Please Enter partnername");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (electpartner.equals("PARTENER")) {
//                if (elect_partname.equals("")) {
//                    ed_elect_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (elect_market.equals("")) {
//                ed_elect_market.setError("Please Enter market");
                    Toast.makeText(this, "Please Select Market ", Toast.LENGTH_SHORT).show();

                } else if (elect_seg_detls.equals("")) {
//                ed_elect_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (elect_pros_dels.equals("")) {
//                ed_elect_pros_dels.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Product Deals With", Toast.LENGTH_SHORT).show();

                } else if (majr_elect_branddetls.equals("")) {
//                ed_majr_elect_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (majr_elect_branddetls.equals("OTHER MAKES")) {
//                if (elect_majr_branddetls_other.equals("")) {
//                    ed_elect_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (elect_stater.equals("")) {
//                ed_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Started Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (elect_alter.equals("")) {
//                ed_elect_alter.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Alternator Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (elect_wiper.equals("")) {
//                ed_elect_wiper.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Wiper Motor Serviced in a Month", Toast.LENGTH_SHORT).show();


                } else if (elect_noofstaff.equals("")) {
//                ed_elect_noofstaff.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select No Of Staff", Toast.LENGTH_SHORT).show();


                }
//                else if (elect_lat.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Latitude", Toast.LENGTH_SHORT).show();
//
//                } else if (elect_long.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Longitude", Toast.LENGTH_SHORT).show();

//                }
                else {
                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
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

                        Latitude.setText(elect_lat);
                        Logitude.setText(elect_long);

                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);

                        if (!electpartner.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!majr_elect_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }

                        ll_monthly.setVisibility(View.GONE);
                        ll_dealsoe.setVisibility(View.GONE);
                        ll_ltvspur.setVisibility(View.GONE);
                        ll_ltvspurother.setVisibility(View.GONE);
                        ll_special.setVisibility(View.GONE);
                        ll_specialother.setVisibility(View.GONE);
                        ll_maintaining.setVisibility(View.GONE);
                        ll_vehicle.setVisibility(View.GONE);

                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
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
                        country.setText(Contry);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);

                        hw_old.setText(elect_hwold_shop);
                        Organisation.setText(electpartner);
                        partnername.setText(elect_partname);
                        market.setText(elect_market);
                        segments.setText(elect_seg_detls);
                        productdeals.setText(elect_pros_dels);
                        MajorBrand.setText(majr_elect_branddetls);
                        majorbrandother.setText(elect_majr_branddetls_other);
                        stater.setText(elect_stater);
                        alter.setText(elect_alter);
                        wiper.setText(elect_wiper);
                        noofstaff.setText(elect_noofstaff);


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
                                Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        elect_insert();
                    }


                }
            } else if (type.equals("RETAILER,MECHANIC")) {
                if (ret_mech_hwold_shop.equals("")) {
//                ed_ret_mech_hwold_shop.setError("Please Enter ret_hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_part.equals("")) {
//                ed_ret_mech_part.setError("Please Enter ret_part");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_part.equals("PARTNER")) {
//                if (ret_mech_partname.equals("")) {
//                    ed_ret_mech_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_market.equals("")) {
//                ed_ret_mech_market.setError("Please Enter ret_market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_seg_detls.equals("")) {
//                ed_ret_mech_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segments", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_pros_dels.equals("")) {
//                ed_ret_mech_pros_dels.setError("Please Enter ret_pros_dels");
                    Toast.makeText(this, "Please Select Product Deals With", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_monthly.equals("")) {
//                ed_ret_mech_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly TurnOver", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_majr_branddetls.equals("")) {
//                ed_ret_mech_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_majr_branddetls.equals("OTHER MAKES")) {
//                if (ret_mech_majr_branddetls_other.equals("")) {
//                    ed_ret_mech_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_mech_dels_oebrands.equals("")) {
//                ed_ret_mech_dels_oebrands.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Deals With OE Brands", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_ltvs_pur.equals("")) {
//                ed_ret_mech_ltvs_pur.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select LTVS purchase Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_ltvs_pur.equals("OTHERS")) {
//                if (ret_mech_ltvs_pur_other.equals("")) {
//                    ed_ret_mech_ltvs_pur_other.setError("Please Enter ret_ltvs_pur_other");
//                }
//            }
                else if (ret_mech_spec_in.equals("")) {
//                ed_ret_mech_spec_in.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Specialist In", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_stock.equals("")) {
//                ed_ret_mech_stock.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Maintaining Stock", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_vehical.equals("")) {
//                ed_ret_mech_vehical.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Vehicle Attend in a month", Toast.LENGTH_SHORT).show();

                }
//                else if (ret_mech_lat.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Latitude", Toast.LENGTH_SHORT).show();
//
//                } else if (ret_mech_long.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Longitude", Toast.LENGTH_SHORT).show();
//
//                }
                else {
                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
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
                        Latitude.setText(ret_mech_lat);
                        Logitude.setText(ret_mech_long);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        EditText specin_other = (EditText) dialogView.findViewById(R.id.con_spec_in_other);

                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);

                        if (!ret_mech_part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!ret_mech_majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ret_mech_ltvs_pur.equals("OTHERS")) {
                            ll_ltvspurother.setVisibility(View.GONE);
                        }

                        ll_stater.setVisibility(View.GONE);
                        ll_alter.setVisibility(View.GONE);
                        ll_wiper.setVisibility(View.GONE);
                        ll_staff.setVisibility(View.GONE);

                        specin_other.setText(spec_inother);
                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
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
                        country.setText(Contry);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);

                        hw_old.setText(ret_mech_hwold_shop);
                        Organisation.setText(ret_mech_part);
                        partnername.setText(ret_mech_partname);
                        market.setText(ret_mech_market);
                        segments.setText(ret_mech_seg_detls);
                        productdeals.setText(ret_mech_pros_dels);
                        monthly_turn.setText(ret_mech_monthly);
                        MajorBrand.setText(ret_mech_majr_branddetls);
                        majorbrandother.setText(ret_mech_majr_branddetls_other);
                        deals_oe.setText(ret_mech_dels_oebrands);
                        ltvs_purchase.setText(ret_mech_ltvs_pur);
                        ltvs_pur_other.setText(ret_mech_ltvs_pur_other);
                        specin.setText(ret_mech_spec_in);
                        ed_stock.setText(ret_mech_stock);
                        vechicle.setText(ret_mech_vehical);

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
                                Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        ret__mech_insert();
                    }

                }

            } else if (type.equals("MECHANIC,ELECTRICIAN")) {
                if (mech_elect_hwold_shop.equals("")) {
//                ed_mech_elect_hwold_shop.setError("Please Enter hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_partner.equals("")) {
//                ed_mech_elect_partner.setError("Please Enter partnername");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (mech_elect_partner.equals("PARTNER")) {
//                if (mech_elect_partname.equals("")) {
//                    ed_mech_elect_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (mech_elect_market.equals("")) {
//                ed_mech_elect_market.setError("Please Enter market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_detls_in.equals("")) {
//                ed_mech_elect_detls_in.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_spec_in.equals("")) {
//                ed_mech_elect_spec_in.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Specialist ", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_monthly.equals("")) {
//                ed_mech_elect_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();


                } else if (mech_elect_stock.equals("")) {
//                ed_mech_elect_stock.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Maintaining Stock", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_vehical.equals("")) {
//                ed_mech_elect_vehical.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Vehicle Attend in a month", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_majr_branddetls.equals("")) {
//                ed_mech_elect_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (mech_elect_majr_branddetls.equals("OTHER MAKES")) {
//                if (mech_elect_majr_branddetls_other.equals("")) {
//                    ed_mech_elect_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (mech_elec_pros_dels.equals("")) {
//                ed_mech_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Product Dealt With", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_stater.equals("")) {
//                ed_mech_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Started Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_alter.equals("")) {
//                ed_mech_elect_alter.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Alternator Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (mech_elect_wiper.equals("")) {
//                ed_mech_elect_wiper.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Wiper Motor Serviced in a Month", Toast.LENGTH_SHORT).show();


                } else if (mech_elect_noofstaff.equals("")) {
//                ed_mech_elect_noofstaff.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select No Of Staff", Toast.LENGTH_SHORT).show();

                }
//                else if (mech_elect_lat.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Latitude", Toast.LENGTH_SHORT).show();
//
//                } else if (mech_elect_long.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Longitude", Toast.LENGTH_SHORT).show();
//
//                }
                else {
                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
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
                        specin_other.setText(spec_inother);
                        Latitude.setText(mech_elect_lat);
                        Logitude.setText(mech_elect_long);
                        final EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);

                        LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.partner);

                        if (!mech_elect_partner.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!mech_elect_majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }


                        ll_dealsoe.setVisibility(View.GONE);
                        ll_ltvspur.setVisibility(View.GONE);
                        ll_ltvspurother.setVisibility(View.GONE);

                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
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
                        country.setText(Contry);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);

                        hw_old.setText(mech_elect_hwold_shop);
                        Organisation.setText(mech_elect_partner);
                        partnername.setText(mech_elect_partname);
                        market.setText(mech_elect_market);
                        segments.setText(mech_elect_detls_in);
                        monthly_turn.setText(mech_monthly);
                        specin.setText(mech_elect_spec_in);
                        ed_stock.setText(mech_elect_stock);
                        vechicle.setText(mech_elect_vehical);
                        monthly_turn.setText(mech_elect_monthly);

                        MajorBrand.setText(mech_elect_majr_branddetls);
                        majorbrandother.setText(mech_elect_majr_branddetls_other);
                        productdeals.setText(mech_elec_pros_dels);
                        stater.setText(mech_elect_stater);
                        alter.setText(mech_elect_alter);
                        wiper.setText(mech_elect_wiper);
                        noofstaff.setText(mech_elect_noofstaff);

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
                                Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        mech_elect_insert();
                    }


                }
            } else if (type.equals("RETAILER,ELECTRICIAN")) {
                if (ret_elect_hwold_shop.equals("")) {
//                ed_ret_elect_hwold_shop.setError("Please Enter ret_hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_part.equals("")) {
//                ed_ret_elect_part.setError("Please Enter ret_part");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_elect_part.equals("PARTNER")) {
//                if (ret_elect_partname.equals("")) {
//                    ed_ret_elect_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_elect_market.equals("")) {
//                ed_ret_elect_market.setError("Please Enter ret_market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_seg_detls.equals("")) {
//                ed_ret_elect_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_pros_dels.equals("")) {
//                ed_ret_elect_pros_dels.setError("Please Enter ret_pros_dels");
                    Toast.makeText(this, "Please Select Product Deals With", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_monthly.equals("")) {
//                ed_ret_elect_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_majr_branddetls.equals("")) {
//                ed_ret_elect_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_elect_majr_branddetls.equals("OTHER MAKES")) {
//                if (ret_majr_elect_branddetls_other.equals("")) {
//                    ed_ret_majr_elect_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_elect_dels_oebrands.equals("")) {
//                ed_ret_elect_dels_oebrands.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Deals With Oe Brands", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_ltvs_pur.equals("")) {
//                ed_ret_elect_ltvs_pur.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select LTVS Purchase", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_elect_ltvs_pur.equals("OTHERS")) {
//                if (ret_elect_ltvs_pur_other.equals("")) {
//                    ed_ret_elect_ltvs_pur_other.setError("Please Enter ret_ltvs_pur_other");
//                }
//            }
                else if (ret_elect_stater.equals("")) {
//                ed_ret_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Stater Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_alter.equals("")) {
//                ed_ret_elect_alter.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Alternator Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_elect_wiper.equals("")) {
//                ed_ret_elect_wiper.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Wiper Motor Serviced in a Month", Toast.LENGTH_SHORT).show();


                } else if (ret_elect_noofstaff.equals("")) {
//                ed_ret_elect_noofstaff.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select No Of Staff", Toast.LENGTH_SHORT).show();


                }
//                else if (ret_elect_lat.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Latitude", Toast.LENGTH_SHORT).show();
//
//                } else if (ret_elect_long.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please enter Longitude", Toast.LENGTH_SHORT).show();
//
//                }
                else {
                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
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

                        if (!ret_elect_part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!ret_elect_majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ret_elect_ltvs_pur.equals("OTHERS")) {
                            ll_ltvspurother.setVisibility(View.GONE);
                        }
                        ll_special.setVisibility(View.GONE);
                        ll_specialother.setVisibility(View.GONE);
                        ll_vehicle.setVisibility(View.GONE);

                        Latitude.setText(ret_elect_lat);
                        Logitude.setText(ret_elect_long);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
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
                        country.setText(Contry);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);

                        hw_old.setText(ret_elect_hwold_shop);
                        Organisation.setText(ret_elect_part);
                        partnername.setText(ret_elect_partname);
                        market.setText(ret_elect_market);
                        segments.setText(ret_elect_seg_detls);
                        productdeals.setText(ret_elect_pros_dels);
                        monthly_turn.setText(ret_elect_monthly);
                        MajorBrand.setText(ret_elect_majr_branddetls);
                        majorbrandother.setText(ret_majr_elect_branddetls_other);
                        deals_oe.setText(ret_elect_dels_oebrands);
                        ltvs_purchase.setText(ret_elect_ltvs_pur);
                        ltvs_pur_other.setText(ret_elect_ltvs_pur_other);

                        stater.setText(ret_elect_stater);
                        alter.setText(ret_elect_alter);
                        wiper.setText(ret_elect_wiper);
                        noofstaff.setText(ret_elect_noofstaff);
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
                                Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        ret_elect_insert();
                    }


                }
            } else if (type.equals("RETAILER,MECHANIC,ELECTRICIAN")) {
                if (ret_mech_elect_hwold_shop.equals("")) {
//                ed_ret_mech_elect_hwold_shop.setError("Please Enter ret_hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_part.equals("")) {
//                ed_ret_mech_elect_part.setError("Please Enter ret_part");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_elect_part.equals("PARTNER")) {
//                if (ret_mech_elect_partname.equals("")) {
//                    ed_ret_mech_elect_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_elect_market.equals("")) {
//                ed_ret_mech_elect_market.setError("Please Enter ret_market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_seg_detls.equals("")) {
//                ed_ret_mech_elect_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment Deals", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_pros_dels.equals("")) {
//                ed_ret_mech_elect_pros_dels.setError("Please Enter ret_pros_dels");
                    Toast.makeText(this, "Please Select Product Dealt with", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_monthly.equals("")) {
//                ed_ret_mech_elect_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn over", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_majr_branddetls.equals("")) {
//                ed_ret_mech_elect_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Dealt With", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_elect_majr_branddetls.equals("OTHER MAKES")) {
//                if (ret_mech_majr_elect_branddetls_other.equals("")) {
//                    ed_ret_mech_majr_elect_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_mech_elect_dels_oebrands.equals("")) {
//                ed_ret_mech_elect_dels_oebrands.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Deals With OE brands", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_ltvs_pur.equals("")) {
//                ed_ret_mech_elect_ltvs_pur.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Lucas TVS Purchase", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_elect_ltvs_pur.equals("OTHERS")) {
//                if (ret_mech_elect_ltvs_pur_other.equals("")) {
//                    ed_ret_mech_elect_ltvs_pur_other.setError("Please Enter ret_ltvs_pur_other");
//                }
//            }
                else if (ret_mech_elect_spec_in.equals("")) {
//                ed_ret_mech_elect_spec_in.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Specialist", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_stock.equals("")) {
//                ed_ret_mech_elect_stock.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Maintaining Stock", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_vehical.equals("")) {
//                ed_ret_mech_elect_vehical.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Vehicle Attend in a month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_stater.equals("")) {
//                ed_ret_mech_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Starter Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_alter.equals("")) {
//                ed_ret_mech_elect_alter.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Alternator Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_wiper.equals("")) {
//                ed_ret_mech_elect_wiper.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Wiper Motor Serviced in a Month", Toast.LENGTH_SHORT).show();


                } else if (ret_mech_elect_noofstaff.equals("")) {
//                ed_ret_mech_elect_noofstaff.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select No Of Staff", Toast.LENGTH_SHORT).show();


                }
//                else if (ret_mech_elect_lat.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please Wait Until fill Latitude", Toast.LENGTH_SHORT).show();
//
//                } else if (ret_mech_elect_long.equals("")) {
////                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
//                    Toast.makeText(this, "Please Wait Until fill Longitude", Toast.LENGTH_SHORT).show();
//
//                }
                else {
                    if (net.CheckInternet()) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
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

                        if (!ret_mech_elect_part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        }
                        if (!ret_mech_elect_majr_branddetls.equals("OTHER MAKES")) {
                            ll_majorother.setVisibility(View.GONE);
                        }
                        if (!ret_mech_elect_ltvs_pur.equals("OTHERS")) {
                            ll_ltvspurother.setVisibility(View.GONE);
                        }
                        specin_other.setText(spec_inother);
                        Latitude.setText(ret_mech_elect_lat);
                        Logitude.setText(ret_mech_elect_long);
                        final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                        final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
                        byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes)
                                .into(con_iv);
                        byte[] imageAsBytes2 = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
                                .load(imageAsBytes2)
                                .into(con_iv2);
                        byte[] imageAsBytes3 = Base64.decode(visitingcard.getBytes(), Base64.DEFAULT);
                        Glide.with(Registration.this)
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
                        country.setText(Contry);
                        pincode.setText(Pincode);
                        Mob1.setText(Mobile1);
                        Mob2.setText(Mobile2);
                        ed_Email.setText(Email);
                        Gst.setText(GstNo);
                        ed_typ.setText(type);

                        hw_old.setText(ret_mech_elect_hwold_shop);
                        Organisation.setText(ret_mech_elect_part);
                        partnername.setText(ret_mech_elect_partname);
                        market.setText(ret_mech_elect_market);
                        segments.setText(ret_mech_elect_seg_detls);
                        productdeals.setText(ret_mech_elect_pros_dels);
                        monthly_turn.setText(ret_mech_elect_monthly);
                        MajorBrand.setText(ret_mech_elect_majr_branddetls);
                        majorbrandother.setText(ret_mech_majr_elect_branddetls_other);
                        deals_oe.setText(ret_mech_elect_dels_oebrands);
                        ltvs_purchase.setText(ret_mech_elect_ltvs_pur);
                        ltvs_pur_other.setText(ret_mech_elect_ltvs_pur_other);

                        specin.setText(ret_mech_elect_spec_in);
                        stock.setText(ret_mech_elect_stock);
                        vechicle.setText(ret_mech_elect_vehical);

                        stater.setText(ret_mech_elect_stater);
                        alter.setText(ret_mech_elect_alter);
                        wiper.setText(ret_mech_elect_wiper);
                        noofstaff.setText(ret_mech_elect_noofstaff);

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
                                Toast.makeText(Registration.this, "Canceled Register", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();


                    } else {
                        DBCreate();
                        ret_mech_elect_insert();
                    }

                }
            } else {
                Toast.makeText(Registration.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();


            // Calling JSON

            Call<Register> call = service.register_ret(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City, State, National,
                    Pincode, Mobile1, Mobile2, Email, GstNo, ret_hwold_shop, ret_part, ret_partname, ret_market, ret_seg_detls, ret_pros_dels,
                    ret_monthly, ret_majr_branddetls, ret_majr_branddetls_other, ret_dels_oebrands, ret_ltvs_pur, ret_ltvs_pur_other, LAT, LONG, image, image2, visitingcard, CaptureLocation,pros_dels_other, dels_oebrands_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);
                        ed_reg_Ownername.setText("");
                        ed_reg_Shopname.setText("");
                        ed_reg_Door.setText("");
                        ed_reg_Street.setText("");
                        ed_reg_Area.setText("");
                        ed_reg_Landmark.setText("");
                        ed_reg_City.setText("");
                        ed_reg_State.setText("");
                        ed_reg_National.setText("");
                        ed_reg_Pincode.setText("");
                        ed_reg_Mobile1.setText("");
                        ed_reg_Mobile2.setText("");
                        ed_reg_Email.setText("");
                        ed_reg_Gst.setText("");
                        ed_type.setText("");

                        ed_hwold_shop.setText("");
                        ed_seg_detls.setText("");
                        ed_pros_dels.setText("");
                        ed_majr_branddetls.setText("");
                        ed_dels_oebrands.setText("");
                        ed_part.setText("");
                        ed_monthly.setText("");
                        ed_market.setText("");
                        ed_ret_majr_branddetls_other.setText("");
                        ed_ltvs_pur.setText("");
                        ed_ret_ltvs_pur_other.setText("");
                        ed_ret_partname.setText("");
                        ed_ret_lat.setText("");
                        ed_ret_long.setText("");
                        Toast.makeText(Registration.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Registration.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_mech(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City, State, National,
                    Pincode, Mobile1, Mobile2, Email, GstNo, mechhwold_shop,
                    mechpartner, mech_partname, mech_market, detls_in, mech_monthly, spec_in, stock, mech_vehical, LAT, LONG, image, image2, visitingcard, spec_inother, CaptureLocation);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);
                        ed_reg_Ownername.setText("");
                        ed_reg_Shopname.setText("");
                        ed_reg_Door.setText("");
                        ed_reg_Street.setText("");
                        ed_reg_Area.setText("");
                        ed_reg_Landmark.setText("");
                        ed_reg_City.setText("");
                        ed_reg_State.setText("");
                        ed_reg_National.setText("");
                        ed_reg_Pincode.setText("");
                        ed_reg_Mobile1.setText("");
                        ed_reg_Mobile2.setText("");
                        ed_reg_Email.setText("");
                        ed_reg_Gst.setText("");
                        ed_type.setText("");

                        ed_mechhwold_shop.setText("");
                        ed_detls_in.setText("");
                        ed_spec_in.setText("");
                        ed_mech_seg_detls_other.setText("");
                        ed_stock.setText("");
                        ed_mech_vehical.setText("");
                        ed_mech_market.setText("");
                        ed_mechpartner.setText("");
                        ed_mech_monthly.setText("");
                        ed_mech_partname.setText("");
                        ed_mech_lat.setText("");
                        ed_mech_long.setText("");
                        Toast.makeText(Registration.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Registration.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    elect_hwold_shop, electpartner, elect_partname, elect_market, elect_seg_detls, elect_pros_dels,
                    majr_elect_branddetls, elect_majr_branddetls_other, elect_stater, elect_alter, elect_wiper, elect_noofstaff, LAT, LONG, image, image2, visitingcard, CaptureLocation,pros_dels_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);
                        ed_reg_Ownername.setText("");
                        ed_reg_Shopname.setText("");
                        ed_reg_Door.setText("");
                        ed_reg_Street.setText("");
                        ed_reg_Area.setText("");
                        ed_reg_Landmark.setText("");
                        ed_reg_City.setText("");
                        ed_reg_State.setText("");
                        ed_reg_National.setText("");
                        ed_reg_Pincode.setText("");
                        ed_reg_Mobile1.setText("");
                        ed_reg_Mobile2.setText("");
                        ed_reg_Email.setText("");
                        ed_reg_Gst.setText("");
                        ed_type.setText("");


                        ed_elect_hwold_shop.setText("");
                        ed_elect_seg_detls.setText("");
                        ed_elect_pros_dels.setText("");
                        ed_majr_elect_branddetls.setText("");
                        ed_elect_market.setText("");
                        ed_electpartner.setText("");
                        ed_elect_stater.setText("");
                        ed_elect_alter.setText("");
                        ed_elect_wiper.setText("");
                        ed_elect_noofstaff.setText("");
                        ed_elect_majr_branddetls_other.setText("");
                        ed_elect_partname.setText("");
                        ed_elect_lat.setText("");
                        ed_elect_long.setText("");
                        Toast.makeText(Registration.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Registration.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_ret_mech(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    ret_mech_hwold_shop, ret_mech_part, ret_mech_partname, ret_mech_market, ret_mech_seg_detls,
                    ret_mech_pros_dels, ret_mech_monthly, ret_mech_majr_branddetls, ret_mech_majr_branddetls_other,
                    ret_mech_dels_oebrands, ret_mech_ltvs_pur, ret_mech_ltvs_pur_other,
                    ret_mech_spec_in, ret_mech_stock, ret_mech_vehical, LAT, LONG, image, image2, visitingcard, ret_mech_spec_inother, CaptureLocation,pros_dels_other, dels_oebrands_other
            );
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);
                        ed_reg_Ownername.setText("");
                        ed_reg_Shopname.setText("");
                        ed_reg_Door.setText("");
                        ed_reg_Street.setText("");
                        ed_reg_Area.setText("");
                        ed_reg_Landmark.setText("");
                        ed_reg_City.setText("");
                        ed_reg_State.setText("");
                        ed_reg_National.setText("");
                        ed_reg_Pincode.setText("");
                        ed_reg_Mobile1.setText("");
                        ed_reg_Mobile2.setText("");
                        ed_reg_Email.setText("");
                        ed_reg_Gst.setText("");
                        ed_type.setText("");


                        ed_ret_mech_hwold_shop.setText("");
                        ed_ret_mech_seg_detls.setText("");
                        ed_ret_mech_seg_detls_other.setText("");

                        ed_ret_mech_pros_dels.setText("");
                        ed_ret_mech_majr_branddetls.setText("");
                        ed_ret_mech_dels_oebrands.setText("");
                        ed_ret_mech_ltvs_pur.setText("");
                        ed_ret_mech_part.setText("");
                        ed_ret_mech_monthly.setText("");
                        ed_ret_mech_spec_in.setText("");
                        ed_ret_mech_stock.setText("");
                        ed_ret_mech_vehical.setText("");
                        ed_ret_mech_market.setText("");
                        ed_ret_mech_majr_branddetls_other.setText("");
                        ed_ret_mech_ltvs_pur_other.setText("");
                        ed_ret_mech_partname.setText("");
                        ed_ret_mech_lat.setText("");
                        ed_ret_mech_long.setText("");
                        ed_ret_mech_seg_detls_other.setText("");
                        Toast.makeText(Registration.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Registration.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_mech_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    mech_elect_hwold_shop, mech_elect_partner, mech_elect_partname, mech_elect_market, mech_elect_detls_in,
                    mech_elect_monthly, mech_elect_majr_branddetls, mech_elect_majr_branddetls_other, mech_elec_pros_dels, mech_elect_stater, mech_elect_alter, mech_elect_wiper,
                    mech_elect_noofstaff, mech_elect_spec_in, mech_elect_stock, mech_elect_vehical, LAT, LONG, image, image2, visitingcard, mech_elect_spec_inother, CaptureLocation,pros_dels_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);
                        ed_reg_Ownername.setText("");
                        ed_reg_Shopname.setText("");
                        ed_reg_Door.setText("");
                        ed_reg_Street.setText("");
                        ed_reg_Area.setText("");
                        ed_reg_Landmark.setText("");
                        ed_reg_City.setText("");
                        ed_reg_State.setText("");
                        ed_reg_National.setText("");
                        ed_reg_Pincode.setText("");
                        ed_reg_Mobile1.setText("");
                        ed_reg_Mobile2.setText("");
                        ed_reg_Email.setText("");
                        ed_reg_Gst.setText("");
                        ed_type.setText("");

                        ed_mech_elect_hwold_shop.setText("");
                        ed_mech_elect_detls_in.setText("");
                        ed_mech_elect_spec_in.setText("");
                        ed_mech_elect_stock.setText("");
                        ed_mech_elect_vehical.setText("");
                        ed_mech_elect_market.setText("");
                        ed_mech_elect_partner.setText("");
                        ed_mech_elect_monthly.setText("");
                        ed_mech_elect_seg_detls_other.setText("");

                        ed_mech_elect_majr_branddetls.setText("");
                        ed_mech_elect_stater.setText("");
                        ed_mech_elect_alter.setText("");
                        ed_mech_elect_wiper.setText("");
                        ed_mech_elect_noofstaff.setText("");
                        ed_mech_elect_majr_branddetls_other.setText("");
                        ed_mech_elect_partname.setText("");
                        ed_mech_elect_lat.setText("");
                        ed_mech_elect_long.setText("");
                        ed_mech_elec_pros_dels.setText("");
                        ed_mech_elect_seg_detls_other.setText("");
                        Toast.makeText(Registration.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Registration.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_ret_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    ret_elect_hwold_shop, ret_elect_part, ret_elect_partname, ret_elect_market,
                    ret_elect_seg_detls, ret_elect_pros_dels, ret_elect_monthly, ret_elect_majr_branddetls, ret_majr_elect_branddetls_other,
                    ret_elect_dels_oebrands, ret_elect_ltvs_pur, ret_elect_ltvs_pur_other,
                    ret_elect_stater, ret_elect_alter, ret_elect_wiper, ret_elect_noofstaff, LAT, LONG, image, image2, visitingcard, CaptureLocation,pros_dels_other, dels_oebrands_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);
                        ed_reg_Ownername.setText("");
                        ed_reg_Shopname.setText("");
                        ed_reg_Door.setText("");
                        ed_reg_Street.setText("");
                        ed_reg_Area.setText("");
                        ed_reg_Landmark.setText("");
                        ed_reg_City.setText("");
                        ed_reg_State.setText("");
                        ed_reg_National.setText("");
                        ed_reg_Pincode.setText("");
                        ed_reg_Mobile1.setText("");
                        ed_reg_Mobile2.setText("");
                        ed_reg_Email.setText("");
                        ed_reg_Gst.setText("");
                        ed_type.setText("");

                        ed_ret_elect_hwold_shop.setText("");
                        ed_ret_elect_seg_detls_other.setText("");

                        ed_ret_elect_seg_detls.setText("");
                        ed_ret_elect_pros_dels.setText("");
                        ed_ret_elect_majr_branddetls.setText("");
                        ed_ret_elect_dels_oebrands.setText("");
                        ed_ret_elect_part.setText("");
                        ed_ret_elect_monthly.setText("");
                        ed_ret_elect_stater.setText("");
                        ed_ret_elect_alter.setText("");
                        ed_ret_elect_wiper.setText("");
                        ed_ret_elect_noofstaff.setText("");
                        ed_ret_elect_market.setText("");
                        ed_ret_majr_elect_branddetls_other.setText("");
                        ed_ret_elect_ltvs_pur_other.setText("");
                        ed_ret_elect_partname.setText("");
                        ed_ret_elect_lat.setText("");
                        ed_ret_elect_long.setText("");
                        Toast.makeText(Registration.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Registration.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            CategoryAPI service = RetroClient.getApiService();

            Call<Register> call = service.register_ret_mech_elect(User_Name, type, Ownername, Shopname, DoorNo, Area, Street, Landmark, City,
                    State, National, Pincode, Mobile1, Mobile2, Email, GstNo,
                    ret_mech_elect_hwold_shop, ret_mech_elect_part, ret_mech_elect_partname, ret_mech_elect_market,
                    ret_mech_elect_seg_detls, ret_mech_elect_pros_dels, ret_mech_elect_monthly, ret_mech_elect_majr_branddetls,
                    ret_mech_majr_elect_branddetls_other, ret_mech_elect_dels_oebrands, ret_mech_elect_ltvs_pur, ret_mech_elect_ltvs_pur_other,
                    ret_mech_elect_stater, ret_mech_elect_alter, ret_mech_elect_wiper, ret_mech_elect_noofstaff,
                    ret_mech_elect_spec_in, ret_mech_elect_stock, ret_mech_elect_vehical, LAT, LONG, image, image2, visitingcard, ret_mech_elect_spec_inother, CaptureLocation,pros_dels_other, dels_oebrands_other);
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {
                        iv_image.setImageResource(R.drawable.soft);
                        iv_image2.setImageResource(R.drawable.soft);
                        iv_card.setImageResource(R.drawable.soft);

                        ed_reg_Ownername.setText("");
                        ed_reg_Shopname.setText("");
                        ed_reg_Door.setText("");
                        ed_reg_Street.setText("");
                        ed_reg_Area.setText("");
                        ed_reg_Landmark.setText("");
                        ed_reg_City.setText("");
                        ed_reg_State.setText("");
                        ed_reg_National.setText("");
                        ed_reg_Pincode.setText("");
                        ed_reg_Mobile1.setText("");
                        ed_reg_Mobile2.setText("");
                        ed_reg_Email.setText("");
                        ed_reg_Gst.setText("");
                        ed_type.setText("");

                        ed_ret_mech_elect_hwold_shop.setText("");
                        ed_ret_mech_elect_seg_detls.setText("");
                        ed_ret_mech_elect_seg_detls_other.setText("");
                        ed_ret_mech_elect_pros_dels.setText("");
                        ed_ret_mech_elect_majr_branddetls.setText("");
                        ed_ret_mech_elect_dels_oebrands.setText("");
                        ed_ret_mech_elect_ltvs_pur.setText("");
                        ed_ret_mech_elect_part.setText("");
                        ed_ret_mech_elect_monthly.setText("");
                        ed_ret_mech_elect_stater.setText("");
                        ed_ret_mech_elect_alter.setText("");
                        ed_ret_mech_elect_wiper.setText("");
                        ed_ret_mech_elect_noofstaff.setText("");
                        ed_ret_mech_elect_spec_in.setText("");
                        ed_ret_mech_elect_stock.setText("");
                        ed_ret_mech_elect_vehical.setText("");
                        ed_ret_mech_elect_market.setText("");
                        ed_ret_mech_majr_elect_branddetls_other.setText("");
                        ed_ret_mech_elect_ltvs_pur_other.setText("");
                        ed_ret_mech_elect_partname.setText("");
                        ed_ret_mech_elect_lat.setText("");
                        ed_ret_mech_elect_long.setText("");
                        ed_ret_mech_elect_seg_detls_other.setText("");
                        Toast.makeText(Registration.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Registration.this, "Records not Register!!!", Toast.LENGTH_SHORT).show();
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

            SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS Register_Table(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "User_Type VARCHAR,UserName VARCHAR, ShopName VARCHAR, DoorNo VARCHAR, Street VARCHAR, Area VARCHAR, LandMark VARCHAR, City VARCHAR" +
                    ", Citypredictive VARCHAR, Country VARCHAR, Pincode VARCHAR, Mobile VARCHAR, Phone VARCHAR, Email VARCHAR, GSTNumber VARCHAR" +
                    ", OwnerName VARCHAR, Dealsin VARCHAR, HowOldTheShopIs VARCHAR, TypeOfOrganisation VARCHAR, Market VARCHAR,OtherPartnersNames VARCHAR, " +
                    "SegmentDeals VARCHAR, ProductDealsWith VARCHAR, MonthyTurnOver VARCHAR, MajorBrandDealsWithElectrical VARCHAR," +
                    " DealsWithOEBrand VARCHAR, LucasTVSPurchaseDealsWith VARCHAR, LucasTVSPurchaseDealsWithOther VARCHAR, NoOfStarterMotorServicedInMonth VARCHAR" +
                    ", MajorBrandDealsWithElectricalother VARCHAR, NoOfAlternatorServicedInMonth VARCHAR, NoOfWiperMotorSevicedInMonth VARCHAR," +
                    " NoOfStaff VARCHAR, SpecialistIn VARCHAR, MaintainingStock VARCHAR," +
                    " VehicleAlterMonth VARCHAR, ShopImage VARCHAR,ShopImage2 VARCHAR,VisitingCard VARCHAR,Latitude VARCHAR, Longitude VARCHAR, Specialist_other VARCHAR, Location VARCHAR);");
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret_insert() {
        try {


            SQLiteQuery = "DELETE FROM Register_Table";
            SQLITEDATABASE.execSQL(SQLiteQuery);

            SQLiteQuery = "INSERT INTO Register_Table (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + ret_hwold_shop + "','" + ret_part + "','" + ret_partname + "','" + ret_market + "','" + ret_seg_detls + "', '" + ret_pros_dels + "'," +
                    "'" + ret_monthly + "', '" + ret_majr_branddetls + "','" + ret_majr_branddetls_other + "','" + ret_dels_oebrands + "', " +
                    "'" + ret_ltvs_pur + "','" + ret_ltvs_pur_other + "','" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Registration.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void mech_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,MonthyTurnOver," +
                    "SpecialistIn,MaintainingStock,VehicleAlterMonth,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,Specialist_other)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + mechhwold_shop + "','" + mechpartner + "','" + mech_partname + "','" + mech_market + "','" + detls_in + "'," +

                    "'" + mech_monthly + "', '" + spec_in + "','" + stock + "','" + mech_vehical + "', " +
                    "'" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + spec_inother + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Registration.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + elect_hwold_shop + "','" + electpartner + "','" + elect_partname + "','" + elect_market + "','" + elect_seg_detls + "', '" + elect_pros_dels + "'," +
                    "'" + majr_elect_branddetls + "', '" + elect_majr_branddetls_other + "', '" + elect_stater + "','" + elect_alter + "','" + elect_wiper + "', " +
                    "'" + elect_noofstaff + "', '" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Registration.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret__mech_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,SpecialistIn,MaintainingStock,VehicleAlterMonth," +
                    "Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,Specialist_other)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + ret_mech_hwold_shop + "','" + ret_mech_part + "','" + ret_mech_partname + "','" + ret_mech_market + "','" + ret_mech_seg_detls + "', '" + ret_mech_pros_dels + "'," +
                    "'" + ret_mech_monthly + "', '" + ret_mech_majr_branddetls + "','" + ret_mech_majr_branddetls_other + "','" + ret_mech_dels_oebrands + "', " +
                    "'" + ret_mech_ltvs_pur + "','" + ret_mech_ltvs_pur_other + "','" + ret_mech_spec_in + "','" + ret_mech_stock + "','" + ret_mech_vehical + "'" +
                    ",'" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + ret_mech_spec_inother + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Registration.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void mech_elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table";
            SQLITEDATABASE.execSQL(SQLiteQuery);


            SQLiteQuery = "INSERT INTO Register_Table (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,MonthyTurnOver," +
                    "SpecialistIn,MaintainingStock,VehicleAlterMonth," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,ProductDealsWith,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,Specialist_other)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + mech_elect_hwold_shop + "','" + mech_elect_partner + "','" + mech_elect_partname + "','" + mech_elect_market + "','" + mech_elect_detls_in + "', '" + mech_elect_monthly + "'," +
                    "'" + mech_elect_spec_in + "', '" + mech_elect_stock + "','" + mech_elect_vehical + "','" + mech_elect_majr_branddetls + "', " +
                    "'" + mech_elect_majr_branddetls_other + "','" + mech_elec_pros_dels + "','" + mech_elect_stater + "','" + mech_elect_alter + "','" + mech_elect_wiper + "','" + mech_elect_noofstaff + "'" +
                    ",'" + LAT + "','" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "','" + mech_elect_spec_inother + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Registration.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret_elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table";
            SQLITEDATABASE.execSQL(SQLiteQuery);

            SQLiteQuery = "INSERT INTO Register_Table (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,Latitude, Longitude,ShopImage,ShopImage2,VisitingCard)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + ret_elect_hwold_shop + "','" + ret_elect_part + "','" + ret_elect_partname + "','" + ret_elect_market + "','" + ret_elect_seg_detls + "', '" + ret_elect_pros_dels + "'," +
                    "'" + ret_elect_monthly + "', '" + ret_elect_majr_branddetls + "','" + ret_majr_elect_branddetls_other + "','" + ret_elect_dels_oebrands + "', " +
                    "'" + ret_elect_ltvs_pur + "','" + ret_elect_ltvs_pur_other + "', '" + ret_elect_stater + "', '" + ret_elect_alter + "', '" + ret_elect_wiper + "'" +
                    ", '" + ret_elect_noofstaff + "', '" + LAT + "', '" + LONG + "', '" + image + "','" + image2 + "','" + visitingcard + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Registration.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ret_mech_elect_insert() {
        try {
            SQLiteQuery = "DELETE FROM Register_Table";
            SQLITEDATABASE.execSQL(SQLiteQuery);

            SQLiteQuery = "INSERT INTO Register_Table (UserName,User_Type,OwnerName,ShopName,DoorNo,Area,Street," +
                    "LandMark,City,Citypredictive,Country,Pincode,Mobile,Phone,Email,GSTNumber," +
                    "HowOldTheShopIs,TypeOfOrganisation,OtherPartnersNames,Market,SegmentDeals,ProductDealsWith,MonthyTurnOver," +
                    "MajorBrandDealsWithElectrical,MajorBrandDealsWithElectricalother,DealsWithOEBrand," +
                    "LucasTVSPurchaseDealsWith,LucasTVSPurchaseDealsWithOther,NoOfStarterMotorServicedInMonth," +
                    "NoOfAlternatorServicedInMonth,NoOfWiperMotorSevicedInMonth,NoOfStaff,SpecialistIn,MaintainingStock,VehicleAlterMonth," +
                    "Latitude, Longitude,ShopImage,ShopImage2,VisitingCard,Specialist_other)" +
                    " VALUES('" + User_Name + "','" + type + "', '" + Ownername + "', '" + Shopname + "','" + DoorNo + "', '" + Area + "','" + Street + "', '" + Landmark + "', " +
                    "'" + City + "', '" + State + "', '" + National + "','" + Pincode + "', '" + Mobile1 + "', '" + Mobile2 + "','" + Email + "','" + GstNo + "', " +
                    "'" + ret_mech_elect_hwold_shop + "','" + ret_mech_elect_part + "','" + ret_mech_elect_partname + "','" + ret_mech_elect_market + "','" + ret_mech_elect_seg_detls + "', '" + ret_mech_elect_pros_dels + "'," +
                    "'" + ret_mech_elect_monthly + "', '" + ret_mech_elect_majr_branddetls + "','" + ret_mech_majr_elect_branddetls_other + "','" + ret_mech_elect_dels_oebrands + "', " +
                    "'" + ret_mech_elect_ltvs_pur + "','" + ret_mech_elect_ltvs_pur_other + "', '" + ret_mech_elect_stater + "', '" + ret_mech_elect_alter + "', " +
                    " '" + ret_mech_elect_wiper + "', '" + ret_mech_elect_noofstaff + "', '" + ret_mech_elect_spec_in + "', '" + ret_mech_elect_stock + "', '" + ret_mech_elect_vehical + "', '" + LAT + "', '" + LONG + "','" + image + "','" + image2 + "','" + visitingcard + "','" + ret_mech_elect_spec_inother + "')";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Registration.this, "You Don't have network Data Submit to locally", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void caploc(View view) {

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(Registration.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
