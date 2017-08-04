package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tvs.Edit.Ret_Mech_ElectEditPage;
import com.tvs.Activity.Navigation;
import com.tvs.R;

import java.util.ArrayList;
import java.util.List;

import Model.FilterData.SearchData;
import Shared.Config;

/**
 * Created by system9 on 7/11/2017.
 */

public class ViewSearch_Data extends BaseAdapter {
    List<SearchData> viewall1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int count = 1;
    Context context;
    String User_Name, User_Name_pos;
    private List<String> sino = new ArrayList<>();

    private String type;
    private String lati, longi, id_pos, type_pos, Ownername, Shopname, DoorNo, Street, Area, Landmark, City, State, National, Pincode, Mobile1, Mobile2, Email, GstNo;

    private String hwold_shop, seg_detls, pros_dels, majr_branddetls, dels_oebrands, ltvs_pur, part, monthly,
            stater, alter, wiper, noofstaff,
            spec_in, stock, vehical, market, major_branddetls_other, ltvs_pur_other, partname, image, image2,
            visiting, spec_in_other, captureloc,pros_dels_other,dels_oebrands_other;
    private LayoutInflater mInflater;

    public ViewSearch_Data(
            Context context2,
            List<SearchData> viewall1

    ) {

        this.context = context2;
        this.viewall1 = viewall1;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return viewall1.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(final int position, View child, ViewGroup parent) {

        final Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.viewadapter, null);
            holder = new Holder();
            holder.tv_sino = (TextView) child.findViewById(R.id.sino1);
            holder.tv_type = (TextView) child.findViewById(R.id.tv_type);
            holder.tv_owname = (TextView) child.findViewById(R.id.tv_owname);
            holder.tv_shopname = (TextView) child.findViewById(R.id.tv_shname);
            holder.tv_city = (TextView) child.findViewById(R.id.tv_city);
            holder.tv_state = (TextView) child.findViewById(R.id.tv_state);
            holder.bt_more = (ImageView) child.findViewById(R.id.bt_mor);
            holder.bt_edit = (ImageView) child.findViewById(R.id.bt_edit);
            holder.bt_Loc = (ImageView) child.findViewById(R.id.bt_Loc);
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        type = viewall1.get(position).getType();
        Ownername = viewall1.get(position).getOwnerName();
        Shopname = viewall1.get(position).getShopName();
        DoorNo = viewall1.get(position).getDoorNo();
        Street = viewall1.get(position).getStreet();
        Area = viewall1.get(position).getArea();
        Landmark = viewall1.get(position).getLandMark();
        City = viewall1.get(position).getCity();
        State = viewall1.get(position).getState();
        National = viewall1.get(position).getCountry();
        Pincode = viewall1.get(position).getPincode();
        Mobile1 = viewall1.get(position).getMobile();
        Mobile2 = viewall1.get(position).getPhone();
        Email = viewall1.get(position).getEmail();
        GstNo = viewall1.get(position).getGSTNumber();
        hwold_shop = viewall1.get(position).getHowOldTheShopIs();
        seg_detls = viewall1.get(position).getSegmentDeals();
        pros_dels = viewall1.get(position).getProductDealsWith();
        majr_branddetls = viewall1.get(position).getMajorBrandDealsWithElectrical();
        dels_oebrands = viewall1.get(position).getDealsWithOEBrand();
        ltvs_pur = viewall1.get(position).getLucasTVSPurchaseDealsWith();
        part = viewall1.get(position).getTypeOfOrganisation();
        monthly = viewall1.get(position).getMonthyTurnOver();
        stater = viewall1.get(position).getNoOfStarterMotorServicedInMonth();
        alter = viewall1.get(position).getNoOfAlternatorServicedInMonth();
        wiper = viewall1.get(position).getNoOfWiperMotorSevicedInMonth();
        noofstaff = viewall1.get(position).getNoOfStaff();
        spec_in = viewall1.get(position).getSpecialistIn();
        spec_in_other = viewall1.get(position).getSpecialistInOther();
        captureloc = viewall1.get(position).getCaptureLocation();
        stock = viewall1.get(position).getMaintainingStock();
        vehical = viewall1.get(position).getVehicleAlterMonth();
        market = viewall1.get(position).getMarket();
        major_branddetls_other = viewall1.get(position).getMajorBrandDealsWithElectricalother();
        ltvs_pur_other = viewall1.get(position).getLucasTVSPurchaseDealsWithOther();
        partname = viewall1.get(position).getOtherPartnersNames();
        image = viewall1.get(position).getShopImage();
        image2 = viewall1.get(position).getshopimage1();
        visiting = viewall1.get(position).getIdproof();
        type_pos = viewall1.get(position).getType();
        id_pos = viewall1.get(position).getId();
        lati = viewall1.get(position).getLatitude();
        longi = viewall1.get(position).getLangtitude();


        pros_dels_other = viewall1.get(position).getProductDealsWithOther();
        dels_oebrands_other = viewall1.get(position).getDealsWithOEBrandOthersr();
        holder.tv_type.setText(viewall1.get(position).getType());
        holder.tv_owname.setText(viewall1.get(position).getOwnerName());
        holder.tv_shopname.setText(viewall1.get(position).getShopName());
        holder.tv_city.setText(viewall1.get(position).getCity());
        holder.tv_state.setText(viewall1.get(position).getState());

        if (viewall1.size() > 0) {
            for (int i1 = 1; i1 <= viewall1.size(); i1++) {
                String sio = String.valueOf(count);
                sino.add(sio);
                count++;
            }

        }
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
        User_Name_pos = viewall1.get(position).getUserName();
        if (User_Name.equals(User_Name_pos)) {
            holder.bt_edit.setVisibility(View.VISIBLE);
        } else {
            holder.bt_edit.setVisibility(View.GONE);
        }
        holder.tv_sino.setText(sino.get(position));
        holder.bt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            context).create();

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View dialogView = inflater.inflate(R.layout.activity_more_info, null);
                    alertDialog.setView(dialogView);
                    Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);
                    final ImageView con_iv = (ImageView) dialogView.findViewById(R.id.con_image);
                    final ImageView con_iv2 = (ImageView) dialogView.findViewById(R.id.con_image2);
                    final ImageView con_iv3 = (ImageView) dialogView.findViewById(R.id.con_image3);
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

                    EditText Market = (EditText) dialogView.findViewById(R.id.con_market);
                    EditText segments = (EditText) dialogView.findViewById(R.id.con_seg_detls);
                    EditText productdeals = (EditText) dialogView.findViewById(R.id.con_pros_dels);
                    EditText monthly_turn = (EditText) dialogView.findViewById(R.id.con_monthly);
                    EditText MajorBrand = (EditText) dialogView.findViewById(R.id.con_majr_branddetls);
                    EditText majorbrandother = (EditText) dialogView.findViewById(R.id.con_branddetlsother);
                    EditText Specin_Other = (EditText) dialogView.findViewById(R.id.con_spec_in_other);

                    EditText deals_oe = (EditText) dialogView.findViewById(R.id.con_dels_oebrands);
                    EditText ltvs_purchase = (EditText) dialogView.findViewById(R.id.con_ltvs_pur);
                    EditText Ltvs_pur_other = (EditText) dialogView.findViewById(R.id.con_ltvs_pur_other);
                    EditText Stater = (EditText) dialogView.findViewById(R.id.con_starter);
                    EditText Alter = (EditText) dialogView.findViewById(R.id.con_alter);
                    EditText Wiper = (EditText) dialogView.findViewById(R.id.con_wiper);
                    EditText Noofstaff = (EditText) dialogView.findViewById(R.id.con_noofstaf);
                    EditText specin = (EditText) dialogView.findViewById(R.id.con_spec_in);
                    EditText Stock = (EditText) dialogView.findViewById(R.id.con_stock);
                    EditText vechicle = (EditText) dialogView.findViewById(R.id.con_vehical);
                    EditText ed_typ = (EditText) dialogView.findViewById(R.id.con_type);
                    EditText ed_lati = (EditText) dialogView.findViewById(R.id.con_lat);
                    EditText ed_longi = (EditText) dialogView.findViewById(R.id.con_long);
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
                    LinearLayout ll_parname = (LinearLayout) dialogView.findViewById(R.id.ll_ret_mech_elect_partner);

                    LinearLayout ll_productdeals_other = (LinearLayout) dialogView.findViewById(R.id.product_other);
                    LinearLayout ll_deals_oe_other = (LinearLayout) dialogView.findViewById(R.id.dealswithoe_other);
                    EditText productdeals_other = (EditText) dialogView.findViewById(R.id.con_pros_dels_other);
                    EditText deals_oe_other = (EditText) dialogView.findViewById(R.id.con_dels_oebrands_other);

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



                        ll_monthly.setVisibility(View.GONE);
                        ll_dealsoe.setVisibility(View.GONE);
                        ll_ltvspur.setVisibility(View.GONE);
                        ll_ltvspurother.setVisibility(View.GONE);
                        ll_special.setVisibility(View.GONE);
                        ll_specialother.setVisibility(View.GONE);
                        ll_maintaining.setVisibility(View.GONE);
                        ll_vehicle.setVisibility(View.GONE);
                        ll_deals_oe_other.setVisibility(View.GONE);
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
                        ll_dealsoe.setVisibility(View.GONE);
                        ll_ltvspur.setVisibility(View.GONE);
                        ll_ltvspurother.setVisibility(View.GONE);
                        ll_deals_oe_other.setVisibility(View.GONE);
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
                        Glide.with(context)
                                .load(imageAsBytes)
                                .into(con_iv);
                    } else {
                        con_iv.setVisibility(View.GONE);
                    }
                    if (!image2.equals("")) {
                        byte[] imageAsBytes = Base64.decode(image2.getBytes(), Base64.DEFAULT);
                        Glide.with(context)
                                .load(imageAsBytes)
                                .into(con_iv2);
                    } else {
                        con_iv.setVisibility(View.GONE);
                    }
                    if (!visiting.equals("")) {
                        byte[] imageAsBytes = Base64.decode(visiting.getBytes(), Base64.DEFAULT);
                        Glide.with(context)
                                .load(imageAsBytes)
                                .into(con_iv3);
                    } else {
                        con_iv.setVisibility(View.GONE);
                    }
                    ed_lati.setText(lati);
                    ed_longi.setText(longi);
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
                    Specin_Other.setText(spec_in_other);
                    specin.setText(spec_in);
                    Stock.setText(stock);
                    vechicle.setText(vehical);

                    Stater.setText(stater);
                    Alter.setText(alter);
                    Wiper.setText(wiper);
                    Noofstaff.setText(noofstaff);


                    Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Ownername = viewall1.get(position).getOwnerName();
                    Shopname = viewall1.get(position).getShopName();
                    DoorNo = viewall1.get(position).getDoorNo();
                    Street = viewall1.get(position).getStreet();
                    Area = viewall1.get(position).getArea();
                    Landmark = viewall1.get(position).getLandMark();
                    City = viewall1.get(position).getCity();
                    State = viewall1.get(position).getState();
                    National = viewall1.get(position).getCountry();
                    Pincode = viewall1.get(position).getPincode();
                    Mobile1 = viewall1.get(position).getMobile();
                    Mobile2 = viewall1.get(position).getPhone();
                    Email = viewall1.get(position).getEmail();
                    GstNo = viewall1.get(position).getGSTNumber();
                    hwold_shop = viewall1.get(position).getHowOldTheShopIs();
                    seg_detls = viewall1.get(position).getSegmentDeals();
                    pros_dels = viewall1.get(position).getProductDealsWith();
                    majr_branddetls = viewall1.get(position).getMajorBrandDealsWithElectrical();
                    dels_oebrands = viewall1.get(position).getDealsWithOEBrand();
                    ltvs_pur = viewall1.get(position).getLucasTVSPurchaseDealsWith();
                    part = viewall1.get(position).getTypeOfOrganisation();
                    monthly = viewall1.get(position).getMonthyTurnOver();
                    stater = viewall1.get(position).getNoOfStarterMotorServicedInMonth();
                    alter = viewall1.get(position).getNoOfAlternatorServicedInMonth();
                    wiper = viewall1.get(position).getNoOfWiperMotorSevicedInMonth();
                    noofstaff = viewall1.get(position).getNoOfStaff();
                    spec_in = viewall1.get(position).getSpecialistIn();
                    spec_in_other= viewall1.get(position).getSpecialistInOther();
                    captureloc= viewall1.get(position).getCaptureLocation();
                    stock = viewall1.get(position).getMaintainingStock();
                    vehical = viewall1.get(position).getVehicleAlterMonth();
                    market = viewall1.get(position).getMarket();
                    major_branddetls_other = viewall1.get(position).getMajorBrandDealsWithElectricalother();
                    ltvs_pur_other = viewall1.get(position).getLucasTVSPurchaseDealsWithOther();
                    partname = viewall1.get(position).getOtherPartnersNames();
                    image = viewall1.get(position).getShopImage();
                    image2 = viewall1.get(position).getshopimage1();
                    visiting = viewall1.get(position).getIdproof();
                    type_pos = viewall1.get(position).getType();
                    id_pos = viewall1.get(position).getId();
                    lati = viewall1.get(position).getLatitude();
                    longi = viewall1.get(position).getLangtitude();
                    pros_dels_other = viewall1.get(position).getProductDealsWithOther();
                    dels_oebrands_other = viewall1.get(position).getDealsWithOEBrandOthersr();

                    sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("KEY_VA_id_pos", id_pos);
                    editor.putString("KEY_VA_Ownername", Ownername);
                    editor.putString("KEY_VA_Shopname", Shopname);
                    editor.putString("KEY_VA_DoorNo", DoorNo);
                    editor.putString("KEY_VA_Street", Street);
                    editor.putString("KEY_VA_Area", Area);
                    editor.putString("KEY_VA_Landmark", Landmark);
                    editor.putString("KEY_VA_City", City);
                    editor.putString("KEY_VA_State", State);
                    editor.putString("KEY_VA_National", National);
                    editor.putString("KEY_VA_Pincode", Pincode);
                    editor.putString("KEY_VA_Ownername", Ownername);
                    editor.putString("KEY_VA_Mobile1", Mobile1);
                    editor.putString("KEY_VA_Mobile2", Mobile2);
                    editor.putString("KEY_VA_Email", Email);
                    editor.putString("KEY_VA_GstNo", GstNo);
                    editor.putString("KEY_VA_type_pos", type_pos);
                    editor.putString("KEY_VA_hwold_shop", hwold_shop);
                    editor.putString("KEY_VA_part", part);
                    editor.putString("KEY_VA_partname", partname);
                    editor.putString("KEY_VA_seg_detls", seg_detls);
                    editor.putString("KEY_VA_Ownername", Ownername);
                    editor.putString("KEY_VA_pros_dels", pros_dels);
                    editor.putString("KEY_VA_majr_branddetls", majr_branddetls);
                    editor.putString("KEY_VA_major_branddetls_other", major_branddetls_other);
                    editor.putString("KEY_VA_dels_oebrands", dels_oebrands);
                    editor.putString("KEY_VA_ltvs_pur", ltvs_pur);
                    editor.putString("KEY_VA_ltvs_pur_other", ltvs_pur_other);

                    editor.putString("KEY_VA_spec_in_other", spec_in_other);
                    editor.putString("KEY_VA_captureloc", captureloc);
                    editor.putString("KEY_VA_monthly", monthly);
                    editor.putString("KEY_VA_stater", stater);
                    editor.putString("KEY_VA_alter", alter);
                    editor.putString("KEY_VA_wiper", wiper);
                    editor.putString("KEY_VA_noofstaff", noofstaff);
                    editor.putString("KEY_VA_spec_in", spec_in);
                    editor.putString("KEY_VA_stock", stock);
                    editor.putString("KEY_VA_vehical", vehical);
                    editor.putString("KEY_VA_market", market);
                    editor.putString("KEY_VA_image", image);
                    editor.putString("KEY_VA_shopimage2", image2);
                    editor.putString("KEY_VA_visitingimage3", visiting);
                    editor.putString("KEY_VA_lati", lati);
                    editor.putString("KEY_VA_longi", longi);
                    editor.putString("KEY_VA_pros_dels_other", pros_dels_other);
                    editor.putString("KEY_VA_dels_oebrands_other", dels_oebrands_other);


                    editor.apply();

                    Intent i = new Intent(context, Ret_Mech_ElectEditPage.class);
                    context.startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.bt_Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(context, Navigation.class);

                    captureloc= viewall1.get(position).getCaptureLocation();
                    lati = viewall1.get(position).getLatitude();
                    longi = viewall1.get(position).getLangtitude();
                    sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("KEY_VA_lati", lati);
                    editor.putString("KEY_VA_longi", longi);
                    editor.putString("KEY_VA_captureloc", captureloc);
                    editor.apply();
                    context.startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return child;
    }

    private class Holder {
        TextView tv_sino, tv_type, tv_owname, tv_shopname, tv_city, tv_state;
        ImageView bt_more, bt_edit, bt_Loc;


    }
}