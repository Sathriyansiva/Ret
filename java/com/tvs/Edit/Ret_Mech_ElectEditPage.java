package com.tvs.Edit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.tvs.Activity.ViewAll;
import com.tvs.R;
import com.tvs.Split.Persional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Model.EditPageModel.UpdateModel;
import Model.Predictive.Citypredictive;
import Model.Predictive.StatePridictive;
import RetroClient.RetroClient;
import Shared.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ret_Mech_ElectEditPage extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String[] Statear = {"Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh",
            "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
            "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
            "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",};
    private int StatearValue;
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
    private String[] MarketAr = {"DOMESTIC", "EXPORT"};
    private int MarketValue;
    private String[] Spec_In = {"ENGINE", "CHASIS", "GEAR BOX", "BARAKE", "BODAY PARTS", "OTHERS"};
    private int Spec_InValue;
    private String[] StockAr = {"YES", "NO"};
    private int StockValue;
    private String[] VehicalAttend = {"0-50 VEHICLE", "50-100 VEHICLE", "100-200 VEHICLE", "200 & above VEHICLE"};
    private int VehicalAttendValue;
    private String[] No_OffStaff = {"0-5 STAFF", "5-10 STAFF", "10 & above STAFF"};
    private int No_OffStaffValue;
    EditText on_name,
            shop_name, door_no, street, area, landmark,  state, country, pincode, Mob1, Mob2, ed_Email,
            Gst, hw_old, Organisation, partnername, Market, segments, productdeals, monthly_turn, MajorBrand,
            majorbrandother, deals_oe, ltvs_purchase, Ltvs_pur_other, Stater, Alter, Wiper, Noofstaff, specin, specin_othr,
            Stock, vechicle, ed_typ;
    private String id_pos, User_Name;
    private String Ownername, Shopname, DoorNo, Street, Area, Landmark, City, State, National, Pincode, Mobile1, Mobile2,
            Email, GstNo, lati, longi;
    private String type_pos, hwold_shop, seg_detls, pros_dels, majr_branddetls, dels_oebrands, ltvs_pur, part,
            monthly, stater, alter, wiper,
            noofstaff, spec_in, stock, vehical, market, major_branddetls_other, ltvs_pur_other, partname, image,
            image2, image3, spec_in_other, captureloc, pros_dels_other, dels_oebrands_other;

    private String Ownername_ed, Shopname_ed, DoorNo_ed, Street_ed, Area_ed, Landmark_ed, City_ed, State_ed,
            National_ed, Pincode_ed, Mobile1_ed, Mobile2_ed, Email_ed, GstNo_ed, lati_ed, longi_ed, pros_dels_other_ed, dels_oebrands_other_ed;

    TextView tv_locion;

    String saveloc, ret_mech_elect_type, ret_mech_elect_hwold_shop, ret_mech_elect_seg_detls, ret_mech_elect_pros_dels, ret_mech_elect_majr_branddetls,
            ret_mech_elect_dels_oebrands, ret_mech_elect_ltvs_pur, ret_mech_elect_part, ret_mech_elect_monthly,
            ret_mech_elect_stater, ret_mech_elect_alter, ret_mech_elect_wiper, ret_mech_elect_noofstaff,
            ret_mech_elect_spec_in, ret_mech_elect_stock, ret_mech_elect_vehical,
            ret_mech_elect_market, ret_mech_majr_elect_branddetls_other, ret_mech_elect_ltvs_pur_other, ret_mech_elect_partname, ret_mech_elect_spec_inother;
    AlertDialog.Builder builder;
    ImageView con_iv, con_iv2, con_iv3;
    EditText ed_lati, ed_longi, productdeals_other, deals_oe_other;
    LinearLayout ll_ret_mech_elect, ll_product, ll_monthly, ll_major, ll_majorother, ll_dealsoe, ll_ltvspur, ll_ltvspurother,
            ll_stater, ll_alter, ll_wiper, ll_staff, ll_special, ll_specialother, ll_maintaining, ll_vehicle, ll_productdeals_other, ll_deals_oe_other;
    LinearLayout ll_parname;
    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    String userChoosenTask;
    private int REQUEST_CAMERA = 1888, SELECT_FILE = 1;
    private Bitmap bitmap, bitmap2, bitmap3;
    String fileName;
    String edimage, edimage2, edvisitingcard;
    BitmapFactory.Options bfo, bfo2, bfo3;
    ByteArrayOutputStream bao, bao2, bao3;
    BitmapDrawable drawable, drawable2, drawable3;
    int iv_pos;
    int PLACE_PICKER_REQUEST = 1;
    String CaptureLocation;
    String LAT, LONG;
    AutoCompleteTextView city;
    List<String> Cityarray;
    //LinearLayout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
        ll_ret_mech_elect = (LinearLayout) findViewById(R.id.ll_ret_mech_elect);
        sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Cityarray = new ArrayList<String>();

        ll_product = (LinearLayout) findViewById(R.id.product);
        ll_monthly = (LinearLayout) findViewById(R.id.monthly);
        ll_major = (LinearLayout) findViewById(R.id.major);
        ll_majorother = (LinearLayout) findViewById(R.id.majr_branddetlsother);
        ll_dealsoe = (LinearLayout) findViewById(R.id.dealtoe);
        ll_ltvspur = (LinearLayout) findViewById(R.id.ltvs_pur);
        ll_ltvspurother = (LinearLayout) findViewById(R.id.ltvs_purother);
        ll_stater = (LinearLayout) findViewById(R.id.statermotor);
        ll_alter = (LinearLayout) findViewById(R.id.alternator);
        ll_wiper = (LinearLayout) findViewById(R.id.wiper);
        ll_staff = (LinearLayout) findViewById(R.id.staff);
        ll_special = (LinearLayout) findViewById(R.id.specialist);
        ll_specialother = (LinearLayout) findViewById(R.id.specialistother);
        ll_maintaining = (LinearLayout) findViewById(R.id.stock);
        ll_vehicle = (LinearLayout) findViewById(R.id.vehicle_attend);
        ll_parname = (LinearLayout) findViewById(R.id.ll_ret_mech_elect_partner);
        ll_productdeals_other = (LinearLayout) findViewById(R.id.product_other);
        ll_deals_oe_other = (LinearLayout) findViewById(R.id.dealswithoe_other);

        tv_locion = (TextView) findViewById(R.id.tv_loc);


        productdeals_other = (EditText) findViewById(R.id.con_pros_dels_other);
        deals_oe_other = (EditText) findViewById(R.id.con_dels_oebrands_other);
        ed_lati = (EditText) findViewById(R.id.con_lat);
        ed_longi = (EditText) findViewById(R.id.con_long);
        con_iv = (ImageView) findViewById(R.id.con_image);
        con_iv2 = (ImageView) findViewById(R.id.con_image2);
        con_iv3 = (ImageView) findViewById(R.id.con_image3);
        on_name = (EditText) findViewById(R.id.con_ownname);
        shop_name = (EditText) findViewById(R.id.con_shopname);
        door_no = (EditText) findViewById(R.id.con_door_no);
        street = (EditText) findViewById(R.id.con_street);
        area = (EditText) findViewById(R.id.con_area);
        landmark = (EditText) findViewById(R.id.con_landmark);
        city = (AutoCompleteTextView) findViewById(R.id.con_city);
        state = (EditText) findViewById(R.id.con_satet);
        country = (EditText) findViewById(R.id.con_national);
        pincode = (EditText) findViewById(R.id.con_pincode);
        Mob1 = (EditText) findViewById(R.id.con_mob1);
        Mob2 = (EditText) findViewById(R.id.con_mob2);
        ed_Email = (EditText) findViewById(R.id.con_email);
        Gst = (EditText) findViewById(R.id.con_gst);
        hw_old = (EditText) findViewById(R.id.con_hwold_shop);
        Organisation = (EditText) findViewById(R.id.con_part);
        partnername = (EditText) findViewById(R.id.con_partname);

        Market = (EditText) findViewById(R.id.con_market);
        segments = (EditText) findViewById(R.id.con_seg_detls);
        productdeals = (EditText) findViewById(R.id.con_pros_dels);
        monthly_turn = (EditText) findViewById(R.id.con_monthly);
        MajorBrand = (EditText) findViewById(R.id.con_majr_branddetls);
        majorbrandother = (EditText) findViewById(R.id.con_branddetlsother);

        deals_oe = (EditText) findViewById(R.id.con_dels_oebrands);
        ltvs_purchase = (EditText) findViewById(R.id.con_ltvs_pur);
        Ltvs_pur_other = (EditText) findViewById(R.id.con_ltvs_pur_other);
        Stater = (EditText) findViewById(R.id.con_starter);
        Alter = (EditText) findViewById(R.id.con_alter);
        Wiper = (EditText) findViewById(R.id.con_wiper);
        Noofstaff = (EditText) findViewById(R.id.con_noofstaf);
        specin = (EditText) findViewById(R.id.con_spec_in);
        specin_othr = (EditText) findViewById(R.id.con_spec_in_other);
        Stock = (EditText) findViewById(R.id.con_stock);
        vechicle = (EditText) findViewById(R.id.con_vehical);
        ed_typ = (EditText) findViewById(R.id.con_type);

        on_name.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        shop_name.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        door_no.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        street.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        area.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        landmark.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        city.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        state.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        country.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Gst.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        productdeals_other.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        deals_oe_other.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        partnername.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        majorbrandother.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Ltvs_pur_other.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        specin_othr.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        getcity();
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city.setText("");
            }

        });
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Statedata();
            }
        });

        id_pos = sharedPreferences.getString("KEY_VA_id_pos", "");
        Ownername = sharedPreferences.getString("KEY_VA_Ownername", "");
        Shopname = sharedPreferences.getString("KEY_VA_Shopname", "");
        DoorNo = sharedPreferences.getString("KEY_VA_DoorNo", "");
        Street = sharedPreferences.getString("KEY_VA_Street", "");
        Area = sharedPreferences.getString("KEY_VA_Area", "");
        Landmark = sharedPreferences.getString("KEY_VA_Landmark", "");
        City = sharedPreferences.getString("KEY_VA_City", "");
        State = sharedPreferences.getString("KEY_VA_State", "");
        National = sharedPreferences.getString("KEY_VA_National", "");
        Pincode = sharedPreferences.getString("KEY_VA_Pincode", "");
        Mobile1 = sharedPreferences.getString("KEY_VA_Mobile1", "");
        Mobile2 = sharedPreferences.getString("KEY_VA_Mobile2", "");
        Email = sharedPreferences.getString("KEY_VA_Email", "");
        GstNo = sharedPreferences.getString("KEY_VA_GstNo", "");
        type_pos = sharedPreferences.getString("KEY_VA_type_pos", "");
        hwold_shop = sharedPreferences.getString("KEY_VA_hwold_shop", "");
        part = sharedPreferences.getString("KEY_VA_part", "");
        partname = sharedPreferences.getString("KEY_VA_partname", "");
        seg_detls = sharedPreferences.getString("KEY_VA_seg_detls", "");
        Ownername = sharedPreferences.getString("KEY_VA_Ownername", "");
        pros_dels = sharedPreferences.getString("KEY_VA_pros_dels", "");
        majr_branddetls = sharedPreferences.getString("KEY_VA_majr_branddetls", "");
        major_branddetls_other = sharedPreferences.getString("KEY_VA_major_branddetls_other", "");
        dels_oebrands = sharedPreferences.getString("KEY_VA_dels_oebrands", "");
        ltvs_pur = sharedPreferences.getString("KEY_VA_ltvs_pur", "");
        ltvs_pur_other = sharedPreferences.getString("KEY_VA_ltvs_pur_other", "");
        monthly = sharedPreferences.getString("KEY_VA_monthly", "");
        stater = sharedPreferences.getString("KEY_VA_stater", "");
        alter = sharedPreferences.getString("KEY_VA_alter", "");
        wiper = sharedPreferences.getString("KEY_VA_wiper", "");
        noofstaff = sharedPreferences.getString("KEY_VA_noofstaff", "");
        spec_in = sharedPreferences.getString("KEY_VA_spec_in", "");
        spec_in_other = sharedPreferences.getString("KEY_VA_spec_in_other", "");
        captureloc = sharedPreferences.getString("KEY_VA_captureloc", "");
        stock = sharedPreferences.getString("KEY_VA_stock", "");
        vehical = sharedPreferences.getString("KEY_VA_vehical", "");
        market = sharedPreferences.getString("KEY_VA_market", "");
        image = sharedPreferences.getString("KEY_VA_image", "");
        image2 = sharedPreferences.getString("KEY_VA_shopimage2", "");
        image3 = sharedPreferences.getString("KEY_VA_visitingimage3", "");

        LAT = sharedPreferences.getString("KEY_VA_lati", "");
        LONG = sharedPreferences.getString("KEY_VA_longi", "");
        pros_dels_other = sharedPreferences.getString("KEY_VA_pros_dels_other", "");
        dels_oebrands_other = sharedPreferences.getString("KEY_VA_dels_oebrands_other", "");


        if (type_pos.equals("RETAILER")) {


            ll_stater.setVisibility(View.GONE);
            ll_alter.setVisibility(View.GONE);
            ll_wiper.setVisibility(View.GONE);
            ll_staff.setVisibility(View.GONE);
            ll_special.setVisibility(View.GONE);
            ll_specialother.setVisibility(View.GONE);
            ll_maintaining.setVisibility(View.GONE);
            ll_vehicle.setVisibility(View.GONE);
            if (!part.equals("PARTNER")) {
                ll_parname.setVisibility(View.GONE);
            }

            if (!majr_branddetls.equals("OTHER MAKES")) {
                ll_majorother.setVisibility(View.GONE);
            }
            if (!ltvs_pur.equals("OTHERS")) {
                ll_ltvspurother.setVisibility(View.GONE);
            }
            if (!pros_dels.equals("OTHERS")) {
                ll_productdeals_other.setVisibility(View.GONE);
            }
            if (!dels_oebrands.equals("OTHERS")) {
                ll_deals_oe_other.setVisibility(View.GONE);
            }

        } else if (type_pos.equals("MECHANIC")) {
            if (!part.equals("PARTNER")) {
                ll_parname.setVisibility(View.GONE);
            }
            if (!spec_in.equals("OTHERS")) {
                ll_specialother.setVisibility(View.GONE);
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
            ll_productdeals_other.setVisibility(View.GONE);
            ll_deals_oe_other.setVisibility(View.GONE);
        } else if (type_pos.equals("ELECTRICIAN")) {
            if (!part.equals("PARTNER")) {
                ll_parname.setVisibility(View.GONE);
            }
            if (!majr_branddetls.equals("OTHER MAKES")) {
                ll_majorother.setVisibility(View.GONE);
            }
            if (!pros_dels.equals("OTHERS")) {
                ll_productdeals_other.setVisibility(View.GONE);
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
        } else if (type_pos.equals("RETAILER,MECHANIC")) {
            if (!part.equals("PARTNER")) {
                ll_parname.setVisibility(View.GONE);
            }
            if (!majr_branddetls.equals("OTHER MAKES")) {
                ll_majorother.setVisibility(View.GONE);
            }
            if (!ltvs_pur.equals("OTHERS")) {
                ll_ltvspurother.setVisibility(View.GONE);
            }
            if (!spec_in.equals("OTHERS")) {
                ll_specialother.setVisibility(View.GONE);
            }
            if (!pros_dels.equals("OTHERS")) {
                ll_productdeals_other.setVisibility(View.GONE);
            }
            if (!dels_oebrands.equals("OTHERS")) {
                ll_deals_oe_other.setVisibility(View.GONE);
            }

            ll_alter.setVisibility(View.GONE);
            ll_wiper.setVisibility(View.GONE);
            ll_staff.setVisibility(View.GONE);
        } else if (type_pos.equals("MECHANIC,ELECTRICIAN")) {
            if (!part.equals("PARTNER")) {
                ll_parname.setVisibility(View.GONE);
            }
            if (!majr_branddetls.equals("OTHER MAKES")) {
                ll_majorother.setVisibility(View.GONE);
            }
            if (!spec_in.equals("OTHERS")) {
                ll_specialother.setVisibility(View.GONE);
            }
            ll_productdeals_other.setVisibility(View.GONE);
            ll_dealsoe.setVisibility(View.GONE);
            ll_ltvspur.setVisibility(View.GONE);
            ll_ltvspurother.setVisibility(View.GONE);

        } else if (type_pos.equals("RETAILER,ELECTRICIAN")) {
            if (!part.equals("PARTNER")) {
                ll_parname.setVisibility(View.GONE);
            }
            if (!majr_branddetls.equals("OTHER MAKES")) {
                ll_majorother.setVisibility(View.GONE);
            }
            if (!ltvs_pur.equals("OTHERS")) {
                ll_ltvspurother.setVisibility(View.GONE);
            }
            if (!pros_dels.equals("OTHERS")) {
                ll_productdeals_other.setVisibility(View.GONE);
            }
            if (!dels_oebrands.equals("OTHERS")) {
                ll_deals_oe_other.setVisibility(View.GONE);
            }

            ll_special.setVisibility(View.GONE);
            ll_specialother.setVisibility(View.GONE);
            ll_vehicle.setVisibility(View.GONE);
        } else if (type_pos.equals("RETAILER,MECHANIC,ELECTRICIAN")) {
            if (!part.equals("PARTNER")) {
                ll_parname.setVisibility(View.GONE);
            }
            if (!majr_branddetls.equals("OTHER MAKES")) {
                ll_majorother.setVisibility(View.GONE);
            }
            if (!ltvs_pur.equals("OTHERS")) {
                ll_ltvspurother.setVisibility(View.GONE);
            }
            if (!spec_in.equals("OTHERS")) {
                ll_specialother.setVisibility(View.GONE);
            }
            if (!pros_dels.equals("OTHERS")) {
                ll_productdeals_other.setVisibility(View.GONE);
            }
            if (!dels_oebrands.equals("OTHERS")) {
                ll_deals_oe_other.setVisibility(View.GONE);
            }

        }
        productdeals_other.setText(pros_dels_other);
        deals_oe_other.setText(dels_oebrands_other);
        if (!image.equals("")) {
            byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
            Glide.with(Ret_Mech_ElectEditPage.this)
                    .load(imageAsBytes)
                    .into(con_iv);
        } else {
            con_iv.setImageResource(R.drawable.soft);
        }
        if (!image2.equals("")) {
            byte[] imageAsBytes = Base64.decode(image2.getBytes(), Base64.DEFAULT);
            Glide.with(Ret_Mech_ElectEditPage.this)
                    .load(imageAsBytes)
                    .into(con_iv2);
        } else {
            con_iv2.setImageResource(R.drawable.soft);
        }
        if (!image3.equals("")) {
            byte[] imageAsBytes = Base64.decode(image3.getBytes(), Base64.DEFAULT);
            Glide.with(Ret_Mech_ElectEditPage.this)
                    .load(imageAsBytes)
                    .into(con_iv3);
        } else {
            con_iv3.setImageResource(R.drawable.soft);
        }
        ed_lati.setText(LAT);
        ed_longi.setText(LONG);
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
        ed_typ.setText(type_pos);
        tv_locion.setText(captureloc);
        hw_old.setText(hwold_shop);
        Organisation.setText(part);
        partnername.setText(partname);
        Market.setText(market);
        segments.setText(seg_detls);
        productdeals.setText(pros_dels);
        monthly_turn.setText(monthly);
        MajorBrand.setText(majr_branddetls);
        majorbrandother.setText(major_branddetls_other);
        deals_oe.setText(dels_oebrands);
        ltvs_purchase.setText(ltvs_pur);
        Ltvs_pur_other.setText(ltvs_pur_other);

        specin.setText(spec_in);
        specin_othr.setText(spec_in_other);
        Stock.setText(stock);
        vechicle.setText(vehical);
        Stater.setText(stater);
        Alter.setText(alter);
        Wiper.setText(wiper);
        Noofstaff.setText(noofstaff);

//        state.setOnClickListener(new View.OnClickListener() {
////        ed_reg_Pincode.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
////        ed_reg_Mobile1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
////        ed_reg_Mobile2.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
////        ed_reg_Email.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//
//            @Override
//            public void onClick(View v) {
//                builder.setItems(Statear, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        state.setText(Statear[which]);
//                        StatearValue = which + 1;
//                        State_ed = state.getText().toString();
//
//                    }
//                });
//                builder.show();
//            }
//        });
        getcity();
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city.setText("");
            }

        });
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Statedata();
            }
        });
        ed_typ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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

                                    ed_typ.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_type = ed_typ.getText().toString().trim();

                                } else {

                                    ed_typ.setText(stringBuilder);
                                    ret_mech_elect_type = ed_typ.getText().toString().trim();
                                    if (ret_mech_elect_type.equals("RETAILER")) {

                                        ll_product.setVisibility(View.VISIBLE);
                                        ll_monthly.setVisibility(View.VISIBLE);
                                        ll_major.setVisibility(View.VISIBLE);
                                        ll_dealsoe.setVisibility(View.VISIBLE);
                                        ll_ltvspur.setVisibility(View.VISIBLE);
                                        ll_ltvspurother.setVisibility(View.VISIBLE);
                                        ll_productdeals_other.setVisibility(View.VISIBLE);
                                        ll_deals_oe_other.setVisibility(View.VISIBLE);

                                        ll_stater.setVisibility(View.GONE);
                                        ll_alter.setVisibility(View.GONE);
                                        ll_wiper.setVisibility(View.GONE);
                                        ll_staff.setVisibility(View.GONE);
                                        ll_special.setVisibility(View.GONE);
                                        ll_maintaining.setVisibility(View.GONE);
                                        ll_vehicle.setVisibility(View.GONE);
                                    } else if (ret_mech_elect_type.equals("MECHANIC")) {

                                        ll_monthly.setVisibility(View.VISIBLE);
                                        ll_special.setVisibility(View.VISIBLE);
                                        ll_maintaining.setVisibility(View.VISIBLE);
                                        ll_vehicle.setVisibility(View.VISIBLE);

                                        ll_productdeals_other.setVisibility(View.GONE);
                                        ll_deals_oe_other.setVisibility(View.GONE);
                                        ll_stater.setVisibility(View.GONE);
                                        ll_alter.setVisibility(View.GONE);
                                        ll_wiper.setVisibility(View.GONE);
                                        ll_staff.setVisibility(View.GONE);
                                        ll_product.setVisibility(View.GONE);
                                        ll_major.setVisibility(View.GONE);
//                                        ll_majorother.setVisibility(View.GONE);
                                        ll_dealsoe.setVisibility(View.GONE);
                                        ll_ltvspur.setVisibility(View.GONE);
                                        ll_ltvspurother.setVisibility(View.GONE);
                                    } else if (ret_mech_elect_type.equals("ELECTRICIAN")) {

                                        ll_stater.setVisibility(View.VISIBLE);
                                        ll_alter.setVisibility(View.VISIBLE);
                                        ll_wiper.setVisibility(View.VISIBLE);
                                        ll_staff.setVisibility(View.VISIBLE);
                                        ll_product.setVisibility(View.VISIBLE);
                                        ll_major.setVisibility(View.VISIBLE);
                                        ll_productdeals_other.setVisibility(View.VISIBLE);

                                        ll_deals_oe_other.setVisibility(View.GONE);
                                        ll_monthly.setVisibility(View.GONE);
                                        ll_dealsoe.setVisibility(View.GONE);
                                        ll_ltvspur.setVisibility(View.GONE);
                                        ll_ltvspurother.setVisibility(View.GONE);
                                        ll_special.setVisibility(View.GONE);
                                        ll_maintaining.setVisibility(View.GONE);
                                        ll_vehicle.setVisibility(View.GONE);
                                    } else if (ret_mech_elect_type.equals("RETAILER,MECHANIC")) {

                                        ll_product.setVisibility(View.VISIBLE);
                                        ll_monthly.setVisibility(View.VISIBLE);
                                        ll_major.setVisibility(View.VISIBLE);
                                        ll_dealsoe.setVisibility(View.VISIBLE);
                                        ll_ltvspur.setVisibility(View.VISIBLE);
                                        ll_special.setVisibility(View.VISIBLE);
                                        ll_maintaining.setVisibility(View.VISIBLE);
                                        ll_vehicle.setVisibility(View.VISIBLE);
                                        ll_productdeals_other.setVisibility(View.VISIBLE);
                                        ll_deals_oe_other.setVisibility(View.VISIBLE);


                                        ll_stater.setVisibility(View.GONE);
                                        ll_alter.setVisibility(View.GONE);
                                        ll_wiper.setVisibility(View.GONE);
                                        ll_staff.setVisibility(View.GONE);
                                    } else if (ret_mech_elect_type.equals("MECHANIC,ELECTRICIAN")) {

                                        ll_special.setVisibility(View.VISIBLE);
                                        ll_monthly.setVisibility(View.VISIBLE);
                                        ll_maintaining.setVisibility(View.VISIBLE);
                                        ll_vehicle.setVisibility(View.VISIBLE);
                                        ll_stater.setVisibility(View.VISIBLE);
                                        ll_alter.setVisibility(View.VISIBLE);
                                        ll_wiper.setVisibility(View.VISIBLE);
                                        ll_staff.setVisibility(View.VISIBLE);
                                        ll_product.setVisibility(View.VISIBLE);
                                        ll_major.setVisibility(View.VISIBLE);

                                        ll_dealsoe.setVisibility(View.GONE);
                                        ll_ltvspur.setVisibility(View.GONE);
                                        ll_productdeals_other.setVisibility(View.VISIBLE);
                                        ll_deals_oe_other.setVisibility(View.GONE);
                                        ll_ltvspurother.setVisibility(View.GONE);

                                    } else if (ret_mech_elect_type.equals("RETAILER,ELECTRICIAN")) {

                                        ll_product.setVisibility(View.VISIBLE);
                                        ll_monthly.setVisibility(View.VISIBLE);
                                        ll_major.setVisibility(View.VISIBLE);
                                        ll_dealsoe.setVisibility(View.VISIBLE);
                                        ll_ltvspur.setVisibility(View.VISIBLE);
                                        ll_stater.setVisibility(View.VISIBLE);
                                        ll_alter.setVisibility(View.VISIBLE);
                                        ll_wiper.setVisibility(View.VISIBLE);
                                        ll_staff.setVisibility(View.VISIBLE);
                                        ll_productdeals_other.setVisibility(View.VISIBLE);
                                        ll_deals_oe_other.setVisibility(View.VISIBLE);

                                        ll_special.setVisibility(View.GONE);
                                        ll_vehicle.setVisibility(View.GONE);
                                    } else if (ret_mech_elect_type.equals("RETAILER,MECHANIC,ELECTRICIAN")) {
                                        ll_product.setVisibility(View.VISIBLE);
                                        ll_monthly.setVisibility(View.VISIBLE);
                                        ll_major.setVisibility(View.VISIBLE);
                                        ll_dealsoe.setVisibility(View.VISIBLE);
                                        ll_ltvspur.setVisibility(View.VISIBLE);

                                        ll_special.setVisibility(View.VISIBLE);
                                        ll_maintaining.setVisibility(View.VISIBLE);
                                        ll_vehicle.setVisibility(View.VISIBLE);

                                        ll_stater.setVisibility(View.VISIBLE);
                                        ll_alter.setVisibility(View.VISIBLE);
                                        ll_wiper.setVisibility(View.VISIBLE);
                                        ll_staff.setVisibility(View.VISIBLE);

                                        ll_productdeals_other.setVisibility(View.VISIBLE);
                                        ll_deals_oe_other.setVisibility(View.VISIBLE);
                                    }


                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ed_typ.setText("");
                                ret_mech_elect_type = ed_typ.getText().toString().trim();
                                Toast.makeText(Ret_Mech_ElectEditPage.this, "Please Select User_Type", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        vechicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vechicle.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_vehical = vechicle.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        specin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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

                                    specin.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_spec_in = specin.getText().toString().trim();


                                } else {

                                    specin.setText(stringBuilder);
                                    ret_mech_elect_spec_in = specin.getText().toString().trim();
                                    if (!ret_mech_elect_spec_in.equals("OTHERS")) {
                                        ll_specialother.setVisibility(View.GONE);
                                    } else {
                                        ll_specialother.setVisibility(View.VISIBLE);

                                    }
                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                specin.setText("");
                                ret_mech_elect_spec_in = specin.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(StockAr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Stock.setText(StockAr[which]);
                        StockValue = which + 1;
                        ret_mech_elect_stock = Stock.getText().toString().trim();


                    }
                });
                builder.show();
            }
        });
        Stater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Stater.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_stater = Stater.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        Alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Alter.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_alter = Alter.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        Wiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(VehicalAttend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Wiper.setText(VehicalAttend[which]);
                        VehicalAttendValue = which + 1;
                        ret_mech_elect_wiper = Wiper.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        Noofstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(No_OffStaff, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Noofstaff.setText(No_OffStaff[which]);
                        No_OffStaffValue = which + 1;
                        ret_mech_elect_noofstaff = Noofstaff.getText().toString().trim();
                    }
                });
                builder.show();
            }
        });
        Market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MarketAr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Market.setText(MarketAr[which]);
                        MarketValue = which + 1;
                        ret_mech_elect_market = Market.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        Organisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(Partner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Organisation.setText(Partner[which]);

                        PartnerValue = which + 1;
                        ret_mech_elect_part = Organisation.getText().toString().trim();
                        if (!ret_mech_elect_part.equals("PARTNER")) {
                            ll_parname.setVisibility(View.GONE);
                        } else {
                            ll_parname.setVisibility(View.VISIBLE);
                        }

                    }
                });
                builder.show();
            }
        });

        monthly_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setItems(MonthlyTurnOver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        monthly_turn.setText(MonthlyTurnOver[which]);
                        MonthlyTurnOverValue = which + 1;
                        ret_mech_elect_monthly = monthly_turn.getText().toString().trim();

                    }
                });
                builder.show();
            }
        });
        hw_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.setTitle("Select The Person");
                builder.setItems(Hw_Oldshop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hw_old.setText(Hw_Oldshop[which]);
                        Hw_OldshopValue = which + 1;
                        ret_mech_elect_hwold_shop = hw_old.getText().toString().trim();

                    }
                });
                builder.show();

            }
        });

        segments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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

                                    segments.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_seg_detls = segments.getText().toString().trim();

                                } else {

                                    segments.setText(stringBuilder);
                                    if (segments.getText().toString().trim().equals("ALL")) {
                                        segments.setText("TWO WHEELER,THREE WHEELER,TRACTOR,CAR,UV,LCV,HCV,ENGINES,EARTH MOVERS,MINING,MARINE,GENSETS");
                                    }
                                    ret_mech_elect_seg_detls = segments.getText().toString().trim();

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                segments.setText("");
                                ret_mech_elect_seg_detls = segments.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        productdeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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

                                    productdeals.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_pros_dels = productdeals.getText().toString().trim();

                                } else {

                                    productdeals.setText(stringBuilder);
                                    ret_mech_elect_pros_dels = productdeals.getText().toString().trim();
                                    if (!ret_mech_elect_pros_dels.equals("OTHERS")) {
                                        ll_productdeals_other.setVisibility(View.GONE);
                                    } else {
                                        ll_productdeals_other.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                productdeals.setText("");
                                ret_mech_elect_pros_dels = productdeals.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        MajorBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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

                                    MajorBrand.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_majr_branddetls = MajorBrand.getText().toString().trim();

                                } else {

                                    MajorBrand.setText(stringBuilder);

                                    ret_mech_elect_majr_branddetls = MajorBrand.getText().toString().trim();
                                    if (!ret_mech_elect_majr_branddetls.equals("OTHER MAKES")) {
                                        ll_majorother.setVisibility(View.GONE);
                                    } else {
                                        ll_majorother.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MajorBrand.setText("");
                                ret_mech_elect_majr_branddetls = MajorBrand.getText().toString().trim();


                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        deals_oe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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

                                    deals_oe.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_dels_oebrands = deals_oe.getText().toString().trim();

                                } else {

                                    deals_oe.setText(stringBuilder);
                                    ret_mech_elect_dels_oebrands = deals_oe.getText().toString().trim();
                                    if (!ret_mech_elect_dels_oebrands.equals("OTHERS")) {
                                        ll_deals_oe_other.setVisibility(View.GONE);
                                    } else {
                                        ll_deals_oe_other.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deals_oe.setText("");
                                ret_mech_elect_dels_oebrands = deals_oe.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ltvs_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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

                                    ltvs_purchase.setText("");
                                    stringBuilder.setLength(0);
                                    ret_mech_elect_ltvs_pur = ltvs_purchase.getText().toString().trim();

                                } else {

                                    ltvs_purchase.setText(stringBuilder);

                                    ret_mech_elect_ltvs_pur = ltvs_purchase.getText().toString().trim();
                                    if (!ret_mech_elect_ltvs_pur.equals("OTHERS")) {
                                        ll_ltvspurother.setVisibility(View.GONE);
                                    } else {
                                        ll_ltvspurother.setVisibility(View.VISIBLE);

                                    }
                                }
                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ltvs_purchase.setText("");
                                ret_mech_elect_ltvs_pur = ltvs_purchase.getText().toString().trim();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
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
                        ArrayAdapter adapter = new ArrayAdapter(Ret_Mech_ElectEditPage.this, android.R.layout.simple_list_item_1, Cityarray);
                        city.setAdapter(adapter);
                        City = city.getText().toString();

                    } else {
                        Toast.makeText(Ret_Mech_ElectEditPage.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Citypredictive> call, Throwable t) {
                    Toast.makeText(Ret_Mech_ElectEditPage.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Statedata() {
        try {
            City = city.getText().toString();
            CategoryAPI service = RetroClient.getApiService();

            Call<StatePridictive> call = service.getstate(City);
            call.enqueue(new Callback<StatePridictive>() {
                @Override
                public void onResponse(Call<StatePridictive> call, Response<StatePridictive> response) {
                    if (response.body().getResult().equals("Success")) {
                        State = response.body().getData();
                       state.setText(State);
                    } else {


                        Toast.makeText(Ret_Mech_ElectEditPage.this, "Sorry No Data Found", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<StatePridictive> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    public void locaioncap(View view) {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(Ret_Mech_ElectEditPage.this), PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            Toast.makeText(Ret_Mech_ElectEditPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Toast.makeText(Ret_Mech_ElectEditPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Ret_Mech_ElectEditPage.this);
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
                        ed_lati.setText(LAT);
                        ed_longi.setText(LONG);
                        tv_locion.setText(CaptureLocation);
                        Toast.makeText(this, CaptureLocation, Toast.LENGTH_LONG).show();

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
                con_iv.setImageBitmap(bitmap);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable = (BitmapDrawable) con_iv.getDrawable();
                bitmap = drawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();
                edimage = Base64.encodeToString(ba, Base64.DEFAULT);

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

                con_iv2.setImageBitmap(bitmap2);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable2 = (BitmapDrawable) con_iv2.getDrawable();
                bitmap2 = drawable2.getBitmap();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba2 = bao.toByteArray();
                edimage2 = Base64.encodeToString(ba2, Base64.DEFAULT);

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
                con_iv3.setImageBitmap(bitmap3);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable3 = (BitmapDrawable) con_iv3.getDrawable();
                bitmap3 = drawable3.getBitmap();
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba3 = bao.toByteArray();
                edvisitingcard = Base64.encodeToString(ba3, Base64.DEFAULT);

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

                con_iv.setImageBitmap(bitmap);
                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();
                drawable = (BitmapDrawable) con_iv.getDrawable();
                bitmap = drawable.getBitmap();


                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba = bao.toByteArray();


                edimage = Base64.encodeToString(ba, Base64.DEFAULT);


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
                con_iv2.setImageBitmap(bitmap2);

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;

                bao = new ByteArrayOutputStream();
                drawable2 = (BitmapDrawable) con_iv2.getDrawable();
                bitmap2 = drawable2.getBitmap();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba2 = bao.toByteArray();
                edimage2 = Base64.encodeToString(ba2, Base64.DEFAULT);

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
                con_iv3.setImageBitmap(bitmap3);
                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                bao = new ByteArrayOutputStream();

                drawable3 = (BitmapDrawable) con_iv3.getDrawable();
                bitmap3 = drawable3.getBitmap();
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte[] ba3 = bao.toByteArray();
                edvisitingcard = Base64.encodeToString(ba3, Base64.DEFAULT);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Ep_update(View view) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
            con_iv.buildDrawingCache();
            Bitmap bmap = con_iv.getDrawingCache();
            bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 2;
            bao = new ByteArrayOutputStream();
//        drawable = (BitmapDrawable) con_iv.getDrawable();
//        bitmap = drawable.getBitmap();
            bmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            edimage = Base64.encodeToString(ba, Base64.DEFAULT);

            con_iv2.buildDrawingCache();
            Bitmap bmap2 = con_iv2.getDrawingCache();
            bao2 = new ByteArrayOutputStream();
//            drawable2 = (BitmapDrawable) con_iv2.getDrawable();
//            bitmap2 = drawable2.getBitmap();
            bmap2.compress(Bitmap.CompressFormat.JPEG, 90, bao2);
            byte[] ba2 = bao2.toByteArray();
            edimage2 = Base64.encodeToString(ba2, Base64.DEFAULT);

            con_iv3.buildDrawingCache();
            Bitmap bmap3 = con_iv3.getDrawingCache();
            bao3 = new ByteArrayOutputStream();
//            drawable3 = (BitmapDrawable) con_iv3.getDrawable();
//            bitmap3 = drawable3.getBitmap();
            bmap3.compress(Bitmap.CompressFormat.JPEG, 90, bao3);
            byte[] ba3 = bao3.toByteArray();
            edvisitingcard = Base64.encodeToString(ba3, Base64.DEFAULT);

            ret_mech_elect_type = ed_typ.getText().toString().trim();
            Ownername_ed = on_name.getText().toString().trim();
            Shopname_ed = shop_name.getText().toString().trim();
            DoorNo_ed = door_no.getText().toString().trim();
            Area_ed = area.getText().toString().trim();
            Street_ed = street.getText().toString().trim();
            Landmark_ed = landmark.getText().toString().trim();
            City_ed = city.getText().toString().trim();
            State_ed = state.getText().toString().trim();
            National_ed = country.getText().toString().trim();
            Pincode_ed = pincode.getText().toString().trim();
            Mobile1_ed = Mob1.getText().toString().trim();
            Mobile2_ed = Mob2.getText().toString().trim();
            Email_ed = ed_Email.getText().toString().trim();
            GstNo_ed = Gst.getText().toString().trim();
            LAT = ed_lati.getText().toString().trim();
            LONG = ed_longi.getText().toString().trim();

            if (ret_mech_elect_type.equals("RETAILER")) {


                specin.setText("");
                Stock.setText("");
                vechicle.setText("");

                Stater.setText("");
                Alter.setText("");
                Wiper.setText("");
                Noofstaff.setText("");

            } else if (ret_mech_elect_type.equals("MECHANIC")) {
                productdeals.setText("");
                MajorBrand.setText("");
                majorbrandother.setText("");
                deals_oe.setText("");
                ltvs_purchase.setText("");
                Ltvs_pur_other.setText("");

                Stater.setText("");
                Alter.setText("");
                Wiper.setText("");
                Noofstaff.setText("");

            } else if (ret_mech_elect_type.equals("ELECTRICIAN")) {
                monthly_turn.setText("");
                deals_oe.setText("");
                ltvs_purchase.setText("");
                Ltvs_pur_other.setText("");
                specin.setText("");
                Stock.setText("");
                vechicle.setText("");

            } else if (ret_mech_elect_type.equals("RETAILER,MECHANIC")) {


                Stater.setText("");
                Alter.setText("");
                Wiper.setText("");
                Noofstaff.setText("");


            } else if (ret_mech_elect_type.equals("MECHANIC,ELECTRICIAN")) {


                deals_oe.setText("");
                ltvs_purchase.setText("");
                Ltvs_pur_other.setText("");

            } else if (ret_mech_elect_type.equals("RETAILER,ELECTRICIAN")) {
                specin.setText("");
                Stock.setText("");
                vechicle.setText("");

            }

            ret_mech_elect_hwold_shop = hw_old.getText().toString().trim();
            ret_mech_elect_seg_detls = segments.getText().toString().trim();
            ret_mech_elect_pros_dels = productdeals.getText().toString().trim();
            ret_mech_elect_majr_branddetls = MajorBrand.getText().toString().trim();
            ret_mech_elect_dels_oebrands = deals_oe.getText().toString().trim();
            ret_mech_elect_ltvs_pur = ltvs_purchase.getText().toString().trim();
            ret_mech_elect_part = Organisation.getText().toString().trim();
            ret_mech_elect_monthly = monthly_turn.getText().toString().trim();
            ret_mech_elect_stater = Stater.getText().toString().trim();
            ret_mech_elect_alter = Alter.getText().toString().trim();
            ret_mech_elect_wiper = Wiper.getText().toString().trim();
            ret_mech_elect_noofstaff = Noofstaff.getText().toString().trim();
            ret_mech_elect_spec_in = specin.getText().toString().trim();
            ret_mech_elect_stock = Stock.getText().toString().trim();
            ret_mech_elect_vehical = vechicle.getText().toString().trim();
            ret_mech_elect_market = Market.getText().toString().trim();
            ret_mech_majr_elect_branddetls_other = majorbrandother.getText().toString().trim();
            ret_mech_elect_ltvs_pur_other = Ltvs_pur_other.getText().toString().trim();
            ret_mech_elect_partname = partnername.getText().toString().trim();
            ret_mech_elect_spec_inother = specin_othr.getText().toString().trim();
            saveloc = tv_locion.getText().toString().trim();

            pros_dels_other_ed = productdeals_other.getText().toString().trim();
            dels_oebrands_other_ed = deals_oe_other.getText().toString().trim();

            if (Ownername_ed.equals("")) {
                on_name.setError("Please Enter Owner name");
                Toast.makeText(this, "Please Enter Owner name", Toast.LENGTH_SHORT).show();
            } else if (Shopname_ed.equals("")) {
                Toast.makeText(this, "Please Enter Shop name", Toast.LENGTH_SHORT).show();
                shop_name.setError("Please Enter Shop name");
            } else if (DoorNo_ed.equals("")) {
                Toast.makeText(this, "Please Enter DoorNo", Toast.LENGTH_SHORT).show();
                door_no.setError("Please Enter DoorNo");
            } else if (Street_ed.equals("")) {
                street.setError("Please Enter Street");
                Toast.makeText(this, "Please Enter Street", Toast.LENGTH_SHORT).show();

            } else if (Area_ed.equals("")) {
                area.setError("Please Enter Area");
                Toast.makeText(this, "Please Enter Area", Toast.LENGTH_SHORT).show();

            } else if (City_ed.equals("")) {
                city.setError("Please Enter City");
                Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();

            } else if (State_ed.equals("")) {
                Toast.makeText(this, "Please Enter Citypredictive", Toast.LENGTH_SHORT).show();
//            ed_reg_State.setError("Please Enter Citypredictive");
            } else if (Pincode_ed.equals("") || Pincode.length() != 6) {
                pincode.setError("Please Enter Pincode");
                Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();

            } else if (Mobile1_ed.equals("") || Mobile1.length() != 10) {
                Mob1.setError("Please Enter Mobile Number");
                Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();

            } else if (Email_ed.equals("") || !Email_ed.matches(emailPattern)) {
                ed_Email.setError("Please Enter Email");
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

            } else if (GstNo_ed.equals("")) {
                Gst.setError("Please Enter GSt No.");
                Toast.makeText(this, "Please Enter GST No.", Toast.LENGTH_SHORT).show();

            } else if (ret_mech_elect_type.equals("")) {
//            ed_type.setError("Please Enter type");
                Toast.makeText(this, "Please Select User_Type", Toast.LENGTH_SHORT).show();

            }


//        else if (type_pos.equals("RETAILER,MECHANIC,ELECTRICIAN")) {
//
//        }

            else if (ret_mech_elect_type.equals("RETAILER")) {

                if (ret_mech_elect_hwold_shop.equals("")) {
//                ed_hwold_shop.setError("Please Enter ret_hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_part.equals("")) {
//                ed_part.setError("Please Enter ret_part");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }

//            else if (ret_part.equals("PARTNER")) {
//                if (ret_partname.equals("")) {
//                    ed_ret_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_elect_market.equals("")) {
//                ed_market.setError("Please Enter ret_market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_seg_detls.equals("")) {
//                ed_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_pros_dels.equals("")) {
//                ed_pros_dels.setError("Please Enter ret_pros_dels");
                    Toast.makeText(this, "Please Select Product Deals", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_monthly.equals("")) {
//                ed_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_majr_branddetls.equals("")) {
//                ed_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With Electrical", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_majr_branddetls.equals("OTHER MAKES")) {
//                if (ret_majr_branddetls_other.equals("")) {
//                    ed_ret_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_mech_elect_dels_oebrands.equals("")) {
//                ed_dels_oebrands.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Deals With Oe Brands", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_ltvs_pur.equals("")) {
//                ed_ltvs_pur.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select LTVS Purchase Deals With", Toast.LENGTH_SHORT).show();

                } else {
                    Edit_update();
                }
//            else if (ret_ltvs_pur.equals("OTHERS")) {
//                if (ret_ltvs_pur_other.equals("")) {
//                    ed_ret_ltvs_pur_other.setError("Please Enter ret_ltvs_pur_other");
//                }
//            }

            } else if (ret_mech_elect_type.equals("MECHANIC")) {
                if (ret_mech_elect_hwold_shop.equals("")) {
//                ed_mechhwold_shop.setError("Please Enter hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_part.equals("")) {
//                ed_mechpartner.setError("Please Enter partnername");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (mechpartner.equals("PARTNER")) {
//                if (mech_partname.equals("")) {
//                    ed_mech_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_elect_market.equals("")) {
//                ed_mech_market.setError("Please Enter market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_seg_detls.equals("")) {
                    Toast.makeText(this, "Please Select Segment ", Toast.LENGTH_SHORT).show();
//                ed_detls_in.setError("Please Enter ret_seg_detls");
                } else if (ret_mech_elect_spec_in.equals("")) {
//                ed_spec_in.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Specialist", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_monthly.equals("")) {
//                ed_mech_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_stock.equals("")) {
//                ed_stock.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Maintaining Stock", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_vehical.equals("")) {
//                ed_mech_vehical.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Vehicle Attend in a Month   ", Toast.LENGTH_SHORT).show();

                } else {
                    Edit_update();
                }

            } else if (ret_mech_elect_type.equals("ELECTRICIAN")) {

                if (ret_mech_elect_hwold_shop.equals("")) {
//                ed_elect_hwold_shop.setError("Please Enter hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_part.equals("")) {
//                ed_electpartner.setError("Please Enter partnername");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (electpartner.equals("PARTENER")) {
//                if (elect_partname.equals("")) {
//                    ed_elect_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_elect_market.equals("")) {
//                ed_elect_market.setError("Please Enter market");
                    Toast.makeText(this, "Please Select Market ", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_seg_detls.equals("")) {
//                ed_elect_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_pros_dels.equals("")) {
//                ed_elect_pros_dels.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Product Deals With", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_majr_branddetls.equals("")) {
//                ed_majr_elect_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (majr_elect_branddetls.equals("OTHER MAKES")) {
//                if (elect_majr_branddetls_other.equals("")) {
//                    ed_elect_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_mech_elect_stater.equals("")) {
//                ed_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Started Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_alter.equals("")) {
//                ed_elect_alter.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Alternator Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_wiper.equals("")) {
//                ed_elect_wiper.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Wiper Motor Serviced in a Month", Toast.LENGTH_SHORT).show();


                } else if (ret_mech_elect_noofstaff.equals("")) {
//                ed_elect_noofstaff.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select No Of Staff", Toast.LENGTH_SHORT).show();


                } else {
                    Edit_update();
                }

            } else if (ret_mech_elect_type.equals("RETAILER,MECHANIC")) {

                if (ret_mech_elect_hwold_shop.equals("")) {
//                ed_ret_mech_hwold_shop.setError("Please Enter ret_hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_part.equals("")) {
//                ed_ret_mech_part.setError("Please Enter ret_part");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_part.equals("PARTNER")) {
//                if (ret_mech_partname.equals("")) {
//                    ed_ret_mech_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_elect_market.equals("")) {
//                ed_ret_mech_market.setError("Please Enter ret_market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_seg_detls.equals("")) {
//                ed_ret_mech_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segments", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_pros_dels.equals("")) {
//                ed_ret_mech_pros_dels.setError("Please Enter ret_pros_dels");
                    Toast.makeText(this, "Please Select Product Deals With", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_monthly.equals("")) {
//                ed_ret_mech_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly TurnOver", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_majr_branddetls.equals("")) {
//                ed_ret_mech_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_majr_branddetls.equals("OTHER MAKES")) {
//                if (ret_mech_majr_branddetls_other.equals("")) {
//                    ed_ret_mech_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_mech_elect_dels_oebrands.equals("")) {
//                ed_ret_mech_dels_oebrands.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Deals With OE Brands", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_ltvs_pur.equals("")) {
//                ed_ret_mech_ltvs_pur.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select LTVS purchase Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_mech_ltvs_pur.equals("OTHERS")) {
//                if (ret_mech_ltvs_pur_other.equals("")) {
//                    ed_ret_mech_ltvs_pur_other.setError("Please Enter ret_ltvs_pur_other");
//                }
//            }
                else if (ret_mech_elect_spec_in.equals("")) {
//                ed_ret_mech_spec_in.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Specialist In", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_stock.equals("")) {
//                ed_ret_mech_stock.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Maintaining Stock", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_vehical.equals("")) {
//                ed_ret_mech_vehical.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Vehicle Attend in a month", Toast.LENGTH_SHORT).show();

                } else {
                    Edit_update();
                }

            } else if (ret_mech_elect_type.equals("MECHANIC,ELECTRICIAN")) {

                if (ret_mech_elect_hwold_shop.equals("")) {
//                ed_mech_elect_hwold_shop.setError("Please Enter hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_part.equals("")) {
//                ed_mech_elect_partner.setError("Please Enter partnername");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (mech_elect_partner.equals("PARTNER")) {
//                if (mech_elect_partname.equals("")) {
//                    ed_mech_elect_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_elect_market.equals("")) {
//                ed_mech_elect_market.setError("Please Enter market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_seg_detls.equals("")) {
//                ed_mech_elect_detls_in.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_spec_in.equals("")) {
//                ed_mech_elect_spec_in.setError("Please Enter specealist");
                    Toast.makeText(this, "Please Select Specialist ", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_monthly.equals("")) {
//                ed_mech_elect_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();


                } else if (ret_mech_elect_stock.equals("")) {
//                ed_mech_elect_stock.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Maintaining Stock", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_vehical.equals("")) {
//                ed_mech_elect_vehical.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Vehicle Attend in a month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_majr_branddetls.equals("")) {
//                ed_mech_elect_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals With", Toast.LENGTH_SHORT).show();

                }
//            else if (mech_elect_majr_branddetls.equals("OTHER MAKES")) {
//                if (mech_elect_majr_branddetls_other.equals("")) {
//                    ed_mech_elect_majr_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_mech_elect_pros_dels.equals("")) {
//                ed_mech_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Product Dealt With", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_stater.equals("")) {
//                ed_mech_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Started Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_alter.equals("")) {
//                ed_mech_elect_alter.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Alternator Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_wiper.equals("")) {
//                ed_mech_elect_wiper.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Wiper Motor Serviced in a Month", Toast.LENGTH_SHORT).show();


                } else if (ret_mech_elect_noofstaff.equals("")) {
//                ed_mech_elect_noofstaff.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select No Of Staff", Toast.LENGTH_SHORT).show();

                } else {
                    Edit_update();
                }
            } else if (ret_mech_elect_type.equals("RETAILER,ELECTRICIAN")) {

                if (ret_mech_elect_hwold_shop.equals("")) {
//                ed_ret_elect_hwold_shop.setError("Please Enter ret_hwold_shop");
                    Toast.makeText(this, "Please Select How Old Shop", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_part.equals("")) {
//                ed_ret_elect_part.setError("Please Enter ret_part");
                    Toast.makeText(this, "Please Select Organisation", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_elect_part.equals("PARTNER")) {
//                if (ret_elect_partname.equals("")) {
//                    ed_ret_elect_partname.setError("Please Enter ret_partname");
//                }
//            }
                else if (ret_mech_elect_market.equals("")) {
//                ed_ret_elect_market.setError("Please Enter ret_market");
                    Toast.makeText(this, "Please Select Market", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_seg_detls.equals("")) {
//                ed_ret_elect_seg_detls.setError("Please Enter ret_seg_detls");
                    Toast.makeText(this, "Please Select Segment", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_pros_dels.equals("")) {
//                ed_ret_elect_pros_dels.setError("Please Enter ret_pros_dels");
                    Toast.makeText(this, "Please Select Product Deals With", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_monthly.equals("")) {
//                ed_ret_elect_monthly.setError("Please Enter ret_monthly");
                    Toast.makeText(this, "Please Select Monthly Turn Over", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_majr_branddetls.equals("")) {
//                ed_ret_elect_majr_branddetls.setError("Please Enter ret_majr_branddetls");
                    Toast.makeText(this, "Please Select Major Brand Deals", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_elect_majr_branddetls.equals("OTHER MAKES")) {
//                if (ret_majr_elect_branddetls_other.equals("")) {
//                    ed_ret_majr_elect_branddetls_other.setError("Please Enter ret_majr_branddetls_other");
//                }
//            }
                else if (ret_mech_elect_dels_oebrands.equals("")) {
//                ed_ret_elect_dels_oebrands.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Deals With Oe Brands", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_ltvs_pur.equals("")) {
//                ed_ret_elect_ltvs_pur.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select LTVS Purchase", Toast.LENGTH_SHORT).show();

                }
//            else if (ret_elect_ltvs_pur.equals("OTHERS")) {
//                if (ret_elect_ltvs_pur_other.equals("")) {
//                    ed_ret_elect_ltvs_pur_other.setError("Please Enter ret_ltvs_pur_other");
//                }
//            }
                else if (ret_mech_elect_stater.equals("")) {
//                ed_ret_elect_stater.setError("Please Enter ret_dels_oebrands");
                    Toast.makeText(this, "Please Select Stater Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_alter.equals("")) {
//                ed_ret_elect_alter.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Alternator Motor Serviced in a Month", Toast.LENGTH_SHORT).show();

                } else if (ret_mech_elect_wiper.equals("")) {
//                ed_ret_elect_wiper.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select Wiper Motor Serviced in a Month", Toast.LENGTH_SHORT).show();


                } else if (ret_mech_elect_noofstaff.equals("")) {
//                ed_ret_elect_noofstaff.setError("Please Enter ret_ltvs_pur");
                    Toast.makeText(this, "Please Select No Of Staff", Toast.LENGTH_SHORT).show();

                } else {
                    Edit_update();
                }
            } else if (ret_mech_elect_type.equals("RETAILER,MECHANIC,ELECTRICIAN")) {
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
                } else {
                    Edit_update();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void Ep_Cancel(View view) {
        ///startActivity(new Intent(this, Home.class));
        onBackPressed();
    }

    private void Edit_update() {
        try {

            final ProgressDialog progressDialog = new ProgressDialog(Ret_Mech_ElectEditPage.this,
                    R.style.Progress);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<UpdateModel> call = service.EditPageData(id_pos, User_Name, ret_mech_elect_type, Ownername_ed, Shopname_ed, DoorNo_ed, Area_ed, Street_ed, Landmark_ed, City_ed, State_ed,
                    National_ed, Pincode_ed, Mobile1_ed, Mobile2_ed, Email_ed, GstNo_ed,
                    ret_mech_elect_hwold_shop, ret_mech_elect_part, ret_mech_elect_partname, ret_mech_elect_market,
                    ret_mech_elect_seg_detls, ret_mech_elect_pros_dels, ret_mech_elect_monthly, ret_mech_elect_majr_branddetls,
                    ret_mech_majr_elect_branddetls_other, ret_mech_elect_dels_oebrands, ret_mech_elect_ltvs_pur, ret_mech_elect_ltvs_pur_other,
                    ret_mech_elect_stater, ret_mech_elect_alter, ret_mech_elect_wiper, ret_mech_elect_noofstaff,
                    ret_mech_elect_spec_in, ret_mech_elect_stock, ret_mech_elect_vehical, edimage, edimage2, edvisitingcard, ret_mech_elect_spec_inother, saveloc, LAT, LONG, pros_dels_other_ed, dels_oebrands_other_ed);
            call.enqueue(new Callback<UpdateModel>() {
                @Override
                public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("Success")) {

                        Toast.makeText(Ret_Mech_ElectEditPage.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Ret_Mech_ElectEditPage.this, ViewAll.class));

                    } else {
                        Toast.makeText(Ret_Mech_ElectEditPage.this, "Records not Update!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateModel> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
