package com.tvs.Split;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.Activity.Home;
import com.tvs.R;
import com.tvs.Tracker.GPSTracker;

import java.io.ByteArrayOutputStream;

import Shared.Config;
import network.NetworkConnection;

public class Shop extends AppCompatActivity {
    TextView tv;
    String type;
    private String[] state = {"Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh",
            "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
            "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
            "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
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
            , "FUEL INJECTION", "TURBO", "BRAKES", "OTHERS"};
    private int ProductDealsValue;
    private String[] MajorBrands = {"LUCAS TVS", "BOSCH", "VALEO", "COMSTAR", "AUTO-LEK", "VARROC", "DENSO", "MAGNETON",
            "MITSUBISHI", "NIKKO", "LOCAL MAKES", "OTHER MAKES"};
    private int MajorBrandsValue;
    private String[] DealsWithOE = {"TATA", "AL", "M&M", "MARUTI", "HYUNDAI", "EICHER", "DAIMLER", "BAJAJ", "OTHERS"};
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


    //ret
    LinearLayout ll_ret, ll_mech, ll_elect, ll_ret_mech, ll_mech_elect, ll_elect_ret, ll_ret_mech_elect,
            ll_partner, ll_majr_branddetls_other, ll_ltvs_pur_other, ll_ret_seg_detls_other, ll_ret_dels_oebrands_other, ll_pros_dels_other;
    EditText ed_hwold_shop, ed_seg_detls, ed_pros_dels, ed_majr_branddetls, ed_dels_oebrands, ed_dels_oebrands_other,
            ed_ltvs_pur, ed_part, ed_monthly, ed_market, ed_ret_majr_branddetls_other,
            ed_ret_lat, ed_ret_long, ed_ret_ltvs_pur_other, ed_ret_partname, ed_ret_seg_detls_other, ed_pros_dels_other;

    String ret_hwold_shop, ret_seg_detls, ret_pros_dels, ret_majr_branddetls, ret_dels_oebrands, ret_dels_oebrands_other, ret_pros_dels_other,
            ret_ltvs_pur, ret_part, ret_monthly, ret_market, ret_majr_branddetls_other, ret_ltvs_pur_other, ret_partname, ret_long, ret_lat;

    //mech
    LinearLayout ll_mech_partner, ll_mech_seg_detls_other;

    EditText ed_mechhwold_shop, ed_detls_in, ed_spec_in, ed_stock, ed_mech_vehical, ed_mech_market, ed_mechpartner, ed_mech_monthly,
            ed_mech_lat, ed_mech_long, ed_mech_partname, ed_mech_seg_detls_other;

    String mechhwold_shop, detls_in, spec_in, stock, mech_vehical, mech_market, mechpartner, mech_monthly, mech_partname, mech_long, mech_lat, spec_inother;

    //elect
    EditText ed_elect_hwold_shop, ed_elect_seg_detls, ed_elect_pros_dels, ed_majr_elect_branddetls,
            ed_elect_market, ed_electpartner, ed_elect_stater, ed_elect_alter, ed_elect_wiper, ed_elect_noofstaff,
            ed_elect_majr_branddetls_other, ed_elect_lat, ed_elect_long, ed_elect_partname, ed_elect_seg_detls_other, ed_elect_pros_dels_other;

    String elect_hwold_shop, elect_seg_detls, elect_pros_dels, majr_elect_branddetls,
            elect_market, electpartner, elect_stater, elect_alter, elect_wiper, elect_noofstaff, elect_pros_dels_other,
            elect_majr_branddetls_other, elect_partname, elect_long, elect_lat;

    LinearLayout ll_elect_majr_branddetls_others, ll_elect_partner, ll_elect_seg_detls_other, ll_elect_pros_dels_other;

    //ret_mech
    LinearLayout ll_ret_mech_partner, ll_ret_mech_majr_branddetls_other, ll_ret_mech_ltvs_pur_other,
            ll_ret_mech_seg_detls_other, ll_ret_mech_pros_dels_other, ll_ret_mech_dels_oebrands_other;

    EditText ed_ret_mech_hwold_shop, ed_ret_mech_seg_detls, ed_ret_mech_pros_dels, ed_ret_mech_majr_branddetls,
            ed_ret_mech_dels_oebrands, ed_ret_mech_ltvs_pur, ed_ret_mech_part, ed_ret_mech_monthly,
            ed_ret_mech_spec_in, ed_ret_mech_stock, ed_ret_mech_vehical, ed_ret_mech_dels_oebrands_other,
            ed_ret_mech_market, ed_ret_mech_majr_branddetls_other, ed_ret_mech_ltvs_pur_other, ed_ret_mech_partname,
            ed_ret_mech_lat, ed_ret_mech_long, ed_ret_mech_seg_detls_other, ed_ret_mech_pros_dels_other;

    String ret_mech_hwold_shop, ret_mech_seg_detls, ret_mech_pros_dels, ret_mech_majr_branddetls,
            ret_mech_dels_oebrands, ret_mech_ltvs_pur, ret_mech_part, ret_mech_monthly,
            ret_mech_spec_in, ret_mech_stock, ret_mech_vehical, ret_mech_spec_inother, ret_mech_dels_oebrands_other, ret_mech_pros_dels_other,
            ret_mech_market, ret_mech_majr_branddetls_other, ret_mech_ltvs_pur_other, ret_mech_partname, ret_mech_long, ret_mech_lat;

    //mech_elect
    EditText ed_mech_elect_hwold_shop, ed_mech_elect_detls_in, ed_mech_elect_spec_in, ed_mech_elect_stock, ed_mech_elect_vehical,
            ed_mech_elect_market, ed_mech_elect_partner, ed_mech_elect_monthly, ed_mech_elect_majr_branddetls, ed_mech_elect_stater,
            ed_mech_elect_alter, ed_mech_elect_wiper, ed_mech_elect_pros_dels_other,
            ed_mech_elect_noofstaff, ed_mech_elec_pros_dels, ed_mech_elect_majr_branddetls_other, ed_mech_elect_lat, ed_mech_elect_long, ed_mech_elect_partname, ed_mech_elect_seg_detls_other;

    String mech_elect_hwold_shop, mech_elect_detls_in, mech_elect_spec_in, mech_elect_stock, mech_elect_vehical, mech_elect_spec_inother, mech_elect_pros_dels_other,
            mech_elect_market, mech_elect_partner, mech_elect_monthly, mech_elect_majr_branddetls, mech_elect_stater, mech_elect_alter,
            mech_elect_wiper, mech_elect_noofstaff, mech_elec_pros_dels, mech_elect_majr_branddetls_other, mech_elect_partname, mech_elect_long, mech_elect_lat;

    LinearLayout ll_mech_elect_partner, ll_mech_elect_majr_branddetls_other, ll_mech_elect_seg_detls_other, ll_mech_elect_pros_dels_other;

    //elect_ret
    LinearLayout ll_ret_elect_partner, ll_ret_elect_majr_branddetls_other,
            ll_ret_elect_ltvs_pur_other, ll_ret_elect_seg_detls_other, ll_ret_elect_dels_oebrands_other, ll_ret_elect_pros_dels_other;
    EditText ed_ret_elect_hwold_shop, ed_ret_elect_seg_detls, ed_ret_elect_pros_dels, ed_ret_elect_majr_branddetls,
            ed_ret_elect_dels_oebrands, ed_ret_elect_ltvs_pur, ed_ret_elect_part, ed_ret_elect_monthly, ed_ret_elect_dels_oebrands_other,
            ed_ret_elect_stater, ed_ret_elect_alter, ed_ret_elect_wiper, ed_ret_elect_noofstaff, ed_ret_elect_pros_dels_other,
            ed_ret_elect_market, ed_ret_majr_elect_branddetls_other, ed_ret_elect_ltvs_pur_other, ed_ret_elect_partname, ed_ret_elect_lat, ed_ret_elect_long, ed_ret_elect_seg_detls_other;

    String ret_elect_hwold_shop, ret_elect_seg_detls, ret_elect_pros_dels, ret_elect_majr_branddetls,
            ret_elect_dels_oebrands, ret_elect_ltvs_pur, ret_elect_part, ret_elect_monthly,
            ret_elect_stater, ret_elect_alter, ret_elect_wiper, ret_elect_noofstaff, ret_elect_dels_oebrands_other, ret_elect_pros_dels_other,
            ret_elect_market, ret_majr_elect_branddetls_other, ret_elect_ltvs_pur_other, ret_elect_partname, ret_elect_long, ret_elect_lat;


    //ret_mech_elect_
    LinearLayout ll_ret_mech_elect_partner, ll_ret_mech_elect_majr_branddetls_other, ll_ret_mech_elect_ltvs_pur_other,
            ll_ret_mech_elect_seg_detls_other, ll_ret_mech_elect_dels_oebrands_other, ll_ret_mech_elect_pros_dels_other;
    EditText ed_ret_mech_elect_hwold_shop, ed_ret_mech_elect_seg_detls, ed_ret_mech_elect_pros_dels, ed_ret_mech_elect_majr_branddetls,
            ed_ret_mech_elect_dels_oebrands, ed_ret_mech_elect_ltvs_pur, ed_ret_mech_elect_part, ed_ret_mech_elect_monthly,
            ed_ret_mech_elect_stater, ed_ret_mech_elect_alter, ed_ret_mech_elect_wiper, ed_ret_mech_elect_noofstaff, ed_ret_mech_elect_dels_oebrands_other,
            ed_ret_mech_elect_spec_in, ed_ret_mech_elect_stock, ed_ret_mech_elect_vehical, ed_ret_mech_elect_pros_dels_other,
            ed_ret_mech_elect_market, ed_ret_mech_majr_elect_branddetls_other, ed_ret_mech_elect_lat, ed_ret_mech_elect_long, ed_ret_mech_elect_ltvs_pur_other, ed_ret_mech_elect_partname, ed_ret_mech_elect_seg_detls_other;

    String ret_mech_elect_hwold_shop, ret_mech_elect_seg_detls, ret_mech_elect_pros_dels, ret_mech_elect_majr_branddetls,
            ret_mech_elect_dels_oebrands, ret_mech_elect_ltvs_pur, ret_mech_elect_part, ret_mech_elect_monthly,
            ret_mech_elect_stater, ret_mech_elect_alter, ret_mech_elect_wiper, ret_mech_elect_noofstaff,
            ret_mech_elect_spec_in, ret_mech_elect_stock, ret_mech_elect_spec_inother, ret_mech_elect_vehical, ret_mech_elect_dels_oebrands_other, ret_mech_elect_pros_dels_other,
            ret_mech_elect_market, ret_mech_majr_elect_branddetls_other, ret_mech_elect_ltvs_pur_other, ret_mech_elect_partname, ret_mech_elect_long, ret_mech_elect_lat;

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
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_shop);

        net = new NetworkConnection(Shop.this);
        builder = new AlertDialog.Builder(Shop.this);
        sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString("KEY_type", "");
        tv = (TextView) findViewById(R.id.textView2);
        tv.setText(type + " SHOP INFORMATION");
        ll_ret = (LinearLayout) findViewById(R.id.ll_ret);
        ll_mech = (LinearLayout) findViewById(R.id.ll_mech);
        ll_elect = (LinearLayout) findViewById(R.id.ll_elect);
        ll_ret_mech = (LinearLayout) findViewById(R.id.ll_ret_mech);
        ll_mech_elect = (LinearLayout) findViewById(R.id.ll_mech_elect);
        ll_elect_ret = (LinearLayout) findViewById(R.id.ll_elect_ret);
        ll_ret_mech_elect = (LinearLayout) findViewById(R.id.ll_ret_mech_elect);

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
            Toast.makeText(Shop.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
        }
        //ret
        ll_partner = (LinearLayout) findViewById(R.id.ll_ret_partner);
        ll_majr_branddetls_other = (LinearLayout) findViewById(R.id.ret_ll_othersmakes);
        ll_ltvs_pur_other = (LinearLayout) findViewById(R.id.ret_ll_ltvs_pur_others);
        ll_ret_seg_detls_other = (LinearLayout) findViewById(R.id.ll_seg_detls_other);
        ll_ret_dels_oebrands_other = (LinearLayout) findViewById(R.id.ll_ret_dels_oebrands_others);
        ll_pros_dels_other = (LinearLayout) findViewById(R.id.ll_ret_pros_dels_other);
        ed_hwold_shop = (EditText) findViewById(R.id.hwold_shop);
        ed_seg_detls = (EditText) findViewById(R.id.seg_detls);
        ed_pros_dels = (EditText) findViewById(R.id.pros_dels);
        ed_pros_dels_other = (EditText) findViewById(R.id.pros_dels_other);
        ed_majr_branddetls = (EditText) findViewById(R.id.majr_branddetls);
        ed_dels_oebrands = (EditText) findViewById(R.id.dels_oebrands);
        ed_dels_oebrands_other = (EditText) findViewById(R.id.dels_oebrands_others);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_pros_dels.equals("OTHERS")) {
                                        ll_pros_dels_other.setVisibility(View.GONE);
                                    } else {
                                        ll_pros_dels_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_dels_oebrands.equals("OTHERS")) {
                                        ll_ret_dels_oebrands_other.setVisibility(View.GONE);
                                    } else {
                                        ll_ret_dels_oebrands_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
        ll_elect_pros_dels_other = (LinearLayout) findViewById(R.id.ll_ret_elect_pros_dels_other);
        ed_elect_seg_detls_other = (EditText) findViewById(R.id.seg_elect_detls_other);
        ed_elect_pros_dels_other = (EditText) findViewById(R.id.elect_pros_dels_other);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!elect_pros_dels.equals("OTHERS")) {
                                        ll_elect_pros_dels_other.setVisibility(View.GONE);
                                    } else {
                                        ll_elect_pros_dels_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
        ll_ret_mech_pros_dels_other = (LinearLayout) findViewById(R.id.ll_ret_mech_pros_dels_other);
        ll_ret_mech_dels_oebrands_other = (LinearLayout) findViewById(R.id.ll_ret_mech_dels_oebrands_others);

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
        ed_ret_mech_dels_oebrands_other = (EditText) findViewById(R.id.ret_mech_elect_dels_oebrands_others);
        ed_ret_mech_majr_branddetls_other = (EditText) findViewById(R.id.ret_mech_majr_branddetlsother);
        ed_ret_mech_ltvs_pur_other = (EditText) findViewById(R.id.ret_mechltvs_pur_other);
        ed_ret_mech_partname = (EditText) findViewById(R.id.ret_mech_partname);
        ed_ret_mech_lat = (EditText) findViewById(R.id.ret_mech_lat);
        ed_ret_mech_long = (EditText) findViewById(R.id.ret_mech_long);
        ed_ret_mech_pros_dels_other = (EditText) findViewById(R.id.ret_mech_pros_dels_other);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_mech_pros_dels.equals("OTHERS")) {
                                        ll_ret_mech_pros_dels_other.setVisibility(View.GONE);
                                    } else {
                                        ll_ret_mech_pros_dels_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_mech_dels_oebrands.equals("OTHERS")) {
                                        ll_ret_mech_dels_oebrands_other.setVisibility(View.GONE);
                                    } else {
                                        ll_ret_mech_dels_oebrands_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
        ll_mech_elect_pros_dels_other = (LinearLayout) findViewById(R.id.ll_mech_elect_pros_dels_other);
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
        ed_mech_elect_pros_dels_other = (EditText) findViewById(R.id.mech_elect_pros_dels_other);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!mech_elec_pros_dels.equals("OTHERS")) {
                                        ll_mech_elect_pros_dels_other.setVisibility(View.GONE);
                                    } else {
                                        ll_mech_elect_pros_dels_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
        ll_ret_elect_dels_oebrands_other = (LinearLayout) findViewById(R.id.ll_ret_elect_dels_oebrands_others);
        ll_ret_elect_pros_dels_other = (LinearLayout) findViewById(R.id.ll_ret_elect_pros_dels_other);
        ed_ret_elect_hwold_shop = (EditText) findViewById(R.id.ret_elect_hwold_shop);
        ed_ret_elect_seg_detls = (EditText) findViewById(R.id.ret_elect_seg_detls);
        ed_ret_elect_pros_dels = (EditText) findViewById(R.id.ret_elect_pros_dels);
        ed_ret_elect_pros_dels_other = (EditText) findViewById(R.id.ret_elect_pros_dels_other);
        ed_ret_elect_majr_branddetls = (EditText) findViewById(R.id.ret_elect_majr_branddetls);
        ed_ret_elect_dels_oebrands = (EditText) findViewById(R.id.ret_elect_dels_oebrands);
        ed_ret_elect_dels_oebrands_other = (EditText) findViewById(R.id.ret_elect_dels_oebrands_others);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_elect_pros_dels.equals("OTHERS")) {
                                        ll_ret_elect_pros_dels_other.setVisibility(View.GONE);
                                    } else {
                                        ll_ret_elect_pros_dels_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_elect_dels_oebrands.equals("OTHERS")) {
                                        ll_ret_elect_dels_oebrands_other.setVisibility(View.GONE);
                                    } else {
                                        ll_ret_elect_dels_oebrands_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
        ll_ret_mech_elect_dels_oebrands_other = (LinearLayout) findViewById(R.id.ll_ret_mech_elect_dels_oebrands_others);
        ll_ret_mech_elect_pros_dels_other = (LinearLayout) findViewById(R.id.ll_ret_mech_elect_pros_dels_other);

        ed_ret_mech_elect_hwold_shop = (EditText) findViewById(R.id.ret_mech_elect_hwold_shop);
        ed_ret_mech_elect_seg_detls = (EditText) findViewById(R.id.ret_mech_elect_seg_detls);
        ed_ret_mech_elect_pros_dels = (EditText) findViewById(R.id.ret_mech_elect_pros_dels);
        ed_ret_mech_elect_pros_dels_other = (EditText) findViewById(R.id.ret_mech_elect_pros_dels_other);
        ed_ret_mech_elect_majr_branddetls = (EditText) findViewById(R.id.ret_mech_elect_majr_branddetls);
        ed_ret_mech_elect_dels_oebrands = (EditText) findViewById(R.id.ret_mech_elect_dels_oebrands);
        ed_ret_mech_elect_dels_oebrands_other = (EditText) findViewById(R.id.ret_mech_elect_dels_oebrands_others);

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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_mech_elect_pros_dels.equals("OTHERS")) {
                                        ll_ret_mech_elect_pros_dels_other.setVisibility(View.GONE);
                                    } else {
                                        ll_ret_mech_elect_pros_dels_other.setVisibility(View.VISIBLE);
                                    }


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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
                                    if (!ret_mech_elect_dels_oebrands.equals("OTHERS")) {
                                        ll_ret_mech_elect_dels_oebrands_other.setVisibility(View.GONE);
                                    } else {
                                        ll_ret_mech_elect_dels_oebrands_other.setVisibility(View.VISIBLE);
                                    }
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
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
    }

    public void next(View view) {
        try {

//ret
            ret_ltvs_pur = ed_ltvs_pur.getText().toString();
            ret_majr_branddetls = ed_majr_branddetls.getText().toString();
            ret_monthly = ed_monthly.getText().toString();
            ret_pros_dels = ed_pros_dels.getText().toString();
            ret_seg_detls = ed_seg_detls.getText().toString();
            ret_market = ed_market.getText().toString();
            ret_part = ed_part.getText().toString();
            ret_hwold_shop = ed_hwold_shop.getText().toString();
            ret_dels_oebrands = ed_dels_oebrands.getText().toString();
            ret_ltvs_pur = ed_ltvs_pur.getText().toString();


            ret_majr_branddetls_other = ed_ret_majr_branddetls_other.getText().toString();
            ret_ltvs_pur_other = ed_ret_ltvs_pur_other.getText().toString();
            ret_partname = ed_ret_partname.getText().toString();
            ret_lat = ed_ret_lat.getText().toString();
            ret_long = ed_ret_long.getText().toString();
            ret_dels_oebrands_other = ed_dels_oebrands_other.getText().toString();
            ret_pros_dels_other = ed_pros_dels_other.getText().toString();
            //mech

            mechhwold_shop = ed_mechhwold_shop.getText().toString();
            detls_in = ed_detls_in.getText().toString();
            spec_in = ed_spec_in.getText().toString();
            stock = ed_stock.getText().toString();
            mech_market = ed_mech_market.getText().toString();
            mech_vehical = ed_mech_vehical.getText().toString();
            mechpartner = ed_mechpartner.getText().toString();
            mech_monthly = ed_mech_monthly.getText().toString();
            mech_partname = ed_mech_partname.getText().toString();
            mech_lat = ed_ret_lat.getText().toString();
            mech_long = ed_ret_long.getText().toString();
            spec_inother = ed_mech_seg_detls_other.getText().toString();

            //elect

            elect_hwold_shop = ed_elect_hwold_shop.getText().toString().trim();
            electpartner = ed_electpartner.getText().toString().trim();
            elect_market = ed_elect_market.getText().toString().trim();
            elect_seg_detls = ed_elect_seg_detls.getText().toString().trim();
            elect_pros_dels = ed_elect_pros_dels.getText().toString().trim();
            majr_elect_branddetls = ed_majr_elect_branddetls.getText().toString().trim();
            elect_stater = ed_elect_stater.getText().toString().trim();
            elect_alter = ed_elect_alter.getText().toString().trim();
            elect_wiper = ed_elect_wiper.getText().toString().trim();
            elect_noofstaff = ed_elect_noofstaff.getText().toString().trim();

            elect_majr_branddetls_other = ed_elect_majr_branddetls_other.getText().toString().trim();
            elect_partname = ed_elect_partname.getText().toString().trim();
            elect_lat = ed_elect_lat.getText().toString();
            elect_long = ed_elect_long.getText().toString();
            elect_pros_dels_other = ed_elect_pros_dels_other.getText().toString();
            //ret_mech

            ret_mech_hwold_shop= ed_ret_mech_hwold_shop.getText().toString();
            ret_mech_part= ed_ret_mech_part.getText().toString();
            ret_mech_market= ed_ret_mech_market.getText().toString();
            ret_mech_seg_detls= ed_ret_mech_seg_detls.getText().toString();
            ret_mech_pros_dels= ed_ret_mech_pros_dels.getText().toString();
            ret_mech_monthly= ed_ret_mech_monthly.getText().toString();
            ret_mech_majr_branddetls= ed_ret_mech_majr_branddetls.getText().toString();
            ret_mech_dels_oebrands= ed_ret_mech_dels_oebrands.getText().toString();
            ret_mech_ltvs_pur= ed_ret_mech_ltvs_pur.getText().toString();
            ret_mech_pros_dels_other= ed_ret_mech_pros_dels_other.getText().toString();


            ret_mech_spec_in= ed_ret_mech_spec_in.getText().toString();
            ret_mech_stock= ed_ret_mech_stock.getText().toString();
            ret_mech_vehical= ed_ret_mech_vehical.getText().toString();

            ret_mech_majr_branddetls_other = ed_ret_mech_majr_branddetls_other.getText().toString().trim();
            ret_mech_ltvs_pur_other = ed_ret_mech_ltvs_pur_other.getText().toString().trim();
            ret_mech_partname = ed_ret_mech_partname.getText().toString().trim();
            ret_mech_lat = ed_ret_mech_lat.getText().toString();
            ret_mech_long = ed_ret_mech_long.getText().toString();
            ret_mech_spec_inother = ed_ret_mech_seg_detls_other.getText().toString();
            ret_mech_dels_oebrands_other = ed_ret_mech_dels_oebrands_other.getText().toString();
            //mech_elect

           mech_elect_hwold_shop= ed_mech_elect_hwold_shop.getText().toString();
           mech_elect_partner= ed_mech_elect_partner.getText().toString();
           mech_elect_market= ed_mech_elect_market.getText().toString();
           mech_elect_detls_in= ed_mech_elect_detls_in.getText().toString();
           mech_elec_pros_dels= ed_mech_elec_pros_dels.getText().toString();
           mech_elect_monthly= ed_mech_elect_monthly.getText().toString();
           mech_elect_pros_dels_other= ed_mech_elect_pros_dels_other.getText().toString();
           mech_elect_majr_branddetls= ed_mech_elect_majr_branddetls.getText().toString();
           mech_elect_spec_in= ed_mech_elect_spec_in.getText().toString();
           mech_elect_stock= ed_mech_elect_stock.getText().toString();
           mech_elect_vehical= ed_mech_elect_vehical.getText().toString();

           mech_elect_stater= ed_mech_elect_stater.getText().toString();
           mech_elect_alter= ed_mech_elect_alter.getText().toString();
           mech_elect_wiper= ed_mech_elect_wiper.getText().toString();
           mech_elect_noofstaff= ed_mech_elect_noofstaff.getText().toString();

            mech_elect_majr_branddetls_other = ed_mech_elect_majr_branddetls_other.getText().toString().trim();
            mech_elect_partname = ed_mech_elect_partname.getText().toString().trim();
            mech_elect_lat = ed_mech_elect_lat.getText().toString();
            mech_elect_long = ed_mech_elect_long.getText().toString();
            mech_elect_spec_inother = ed_mech_elect_seg_detls_other.getText().toString();
            //elect_ret

            ret_elect_hwold_shop= ed_ret_elect_hwold_shop.getText().toString();
            ret_elect_part= ed_ret_elect_part.getText().toString();
            ret_elect_market= ed_ret_elect_market.getText().toString();
            ret_elect_seg_detls= ed_ret_elect_seg_detls.getText().toString();
            ret_elect_pros_dels= ed_ret_elect_pros_dels.getText().toString();
            ret_elect_monthly= ed_ret_elect_monthly.getText().toString();
            ret_elect_majr_branddetls= ed_ret_elect_majr_branddetls.getText().toString();
            ret_majr_elect_branddetls_other= ed_ret_majr_elect_branddetls_other.getText().toString();
            ret_elect_dels_oebrands= ed_ret_elect_dels_oebrands.getText().toString();
            ret_elect_ltvs_pur= ed_ret_elect_ltvs_pur.getText().toString();
            ret_elect_ltvs_pur_other= ed_ret_elect_ltvs_pur_other.getText().toString();

            ret_elect_stater= ed_ret_elect_stater.getText().toString();
            ret_elect_alter= ed_ret_elect_alter.getText().toString();
            ret_elect_wiper= ed_ret_elect_wiper.getText().toString();
            ret_elect_noofstaff= ed_ret_elect_noofstaff.getText().toString();

            ret_majr_elect_branddetls_other = ed_ret_majr_elect_branddetls_other.getText().toString().trim();
            ret_elect_ltvs_pur_other = ed_ret_elect_ltvs_pur_other.getText().toString().trim();
            ret_elect_dels_oebrands_other = ed_ret_elect_dels_oebrands_other.getText().toString().trim();
            ret_elect_pros_dels_other = ed_ret_elect_pros_dels_other.getText().toString().trim();
            ret_elect_partname = ed_ret_elect_partname.getText().toString().trim();
            ret_elect_lat = ed_ret_elect_lat.getText().toString();
            ret_elect_long = ed_ret_elect_long.getText().toString();

            //ret_mech_elect

            ret_mech_elect_hwold_shop = ed_ret_mech_elect_hwold_shop.getText().toString().trim();
            ret_mech_elect_part = ed_ret_mech_elect_part.getText().toString().trim();
            ret_mech_elect_partname = ed_ret_mech_elect_partname.getText().toString().trim();
            ret_mech_elect_market = ed_ret_mech_elect_market.getText().toString().trim();
            ret_mech_elect_seg_detls = ed_ret_mech_elect_seg_detls.getText().toString().trim();
            ret_mech_elect_pros_dels = ed_ret_mech_elect_pros_dels.getText().toString().trim();
            ret_mech_elect_monthly = ed_ret_mech_elect_monthly.getText().toString().trim();
            ret_mech_elect_majr_branddetls = ed_ret_mech_elect_majr_branddetls.getText().toString().trim();
            ret_mech_majr_elect_branddetls_other = ed_ret_mech_majr_elect_branddetls_other.getText().toString().trim();
            ret_mech_elect_dels_oebrands = ed_ret_mech_elect_dels_oebrands.getText().toString().trim();
            ret_mech_elect_ltvs_pur = ed_ret_mech_elect_ltvs_pur.getText().toString().trim();
            ret_mech_elect_ltvs_pur_other = ed_ret_mech_elect_ltvs_pur_other.getText().toString().trim();

            ret_mech_elect_stater = ed_ret_mech_elect_stater.getText().toString().trim();
            ret_mech_elect_alter = ed_ret_mech_elect_alter.getText().toString().trim();
            ret_mech_elect_wiper = ed_ret_mech_elect_wiper.getText().toString().trim();
            ret_mech_elect_noofstaff = ed_ret_mech_elect_noofstaff.getText().toString().trim();

            ret_mech_elect_spec_in = ed_ret_mech_elect_spec_in.getText().toString().trim();
            ret_mech_elect_stock = ed_ret_mech_elect_stock.getText().toString().trim();
            ret_mech_elect_spec_inother = ed_ret_mech_elect_seg_detls_other.getText().toString().trim();
            ret_mech_elect_vehical = ed_ret_mech_elect_vehical.getText().toString().trim();
            ret_mech_elect_dels_oebrands_other = ed_ret_mech_elect_dels_oebrands_other.getText().toString().trim();
            ret_mech_elect_pros_dels_other = ed_ret_mech_elect_pros_dels_other.getText().toString().trim();


            if (type.equals("")) {
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

                else {
                    sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();

                    editor.putString("KEY_hwold_shop", ret_hwold_shop);
                    editor.putString("KEY_part", ret_part);
                    editor.putString("KEY_partname", ret_partname);
                    editor.putString("KEY_market", ret_market);
                    editor.putString("KEY_seg_detls", ret_seg_detls);
                    editor.putString("KEY_pros_dels", ret_pros_dels);
                    editor.putString("KEY_monthly", ret_monthly);
                    editor.putString("KEY_majr_branddetls", ret_majr_branddetls);
                    editor.putString("KEY_majr_branddetls_other", ret_majr_branddetls_other);
                    editor.putString("KEY_dels_oebrands", ret_dels_oebrands);
                    editor.putString("KEY_ltvs_pur", ret_ltvs_pur);
                    editor.putString("KEY_ltvs_pur_othe", ret_ltvs_pur_other);
                    editor.putString("KEY_dels_oebrands_other", ret_dels_oebrands_other);
                    editor.putString("KEY_pros_dels_other", ret_pros_dels_other);
                    editor.apply();
                    startActivity(new Intent(Shop.this, Capture.class));
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
                    sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();


                    editor.putString("KEY_hwold_shop", mechhwold_shop);
                    editor.putString("KEY_seg_detls", detls_in);
                    editor.putString("KEY_spec_in", spec_in);
                    editor.putString("KEY_stock", stock);
                    editor.putString("KEY_market", mech_market);
                    editor.putString("KEY_partname", mech_partname);
                    editor.putString("KEY_vehical", mech_vehical);
                    editor.putString("KEY_part", mechpartner);
                    editor.putString("KEY_monthly", mech_monthly);
                    editor.putString("KEY_spec_inother", spec_inother);

                    editor.apply();
                    startActivity(new Intent(Shop.this, Capture.class));

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
                    sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();

                    editor.putString("KEY_hwold_shop", elect_hwold_shop);
                    editor.putString("KEY_part", electpartner);
                    editor.putString("KEY_partname", elect_partname);
                    editor.putString("KEY_market", elect_market);
                    editor.putString("KEY_seg_detls", elect_seg_detls);
                    editor.putString("KEY_pros_dels", elect_pros_dels);
                    editor.putString("KEY_majr_branddetls", majr_elect_branddetls);
                    editor.putString("KEY_majr_branddetls_other", elect_majr_branddetls_other);
                    editor.putString("KEY_stater", elect_stater);
                    editor.putString("KEY_alter", elect_alter);
                    editor.putString("KEY_wiper", elect_wiper);
                    editor.putString("KEY_noofstaff", elect_noofstaff);
                    editor.putString("KEY_pros_dels_other", elect_pros_dels_other);
                    editor.apply();
                    startActivity(new Intent(Shop.this, Capture.class));
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
                    sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();

                    editor.putString("KEY_hwold_shop", ret_mech_hwold_shop);
                    editor.putString("KEY_part", ret_mech_part);
                    editor.putString("KEY_partname", ret_mech_partname);
                    editor.putString("KEY_market", ret_mech_market);
                    editor.putString("KEY_seg_detls", ret_mech_seg_detls);
                    editor.putString("KEY_pros_dels", ret_mech_pros_dels);
                    editor.putString("KEY_monthly", ret_mech_monthly);
                    editor.putString("KEY_majr_branddetls", ret_mech_majr_branddetls);
                    editor.putString("KEY_majr_branddetls_other", ret_mech_majr_branddetls_other);
                    editor.putString("KEY_dels_oebrands", ret_mech_dels_oebrands);
                    editor.putString("KEY_ltvs_pur", ret_mech_ltvs_pur);
                    editor.putString("KEY_ltvs_pur_othe", ret_mech_ltvs_pur_other);
                    editor.putString("KEY_dels_oebrands_other", ret_mech_dels_oebrands_other);
                    editor.putString("KEY_pros_dels_other", ret_mech_pros_dels_other);


                    editor.putString("KEY_spec_in", ret_mech_spec_in);
                    editor.putString("KEY_stock", ret_mech_stock);
                    editor.putString("KEY_spec_inother", ret_mech_spec_inother);
                    editor.putString("KEY_vehical", ret_mech_vehical);

                    editor.apply();
                    startActivity(new Intent(Shop.this, Capture.class));
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
                    sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();

                    editor.putString("KEY_hwold_shop", mech_elect_hwold_shop);
                    editor.putString("KEY_part", mech_elect_partner);
                    editor.putString("KEY_partname", mech_elect_partname);
                    editor.putString("KEY_market", mech_elect_market);
                    editor.putString("KEY_seg_detls", mech_elect_detls_in);
                    editor.putString("KEY_pros_dels", mech_elec_pros_dels);
                    editor.putString("KEY_monthly", mech_elect_monthly);
                    editor.putString("KEY_pros_dels_other", mech_elect_pros_dels_other);
                    editor.putString("KEY_majr_branddetls", mech_elect_majr_branddetls);
                    editor.putString("KEY_majr_branddetls_other", mech_elect_majr_branddetls_other);
                    editor.putString("KEY_spec_in", mech_elect_spec_in);
                    editor.putString("KEY_stock", mech_elect_stock);
                    editor.putString("KEY_spec_inother", mech_elect_spec_inother);
                    editor.putString("KEY_vehical", mech_elect_vehical);

                    editor.putString("KEY_stater", mech_elect_stater);
                    editor.putString("KEY_alter", mech_elect_alter);
                    editor.putString("KEY_wiper", mech_elect_wiper);
                    editor.putString("KEY_noofstaff", mech_elect_noofstaff);


                    editor.apply();
                    startActivity(new Intent(Shop.this, Capture.class));

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
                    sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();

                    editor.putString("KEY_hwold_shop", ret_elect_hwold_shop);
                    editor.putString("KEY_part", ret_elect_part);
                    editor.putString("KEY_partname", ret_elect_partname);
                    editor.putString("KEY_market", ret_elect_market);
                    editor.putString("KEY_seg_detls", ret_elect_seg_detls);
                    editor.putString("KEY_pros_dels", ret_elect_pros_dels);
                    editor.putString("KEY_monthly", ret_elect_monthly);
                    editor.putString("KEY_majr_branddetls", ret_elect_majr_branddetls);
                    editor.putString("KEY_majr_branddetls_other", ret_majr_elect_branddetls_other);
                    editor.putString("KEY_dels_oebrands", ret_elect_dels_oebrands);
                    editor.putString("KEY_ltvs_pur", ret_elect_ltvs_pur);
                    editor.putString("KEY_ltvs_pur_othe", ret_elect_ltvs_pur_other);
                    editor.putString("KEY_dels_oebrands_other", ret_elect_dels_oebrands_other);
                    editor.putString("KEY_pros_dels_other", ret_elect_pros_dels_other);

                    editor.putString("KEY_stater", ret_elect_stater);
                    editor.putString("KEY_alter", ret_elect_alter);
                    editor.putString("KEY_wiper", ret_elect_wiper);
                    editor.putString("KEY_noofstaff", ret_elect_noofstaff);


                    editor.apply();
                    startActivity(new Intent(Shop.this, Capture.class));

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
                    sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();

                    editor.putString("KEY_hwold_shop", ret_mech_elect_hwold_shop);
                    editor.putString("KEY_part", ret_mech_elect_part);
                    editor.putString("KEY_partname", ret_mech_elect_partname);
                    editor.putString("KEY_market", ret_mech_elect_market);
                    editor.putString("KEY_seg_detls", ret_mech_elect_seg_detls);
                    editor.putString("KEY_pros_dels", ret_mech_elect_pros_dels);
                    editor.putString("KEY_monthly", ret_mech_elect_monthly);
                    editor.putString("KEY_majr_branddetls", ret_mech_elect_majr_branddetls);
                    editor.putString("KEY_majr_branddetls_other", ret_mech_majr_elect_branddetls_other);
                    editor.putString("KEY_dels_oebrands", ret_mech_elect_dels_oebrands);
                    editor.putString("KEY_ltvs_pur", ret_mech_elect_ltvs_pur);
                    editor.putString("KEY_ltvs_pur_othe", ret_mech_elect_ltvs_pur_other);

                    editor.putString("KEY_stater", ret_mech_elect_stater);
                    editor.putString("KEY_alter", ret_mech_elect_alter);
                    editor.putString("KEY_wiper", ret_mech_elect_wiper);
                    editor.putString("KEY_noofstaff", ret_mech_elect_noofstaff);

                    editor.putString("KEY_spec_in", ret_mech_elect_spec_in);
                    editor.putString("KEY_stock", ret_mech_elect_stock);
                    editor.putString("KEY_spec_inother", ret_mech_elect_spec_inother);
                    editor.putString("KEY_vehical", ret_mech_elect_vehical);
                    editor.putString("KEY_dels_oebrands_other", ret_mech_elect_dels_oebrands_other);
                    editor.putString("KEY_pros_dels_other", ret_mech_elect_pros_dels_other);

                    editor.apply();
                    startActivity(new Intent(Shop.this, Capture.class));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

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
