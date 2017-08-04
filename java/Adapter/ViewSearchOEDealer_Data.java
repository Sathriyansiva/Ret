package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tvs.Activity.Navigation;
import com.tvs.OEDealer.Edit_OEDealer;
import com.tvs.R;

import java.util.ArrayList;
import java.util.List;

import Model.ViewOEdealer.ViewOEdata;
import Model.ViewSearchOEdealer.FilterOEdata;
import Shared.Config;

/**
 * Created by system9 on 7/26/2017.
 */

public class ViewSearchOEDealer_Data extends BaseAdapter {
    private List<FilterOEdata> viewall;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int count = 1;
    Context context;
    String User_Name, User_Name_pos;
    private static final int PERMISSION_REQUEST = 100;

    private List<String> sino = new ArrayList<>();

    String oem, city, cmpnyname, dealercode, adrs, loc, cntctname,
            cntctphone, contctdesin, cnctemail, custm, accbl, network, custmcmnt, acblcmnt, networkcmnt,lat,longi,id_oepos;
    private LayoutInflater mInflater;

    public ViewSearchOEDealer_Data(
            Context context2,
            List<FilterOEdata> viewall

    ) {

        this.context = context2;
        this.viewall = viewall;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return viewall.size();
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
            child = layoutInflater.inflate(R.layout.viewoe_dealeradapter, null);
            holder = new Holder();
            holder.tv_sino= (TextView) child.findViewById(R.id.sino1);
            holder.tv_oem = (TextView) child.findViewById(R.id.tv_oem);
            holder.tv_city = (TextView) child.findViewById(R.id.tv_city);
            holder.tv_cname = (TextView) child.findViewById(R.id.tv_cname);
            holder.tv_dcode = (TextView) child.findViewById(R.id.tv_decode);
            holder.bt_more = (ImageView) child.findViewById(R.id.bt_mor);
            holder.bt_edit = (ImageView) child.findViewById(R.id.bt_edit);
            holder.bt_Loc = (ImageView) child.findViewById(R.id.bt_Loc);
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }


        holder.tv_oem.setText(viewall.get(position).getOEMName());
        holder.tv_city.setText(viewall.get(position).getCity());
        holder.tv_cname.setText(viewall.get(position).getCompanyName());
        holder.tv_dcode.setText(viewall.get(position).getDealerCode());

        if (viewall.size() > 0) {
            for (int i1 = 1; i1 <= viewall.size(); i1++) {
                String sio = String.valueOf(count);
                sino.add(sio);
                count++;
            }
        }
        holder.tv_sino.setText(sino.get(position));
        oem = viewall.get(position).getOEMName();
        city = viewall.get(position).getCity();
        cmpnyname = viewall.get(position).getCompanyName();
        dealercode = viewall.get(position).getDealerCode();
        adrs = viewall.get(position).getAddress();
        loc = viewall.get(position).getLocation();
        cntctname = viewall.get(position).getCName();
        cntctphone = viewall.get(position).getCPhone();
        contctdesin = viewall.get(position).getCDesgination();
        cnctemail = viewall.get(position).getCEmail();
        custm = viewall.get(position).getCustomerservice();
        accbl = viewall.get(position).getAccessiblemanner();
        network = viewall.get(position).getNetworkorservices();
        custmcmnt = viewall.get(position).getCustomerserviceComments();
        acblcmnt = viewall.get(position).getAccessiblemannerComments();
        networkcmnt = viewall.get(position).getNetworkorservicesComments();
        lat= viewall.get(position).getLatitude();
        longi= viewall.get(position).getLongitude();

        holder.bt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            context).create();

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View dialogView = inflater.inflate(R.layout.moreinfo_oedealer, null);
                    alertDialog.setView(dialogView);
                    Button Cancel = (Button) dialogView.findViewById(R.id.con_cancel);

                    EditText con_et_oem = (EditText) dialogView.findViewById(R.id.con_OEM_Dealer);
                    EditText con_et_city = (EditText) dialogView.findViewById(R.id.con_City);
                    EditText con_et_cmpnyname = (EditText) dialogView.findViewById(R.id.con_Company_Name);
                    EditText con_et_dealercode = (EditText) dialogView.findViewById(R.id.con_Dealer_Code);
                    EditText con_et_adrs = (EditText) dialogView.findViewById(R.id.con_Address);
                    EditText con_et_loc = (EditText) dialogView.findViewById(R.id.con_location);
                    EditText con_et_cntctname = (EditText) dialogView.findViewById(R.id.con_Name);
                    EditText con_et_cntctphone = (EditText) dialogView.findViewById(R.id.con_Phone);
                    EditText con_et_contctdesin = (EditText) dialogView.findViewById(R.id.con_Designation);
                    EditText con_et_cnctemail = (EditText) dialogView.findViewById(R.id.con_Email);
                    EditText con_et_custmcmnt = (EditText) dialogView.findViewById(R.id.con_customer_Comment);
                    EditText con_et_acblcmnt = (EditText) dialogView.findViewById(R.id.con_accessible_Comment);
                    EditText con_et_networkcmnt = (EditText) dialogView.findViewById(R.id.con_network_Comment);
                    EditText con_custm = (EditText) dialogView.findViewById(R.id.con_customer);
                    EditText con_accbl = (EditText) dialogView.findViewById(R.id.con_accessible);
                    EditText con_network = (EditText) dialogView.findViewById(R.id.con_network);

                    con_et_oem.setText(oem);
                    con_et_city.setText(city);
                    con_et_cmpnyname.setText(cmpnyname);
                    con_et_dealercode.setText(dealercode);
                    con_et_adrs.setText(adrs);
                    con_et_loc.setText(loc);
                    con_et_cntctname.setText(cntctname);
                    con_et_cntctphone.setText(cntctphone);
                    con_et_contctdesin.setText(contctdesin);
                    con_et_cnctemail.setText(cnctemail);
                    con_et_custmcmnt.setText(custmcmnt);
                    con_et_acblcmnt.setText(acblcmnt);
                    con_et_networkcmnt.setText(networkcmnt);
                    con_custm.setText(custm);
                    con_accbl.setText(accbl);
                    con_network.setText(network);


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
                    id_oepos= viewall.get(position).getRegId();
                    oem = viewall.get(position).getOEMName();
                    city = viewall.get(position).getCity();
                    cmpnyname = viewall.get(position).getCompanyName();
                    dealercode = viewall.get(position).getDealerCode();
                    adrs = viewall.get(position).getAddress();
                    loc = viewall.get(position).getLocation();
                    cntctname = viewall.get(position).getCName();
                    cntctphone = viewall.get(position).getCPhone();
                    contctdesin = viewall.get(position).getCDesgination();
                    cnctemail = viewall.get(position).getCEmail();
                    custm = viewall.get(position).getCustomerservice();
                    accbl = viewall.get(position).getAccessiblemanner();
                    network = viewall.get(position).getNetworkorservices();
                    custmcmnt = viewall.get(position).getCustomerserviceComments();
                    acblcmnt = viewall.get(position).getAccessiblemannerComments();
                    networkcmnt = viewall.get(position).getNetworkorservicesComments();
                    lat= viewall.get(position).getLatitude();
                    longi= viewall.get(position).getLongitude();

                    sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("KEY_VA_id_oepos", id_oepos);
                    editor.putString("KEY_VA_oem", oem);
                    editor.putString("KEY_VA_city", city);
                    editor.putString("KEY_VA_cmpnyname", cmpnyname);
                    editor.putString("KEY_VA_dealercode", dealercode);
                    editor.putString("KEY_VA_adrs", adrs);
                    editor.putString("KEY_VA_loc", loc);
                    editor.putString("KEY_VA_cntctname", cntctname);
                    editor.putString("KEY_VA_cntctphone", cntctphone);
                    editor.putString("KEY_VA_contctdesin", contctdesin);
                    editor.putString("KEY_VA_cnctemail", cnctemail);
                    editor.putString("KEY_VA_custm", custm);
                    editor.putString("KEY_VA_accbl", accbl);
                    editor.putString("KEY_VA_network", network);
                    editor.putString("KEY_VA_custmcmnt", custmcmnt);
                    editor.putString("KEY_VA_acblcmnt", acblcmnt);
                    editor.putString("KEY_VA_networkcmnt", networkcmnt);
                    editor.putString("KEY_VA_lat", lat);
                    editor.putString("KEY_VA_longi", longi);


                    editor.apply();

                    Intent i = new Intent(context, Edit_OEDealer.class);
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
                    loc = viewall.get(position).getLocation();
                    lat= viewall.get(position).getLatitude();
                    longi= viewall.get(position).getLongitude();

                    sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("KEY_VA_lati", lat);
                    editor.putString("KEY_VA_longi", longi);
                    editor.putString("KEY_VA_captureloc", loc);
                    editor.apply();
//                    GetPermission();
                    Intent i = new Intent(context, Navigation.class);
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
        TextView tv_sino, tv_oem, tv_cname, tv_dcode, tv_city, tv_address;
        ImageView bt_more, bt_edit, bt_Loc;


    }

}
