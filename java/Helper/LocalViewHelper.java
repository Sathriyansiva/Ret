package Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by system9 on 7/14/2017.
 */

public class LocalViewHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME = "LucasTVS";
    public static final String TABLE_NAME = "Register_Table1";

    public static final String KEY_ID = "id";

    public static final String KEY_UserName = "UserName";

    public static final String KEY_Type= "User_Type";

    public static final String KEY_ShopName = "ShopName";

    public static final String KEY_DoorNo = "DoorNo";

    public static final String KEY_Street = "Street";

    public static final String KEY_Area = "Area";

    public static final String KEY_LandMark = "LandMark";

    public static final String KEY_City = "City";

    public static final String KEY_State = "Citypredictive";

    public static final String KEY_Country = "Country";

    public static final String KEY_Pincode = "Pincode";

    public static final String KEY_Mobile = "Mobile";

    public static final String KEY_Phone = "Phone";
    public static final String KEY_Email = "Email";

    public static final String KEY_GSTNumber = "GSTNumber";

    public static final String KEY_OwnerName = "OwnerName";

    public static final String KEY_Dealsin = "Dealsin";

    public static final String KEY_HowOldTheShopIs = "HowOldTheShopIs";

    public static final String KEY_TypeOfOrganisation = "TypeOfOrganisation";

    public static final String KEY_Market = "Market";

    public static final String KEY_OtherPartnersNames = "OtherPartnersNames";

    public static final String KEY_SegmentDeals = "SegmentDeals";

    public static final String KEY_ProductDealsWith = "ProductDealsWith";

    public static final String KEY_MonthyTurnOver = "MonthyTurnOver";

    public static final String KEY_MajorBrandDealsWithElectrical = "MajorBrandDealsWithElectrical";

    public static final String KEY_DealsWithOEBrand = "DealsWithOEBrand";

    public static final String KEY_LucasTVSPurchaseDealsWith = "LucasTVSPurchaseDealsWith";

    public static final String KEY_LucasTVSPurchaseDealsWithOther = "LucasTVSPurchaseDealsWithOther";
    public static final String KEY_NoOfStarterMotorServicedInMonth = "NoOfStarterMotorServicedInMonth";

    public static final String KEY_MajorBrandDealsWithElectricalother = "MajorBrandDealsWithElectricalother";

    public static final String KEY_NoOfAlternatorServicedInMonth = "NoOfAlternatorServicedInMonth";

    public static final String KEY_NoOfWiperMotorSevicedInMonth = "NoOfWiperMotorSevicedInMonth";

    public static final String KEY_NoOfStaff = "NoOfStaff";

    public static final String KEY_SpecialistIn = "SpecialistIn";

    public static final String KEY_MaintainingStock = "MaintainingStock";

    public static final String KEY_VehicleAlterMonth= "VehicleAlterMonth";

    public static final String KEY_Latitude = "Latitude";

    public static final String KEY_Longitude = "Longitude";

    public static final String KEY_ShopImage = "ShopImage";

    public static final String KEY_ShopImage2 = "ShopImage2";

    public static final String KEY_VisitingCard = "VisitingCard";

    public static final String KEY_Specialist_other = "Specialist_other";

    public static final String KEY_Location = "Location";

    public static final String KEY_ProductDealsOther = "ProductDealsOther";

    public static final String KEY_DealsOEBrandsOther = "DealsOEBrandsOther";

    public LocalViewHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+KEY_Type+" VARCHAR," +
                ""+KEY_UserName+" VARCHAR, "+KEY_ShopName+" VARCHAR, "+KEY_DoorNo+" VARCHAR, "+KEY_Street+" VARCHAR," +
                " "+KEY_Area+" VARCHAR, "+KEY_LandMark+" VARCHAR, "+KEY_City+" VARCHAR ,"+KEY_State+" VARCHAR, "+KEY_Country+" VARCHAR, " +
                ""+KEY_Pincode+" VARCHAR, "+KEY_Mobile +"VARCHAR, "+KEY_Phone +"VARCHAR, "+KEY_Email +"VARCHAR, " +
                ""+KEY_GSTNumber +"VARCHAR,"+KEY_OwnerName +"VARCHAR,"+KEY_Dealsin +"VARCHAR, "+KEY_HowOldTheShopIs +"VARCHAR," +
                " "+KEY_TypeOfOrganisation +"VARCHAR, "+KEY_Market +"VARCHAR,"+KEY_OtherPartnersNames +"VARCHAR," +
                ""+KEY_SegmentDeals +"VARCHAR, "+KEY_ProductDealsWith+" VARCHAR, "+KEY_MonthyTurnOver +"VARCHAR, " +
                ""+KEY_MajorBrandDealsWithElectrical+" VARCHAR,"+KEY_DealsWithOEBrand +"VARCHAR, " +
                ""+KEY_LucasTVSPurchaseDealsWith +"VARCHAR, "+KEY_LucasTVSPurchaseDealsWithOther +"VARCHAR, " +
                ""+KEY_NoOfStarterMotorServicedInMonth+" VARCHAR, "+KEY_MajorBrandDealsWithElectricalother +"VARCHAR," +
                " "+KEY_NoOfAlternatorServicedInMonth +"VARCHAR, "+KEY_NoOfWiperMotorSevicedInMonth +"VARCHAR," +
                ""+KEY_NoOfStaff +"VARCHAR, "+KEY_SpecialistIn +"VARCHAR,"+KEY_MaintainingStock +"VARCHAR," +
                ""+KEY_VehicleAlterMonth+" VARCHAR, "+KEY_ShopImage+" VARCHAR, "+KEY_ShopImage2+" VARCHAR,"+KEY_VisitingCard+" VARCHAR,"+KEY_Latitude +"VARCHAR, "+KEY_Longitude +"VARCHAR, "+KEY_Specialist_other +"VARCHAR, "+KEY_ProductDealsOther +"VARCHAR, "+KEY_DealsOEBrandsOther +"VARCHAR);)";
        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Register_Table1");
        onCreate(db);

    }

}
