package com.tvs.OEDealer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.widget.EditText;

import com.tvs.R;

public class Edit_OEDealer extends AppCompatActivity {
    EditText con_et_oem, con_et_city, con_et_cmpnyname, con_et_dealercode, con_et_adrs, con_et_loc, con_et_cntctname, con_et_cntctphone,
            con_et_contctdesin, con_et_cnctemail, con_et_custmcmnt, con_et_acblcmnt, con_et_networkcmnt, con_custm, con_accbl, con_network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__oedealer);
        con_et_oem = (EditText) findViewById(R.id.con_OEM_Dealer);
        con_et_city = (EditText) findViewById(R.id.con_City);
        con_et_cmpnyname = (EditText) findViewById(R.id.con_Company_Name);
        con_et_dealercode = (EditText) findViewById(R.id.con_Dealer_Code);
        con_et_adrs = (EditText) findViewById(R.id.con_Address);
        con_et_loc = (EditText) findViewById(R.id.con_location);
        con_et_cntctname = (EditText) findViewById(R.id.con_Name);
        con_et_cntctphone = (EditText) findViewById(R.id.con_Phone);
        con_et_contctdesin = (EditText) findViewById(R.id.con_Designation);
        con_et_cnctemail = (EditText) findViewById(R.id.con_Email);
        con_et_custmcmnt = (EditText) findViewById(R.id.con_customer_Comment);
        con_et_acblcmnt = (EditText) findViewById(R.id.con_accessible_Comment);
        con_et_networkcmnt = (EditText) findViewById(R.id.con_network_Comment);
        con_custm = (EditText) findViewById(R.id.con_customer);
        con_accbl = (EditText) findViewById(R.id.con_accessible);
        con_network = (EditText) findViewById(R.id.con_network);

        con_et_oem.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_city.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_cmpnyname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_dealercode.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_adrs.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_loc.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_cntctname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_cntctphone.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        con_et_contctdesin.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_cnctemail.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_custmcmnt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_acblcmnt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_et_networkcmnt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_custm.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_accbl.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        con_network.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


    }
}
