
package Model.Login;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("CompanyName")
    private String mCompanyName;
    @SerializedName("EmailId")
    private String mEmailId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("InsertDate")
    private Object mInsertDate;
    @SerializedName("Mobile")
    private String mMobile;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Password")
    private String mPassword;
    @SerializedName("Place")
    private String mPlace;
    @SerializedName("UserImage")
    private String mUserImage;
    @SerializedName("Username")
    private String mUsername;
    @SerializedName("Usertype")
    private String mUsertype;

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String CompanyName) {
        mCompanyName = CompanyName;
    }

    public String getEmailId() {
        return mEmailId;
    }

    public void setEmailId(String EmailId) {
        mEmailId = EmailId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Object getInsertDate() {
        return mInsertDate;
    }

    public void setInsertDate(Object InsertDate) {
        mInsertDate = InsertDate;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String Mobile) {
        mMobile = Mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        mName = Name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String Password) {
        mPassword = Password;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String Place) {
        mPlace = Place;
    }

    public Object getUserImage() {
        return mUserImage;
    }

    public void setUserImage(String UserImage) {
        mUserImage = UserImage;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String Username) {
        mUsername = Username;
    }

    public String getUsertype() {
        return mUsertype;
    }

    public void setUsertype(String Usertype) {
        mUsertype = Usertype;
    }

}
