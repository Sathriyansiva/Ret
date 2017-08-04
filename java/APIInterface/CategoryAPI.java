package APIInterface;

import Model.AddOEdealer.RegOEdealer;
import Model.ChangePass.Changepassword;
import Model.EditPageModel.UpdateModel;
import Model.EditProfileModel.Update;
import Model.FilterData.Filter;
import Model.Forgot.Forgot;
import Model.LoginRegistermodel.Result;
import Model.Predictive.Citypredictive;
import Model.Predictive.StatePridictive;
import Model.Register.Register;
import Model.ViewAllData.View;
import Model.ViewOEdealer.ViewOE;
import Model.ViewSearchOEdealer.FilterOe;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by system9 on 6/30/2017.
 */

public interface CategoryAPI {
    @FormUrlEncoded
    @POST("UpdateSalesExecutive")
    Call<Result> login_register(

            @Field("Name") String Name,
            @Field("Username") String Code,
            @Field("Mobile") String Mobile_Number,
            @Field("CompanyName") String Company_Name,
            @Field("EmailId") String EmailId


    );

    @FormUrlEncoded
    @POST("LoginSales")
    Call<Model.Login.Result> login(

            @Field("Username") String User_Name,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @POST("getForgotPassword")
    Call<Forgot> forgot(

            @Field("EmailId") String EmailId

    );

    @FormUrlEncoded
    @POST("GetRegisterDetails")
    Call<Register> register_ret(
            @Field("UserName") String UserName,
            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("ProductDealsWith") String ProductDealsWith,
            @Field("MonthyTurnOver") String MonthyTurnOver,
            @Field("MajorBrandDealsWithElectrical") String MajorBrandDealsWithElectrical,
            @Field("MajorBrandDealsWithElectricalother") String MajorBrandDealsWithElectrical_other,
            @Field("DealsWithOEBrand") String DealsWith_OEBrand,
            @Field("LucasTVSPurchaseDealsWith") String LucasTVSPurchaseDealsWith,
            @Field("LucasTVSPurchaseDealsWithOther") String LucasTVSPurchaseDealsWith_Other,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("CaptureLocation") String CaptureLocation,
            @Field("ProductDealsWithOther") String ProductDealsWithOther,
            @Field("DealsWithOEBrandOthers") String DealsWithOEBrandOthers

    );

    @FormUrlEncoded
    @POST("GetRegisterDetails")
    Call<Register> register_mech(
            @Field("UserName") String UserName,

            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("MonthyTurnOver") String MonthyTurnOver,

            @Field("SpecialistIn") String SpecialistIn,
            @Field("MaintainingStock") String MaintainingStock,
            @Field("VehicleAlterMonth") String VehicleAlterMonth,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("SpecialistInOther") String SpecialistInOther,
            @Field("CaptureLocation") String CaptureLocation
    );

    @FormUrlEncoded
    @POST("GetRegisterDetails")
    Call<Register> register_elect(
            @Field("UserName") String UserName,

            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("ProductDealsWith") String ProductDealsWith,
            @Field("MajorBrandDealsWithElectrical") String MajorBrandDealsWithElectrical,
            @Field("MajorBrandDealsWithElectricalother") String MajorBrandDealsWithElectrical_other,

            @Field("NoOfStarterMotorServicedInMonth") String NoOfStarterMotorServicedInMonth,
            @Field("NoOfAlternatorServicedInMonth") String NoOfAlternatorServicedInMonth,
            @Field("NoOfWiperMotorSevicedInMonth") String NoOfWiperMotorSevicedInMonth,
            @Field("NoOfStaff") String NoOfStaff,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("CaptureLocation") String CaptureLocation,
            @Field("ProductDealsWithOther") String ProductDealsWithOther

            );

    @FormUrlEncoded
    @POST("GetRegisterDetails")
    Call<Register> register_ret_mech(
            @Field("UserName") String UserName,

            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("ProductDealsWith") String ProductDealsWith,
            @Field("MonthyTurnOver") String MonthyTurnOver,
            @Field("MajorBrandDealsWithElectrical") String MajorBrandDealsWithElectrical,
            @Field("MajorBrandDealsWithElectricalother") String MajorBrandDealsWithElectrical_other,
            @Field("DealsWithOEBrand") String DealsWith_OEBrand,
            @Field("LucasTVSPurchaseDealsWith") String LucasTVSPurchaseDealsWith,
            @Field("LucasTVSPurchaseDealsWithOther") String LucasTVSPurchaseDealsWith_Other,

            @Field("SpecialistIn") String SpecialistIn,
            @Field("MaintainingStock") String MaintainingStock,
            @Field("VehicleAlterMonth") String VehicleAlterMonth,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("SpecialistInOther") String SpecialistInOther,
            @Field("CaptureLocation") String CaptureLocation,
            @Field("ProductDealsWithOther") String ProductDealsWithOther,
            @Field("DealsWithOEBrandOthers") String DealsWithOEBrandOthers

    );

    @FormUrlEncoded
    @POST("GetRegisterDetails")
    Call<Register> register_mech_elect(
            @Field("UserName") String UserName,

            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("MonthyTurnOver") String MonthyTurnOver,
            @Field("MajorBrandDealsWithElectrical") String MajorBrandDealsWithElectrical,
            @Field("MajorBrandDealsWithElectricalother") String MajorBrandDealsWithElectrical_other,
            @Field("NoOfStarterMotorServicedInMonth") String NoOfStarterMotorServicedInMonth,
            @Field("ProductDealsWith") String mech_elect_pros_dels,
            @Field("NoOfAlternatorServicedInMonth") String NoOfAlternatorServicedInMonth,
            @Field("NoOfWiperMotorSevicedInMonth") String NoOfWiperMotorSevicedInMonth,
            @Field("NoOfStaff") String NoOfStaff,
            @Field("SpecialistIn") String SpecialistIn,
            @Field("MaintainingStock") String MaintainingStock,
            @Field("VehicleAlterMonth") String VehicleAlterMonth,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("SpecialistInOther") String SpecialistInOther,
            @Field("CaptureLocation") String CaptureLocation,
            @Field("ProductDealsWithOther") String ProductDealsWithOther

    );

    @FormUrlEncoded
    @POST("GetRegisterDetails")
    Call<Register> register_ret_elect(
            @Field("UserName") String UserName,

            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("ProductDealsWith") String ProductDealsWith,
            @Field("MonthyTurnOver") String MonthyTurnOver,
            @Field("MajorBrandDealsWithElectrical") String MajorBrandDealsWithElectrical,
            @Field("MajorBrandDealsWithElectricalother") String MajorBrandDealsWithElectrical_other,
            @Field("DealsWithOEBrand") String DealsWith_OEBrand,
            @Field("LucasTVSPurchaseDealsWith") String LucasTVSPurchaseDealsWith,
            @Field("LucasTVSPurchaseDealsWithOther") String LucasTVSPurchaseDealsWith_Other,
            @Field("NoOfStarterMotorServicedInMonth") String NoOfStarterMotorServicedInMonth,
            @Field("NoOfAlternatorServicedInMonth") String NoOfAlternatorServicedInMonth,
            @Field("NoOfWiperMotorSevicedInMonth") String NoOfWiperMotorSevicedInMonth,
            @Field("NoOfStaff") String NoOfStaff,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("CaptureLocation") String CaptureLocation,
            @Field("ProductDealsWithOther") String ProductDealsWithOther,
            @Field("DealsWithOEBrandOthers") String DealsWithOEBrandOthers
    );

    @FormUrlEncoded
    @POST("GetRegisterDetails")
    Call<Register> register_ret_mech_elect(
            @Field("UserName") String UserName,
            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("ProductDealsWith") String ProductDealsWith,
            @Field("MonthyTurnOver") String MonthyTurnOver,
            @Field("MajorBrandDealsWithElectrical") String MajorBrandDealsWithElectrical,
            @Field("MajorBrandDealsWithElectricalother") String MajorBrandDealsWithElectrical_other,
            @Field("DealsWithOEBrand") String DealsWith_OEBrand,
            @Field("LucasTVSPurchaseDealsWith") String LucasTVSPurchaseDealsWith,
            @Field("LucasTVSPurchaseDealsWithOther") String LucasTVSPurchaseDealsWith_Other,
            @Field("NoOfStarterMotorServicedInMonth") String NoOfStarterMotorServicedInMonth,
            @Field("NoOfAlternatorServicedInMonth") String NoOfAlternatorServicedInMonth,
            @Field("NoOfWiperMotorSevicedInMonth") String NoOfWiperMotorSevicedInMonth,
            @Field("NoOfStaff") String NoOfStaff,
            @Field("SpecialistIn") String SpecialistIn,
            @Field("MaintainingStock") String MaintainingStock,
            @Field("VehicleAlterMonth") String VehicleAlterMonth,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("SpecialistInOther") String SpecialistInOther,
            @Field("CaptureLocation") String CaptureLocation,
            @Field("ProductDealsWithOther") String ProductDealsWithOther,
            @Field("DealsWithOEBrandOthers") String DealsWithOEBrandOthers


    );


    @GET("ViewRegisterDetails")
    Call<View> getalldetails();

    @FormUrlEncoded
    @POST("SearchRegister")
    Call<Filter> Search(

            @Field("Type") String User_Type,
            @Field("Citypredictive") String state,
            @Field("City") String city


    );

    @FormUrlEncoded
    @POST("EditProfile")
    Call<Update> EditData(

            @Field("Username") String Username,
            @Field("Password") String Password,
            @Field("Type") String Type,
            @Field("Mobile") String Mobile,
            @Field("CompanyName") String CompanyName,
            @Field("Place") String Place,
            @Field("Name") String Name,
            @Field("EmailId") String EmailId


    );

    @FormUrlEncoded
    @POST("ChangePassword")
    Call<Changepassword> ChangePASS(

            @Field("UserName") String UserName,
            @Field("Password") String Password,
            @Field("NewPassword") String NewPassword


    );

    @FormUrlEncoded
    @POST("EditRegister")
    Call<UpdateModel> EditPageData(
            @Field("id") String id,
            @Field("UserName") String UserName,
            @Field("Type") String Type,
            @Field("OwnerName") String Owner_Name,
            @Field("ShopName") String Shop_Name,
            @Field("DoorNo") String DoorNo,
            @Field("Area") String Area,
            @Field("Street") String Street,
            @Field("LandMark") String LandMark,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("Pincode") String Pincode,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("GSTNumber") String GSTNumber,
            @Field("HowOldTheShopIs") String HowOldThe_Shop,
            @Field("TypeOfOrganisation") String TypeOf_Organisation,
            @Field("OtherPartnersNames") String Other_PartnersNames,
            @Field("Market") String Market,
            @Field("SegmentDeals") String SegmentDeals,
            @Field("ProductDealsWith") String ProductDealsWith,
            @Field("MonthyTurnOver") String MonthyTurnOver,
            @Field("MajorBrandDealsWithElectrical") String MajorBrandDealsWithElectrical,
            @Field("MajorBrandDealsWithElectricalother") String MajorBrandDealsWithElectrical_other,
            @Field("DealsWithOEBrand") String DealsWith_OEBrand,
            @Field("LucasTVSPurchaseDealsWith") String LucasTVSPurchaseDealsWith,
            @Field("LucasTVSPurchaseDealsWithOther") String LucasTVSPurchaseDealsWith_Other,
            @Field("NoOfStarterMotorServicedInMonth") String NoOfStarterMotorServicedInMonth,
            @Field("NoOfAlternatorServicedInMonth") String NoOfAlternatorServicedInMonth,
            @Field("NoOfWiperMotorSevicedInMonth") String NoOfWiperMotorSevicedInMonth,
            @Field("NoOfStaff") String NoOfStaff,
            @Field("SpecialistIn") String SpecialistIn,
            @Field("MaintainingStock") String MaintainingStock,
            @Field("VehicleAlterMonth") String VehicleAlterMonth,
            @Field("ShopImage") String ShopImage,
            @Field("shopimage1") String shopimage1,
            @Field("Idproof") String Visitingcaed,
            @Field("SpecialistInOther") String SpecialistInOther,
            @Field("CaptureLocation") String CaptureLocation,
            @Field("Latitude") String Latitude,
            @Field("Langtitude") String Langtitude,
            @Field("ProductDealsWithOther") String ProductDealsWithOther,
            @Field("DealsWithOEBrandOthers") String DealsWithOEBrandOthers

    );

    @FormUrlEncoded
    @POST("GetRegisterOEDealerDetails")
    Call<RegOEdealer> addOE_dealer(
            @Field("OEMName") String OEMName,
            @Field("City") String City,
            @Field("CompanyName") String CompanyName,
            @Field("DealerCode") String DealerCode,
            @Field("Address") String Address,
            @Field("Location") String Location,
            @Field("C_Name") String C_Name,
            @Field("C_Phone") String C_Phone,
            @Field("C_Desgination") String C_Desgination,
            @Field("C_Email") String C_Email,
            @Field("customerservice") String customerservice,
            @Field("accessiblemanner") String accessiblemanner,
            @Field("networkorservices") String networkorservices,
            @Field("customerserviceComments") String customerserviceComments,
            @Field("accessiblemannerComments") String accessiblemannerComments,
            @Field("networkorservicesComments") String networkorservicesComments,
            @Field("Latitude") String Latitude,
            @Field("Longitude") String Longitude

    );

    @GET("ViewRegisterOEDealerDetails")
    Call<ViewOE> getallOEdetails();

    @GET("GetCityDetails")
    Call<Citypredictive> getallCity();

    @FormUrlEncoded
    @POST("GetState")
    Call<StatePridictive> getstate(
            @Field("City") String City
    );
    @FormUrlEncoded
    @POST("SearchRegisterOEDealer")
    Call<FilterOe> SearchOE(

            @Field("City") String city


    );
}
